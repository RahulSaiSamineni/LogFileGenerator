package MapReduce
//Import Statements
import java.lang.Iterable
import java.util.StringTokenizer
import HelperUtils.{CreateLogger, ObtainConfigReference}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import scala.collection.JavaConverters._
import org.slf4j.Logger

//Object creation
object Job3 :
//Mapper class where it takes input messages and convert to key value pairs
  class Mapper3 extends Mapper[Object, Text, Text, IntWritable] {

    val one = new IntWritable(1)
    val word = new Text()
    val logger: Logger = CreateLogger(classOf[Mapper3])

    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
      //Splitting the value passed to get the type of error message
      val sp = value.toString().split(" ")(2)
      logger.info(s"${sp}")
      val itr = new StringTokenizer(value.toString)
      logger.info(s"${itr}")
      //Setting the error message to word
      word.set(sp)
      context.write(word, one)

    }
  }
//Reducer class where it takes inputs from mappers and converts to set of key value pairs
  class Reducer3 extends Reducer[Text, IntWritable, Text, IntWritable] {
    val logger: Logger = CreateLogger(classOf[Reducer3])
    override def reduce(key: Text, values: java.lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      var sum = values.asScala.foldLeft(0)(_ + _.get)
      logger.info(s"${sum}")
      context.write(key, new IntWritable(sum))
    }
  }

//Main class where Mapper class and reducer class are called
  def main(args: Array[String]): Unit = {
    val configuration = new Configuration
    val job = Job.getInstance(configuration, "word count")
    job.setJarByClass(this.getClass)
    job.setMapperClass(classOf[Mapper3])
    job.setCombinerClass(classOf[Reducer3])
    job.setReducerClass(classOf[Reducer3])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputKeyClass(classOf[Text]);
    job.setOutputValueClass(classOf[IntWritable]);
    FileInputFormat.addInputPath(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))
    System.exit(if (job.waitForCompletion(true)) 0 else 1)
  }


