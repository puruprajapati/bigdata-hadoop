package org.example;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.InetAddressValidator;
public class AverageComputeMapper extends Mapper<Object, Text, Text, IntWritable> {



    private Text word = new Text();
    Map count = new HashMap<String, Integer>();

    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        String[] arr = value.toString().split("\\s+");

        var s = arr[0];
        s = s.trim();
        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(arr[arr.length - 1].trim());

        if(!"".equals(s) && m.find()){
            if (validator.isValid(s)) {
                var keyToken = new Text(s);

                var valueToken = new IntWritable(Integer.parseInt(arr[arr.length - 1]));

                context.write(keyToken, valueToken);
            }
        }

    }
}
