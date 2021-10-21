# Homework 2
### The goal of this homework is for students to gain experience with solving a distributed computational problem using cloud computing technologies. The main textbook group (option 1) will design and implement an instance of the map/reduce computational model whereas the alternative textbook group (option 2) will use the CORBA model. You can check your textbook option in the corresponding column of the gradebook on the Blackboard.
---
Name: Rahul Sai Samineni
---

#Instructions


###Environment

The Project was developed using the following environment:
+ **OS** : Windows 10
+ **IDE** : IntelliJ Idea Community edition 2021.2.3
+ **HyperVisor** : Oracle VM VirtualBox
+ **Hadoop Distribution**: [Hortonworks Data Platform (3.0.1) Sandbox](https://www.cloudera.com/downloads/hortonworks-sandbox.html) deployed on Virtual Box

###PreRequisites
- HDP Sandbox deployed on Virtual Box or VM
- Ability to use SCP and SSH
- SBT should be installed in your system

###Configuring the Project
+ I have created file called Plugins.sbt in Project folder and added following code
```
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "1.1.0")
```
+ In the build.sbt, I have added few lines of commands
```
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
libraryDependencies += "org.apache.hadoop" % "hadoop-core" % "1.2.1"

```

###Generating the Log Files
1. Clone this repository.
2. Browse to the Project directory and then import it to IntelliJ
3. Generate the Log Files using the following command in Terminal/CMD
```
sbt clean compile run
```
4. After this a folder will be created as Log consists of .log file

###Running the MapReduce Job
1. After the above steps, we need to create a Fat Jar using sbt on Terminal/CMD
```
sbt clean compile assembly
```
2. Start the VirtualBox or VM
3. Start the Hortonworks Sandbox that is loaded on VM or Virtual Box
4. Next we need to move the Input File to HDP Sandbox
5. The input file is there in log Folder
6. Using following command go to Log Folder
   ```cd log```
7. After that using the following command we can move the Input File to HDP Sandbox
```
scp -P 2222 <log File Name> root@localhost:~
```
8. Next we need to move the Fat Jar File to HDP Sandbox.
9. Using following command go to Target Folder
   ```cd target```
10. Using following command go to Scala Folder
    ```cd scala-3.0.2```
11. Next use the following command on your terminal or cmd to move jar
```
scp -P 2222 <Jar File Name> root@localhost:~
```
12. Next we need to move Jar and Input from VM to Hadoop using following command in your localhost terminal
```
hadoop fs -put <Input FileName>
hadoop fs -put <Jar File Name>
```
13. Run the MapReduce Job
```
hadoop jar <Jar File Name> MapReduce.Job1 <Input File Name> Output.txt
hadoop jar <Jar File Name> MapReduce.Job2 <Input File Name> Output1.txt
hadoop jar <Jar File Name> MapReduce.Job3 <Input File Name> Output2.txt
hadoop jar <Jar File Name> MapReduce.Job4 <Input File Name> Output3.txt
```

##Functionalities
1. Distribution of different types of messages across predefined time intervals and injected string instances of the designated regex pattern for these log message types.
    1. This job requires Log File as Input.
    2. In the mapper we pass predefined time intervals and take each type of Message and get the values.
    3. In the reducer we count each value for the respective message type for respective intervals.
+ Command to run this Job
```
hadoop jar LogFileGenerator-assembly-0.1.jar MapReduce.Job1 LogFileGenerator.2021-10-20.log output25.txt
```
+ Command to Fetch the Output
```
hadoop fs -cat output25.txt/part-r-00000
```
+ Sample output is shown below
```
[root@sandbox-hdp ~]# hadoop fs -cat output25.txt/part-r-00000                                                                                                          
DEBUG_12:12:17  1                                                                                                                                                       
DEBUG_12:12:18  4                                                                                                                                                       
DEBUG_12:12:19  2                                                                                                                                                       
DEBUG_15:09:37  5                                                                                                                                                       
DEBUG_15:09:38  6                                                                                                                                                       
DEBUG_15:09:39  4                                                                                                                                                       
DEBUG_15:09:40  7                                                                                                                                                       
DEBUG_15:09:41  10                                                                                                                                                      
DEBUG_15:09:42  9                                                                                                                                                       
DEBUG_15:09:43  8                                                                                                                                                       
DEBUG_16:09:40  6                                                                                                                                                       
DEBUG_16:09:41  6                                                                                                                                                       
DEBUG_16:09:42  5                                                                                                                                                       
DEBUG_16:09:43  7                                                                                                                                                       
DEBUG_16:09:44  8                                                                                                                                                       
DEBUG_16:09:45  9                                                                                                                                                       
DEBUG_16:09:46  11                                                                                                                                                      
DEBUG_16:09:47  8                                                                                                                                                       
DEBUG_16:09:48  6                                                                                                                                                       
DEBUG_16:09:49  7                                                                                                                                                       
DEBUG_16:09:50  14                                                                                                                                                      
DEBUG_16:09:51  13                                                                                                                                                      
DEBUG_16:09:52  9                                                                                                                                                       
DEBUG_16:09:53  5                                                                                                                                                       
DEBUG_16:09:54  9                                                                                                                                                       
DEBUG_16:09:55  4                                                                                                                                                       
DEBUG_16:09:56  12                                                                                                                                                      
DEBUG_16:09:57  8                                                                                                                                                       
DEBUG_16:09:58  4                                                                                                                                                       
ERROR_12:12:18  2                                                                                                                                                       
ERROR_15:09:37  2                                                                                                                                                       
ERROR_15:09:39  1                                                                                                                                                       
ERROR_16:09:40  2                                                                                                                                                       
ERROR_16:09:42  1                                                                                                                                                       
ERROR_16:09:46  1                                                                                                                                                       
ERROR_16:09:48  1                                                                                                                                                       
ERROR_16:09:49  1                                                                                                                                                       
ERROR_16:09:54  1                                                                                                                                                       
ERROR_16:09:55  2                                                                                                                                                       
ERROR_16:09:56  2                                                                                                                                                       
INFO_12:12:17   7                                                                                                                                                       
INFO_12:12:18   30                                                                                                                                                      
INFO_12:12:19   29                                                                                                                                                      
INFO_15:09:36   2                                                                                                                                                       
INFO_15:09:37   28                                                                                                                                                      
INFO_15:09:38   53                                                                                                                                                      
INFO_15:09:39   54                                                                                                                                                      
INFO_15:09:40   62                                                                                                                                                      
INFO_15:09:41   61                                                                                                                                                      
INFO_15:09:42   59                                                                                                                                                      
INFO_15:09:43   33                                                                                                                                                      
INFO_16:09:39   2                                                                                                                                                       
INFO_16:09:40   44                                                                                                                                                      
INFO_16:09:41   49                                                                                                                                                      
INFO_16:09:42   57                                                                                                                                                      
INFO_16:09:43   50                                                                                                                                                      
INFO_16:09:44   61                                                                                                                                                      
INFO_16:09:45   61                                                                                                                                                      
INFO_16:09:46   58                                                                                                                                                      
INFO_16:09:47   59                                                                                                                                                      
INFO_16:09:48   65                                                                                                                                                      
INFO_16:09:49   65                                                                                                                                                      
INFO_16:09:50   51                                                                                                                                                      
INFO_16:09:51   56                                                                                                                                                      
INFO_16:09:52   61                                                                                                                                                      
INFO_16:09:53   66                                                                                                                                                      
INFO_16:09:54   53                                                                                                                                                      
INFO_16:09:55   66                                                                                                                                                      
INFO_16:09:56   59                                                                                                                                                      
INFO_16:09:57   61                                                                                                                                                      
INFO_16:09:58   19                                                                                                                                                      
WARN_12:12:17   3                                                                                                                                                       
WARN_12:12:18   17                                                                                                                                                      
WARN_12:12:19   8                                                                                                                                                       
WARN_15:09:36   2                                                                                                                                                       
WARN_15:09:37   14                                                                                                                                                      
WARN_15:09:38   17                                                                                                                                                      
WARN_15:09:39   18                                                                                                                                                      
WARN_15:09:40   13                                                                                                                                                      
WARN_15:09:41   11                                                                                                                                                      
WARN_15:09:42   14                                                                                                                                                      
WARN_15:09:43   10                                                                                                                                                      
WARN_16:09:39   2                                                                                                                                                       
WARN_16:09:40   20                                                                                                                                                      
WARN_16:09:41   16                                                                                                                                                      
WARN_16:09:42   15                                                                                                                                                      
WARN_16:09:43   12                                                                                                                                                      
WARN_16:09:44   11                                                                                                                                                      
WARN_16:09:45   13                                                                                                                                                      
WARN_16:09:46   14                                                                                                                                                      
WARN_16:09:47   18                                                                                                                                                      
WARN_16:09:48   13                                                                                                                                                      
WARN_16:09:49   12                                                                                                                                                      
WARN_16:09:50   21                                                                                                                                                      
WARN_16:09:51   16                                                                                                                                                      
WARN_16:09:52   15                                                                                                                                                      
WARN_16:09:53   14                                                                                                                                                      
WARN_16:09:54   22                                                                                                                                                      
WARN_16:09:55   12                                                                                                                                                      
WARN_16:09:56   13                                                                                                                                                      
WARN_16:09:57   16                                                                                                                                                      
WARN_16:09:58   3
            
```
2. Compute time intervals sorted in the descending order that contained most log messages of the type ERROR with injected regex pattern string instances.
    1. This Job requires log File as Input
    2. In the Mapper phase, we will send the time intervals for the type Error and get the values in respective time intervals
    3. Next we need to count for each time interval and sort them in descending order.

+ command to run this Job:
```
hadoop jar LogFileGenerator-assembly-0.1.jar MapReduce.Job2 LogFileGenerator.2021-10-20.log output30.txt
```
+ Command to Fetch the Output
```
hadoop fs -cat output30.txt/part-r-00000
```
+ Sample output is shown below
```
12:12:18        2                                                                                                                                                       
15:09:37        2                                                                                                                                                       
15:09:39        1                                                                                                                                                       
16:09:40        2                                                                                                                                                       
16:09:42        1                                                                                                                                                       
16:09:46        1                                                                                                                                                       
16:09:48        1                                                                                                                                                       
16:09:49        1                                                                                                                                                       
16:09:54        1                                                                                                                                                       
16:09:55        2                                                                                                                                                       
16:09:56        2 
```

3. For each message type you will produce the number of the generated log messages
    1. This job requires log file as input
    2. Here we pass the type of error message as key to the mapper and get values to that message type.
    3. In the reducer , we take the key value pairs from mapper and count the logs for each type of message.
+ command to run this Job:
```
hadoop jar LogFileGenerator-assembly-0.1.jar MapReduce.Job3 LogFileGenerator.2021-10-20.log output26.txt
```
+ Command to Fetch the Output
```
hadoop fs -cat output26.txt/part-r-00000
```
+ Sample output is shown below
```
DEBUG   207                                                                                                                                                             
ERROR   16                                                                                                                                                              
INFO    1481                                                                                                                                                            
WARN    405 
```

4. Number of characters in each log message for each log message type that contain the highest number of characters in the detected instances of the designated regex pattern.
    1. This requires log File as input.
    2. Here we pass the type of message as input and get the characters as per pattern
    3. In the reducer we will count the total number for the respective key.
+ command to run this Job:
```
hadoop jar LogFileGenerator-assembly-0.1.jar MapReduce.Job4 LogFileGenerator.2021-10-20.log output27.txt
```
+ Command to Fetch the Output
```
hadoop fs -cat output27.txt/part-r-00000
```
+ Sample output is shown below
```
DEBUG   166                                                                                                                                                             
ERROR   140                                                                                                                                                             
INFO    172                                                                                                                                                             
WARN    161  
```

##EMR DEPLOYMENT LINK
+ I have Explained and deployed jar and log to get output in AWS using S3 and EMR instances.
+  [Youtube link](https://www.youtube.com/watch?v=XwfIHrxU8JQ)