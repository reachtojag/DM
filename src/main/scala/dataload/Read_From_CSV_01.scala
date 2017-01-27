package dataload

import org.apache.spark.sql.{SaveMode, SparkSession}

object Read_From_CSV_01 {
  def main(args: Array[String]) {

    val spark = SparkSession
      .builder
      .master("local")
      .appName("JDBC CSV1234")
      .getOrCreate()

    // Reading from csv file
    val df = spark.read.format("csv").option("header", "false").load("customer.csv")

    df.show()
    df printSchema()

    // Append file in the following location in json format
    df.write.mode(SaveMode.Append).format("json").save("local")
    df.isStreaming

    // df.write.mode("error").jdbc(jdbcUrl, where, dbProperties)

  }
}
