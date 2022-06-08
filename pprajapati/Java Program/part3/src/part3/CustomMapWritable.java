package part3;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Writable;

public class CustomMapWritable extends MapWritable {

	public CustomMapWritable() {
		super();
	}

	@Override
	public String toString() {
		String out = "[";
		for (Entry<Writable, Writable> entry : this.entrySet()) {
			out += String.format("(%s, %s), ", entry.getKey().toString(), entry
					.getValue().toString());
		}
		return out.substring(0, out.length()-1) + "]";
	}

}