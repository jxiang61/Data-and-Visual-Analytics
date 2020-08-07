package edu.gatech.cse6242;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.*;
import java.lang.Object;




public class Q1 {
	final String gtid = "jxiang37";
	public static int sum = 0;
	public static int max = 0;

	public static class TripMap extends Mapper<Object, Text, IntWritable, Text> {
		private IntWritable source = new IntWritable();
	    private Text total = new Text();

	    public void map(Object key, Text text, Context context) throws IOException, InterruptedException {
	        StringTokenizer iterator = new StringTokenizer(text.toString(), "\n");

	        while (iterator.hasMoreTokens()) {
	          	String cab = iterator.nextToken();
	          	String token[] = cab.split(",");
		      	if (token.length == 4 && Double.valueOf(token[2]) != 0 && Double.valueOf(token[3]) >= 0) {
		  	     	 source.set(Integer.valueOf(token[0]));
				  	 total.set("1" +","+ token[3]);
				 	 context.write(source, total);
	          	}

	        }
	    }
	}


	public static class MapReducer extends Reducer<IntWritable, Text, IntWritable, Text> {
		private IntWritable index = new IntWritable();
	    private Text ans = new Text();

	    public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			int count = 0;
			double sum = 0;
			for (Text value : values) {
				String strg[] = value.toString().split(",",2);
				count += Integer.valueOf(strg[0]);
				String s = strg[1].replaceAll(",","").trim();
				double tmp = Double.valueOf(s);
				sum += tmp;
			}
			String tt = Integer.toString(count) + "," + String.format("%.2f",sum);
			String splitTwo[] = tt.split(",", 2);
			String t1 = splitTwo[0];
			String r = splitTwo[1];
			List<Character> r_tmp = new ArrayList<>();
			int count_flag = 0;
			int f = 0;
			for (int i = r.length()-1; i>=0; i--) {
				f++;
				if (f <= 3) {
					r_tmp.add(r.charAt(i));
				} else {
					count_flag++;
					r_tmp.add(r.charAt(i));
					if (i > 0 && count_flag % 3 == 0) {
						r_tmp.add(',');
					}
				}
			}
			Collections.reverse(r_tmp);
			String rr = "";
			for (char cc : r_tmp) {
				rr += Character.toString(cc);
			}


			ans.set(t1 + "," + rr);
			context.write(key, ans);

	    }

	}


	public static void main(String[] args) throws Exception {
	    Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "Q1");

	    job.setJarByClass(Q1.class);
	    job.setMapperClass(TripMap.class);
	    job.setCombinerClass(MapReducer.class);
	    job.setReducerClass(MapReducer.class);
	    job.setOutputKeyClass(IntWritable.class);
	    job.setOutputValueClass(Text.class);
	    /* TODO: Needs to be implemented */

	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
