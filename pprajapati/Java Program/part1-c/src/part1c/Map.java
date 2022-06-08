package part1c;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class Map  extends Mapper<LongWritable, Text, Text, IntWritable> {
	private Text word = new Text();

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		InetAddressValidator validator = InetAddressValidator.getInstance();
		String[] elements = value.toString().split("\\s+");
		String ipAddress = elements[0].trim();
		Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(elements[elements.length - 1].trim());
		
		if(!"".equals(ipAddress) && m.find()){
			if(validator.isValid(ipAddress)){
				word.set(ipAddress);
				context.write(word, new IntWritable(Integer.parseInt(elements[elements.length - 1])));
			}
			
		}
	}
}
