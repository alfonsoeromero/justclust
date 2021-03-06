package justclust.plugins.parsing;

import java.io.File;
import java.util.ArrayList;
import justclust.datastructures.Edge;
import justclust.datastructures.Node;
import justclust.plugins.configurationcontrols.PluginConfigurationControl;

/**
 * This interface defines classes which have instances which act as file
 * parsers.
 */
public interface FileParserPluginInterface {

    public String getFileType() throws Exception;

    public String getDescription() throws Exception;

    public ArrayList<PluginConfigurationControl> getConfigurationControls() throws Exception;

    /**
     * This method signature defines a method which parses a file.
     */
    public void parseFile(File file, ArrayList<Node> networkNodes, ArrayList<Edge> networkEdges) throws Exception;
    
    public boolean microarrayData() throws Exception;
    
    public ArrayList<String> microarrayHeaders() throws Exception;
    
}
