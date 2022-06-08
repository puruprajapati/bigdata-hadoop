package part1d;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text,Pair,Text,DoubleWritable>{
	@Override
	public void reduce(Text key, Iterable<Pair> values, Context context) throws IOException, InterruptedException
	{
		double sum = 0, count = 0;
		for (Pair val : values) {
			sum += val.getSum();
			count += val.getCount();
		}
		context.write(key, new DoubleWritable(sum / count));
	}
}
