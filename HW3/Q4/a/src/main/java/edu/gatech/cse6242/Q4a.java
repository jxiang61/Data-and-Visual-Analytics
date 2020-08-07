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




public class Q4a {
	/*write node ID and 1/-1 to reducer*/
	public static class TokenMapper extends Mapper<Object, Text, IntWritable, IntWritable> {
		private IntWritable out = new IntWritable(1);
		private IntWritable in = new IntWritable(-1);
		private IntWritable sourceNode = new IntWritable();
		private IntWritable targetNode = new IntWritable();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer iterator = new StringTokenizer(value.toString(), "\n");
			while (iterator.hasMoreTokens()) {
				String ln = iterator.nextToken();
				String[] tokens = ln.split("\t");

				if (tokens.length == 4) {
					/*write source node to reducer*/
					sourceNode.set(Integer.valueOf(tokens[0]));
					context.write(sourceNode, out);
					/*write target node to reducer*/
					targetNode.set(Integer.valueOf(tokens[1]));
					context.write(targetNode, in);
				}
			}
		}
	}

	public static class TokenReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
		private IntWritable degree = new IntWritable();

		public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int subDegree = 0;
			for (IntWritable v : values) {
				subDegree += (int)v.get();
			}
			degree.set(subDegree);
			context.write(key, degree);
		}
	}

	public static class DiffMapper extends Mapper<Object, Text, IntWritable, IntWritable> {
		private IntWritable count = new IntWritable(1);
		private IntWritable degree = new IntWritable();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer iterator = new StringTokenizer(value.toString(), "\n");
			while (iterator.hasMoreTokens()) {
				String ln = iterator.nextToken();
				String[] tokens = ln.split("\t");
				/*change the format and re-write it to reducer*/
				int tmp = Integer.valueOf(tokens[1]);
				degree.set(tmp);
				context.write(degree, count);
			}
		}
	}

	public static class DiffReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
		private IntWritable ans = new IntWritable();

		public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int total = 0;
			for (IntWritable v : values) {
				total += (int) v.get();
			}
			ans.set(total);
			context.write(key, ans);

		}
	}



	public static void main(String[] args) throws Exception {
		final String gtid = "jxiang37";
	    Configuration conf = new Configuration();
	    Job job1 = Job.getInstance(conf, "Q4a_1");

		job1.setJarByClass(Q4a.class);
		job1.setMapperClass(TokenMapper.class);
		job1.setCombinerClass(TokenReducer.class);
		job1.setReducerClass(TokenReducer.class);
		job1.setOutputKeyClass(IntWritable.class);
		job1.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(job1, new Path(args[0]));
		FileOutputFormat.setOutputPath(job1, new Path("output"));
		job1.waitForCompletion(true);

	    /* TODO: Needs to be implemented */
		Job job2 = Job.getInstance(conf, "Q4a_2");

		job2.setJarByClass(Q4a.class);
		job2.setMapperClass(DiffMapper.class);
		job2.setCombinerClass(DiffReducer.class);
		job2.setReducerClass(DiffReducer.class);
		job2.setOutputKeyClass(IntWritable.class);
		job2.setOutputValueClass(IntWritable.class);

	    FileInputFormat.addInputPath(job2, new Path("output"));
	    FileOutputFormat.setOutputPath(job2, new Path(args[1]));
	    System.exit(job2.waitForCompletion(true) ? 0 : 1);
	}
}
