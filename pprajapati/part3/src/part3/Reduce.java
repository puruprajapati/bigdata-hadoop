package part3;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map.Entry;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;


public class Reduce extends Reducer<Text, MapWritable, Text, CustomMapWritable> {

	@Override
	protected void reduce(Text item, Iterable<MapWritable> values,Context context)throws IOException, InterruptedException {
		MapWritable sumMap = new MapWritable();
		double total = 0.0;
		for (MapWritable v : values) {
			for (Entry<Writable, Writable> entry : v.entrySet()) {
				if (sumMap.containsKey(entry.getKey())) {
					int t = ((IntWritable) sumMap.get(entry.getKey())).get();
					sumMap.put(entry.getKey(), new IntWritable(t
							+ ((IntWritable) entry.getValue()).get()));
				} else {
					sumMap.put(entry.getKey(), entry.getValue());
				}
				total += ((IntWritable) entry.getValue()).get();
			}
		}
		
		CustomMapWritable finalMap = new CustomMapWritable();
		for (Entry<Writable, Writable> entry : sumMap.entrySet()) {
			double r = ((IntWritable) entry.getValue()).get() / total;
			DecimalFormat df = new DecimalFormat("#.###"); 
			finalMap.put(entry.getKey(), new DoubleWritable(Double.valueOf(df.format(r))));
		}
		context.write(item, finalMap);
	}

}
