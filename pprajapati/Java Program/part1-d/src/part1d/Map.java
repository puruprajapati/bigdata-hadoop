package part1d;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class Map  extends Mapper<LongWritable, Text, Text, Pair> {
	private HashMap<String, Pair> pairSum;
	
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		pairSum = new HashMap<>();
	}

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
				String intVal = elements[elements.length - 1];
				Integer num = Integer.parseInt(intVal);
				Pair sumCount = pairSum.get(ipAddress);
				
				if (sumCount == null) {
					sumCount = new Pair(num, 1);
				} else {
					sumCount.setSum(sumCount.getSum() + num);
					sumCount.setCount(sumCount.getCount() + 1);
				}
				pairSum.put(ipAddress, sumCount);
				
			}
			
		}
	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		for (java.util.Map.Entry<String, Pair> entry : pairSum.entrySet()) {
			context.write(new Text(entry.getKey()), entry.getValue());
		}
	}
}
