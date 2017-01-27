package dataload

import org.apache.spark.sql.{SaveMode, SparkSession}


object Postgress_to_DB_04 {
  def main(args: Array[String]) {

    val spark = SparkSession
      .builder
      .master("local")
      .appName("JDBC CSV")
      .getOrCreate()

    // Registering Driver - Might be redundant in some cases
    Class.forName("org.postgresql.Driver").newInstance

    // Reading from csv file
    val df=spark.read.format("csv").option("header", "false").load("customer.csv")

    // Setting database connection details
    val dbProperties = new java.util.Properties
    dbProperties.load(new java.io.FileInputStream(new java.io.File("db-properties.flat")));

    val jdbcUrl = dbProperties.getProperty("jdbcUrl")

    // Creating table 'customer' in the Postgres database
    var where="customer"

    df.write.mode(SaveMode.Append).jdbc(jdbcUrl, where, dbProperties)
    // df.write.mode("error").jdbc(jdbcUrl, where, dbProperties)

    spark.stop()
  }
}
