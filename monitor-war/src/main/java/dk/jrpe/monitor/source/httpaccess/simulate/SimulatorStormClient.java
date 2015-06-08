package dk.jrpe.monitor.source.httpaccess.simulate;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import dk.jrpe.monitor.source.httpaccess.storm.HTTPAccessCXFAsyncWebServiceBolt;
import dk.jrpe.monitor.source.httpaccess.storm.HTTPAccessCXFSyncWebServiceBolt;
import dk.jrpe.monitor.source.httpaccess.storm.HTTPAccessSpout;
import dk.jrpe.monitor.source.httpaccess.storm.HTTPAccessWebSocketBolt;

public class SimulatorStormClient {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("HTTPAccessSource", new HTTPAccessSpout(),2);
        builder.setBolt("CXF Sync. Web-service destination", new HTTPAccessCXFSyncWebServiceBolt(), 1).shuffleGrouping("HTTPAccessSource");
        builder.setBolt("CXF Async. Web-service destination", new HTTPAccessCXFAsyncWebServiceBolt(), 1).shuffleGrouping("HTTPAccessSource");
        builder.setBolt("Web-socket destination", new HTTPAccessWebSocketBolt(), 1).shuffleGrouping("HTTPAccessSource");

        Config conf = new Config();
        conf.setNumWorkers(1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, builder.createTopology());
        Utils.sleep(1000000);
        cluster.killTopology("test");
        cluster.shutdown();
    }
}