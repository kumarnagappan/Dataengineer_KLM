package main.scala.programPackage

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.types.{IntegerType, StringType, StructType, TimestampType}

/* This job reads the aircraftdetails file and generate final raw dataset and 2 KPI's which are finally loaded to SqlServer */

object aircraftDetails  {

  //Function to replace the passed string value with empty string
  def emptyStringReplace = (strVal: String, strRep: String) => {
    strVal.replace(strRep, "")
  }

  def main(args: Array[String]): Unit = {

    try {
      //To get Path and SQL Server variables
      val user = args(0)
      val password = args(1)
      val url = args(2)
      val inputFile = args(3)
      val outputFile = args(4)


      println("Starting of Job aircraftdetails ....")

      val spark = SparkSession.builder()
        .appName("aircraftDetails")
        .master("local[*]")
        .getOrCreate

      // Creating customSchema for meta data part of Dataset

      val customSchema1 = new StructType()
        .add("Currency", StringType, true)
        .add("Customer", StringType, true)
        .add("Date to select", StringType, true)
        .add("End of period", TimestampType, true)
        .add("Start of period", TimestampType, true)
        .add("Unit", StringType, true)

      //Creating customSchema for data part of Dataset

      val customSchema2 = new StructType()
        .add("Year", IntegerType, true)
        .add("Month", IntegerType, true)
        .add("DayofMonth", IntegerType, true)
        .add("DayofWeek", IntegerType, true)
        .add("DepTime", IntegerType, true)
        .add("CRSDepTime", IntegerType, true)
        .add("ArrTime", IntegerType, true)
        .add("CRSArrTime", IntegerType, true)
        .add("UniqueCarrier", StringType, true)
        .add("FlightNum", IntegerType, true)
        .add("TailNum", StringType, true)
        .add("ActualElapsedTime", IntegerType, true)
        .add("CRSElapsedTime", IntegerType, true)
        .add("AirTime", IntegerType, true)
        .add("ArrDelay", IntegerType, true)
        .add("DepDelay", IntegerType, true)
        .add("Origin", StringType, true)
        .add("Dest", StringType, true)
        .add("Distance", IntegerType, true)
        .add("TaxiIn", IntegerType, true)
        .add("TaxiOut", IntegerType, true)
        .add("Cancelled", IntegerType, true)
        .add("CancellationCode", StringType, true)
        .add("Diverted", IntegerType, true)
        .add("CarrierDelay", StringType, true)
        .add("WeatherDelay", StringType, true)
        .add("NASDelay", StringType, true)
        .add("SecurityDelay", StringType, true)
        .add("LateAircraftDelay", StringType, true)


      val EmptyStringReplace = spark.udf.register("EmptyStringReplace", emptyStringReplace)

      import spark.implicits._
      //Reading actual Raw file from resources as this will be part of Jar
      val fileContent = spark.read.textFile(inputFile)
      //Fetching only the meta data part from the file
      val uniqueDF = fileContent.withColumn("index", monotonically_increasing_id())
        .filter(col("index") < 6)
        .drop("index")

      val splitDF = uniqueDF.withColumn("key", split(col("value"), ";").getItem(0))
        .withColumn("value", split(col("value"), ";").getItem(1))
      //Removing additional , in fields and Pivoting the metadata to Join with data part
      val splitDFRem = splitDF.withColumn("value", EmptyStringReplace(col("value"), lit(",")))

      val tempLookup1 = splitDFRem.groupBy().pivot("key").agg(first("value"))
      //casting column values to DataTimeStamp as per the requirement
      val tempLookup2 = tempLookup1
        .withColumn("End of period", to_timestamp(col("End of period"), "dd/MMM/yy"))
        .withColumn("Start of period", to_timestamp(col("Start of period"), "dd/MMM/yy"))

      val finalLookup = tempLookup2.map(i => i.mkString("\t"))

      //Reading the final data as per the customSchema
      val finalLookupDF = spark.read.option("delimiter", "\t").option("header", "false").schema(customSchema1).csv(finalLookup)


      val txtFile12 = fileContent.withColumn("index", monotonically_increasing_id()).filter(col("index") > 6).drop("index")
      val uniqueDF2 = txtFile12.map(i => i.mkString.split(",").mkString("\t"))

      //Reading the final data as per the customSchema
      val finalDF = spark.read.option("delimiter", "\t").option("header", "true").schema(customSchema2).csv(uniqueDF2)

      //Full outer Join to combine Metadata part and the actual data
      val finalDataF = finalLookupDF.join(finalDF, lit(true), "full")

      val resultDf = finalDataF.withColumn("LateAircraftDelay", EmptyStringReplace(col("LateAircraftDelay"), lit(";")))

      //writing the file to local server
      resultDf.coalesce(1).write.mode("overwrite").option("header", true).csv(outputFile)

      resultDf.createOrReplaceTempView("resultDf")
      //Writing the final DF to Sql Server Table CustomerFlightInfo
      resultDf.write.format("jdbc")
        .mode("overwrite")
        .option("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
        .option("url", url)
        .option("dbtable", "CustomerFlightInfo")
        .option("user", user)
        .option("password", password)
        .save()

      // show sample records from data frame
      val delayedCancelled = spark.sql(
        """select * from resultDf
          |where Cancelled=1 or ArrDelay > 0""".stripMargin)

      //Writing the KPI of Delayed and cancelled flights to Sql Server Table DelayedCancelledKPI

      delayedCancelled.write.format("jdbc")
        .mode("overwrite")
        .option("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
        .option("url", url)
        .option("dbtable", "DelayedCancelledKPI")
        .option("user", user)
        .option("password", password)
        .save()

      //DF to identify reason for delay

      val delayMins = spark.sql(
        """select Year
          |,Month
          |,DayofMonth
          |,DayOfWeek
          |,DepTime
          |,CRSDepTime
          |,ArrTime
          |,CRSArrTime
          |,UniqueCarrier
          |,FlightNum
          |,TailNum
          |,WeatherDelay
          |,CarrierDelay
          |,NASDelay
          |,SecurityDelay
          |,LateAircraftDelay
          |from resultDf where ArrDelay > 20""".stripMargin)

      //Appending Reasons to reasonForDelay Column
      val finalDelay = delayMins
        .withColumn("reasonForDelay", concat(when((col("WeatherDelay") =!= "NA") && (col("WeatherDelay") =!= 0), "WeatherDelay,")
          .otherwise(lit("")), when((col("CarrierDelay") =!= "NA") && (col("CarrierDelay") =!= 0), "CarrierDelay,")
          .otherwise(lit("")), when((col("NASDelay") =!= "NA") && (col("NASDelay") =!= 0), "NASDelay,")
          .otherwise(lit("")), when((col("SecurityDelay") =!= "NA") && (col("SecurityDelay") =!= 0), "SecurityDelay,")
          .otherwise(lit("")), when((col("LateAircraftDelay") =!= "NA") && (col("LateAircraftDelay") =!= 0), "LateAircraftDelay")
          .otherwise(lit(""))))

      //KPI data written to DelayedReasonKPI table

      finalDelay.write.format("jdbc")
        .mode("overwrite")
        .option("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
        .option("url", url)
        .option("dbtable", "DelayedReasonKPI")
        .option("user", user)
        .option("password", password)
        .save()
    }
    catch {
      case e: ArrayIndexOutOfBoundsException => println("Array out of bound exception")
      case e: Exception => println(e.printStackTrace())
    }
  }
}
