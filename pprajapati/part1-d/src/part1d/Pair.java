package part1d;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class Pair implements Writable {

    private IntWritable sum;
    private IntWritable count;
    
    public Pair() {
    	this.sum = new IntWritable(0);
        this.count = new IntWritable(0);
	}   
    
    public Pair(int sum, int count) {
    	this.sum = new IntWritable(sum);
        this.count = new IntWritable(count);
    }
  	
   public void setSum(int sum) {
       this.sum.set(sum);
   }

   public int getSum() {
       return sum.get();
   }

   public void setCount(int count) {
       this.count.set(count);
   }

   public int getCount() {
       return count.get();
   }

    @Override
    public void readFields(DataInput in) throws IOException {
       sum.readFields(in);
        count.readFields(in);
    }

	@Override
	public void write(DataOutput out) throws IOException {
		sum.write(out);
		count.write(out);
	}
   
    @Override 
    public int hashCode() {
    	return sum.hashCode() * 17 + count.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o instanceof Pair) {
    		Pair p = (Pair) o;
    		return sum.equals(p.sum) && count.equals(p.count);
    	}
    	return false;
    }

}