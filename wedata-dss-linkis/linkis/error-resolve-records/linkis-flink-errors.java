

// flinkError 3: Caused by: java.lang.ClassNotFoundException: org.apache.flink.connectors.hive.HiveSource

	Caused by: org.apache.flink.util.FlinkException: Failed to execute job 'insert-into_myhive.default._tmp_table_ba9e991fe9b74cb4b36d484f37110ea7'.
		Caused by: org.apache.flink.runtime.client.JobInitializationException: Could not instantiate JobManager.
			Cannot instantiate the coordinator for operator Source: HiveSource-default.tb_user

com.webank.wedatasphere.linkis.engineconnplugin.flink.client.sql.operation.impl.SelectOperation 174 executeQueryInternal - 
Invalid SQL query, sql is select name, age from tb_user limit 5000. org.apache.flink.table.api.TableException: 
Failed to execute sql
	at org.apache.flink.table.api.internal.TableEnvironmentImpl.executeInternal(TableEnvironmentImpl.java:699) ~[flink-table-api-java-1.12.2.jar:1.12.2]
	at org.apache.flink.table.api.internal.TableImpl.executeInsert(TableImpl.java:572) ~[flink-table-api-java-1.12.2.jar:1.12.2]
	
Caused by: org.apache.flink.util.FlinkException: Failed to execute job 'insert-into_myhive.default._tmp_table_ba9e991fe9b74cb4b36d484f37110ea7'.
	at org.apache.flink.streaming.api.environment.StreamExecutionEnvironment.executeAsync(StreamExecutionEnvironment.java:1918) ~[flink-streaming-java_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.client.program.StreamContextEnvironment.executeAsync(StreamContextEnvironment.java:135) ~[flink-clients_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.table.planner.delegation.ExecutorBase.executeAsync(ExecutorBase.java:55) ~[flink-table-planner-blink_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.table.api.internal.TableEnvironmentImpl.executeInternal(TableEnvironmentImpl.java:681) ~[flink-table-api-java-1.12.2.jar:1.12.2]
	... 39 more

Caused by: java.lang.RuntimeException: org.apache.flink.runtime.client.JobInitializationException: Could not instantiate JobManager.
	at org.apache.flink.util.ExceptionUtils.rethrow(ExceptionUtils.java:316) ~[flink-core-1.12.2.jar:1.12.2]
	at org.apache.flink.util.function.FunctionUtils.lambda$uncheckedFunction$2(FunctionUtils.java:75) ~[flink-core-1.12.2.jar:1.12.2]

Caused by: org.apache.flink.runtime.client.JobInitializationException: Could not instantiate JobManager.
	at org.apache.flink.runtime.dispatcher.Dispatcher.lambda$createJobManagerRunner$5(Dispatcher.java:494) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at java.util.concurrent.CompletableFuture$AsyncSupply.run(CompletableFuture.java:1604) ~[?:1.8.0_261]
	... 3 more
	
Caused by: org.apache.flink.runtime.JobException: 
Cannot instantiate the coordinator for operator Source: HiveSource-default.tb_user
	at org.apache.flink.runtime.executiongraph.ExecutionJobVertex.<init>(ExecutionJobVertex.java:231) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.executiongraph.ExecutionGraph.attachJobGraph(ExecutionGraph.java:866) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	
	at org.apache.flink.runtime.dispatcher.DefaultJobManagerRunnerFactory.createJobManagerRunner(DefaultJobManagerRunnerFactory.java:86) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.dispatcher.Dispatcher.lambda$createJobManagerRunner$5(Dispatcher.java:478) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at java.util.concurrent.CompletableFuture$AsyncSupply.run(CompletableFuture.java:1604) ~[?:1.8.0_261]
	... 3 more
	
Caused by: java.lang.ClassNotFoundException: org.apache.flink.connectors.hive.HiveSource
	at java.net.URLClassLoader.findClass(URLClassLoader.java:382) ~[?:1.8.0_261]
	at java.lang.ClassLoader.loadClass(ClassLoader.java:418) ~[?:1.8.0_261]
	at org.apache.flink.util.FlinkUserCodeClassLoader.loadClassWithoutExceptionHandling(FlinkUserCodeClassLoader.java:64) ~[flink-core-1.12.2.jar:1.12.2]
	at org.apache.flink.util.ChildFirstClassLoader.loadClassWithoutExceptionHandling(ChildFirstClassLoader.java:65) ~[flink-core-1.12.2.jar:1.12.2]
	at org.apache.flink.util.FlinkUserCodeClassLoader.loadClass(FlinkUserCodeClassLoader.java:48) ~[flink-core-1.12.2.jar:1.12.2]
	at java.lang.ClassLoader.loadClass(ClassLoader.java:351) ~[?:1.8.0_261]
	at org.apache.flink.runtime.execution.librarycache.FlinkUserCodeClassLoaders$SafetyNetWrapperClassLoader.loadClass(FlinkUserCodeClassLoaders.java:172) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at java.lang.Class.forName0(Native Method) ~[?:1.8.0_261]
	at java.lang.Class.forName(Class.java:348) ~[?:1.8.0_261]
	at org.apache.flink.util.InstantiationUtil$ClassLoaderObjectInputStream.resolveClass(InstantiationUtil.java:76) ~[flink-core-1.12.2.jar:1.12.2]
	at java.io.ObjectInputStream.readNonProxyDesc(ObjectInputStream.java:1946) ~[?:1.8.0_261]
	at java.io.ObjectInputStream.readClassDesc(ObjectInputStream.java:1829) ~[?:1.8.0_261]
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2120) ~[?:1.8.0_261]
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1646) ~[?:1.8.0_261]
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2365) ~[?:1.8.0_261]
	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:2289) ~[?:1.8.0_261]
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2147) ~[?:1.8.0_261]
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1646) ~[?:1.8.0_261]
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:482) ~[?:1.8.0_261]
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:440) ~[?:1.8.0_261]
	at org.apache.flink.util.InstantiationUtil.deserializeObject(InstantiationUtil.java:615) ~[flink-core-1.12.2.jar:1.12.2]
	at org.apache.flink.util.InstantiationUtil.deserializeObject(InstantiationUtil.java:600) ~[flink-core-1.12.2.jar:1.12.2]
	at org.apache.flink.util.InstantiationUtil.deserializeObject(InstantiationUtil.java:587) ~[flink-core-1.12.2.jar:1.12.2]
	at org.apache.flink.util.SerializedValue.deserializeValue(SerializedValue.java:67) ~[flink-core-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.operators.coordination.OperatorCoordinatorHolder.create(OperatorCoordinatorHolder.java:337) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.executiongraph.ExecutionJobVertex.<init>(ExecutionJobVertex.java:225) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.executiongraph.ExecutionGraph.attachJobGraph(ExecutionGraph.java:866) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.executiongraph.ExecutionGraphBuilder.buildGraph(ExecutionGraphBuilder.java:257) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.scheduler.SchedulerBase.createExecutionGraph(SchedulerBase.java:322) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.scheduler.SchedulerBase.createAndRestoreExecutionGraph(SchedulerBase.java:276) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.scheduler.SchedulerBase.<init>(SchedulerBase.java:249) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.scheduler.DefaultScheduler.<init>(DefaultScheduler.java:133) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.scheduler.DefaultSchedulerFactory.createInstance(DefaultSchedulerFactory.java:111) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.jobmaster.JobMaster.createScheduler(JobMaster.java:345) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.jobmaster.JobMaster.<init>(JobMaster.java:330) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.jobmaster.factories.DefaultJobMasterServiceFactory.createJobMasterService(DefaultJobMasterServiceFactory.java:95) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.jobmaster.factories.DefaultJobMasterServiceFactory.createJobMasterService(DefaultJobMasterServiceFactory.java:39) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.jobmaster.JobManagerRunnerImpl.<init>(JobManagerRunnerImpl.java:162) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.dispatcher.DefaultJobManagerRunnerFactory.createJobManagerRunner(DefaultJobManagerRunnerFactory.java:86) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at org.apache.flink.runtime.dispatcher.Dispatcher.lambda$createJobManagerRunner$5(Dispatcher.java:478) ~[flink-runtime_2.11-1.12.2.jar:1.12.2]
	at java.util.concurrent.CompletableFuture$AsyncSupply.run(CompletableFuture.java:1604) ~[?:1.8.0_261]
	... 3 more


????????????
	- ????????? JobManager??????????????? JobSubmit?????????,?????? new JobMaster()?????? ???????????? flink-connector-hive?????? HiveSource ????????????;

??????1: ????????????????????? ??????
??? exception??? trance ???????????????, ?????? ClassNotFoundException: org.apache.flink.connectors.hive.HiveSource ???????????????????????????
	DefaultJobManagerRunnerFactory.createJobManagerRunner()		??????flink-runtime_2.11-1.12.2.jar:1.12.2,????????? Client ?????? JobManager?????????;
		new JobMaster() -> JobMaster.createScheduler()
			SchedulerBase.createExecutionGraph() 
				ClassLoaderObjectInputStream.resolveClass()	FlinkUserCodeClassLoaders$SafetyNetWrapperClassLoader.loadClass()

?????? DefaultJobManagerRunnerFactory.createJobManagerRunner()??????????????????,  ????????????????????????: 
	DefaultJobManagerRunnerFactory.createJobManagerRunner()
		createJobManagerRunner(jobGraph)
			Dispatcher.runJob
				Dispatcher.persistAndRunJob()
					waitForTerminatingJobManager
						Dispatcher.submitJob()

???????????????????????????, ?????? Dispatcher.submitJob(JobGraph jobGraph, Time timeout) ??????Cli?????????JobSubmit(RPC??????), ?????? ????????????Graph?????????JobMaster???runJob()??????;
	??????JobManager????????????: YarnSessionClusterEntrypoint ???, 
	
????????????????????? Yarn JM??? classpath??????jar,????????????flink??????; ???????????? 2???,flink???????????? flink-dist_2.11-1.12.2.jar ?????????; 
jps|grep ClusterEntrypo|awk '{print $1}'|xargs -n1 -i{} jcmd {} VM.system_properties |grep java.class.path |xargs -d ":" -n1 |grep flink
flink-dist_2.11-1.12.2.jar\
flink-conf.yaml\

??????flink-dist?????????,?????????pom???????????? flink-connector-hive?????????, ??????????????? flink-connector-hive jar???????????? JobManager???CP??????;  


??????2: 








