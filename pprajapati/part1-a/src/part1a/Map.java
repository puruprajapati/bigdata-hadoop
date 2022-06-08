package part1a;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map  extends Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		for (String token : value.toString().split("\\s+"))
		{
			token = token.trim();
			if (!"".equals(token)) {
				word.set(token);
				context.write(word, one);
			}
		}
	}
}
