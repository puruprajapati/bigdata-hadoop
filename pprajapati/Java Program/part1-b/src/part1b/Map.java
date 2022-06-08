package part1b;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map  extends Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	private HashMap<String, IntWritable> hmap;
	
	protected void setup(Context context) throws IOException ,InterruptedException {
    	
    	hmap = new HashMap<String, IntWritable>();
    };


	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		for (String token : value.toString().split("\\s+"))
			{
				token = token.trim();
				if (!"".equals(token)) {
					if(hmap.containsKey(token)){
			    		  hmap.put( token,new IntWritable(Integer.parseInt(hmap.get(token).toString()) + 1)  );
			    	  }
			    	  else{
			    		  hmap.put(token, one);    		  
			    	  }  
				}
			}
	}
	
	@Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
    	for ( String k : hmap.keySet()) { 	    	
    	   word.set(k);
           context.write(word, hmap.get(k));  
    	}
    }
}
