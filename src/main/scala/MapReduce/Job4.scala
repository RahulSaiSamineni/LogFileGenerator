package MapReduce
//Import Statements
import HelperUtils.Parameters.getParam
import HelperUtils.{CreateLogger, ObtainConfigReference}
import java.lang.Iterable
import java.util.StringTokenizer
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import scala.collection.JavaConverters.*
import org.slf4j.Logger

//Creating an Object
object Job4 :
  //Mapper Class - takes input and convert into key value pairs
  class Mapper4 extends Mapper[Object, Text, Text, IntWritable] {
    val one = new IntWritable(1)
    val word = new Text()
    val logger: Logger = CreateLogger(classOf[Mapper4])

    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
      //Splitting the log message
      val sp = value.toString().split(" ")(2)
      logger.info(s"${sp}")
      val str = value.toString()
      logger.info(s"${str}")
      val Pattern = "(.*?)".r
      Pattern.findFirstMatchIn(str) match {
        case Some(pattern) => {
          //Setting the type of message to word
        word.set(sp)
          //Checking each case of the message with respective message
          sp match {
            case "INFO" => {
              var s = value.toString().split(" ")(6)
              logger.info(s"${s}")
              context.write(word, new IntWritable(str.length()))
            }
            case "WARN" => {
              var s = value.toString().split(" ")(6)
              logger.info(s"${s}")
              context.write(word, new IntWritable(str.length()))
            }
            case "ERROR" => {
              var s = value.toString().split(" ")(5)
              logger.info(s"${s}")
              context.write(word, new IntWritable(str.length()))
            }
            case "DEBUG" => {
              var s = value.toString().split(" ")(5)
              logger.info(s"${s}")
              context.write(word, new IntWritable(str.length()))
            }
            case _ =>
          }
      }
        case None =>
      }


    }
  }
//Reducer Class --> Calcuating each word count based on key value pairs from mapper
  class Reducer4 extends Reducer[Text, IntWritable, Text, IntWritable] {
    val logger: Logger = CreateLogger(classOf[Reducer4])
    override def reduce(key: Text, values: java.lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      var sum = values.asScala.foldLeft(0){(t,i)=> t max i.get()}
      logger.info(s"${sum}")
      context.write(key, new IntWritable(sum))
    }
  }

//Main Method - runs mapper and reducer
  def main(args: Array[String]): Unit = {
    val configuration = new Configuration
    val job = Job.getInstance(configuration, "word count")
    job.setJarByClass(this.getClass)
    job.setMapperClass(classOf[Mapper4])
    job.setCombinerClass(classOf[Reducer4])
    job.setReducerClass(classOf[Reducer4])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputKeyClass(classOf[Text]);
    job.setOutputValueClass(classOf[IntWritable]);
    FileInputFormat.addInputPath(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))
    System.exit(if (job.waitForCompletion(true)) 0 else 1)
  }

