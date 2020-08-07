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


public class Q4b {
	/*mapper class*/
	public static class AvgMapper extends Mapper<Object, Text, IntWritable, DoubleWritable> {
		private IntWritable passCount = new IntWritable();
		private DoubleWritable fare = new DoubleWritable();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer iterator = new StringTokenizer(value.toString(), "\n");
			while (iterator.hasMoreTokens()) {
				String ln = iterator.nextToken();
				String[] tokens = ln.split("\t");

				if (tokens.length == 4) {
					/*set passerage count*/
					passCount.set(Integer.valueOf(tokens[2]));
					/*set total fare*/
					fare.set(Double.valueOf(tokens[3]));
					context.write(passCount, fare);
				}
			}

		}
	}


	public static class AvgReducer extends Reducer<IntWritable, DoubleWritable, IntWritable, DoubleWritable> {

		private DoubleWritable ans = new DoubleWritable();
		public void reduce(IntWritable key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
			int count = 0;
			double sum = 0;
			for (DoubleWritable value : values) {
				sum += (double)value.get();
				count += 1;
			}
			double avg = sum / (double) count;
			avg = (double)Math.round(avg * 100) / 100;
			ans.set(avg);
			context.write(key, ans);
		}
	}


	public static void main(String[] args) throws Exception {
		final String gtid = "jxiang37";
	    Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "Q4b");

		job.setJarByClass(Q4b.class);
		job.setMapperClass(AvgMapper.class);
		job.setCombinerClass(AvgReducer.class);
		job.setReducerClass(AvgReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(DoubleWritable.class);

	    /* TODO: Needs to be implemented */

	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
