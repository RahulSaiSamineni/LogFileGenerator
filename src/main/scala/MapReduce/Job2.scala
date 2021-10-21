package MapReduce

//Import Statements
import HelperUtils.CreateLogger
import java.lang.Iterable
import java.util.StringTokenizer
import org.slf4j.Logger
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}

import scala.collection.JavaConverters.*

//Created an Object
object Job2 :
  // defining the time intervals
  def divideTimeIntervals(logTime: String): String = {
    val timeUnits = logTime.split(":")
    return s"${timeUnits(0)}:${timeUnits(1)}:${timeUnits(2).toDouble.round.toString}"
  }
 // Mapper class to get data and make them in key value pairs
  class Mapper2 extends Mapper[Object, Text, Text, IntWritable] {

    val one = new IntWritable(1)
    val word = new Text()
    val logger: Logger = CreateLogger(classOf[Mapper2])

    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
      // Splitting the error message after converting it to string
      val sp = value.toString().split(" ")
      logger.info(s"${sp}")
      val itr = new StringTokenizer(value.toString)
      logger.info(s"${itr}")
      //Getting the time interval
      val timeInterval = divideTimeIntervals(sp(0).toString)
      logger.info(s"${timeInterval}")
      //Getting the error type using sp
      val s1=sp(2)
      logger.info(s"${s1}")
      //Using if condition to match the error type of messages
      if(s1 =="ERROR") {
        val key = s"${timeInterval}"
        logger.info(s"${key}")
        //Setting the Value of key to word
        word.set(key)
        context.write(word, one)
      }
    }
  }
//Reducer class to take inputs from mapper and make set of key value pair
  class Reducer2 extends Reducer[Text, IntWritable, Text, IntWritable] {
    val logger: Logger = CreateLogger(classOf[Reducer2])
    override def reduce(key: Text, values: java.lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      val sum = values.asScala.foldLeft(0)(_ + _.get)
      logger.info(s"${sum}")
      context.write(key, new IntWritable(sum))
    }
  }

//main method to call mapper and reducer
  def main(args: Array[String]): Unit = {
    val configuration = new Configuration
    val job = Job.getInstance(configuration, "word count")
    job.setJarByClass(this.getClass)
    job.setMapperClass(classOf[Mapper2])
    job.setCombinerClass(classOf[Reducer2])
    job.setReducerClass(classOf[Reducer2])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputKeyClass(classOf[Text]);
    job.setOutputValueClass(classOf[IntWritable]);
    FileInputFormat.addInputPath(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))
    job.waitForCompletion(true)
  }


