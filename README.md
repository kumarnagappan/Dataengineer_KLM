# DataEngineer_Assignment
Assignment on technical interview

This code base is created as part of technical assessment . The requirements are as below ,

Task: 1
Problem Statement: 
A Dataset has been provided to you (as a attachment), which contains raw data 
which is not useful to the business at the moment in its current form, as they 
requested you as a data engineer to enrich it with all requested changes as follows. 
a) Impose proper schema to the raw dataset with proper types. 
b) All datetime formats should be in the following format yyyy-mm-dd HH:mm:ss
c) Can you calculate the KPI when the arrival of aircraft is delayed for more than 20 
minutes, what caused that delay? 
d) Can you also calculate, how many flight arrivals are cancelled & delayed? 
e) Upon re-run data can’t be duplicated in the sql table, please make sure while 
implementing this as part of your code? 
f) Store the enriched datasets in sql server using spark-scala? 
g) Please write a Scala unit test case to test the results upon running the code with 
maven. 
h) Build the jar 
i) Every column/row is needed to be part of enriched dataset from raw dataset, no
ignore/ skip / remove is allowed. 
Things excepted from you: 
a) Completed code on your GitHub repository including docker file & spark-submit. 
b) Output of enriched dataset as csv file you created before loading it into SQL server on github
repo. 
c) Output of the final frame as screenshot (from your IntelliJ IDE). 
d) Output of the testcases success screenshot (from your IntelliJ IDE). 
e) SQL table screen shot after loading data (indicating load_date). 
Assignment context: 
• You must implement the code using maven, spark & Scala only. 
• Upload the written code into public GitHub repository of your own by forking it and 
complete the code push it and share the link with us for validation. 
• You must write unit test case using Scala flat spec to test problem statements. 
• You must use IntelliJ IDEA to code the use case as IDE. 
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