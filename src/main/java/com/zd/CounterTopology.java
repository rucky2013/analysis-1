package com.zd;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.google.common.collect.ImmutableList;
import storm.kafka.*;

public class CounterTopology {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try{
            String kafkaZookeeper = "127.0.0.1:2181";
            BrokerHosts brokerHosts = new ZkHosts(kafkaZookeeper);
            SpoutConfig kafkaConfig = new SpoutConfig(brokerHosts, "mytopic", "/brokers", "id");
            kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
            kafkaConfig.zkServers =  ImmutableList.of("127.0.0.1");
            kafkaConfig.zkPort = 2181;


            //kafkaConfig.forceFromStart = true;

            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("spout", new KafkaSpout(kafkaConfig), 1);
            builder.setBolt("split", new SplitLogTestBolt(),1).allGrouping("spout");



            Config config = new Config();
            config.setDebug(true);
            config.setNumAckers(0);
            if(args!=null && args.length > 0) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                config.setNumWorkers(2);

                StormSubmitter.submitTopology(args[0], config, builder.createTopology());
            } else {
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                config.setMaxTaskParallelism(1);

                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("special-topology", config, builder.createTopology());


            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
