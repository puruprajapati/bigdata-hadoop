package part1c;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text,IntWritable,Text,DoubleWritable>{
	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
	{
		 double sum = 0;
	      double count= 0;
	      
	      for (IntWritable val : values) {
	    	  count++;
	    	  sum += val.get();
	      }
	      
	      DoubleWritable result = new DoubleWritable();
	      result.set(sum/count);
	      context.write(key, result);
	}
}
