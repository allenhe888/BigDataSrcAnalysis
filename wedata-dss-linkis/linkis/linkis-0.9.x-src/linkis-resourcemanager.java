


RMReceiver.receiveAndReply(message: Any, sender: Sender){
	case RequestResource(moduleInstance, user, creator, resource) =>
      rm.requestResource(moduleInstance, user, creator, resource)
	  
    case RequestResourceAndWait(moduleInstance, user, creator, resource, waitTime) =>
      rm.requestResource(moduleInstance, user, creator, resource, waitTime) {//DefaultResourceManager.requestResource()
		if (hasModuleInstanceEvent(moduleInstance)) {}
		if (hasUserEvent(user, moduleInstance.getApplicationName)) {}
		val reqService = getRequestResourceService(moduleInstance)
		var canRequest = reqService.canRequest(moduleInstance, user, creator, resource);{//DriverAndYarnReqResourceService.canRequest()
			var canSuperRequest = super.canRequest(moduleInstance, user, creator, requestResource);{//RequestResourceService
				if ((moduleLeftResource - requestResource) < protectedResource) {
					throw new RMWarnException(111005, s"${generateNotEnoughMessage(requestResource, moduleLeftResource)}")
				}
				val (moduleAvailableResource, creatorAvailableResource) = userMetaData.getUserAvailableResource(moduleInstance.getApplicationName, user, creator)
												instances	RAM	cpu	Queuememory	Queuecore	queueInstances
					moduleAvailableResource		10			20G	20	10G			10			3
					creatorAvailableResource	10			20G	20	10G			10			3
								
				val (moduleUsedResource, creatorUsedResource) = userResourceRecordService.getModuleAndCreatorResource(moduleInstance.getApplicationName, user, creator, requestResource)
					moduleUsedResource			1			2G	1	12G			6			0
					creatorUsedResource			1			2G	1	12G			6			0


				if (moduleAvailableResource.resource >= moduleUsedResource) if (creatorAvailableResource.resource >= creatorUsedResource)
				  true
				else {
				  info(s"creator:$creator for $user had used module resource:$creatorUsedResource > creatorAvailableResource:${creatorAvailableResource.resource} ")
				  throw new RMWarnException(111007, s"${generateNotEnoughMessage(creatorUsedResource, creatorAvailableResource.resource)}")
				} else {// module??????????????? ????????? module???????????????; 
				  info(s"$user had used module resource:$moduleUsedResource > moduleAvailableResource: $moduleAvailableResource")
				  throw new RMWarnException(111005, s"${generateNotEnoughMessage(moduleUsedResource, moduleAvailableResource.resource)}")
				}	
				
			}
			if (! canSuperRequest) return false
			val yarnResource = requestResource.asInstanceOf[DriverAndYarnResource].yarnResource
			// ???Hadoop-Yarn???8088???????????? ??????max???userd????????????; 
			val (maxCapacity, usedCapacity) = YarnUtil.getQueueInfo(yarnResource.queueName);{
				
				Utils.tryCatch(YarnUtil.getResources(){// ??????(maxCapacity, usedCapacity) : (YarnResource, YarnResource) 
					val resp = getResponseByUrl("scheduler"){
						val httpGet = new HttpGet(rm_web_address + "/ws/v1/cluster/" + url)
						val response = httpClient.execute(httpGet) // ??? hadoop-yarn???????????? scheduler??????:  http://bdnode101:8088/ws/v1/cluster/scheduler ??????Get??????; 
						parse(EntityUtils.toString(response.getEntity()))
					}
					val schedulerType = (resp \ "scheduler" \ "schedulerInfo" \ "type").asInstanceOf[JString].values
					if ("capacityScheduler".equals(schedulerType)) {
						val queue = getQueueOfCapacity(childQueues)
						val maxCapacity = maxEffectiveHandle(queue).get;{//YarnUtil.$.maxEffectiveHandle()
							val totalResouceInfoResponse = ((metrics \ "clusterMetrics" \ "totalMB").asInstanceOf[JInt].values.toLong, (metrics \ "clusterMetrics" \ "totalVirtualCores").asInstanceOf[JInt].values.toLong)
							// ??????????????????; org.json4s.JsonAST$JDouble cannot be cast to org.json4s.JsonAST$JDecimal
							val effectiveResource = (r \ "absoluteCapacity").asInstanceOf[JDecimal].values.toDouble- (r \ "absoluteUsedCapacity").asInstanceOf[JDecimal].values.toDouble
						}
						
						(maxCapacity, getYarnResource(queue.map( _ \ "resourcesUsed")).get)
					}
					
				}){// catch????????????,
					if ( (t.getCause.isInstanceOf[JsonParseException] && t.getCause.getMessage.contains("This is standby RM"))
						|| t.getCause.isInstanceOf[ConnectException]) {
							reloadRMWebAddress()
							getQueueInfo(queueName)
						}else {
							throw new RMErrorException(11006, "Get the Yarn queue information exception.(??????Yarn??????????????????)", t);// ??????????????????????????????; 
						}
				}
			}
			info(s"This queue:${yarnResource.queueName} used resource:$usedCapacity and max resource???$maxCapacity")
			
			val queueLeftResource = maxCapacity - usedCapacity // 
			if (queueLeftResource < yarnResource) { // ???????????????yarnResource ???????????? ????????????(queueLeftResource) 
										memory	cpu	
					queueLeftResource	8G		8	0
					yarnResource		12G		6	0
					
				info(s"User: $user request queue (${yarnResource.queueName}) resource $yarnResource is greater than queue (${yarnResource.queueName}) remaining resources $queueLeftResource(??????:$user ??????????????????${yarnResource.queueName}?????????$yarnResource ???????????????${yarnResource.queueName}???????????????$queueLeftResource)")
				throw new RMWarnException(111007, s"${generateNotEnoughMessage(yarnResource, queueLeftResource)}")
			}else{
				true
			}				
		}
		if (!canRequest){
			return NotEnoughResource(s"user???$user not enough resource")
		}
	  }
	  
    case moduleInstance: ServiceInstance => ResourceInfo(rm.getModuleResourceInfo(moduleInstance))
    case ResourceOverload(moduleInstance) => rm.instanceCanService(moduleInstance)
}





