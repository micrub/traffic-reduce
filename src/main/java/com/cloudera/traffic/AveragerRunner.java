package com.cloudera.traffic;
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class AveragerRunner extends Configured implements Tool {

	private AveragerRunner() {
	} //

	public int run(String[] args) throws Exception {
		
		if (args.length < 2) {
			System.out.println("<inDir> <outDir>");
			ToolRunner.printGenericCommandUsage(System.out);
			return 2;
		}

		Configuration conf = getConf();
		Job grepJob = Job.getInstance(conf);

		try {

			grepJob.setJobName("averager");

			FileInputFormat.setInputPaths(grepJob, new Path(args[0]));
			FileOutputFormat.setOutputPath(grepJob, new Path(args[1]));
			
			grepJob.setJarByClass(AveragerRunner.class);
			grepJob.setMapperClass(AveragerMapper.class);
			grepJob.setReducerClass(AveragerReducer.class);
			grepJob.setCombinerClass(AveragerReducer.class);
			grepJob.setMapOutputKeyClass(Text.class);
			grepJob.setMapOutputValueClass(AverageWritable.class);
			grepJob.setInputFormatClass(TextInputFormat.class);
			
			grepJob.waitForCompletion(true);

		} finally {
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new AveragerRunner(),
				args);
		System.exit(res);
	}
}
