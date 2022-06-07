package org.example;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    Map count = new HashMap<String, Integer>();

    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {

        for (String token : value.toString().split("\\s+"))
        {
            token = token.trim();
            token = token.replaceAll("[(),\"?,'.]","");

            if (!"".equals(token)) {
                word.set(token);
                if(!count.containsKey(token)){
                    count.put(token,1);
                }else{
                    var sum = (Integer)count.get(token);
                    count.put(token,sum+1);
                }

            }
        }

    }

    @Override
    protected void cleanup(Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        Iterator<Map.Entry<Integer, Integer>> temp = count.entrySet().iterator();

        while(temp.hasNext()) {
            Map.Entry<Integer, Integer> entry = temp.next();
            String keyVal = entry.getKey()+"";
            Integer countVal = entry.getValue();

            context.write(new Text(keyVal), new IntWritable(countVal));
        }
    }
}