2021-09-04 03:36:44.630 [ERROR] [qtp484258212-24                         ] 
c.w.w.l.r.RPCReceiveRestful (72) [apply] - error code(?????????): 11006, error message(????????????): 
	Get the Yarn queue information exception.(??????Yarn??????????????????). 
	com.webank.wedatasphere.linkis.resourcemanager.exception.RMErrorException: errCode: 11006 ,
	desc: Get the Yarn queue information exception.(??????Yarn??????????????????) ,ip: 192.168.51.111 ,port: 9104 ,serviceKind: ResourceManager
		
	at com.webank.wedatasphere.linkis.resourcemanager.utils.YarnUtil$$anonfun$getQueueInfo$2.apply(YarnUtil.scala:228) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.resourcemanager.utils.YarnUtil$$anonfun$getQueueInfo$2.apply(YarnUtil.scala:223) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.common.utils.Utils$.tryCatch(Utils.scala:54) ~[linkis-common-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.resourcemanager.utils.YarnUtil$.getQueueInfo(YarnUtil.scala:223) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.resourcemanager.DriverAndYarnReqResourceService.canRequest(RequestResourceService.scala:215) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.resourcemanager.service.rm.DefaultResourceManager.requestResource(DefaultResourceManager.scala:240) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.resourcemanager.RMReceiver.receiveAndReply(RMReceiver.scala:50) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.rpc.RPCReceiveRestful$$anonfun$receiveAndReply$1.apply(RPCReceiveRestful.scala:139) ~[linkis-cloudRPC-0.11.0.jar:?]


Caused by: java.lang.ClassCastException: org.json4s.JsonAST$JDouble cannot be cast to org.json4s.JsonAST$JDecimal
	at com.webank.wedatasphere.linkis.resourcemanager.utils.YarnUtil$$anonfun$maxEffectiveHandle$1$1.apply(YarnUtil.scala:140) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.resourcemanager.utils.YarnUtil$$anonfun$maxEffectiveHandle$1$1.apply(YarnUtil.scala:139) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at scala.Option.map(Option.scala:146) ~[scala-library-2.11.8.jar:?]
	at com.webank.wedatasphere.linkis.resourcemanager.utils.YarnUtil$.maxEffectiveHandle$1(YarnUtil.scala:139) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.resourcemanager.utils.YarnUtil$.com$webank$wedatasphere$linkis$resourcemanager$utils$YarnUtil$$getResources$1(YarnUtil.scala:207) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.resourcemanager.utils.YarnUtil$$anonfun$getQueueInfo$1.apply(YarnUtil.scala:223) ~[linkis-resourcemanager-server-0.11.0.jar:?]
	at com.webank.wedatasphere.linkis.resourcemanager.utils.YarnUtil$$anonfun$getQueueInfo$1.apply(YarnUtil.scala:223) ~[linkis-resourcemanager-server-0.11.0.jar:?]
