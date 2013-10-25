Traffic Induce
==============
This repository contains MapReduce programs for analyzing traffic data.  This
document contains instructions for compiling them and running them. Everything
below should be run from the project's root directory with .  
We assume an existing hadoop  CDH4 installed in pseudo distributed mode on Ubuntu 12.04, as well as maven and java.


## Building

    mvn install

## Placing the sample data on your cluster

    hadoop fs -mkdir trafficcounts
    hadoop fs -put samples/input.txt trafficcounts

## Running

    hadoop jar target/trafficinduce-1.0-SNAPSHOT.jar com.cloudera.traffic.AveragerRunner trafficcounts/input.txt trafficcounts/output

## Inspecting the output

    hadoop fs -cat trafficcounts/output/part-r-00000

## Running the MRUnit tests

    mvn test

