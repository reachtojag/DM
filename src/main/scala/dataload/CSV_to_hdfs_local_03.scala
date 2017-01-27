package dataload

import java.io.FileOutputStream
import java.util.Calendar

import org.apache.spark.{SparkConf, SparkContext}

object CSV_to_hdfs_local_03 {

  def main(args: Array[String]) {

    // user to login in to hdfs system.
    val output = new FileOutputStream("application.conf")
    System.setProperty("HADOOP_USER_NAME", "edureka")

    // -----------------------------
    // spark config
    val conf = new SparkConf()
      .setAppName("deptsal")
      .setMaster("local")

    val sc = new SparkContext(conf)
    val ipFile = sc.textFile("cdr.csv")

    // -----------------------------
    // method for countByNumber by fetchElement
    def fetchElement(line: String, loc: Int) = {
      val s = line.split(",");
      s(loc)
    }

    val countByNumber = ipFile
      .map(line => fetchElement(line, 2))
      .map(number => (number, 1))
      .reduceByKey(_ + _)

    val countByNumber_sortedDsc = countByNumber.sortBy(_._2, false)

    // -----------------------------
    // timestamp to save files
    val now = Calendar.getInstance().getTime()
    val format = new java.text.SimpleDateFormat("ddMMyyyyHHmmss")
    val dateformat = format.format(new java.util.Date())

    // -----------------------------
    // get the path for the local, hdfs from application .conf
    val myConfig = new MyConfig()
    val hdfspath = myConfig.envOrElseConfig("my.secret.hdfspath")
    val localpath = myConfig.envOrElseConfig("my.secret.localpath")

    // save data into local, hdfs, postgress
    countByNumber_sortedDsc.saveAsTextFile(localpath + "data_" + dateformat)
    countByNumber_sortedDsc.saveAsTextFile(hdfspath + "data_" + dateformat)

    // print path from application.conf
    println(hdfspath);
    println(s"My secret value is hdfspath");
    println("My secret value is hdfspath")

  }
}
