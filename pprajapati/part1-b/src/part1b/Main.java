package part1b;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Main extends Configured implements Tool{
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Main(), args);
		System.exit(res);
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), "Part1b-Wordcount-Inmapper");
		job.setJarByClass(this.getClass());
		
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setJarByClass(Main.class); 
		
		//job.setNumReduceTasks(2);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
        Configuration conf = new Configuration();
		
		Path output = new Path(args[1]);
		FileSystem hdfs = FileSystem.get(conf);
		
		// delete existing directory
		if (hdfs.exists(output)) {
		  hdfs.delete(output, true);
		}
		
		
		return job.waitForCompletion(true) ? 0 : 1;
	}
}
