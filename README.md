# DataEngineer_Assignment
Assignment on technical 

This code base is created as part of technical assessment . The requirements are as below ,

Task: 1
Problem Statement: 
A Dataset has been provided to you (as a attachment), which contains raw data 
which is not useful to the business at the moment in its current form, as they 
requested you as a data engineer to enrich it with all requested changes as follows. 

Task -2:
Write a Scala functional code for the following scenario 
If I give a string as input, can you extract all the substrings present inside that given input 
string (which are continuous)?
Where the length of substrings should be of ‘x’ length.
For example: 
Input: 132765
Output:
132
276
765
For 4-digit length 
1327
3276
2765
For 5-digit length & so on…

Toolsets used :


Spark  --> 2.4
scala  --> 2.12
Java  --> jdk 1.8.0_131
IDE  --> Intellij
build tool --> Maven
SQL  --> microsoft sqlserver 2019
Remote  --> Github
OS --> windows 10

Assignment 1 : csv feed to SQLSERVER
Steps To Run aircraft details Job:
Scala File name : aircraftDetails

Make sure the jar path is provided as per your save location of the shadow jar. 

Step 1: pass the variables to spark-submit command 

Variable 1 : SQL SERVER DB USER NAME ( This project works in Login based and not windows based authentication for now)
Variable 2 : SQL SERVER DB Password
Variable 3 : JDBC URL made of host , instance and port number and DB name . For example given below

"jdbc:sqlserver://192.168.1.3:1433;instanceName=LAPTOP-597CGSMP;databaseName=aircraft_info"

Variable 4 : Input file along with path where the dataset is present
Variable 5 : Output file along with the path where the final raw dataset to be placed

spark-submit --master local[*] --driver-memory 1g --executor-memory 1g --class main.scala.programPackage.aircraftDetails C:\Users\kumar\IdeaProjects\DataEngineer\target\DataEngineer-1.0-SNAPSHOT.jar admin admin "jdbc:sqlserver://192.168.1.3:1433;instanceName=LAPTOP-597CGSMP;databaseName=aircraft_info" "src/main/resources/dataset_flights.csv" "file:///C:/Users/kumar/Downloads/finalraw.csv"


Please refer spark-submit command file in repo for reference .


Assignment 2 : Substring Generation
Steps To Run substring details Job:
Scala File name :  substringProgram

Make sure the jar path is provided as per your save location of the shadow jar. 

Step 1: pass the variables to spark-submit command 

Variable 1 : Actual string which needs to be subdivided into substrings
Variable 2 : length of substrings to be created

Please refer spark-submit command file in repo for reference .



spark-submit --master local[*] --driver-memory 1g --executor-memory 1g --class main.scala.programPackage.substringProgram C:\Users\kumar\IdeaProjects\DataEngineer\target\DataEngineer-1.0-SNAPSHOT.jar 146425 3
