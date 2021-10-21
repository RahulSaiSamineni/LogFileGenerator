package MapReduce


import java.lang.Iterable
import java.util.StringTokenizer
import HelperUtils.{CreateLogger, ObtainConfigReference}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import scala.collection.JavaConverters.*
import org.slf4j.Logger

//Created an Object
object Job1 :
  // defining the time intervals
  def divideTimeIntervals(logTime: String): String = {
    val timeUnits = logTime.split(":")
    return s"${timeUnits(0)}:${timeUnits(1)}:${timeUnits(2).toDouble.round.toString}"
  }
  // Mapper class to get data and make them in key value pairs
  class Mapper1 extends Mapper[Object, Text, Text, IntWritable] {

    val one = new IntWritable(1)
    val word = new Text()
    val logger: Logger = CreateLogger(classOf[Mapper1])

    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
      // Splitting the error message after converting it to string
      val sp = value.toString().split(" ")
      logger.info(s"${sp}")
      val itr = new StringTokenizer(value.toString)
      logger.info(s"${itr}")
      val timeInterval = divideTimeIntervals(sp(0).toString)
      //Getting the type of message and passing it to variable
      val s1=sp(2)
      val key = s"${s1}_${timeInterval}"
      logger.info(s"${key}")
      //setting key to word
      word.set(key)
      context.write(word, one)

    }
  }
  //Reducer class to take inputs from mapper and make set of key value pair
  class Reducer1 extends Reducer[Text, IntWritable, Text, IntWritable] {
    val logger: Logger = CreateLogger(classOf[Reducer1])
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
    job.setMapperClass(classOf[Mapper1])
    job.setCombinerClass(classOf[Reducer1])
    job.setReducerClass(classOf[Reducer1])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputKeyClass(classOf[Text]);
    job.setOutputValueClass(classOf[IntWritable]);
    FileInputFormat.addInputPath(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))
    job.waitForCompletion(true)
  }

