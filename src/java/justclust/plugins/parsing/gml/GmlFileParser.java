/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package justclust.plugins.parsing.gml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import justclust.datastructures.Cluster;
import justclust.datastructures.Edge;
import justclust.datastructures.Node;
import justclust.plugins.clustering.mcode.McodeClusteringAlgorithm;
import static justclust.plugins.clustering.mcode.McodeClusteringAlgorithm.classInstance;
import justclust.plugins.configurationcontrols.PluginConfigurationControl;
import justclust.plugins.parsing.FileParserPluginInterface;

/**
 *
 * @author wuaz008
 */
public class GmlFileParser implements FileParserPluginInterface {
    
    public static GmlFileParser classInstance;
    public ArrayList<Node> networkNodes;
    public ArrayList<Edge> networkEdges;
    public ArrayList<Cluster> networkClusters;

    public GmlFileParser() {
        classInstance = this;
    }

    public String getFileType() throws Exception {
        return "GML file parser";
    }

    public String getDescription() throws Exception {
        return "This file parser plug-in parses GML (.gml) files.";
    }

    public ArrayList<PluginConfigurationControl> getConfigurationControls() throws Exception {
        return new ArrayList<PluginConfigurationControl>();
    }

    public void parseFile(File file, ArrayList<Node> networkNodes, ArrayList<Edge> networkEdges) throws Exception {
        
        // these variables are made available here to the other classes in this
        // plug-in
        this.networkNodes = networkNodes;
        this.networkEdges = networkEdges;
        this.networkClusters = networkClusters;
        
        GmlNetworkReader gmlNetworkReader = new GmlNetworkReader(new FileInputStream(file));
        gmlNetworkReader.run();
        
    }

    public boolean microarrayData() throws Exception {
        return false;
    }

    public ArrayList<String> microarrayHeaders() throws Exception {
        return null;
    }
}