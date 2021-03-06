/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package justclust.plugins.visualisation.cytoscapedegreesortedcircle;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import justclust.datastructures.Data;
import justclust.datastructures.Cluster;
import justclust.datastructures.Edge;
import justclust.datastructures.Node;
import justclust.plugins.configurationcontrols.PluginConfigurationControl;
import justclust.plugins.visualisation.VisualisationLayoutPluginInterface;

/**
 *
 * @author wuaz008
 */
public class CytoscapeDegreeSortedCircleVisualisationLayout implements VisualisationLayoutPluginInterface {

    public String getName() throws Exception {
        return "Cytoscape degree sorted circle visualisation layout";
    }

    public String getDescription() throws Exception {
        return "This visualisation layout plug-in lays-out the graphical representation of the current network with the degree sorted circle layout from Cytoscape.";
    }

    public ArrayList<PluginConfigurationControl> getConfigurationControls() throws Exception {
        return new ArrayList<PluginConfigurationControl>();
    }
    public ArrayList<Node> networkNodes;
    public ArrayList<Edge> networkEdges;
    public ArrayList<Cluster> networkClusters;

    public void applyLayout(ArrayList<Node> networkNodes, ArrayList<Edge> networkEdges, final ArrayList<Cluster> networkClusters) throws Exception {

        // these variables are made available here to the other methods in this
        // plug-in
        this.networkNodes = networkNodes;
        this.networkEdges = networkEdges;
        this.networkClusters = networkClusters;

        // create an array.
        // one element for each node.
        // the element contains whether the node's connected component has been
        // visited yet by the below algorithm.
        // the purpose of the array is to prevent the algorithm from finding the
        // connected component of each node even when it has already been found.
        boolean[] componentFound = new boolean[networkNodes.size()];
        // this ArrayList will contain all the connected components
        ArrayList<ArrayList<Node>> connectedComponents = new ArrayList<ArrayList<Node>>();
        // iterate through each node
        for (int i = 0; i < networkNodes.size(); i++) {
            // if the node's component has not been found
            if (!componentFound[i]) {

                // get the node's connected component
                ArrayList<Node> component = new ArrayList<Node>();
                component = getConnectedComponent(component, networkNodes.get(i));

                // the componentFound array is updated for all nodes in the
                // component
                for (int j = 0; j < component.size(); j++) {
                    componentFound[networkNodes.indexOf(component.get(j))] = true;
                }

                connectedComponents.add(component);

            }
        }

        // iterate through all the connected components, and lay them out with
        // the degree sorted circle layout
        for (int i = 0; i < connectedComponents.size(); i++) {

//        // Create attribute
//        final CyTable table = network.getDefaultNodeTable();
//        if (table.getColumn(DEGREE_ATTR_NAME) == null) {
//            table.createColumn(DEGREE_ATTR_NAME, Integer.class, false);
//        }

            // just add the unlocked nodes
//        final List<LayoutNode> nodes = new ArrayList<LayoutNode>();
            final List<Node> nodes = new ArrayList<Node>();
//        for (final LayoutNode ln : partition.getNodeList()) {
            for (final Node ln : connectedComponents.get(i)) {
//            if (!ln.isLocked()) {
                nodes.add(ln);
//            }
            }

//        if (cancelled) {
//            return;
//        }

            // sort the Nodes based on the degree
//        Collections.sort(nodes, new Comparator<LayoutNode>() {
            Collections.sort(nodes, new Comparator<Node>() {
//            public int compare(LayoutNode o1, LayoutNode o2) {
                public int compare(Node o1, Node o2) {
//                final CyNode node1 = o1.getNode();
                    final Node node1 = o1;
//                final CyNode node2 = o2.getNode();
                    final Node node2 = o2;
                    // FIXME: should allow parametrization of edge type? (expose as
                    // tunable)
//                final int d1 = network.getAdjacentEdgeList(node1, CyEdge.Type.ANY).size();
                    int edgeAmount = 0;
                    for (Edge edge : node1.edges) {
                        if (networkClusters != null) {
                            if (edge.node1.cluster == edge.node2.cluster) {
                                edgeAmount++;
                                continue;
                            }
                        } else {
                            edgeAmount++;
                        }
                    }
                    final int d1 = edgeAmount;
//                final int d2 = network.getAdjacentEdgeList(node2, CyEdge.Type.ANY).size();
                    edgeAmount = 0;
                    for (Edge edge : node2.edges) {
                        if (networkClusters != null) {
                            if (edge.node1.cluster == edge.node2.cluster) {
                                edgeAmount++;
                                continue;
                            }
                        } else {
                            edgeAmount++;
                        }
                    }
                    final int d2 = edgeAmount;

                    // Create Degree Attribute
//                o1.getRow().set(DEGREE_ATTR_NAME, d1);
//                o2.getRow().set(DEGREE_ATTR_NAME, d2);

                    return (d2 - d1);
                }
//                public boolean equals(Object o) {
//                    return false;
//                }
            });

//        if (cancelled) {
//            return;
//        }

            // place each Node in a circle
            int r = 100 * (int) Math.sqrt(nodes.size());
            double phi = (2 * Math.PI) / nodes.size();
//        partition.resetNodes(); // We want to figure out our mins & maxes anew

            for (int j = 0; j < nodes.size(); j++) {
//            LayoutNode node = nodes.get(i);
                Node node = nodes.get(j);
//            node.setX(r + (r * Math.sin(i * phi)));
//            node.setY(r + (r * Math.cos(i * phi)));
//            partition.moveNodeToLocation(node);
                node.setGraphicalNodeXCoordinate(r + (r * Math.sin(j * phi)));
                node.setGraphicalNodeYCoordinate(r + (r * Math.cos(j * phi)));
            }

        }

        // the connected components are spaced-out in rows in order of the amount of nodes they contain with
        // the largest component having its own row on top, and all subsequent rows
        // being roughly the width of the top row
        layoutConnectedComponents();

    }

    // the connected components are spaced-out in rows in order of area with
    // from largest to smallest.
    // the layout is approximately as square as possible.
    public void layoutConnectedComponents() {

        // this ArrayList will contain all the connected components in order of
        // area from largest to smallest
        ArrayList<ArrayList<Node>> connectedComponents = new ArrayList<ArrayList<Node>>();

        // create an array.
        // one element for each node.
        // each element contains whether the corresponding node's connected
        // component has been added to connectedComponents.
        // the purpose of the array is to avoid computing the components more
        // than once for different nodes in the same component.
        // the elements are initialized to false by default.
        boolean[] componentFound = new boolean[networkNodes.size()];

        double[] componentTopMostYs = new double[networkNodes.size()];
        double[] componentRightMostXs = new double[networkNodes.size()];
        double[] componentBottomMostYs = new double[networkNodes.size()];
        double[] componentLeftMostXs = new double[networkNodes.size()];

        double componentsTotalWidth = 0;
        double componentsTotalArea = 0;
        // iterate through each node
        for (int i = 0; i < networkNodes.size(); i++) {
            // if the node's component has not been found
            if (!componentFound[i]) {

                // get the node's connected component
                ArrayList<Node> component = new ArrayList<Node>();
                component = getConnectedComponent(component, networkNodes.get(i));

                // find the nodes coordinates and initialize currentComponentTopMostY,
                // currentComponentRightMostX, currentComponentBottomMostY, and
                // currentComponentLeftMostX with them.
                // these variables will hold the top-most y, right-most x,
                // bottom-most y, and left-most x coordinates of the connected
                // component.
                Node node = networkNodes.get(i);
                double currentComponentTopMostY = node.getGraphicalNodeYCoordinate();
                double currentComponentRightMostX = node.getGraphicalNodeXCoordinate();
                double currentComponentBottomMostY = node.getGraphicalNodeYCoordinate();
                double currentComponentLeftMostX = node.getGraphicalNodeXCoordinate();
                // currentComponentTopMostY, currentComponentRightMostX,
                // currentComponentBottomMostY, and currentComponentLeftMostX
                // are updated by iterating through the nodes of the component.
                // the index, j, starts at one because the first node has
                // been considered.
                for (int j = 1; j < component.size(); j++) {
                    node = component.get(j);
                    currentComponentTopMostY = Math.min(currentComponentTopMostY, node.getGraphicalNodeYCoordinate());
                    currentComponentRightMostX = Math.max(currentComponentRightMostX, node.getGraphicalNodeXCoordinate());
                    currentComponentBottomMostY = Math.max(currentComponentBottomMostY, node.getGraphicalNodeYCoordinate());
                    currentComponentLeftMostX = Math.min(currentComponentLeftMostX, node.getGraphicalNodeXCoordinate());
                }

                // the componentFound array and the coordinates arrays are
                // updated for all nodes in the component
                for (int j = 0; j < component.size(); j++) {
                    componentFound[networkNodes.indexOf(component.get(j))] = true;
                    componentTopMostYs[networkNodes.indexOf(component.get(j))] = currentComponentTopMostY;
                    componentRightMostXs[networkNodes.indexOf(component.get(j))] = currentComponentRightMostX;
                    componentBottomMostYs[networkNodes.indexOf(component.get(j))] = currentComponentBottomMostY;
                    componentLeftMostXs[networkNodes.indexOf(component.get(j))] = currentComponentLeftMostX;
                }

                // the width of the component is added to componentsTotalWidth.
                // the additional 100 is the space between components and is
                // also included to make calculations more accurate.
                componentsTotalWidth += currentComponentRightMostX - currentComponentLeftMostX + 100;
                // the area of the component is added to componentsTotalArea.
                // the additional 100 is the space between components and is
                // included to make calculations more accurate.
                componentsTotalArea += (currentComponentBottomMostY - currentComponentTopMostY + 100) * (currentComponentRightMostX - currentComponentLeftMostX + 100);

                // the component is added to the ArrayList of components in
                // order of amount of nodes from largest to smallest amount of
                // nodes
                int j = 0;
                while (j < connectedComponents.size()) {
                    // if the amount of nodes of the current component is less
                    // than the amount of nodes of the component at index j in
                    // connectedComponents, j is increased and the loop is
                    // repeated, otherwise, the loop is broken out of.
                    // this is because the current component should be inserted
                    // in connectedComponents in order of largest amount of
                    // nodes to smallest amount of nodes.
                    if (component.size() < connectedComponents.get(j).size()) {
                        j++;
                    } else {
                        break;
                    }
                }
                connectedComponents.add(j, component);

            }
        }

        // if the current network has been clustered
        if (networkClusters != null) {
            // connectedComponents is populated with the clusters.
            // this is done so that the clusters are laid-out in the
            // same order as they are displayed in the Network Clusters
            // dialog and elsewhere.
            connectedComponents.clear();
            for (Cluster cluster : networkClusters) {
                connectedComponents.add((ArrayList<Node>) cluster.nodes.clone());
            }
        }

        // the average width which each row of components should have is
        // calculated by finding a number which gives the same result when
        // multiplying the average height of the components by it, and when
        // dividing the total width of the components by it.
        // this number represents the amount of rows of components in the layout
        // and is contained in approximateRowAmount.
        // the reason this way of calculating the approximate width is useful,
        // is that the approximate width of each row of components should be
        // similar to the approximate height of all the rows (so the components
        // are laid-out as square as possible).
        double averageComponentWidth = componentsTotalWidth / connectedComponents.size();
        // the value of componentsTotalHeightAdjusted is not exactly the total
        // height of all the components.
        // if there are many components with smaller widths than
        // averageComponentWidth, componentsTotalHeightAdjusted will be larger
        // than the real total height and vice versa.
        double componentsTotalHeightAdjusted = componentsTotalArea / averageComponentWidth;
        double averageComponentHeightAdjusted = componentsTotalHeightAdjusted / connectedComponents.size();
        double approximateRowAmount = Math.sqrt(componentsTotalWidth / averageComponentHeightAdjusted);
        double averageComponentRowWidth = componentsTotalWidth / approximateRowAmount;

        // all the components are laid out in rows.
        // currentComponentLeftMostX contains the left-most x coordinate which
        // the current component should have, its left edge should be inline
        // with this coordinate.
        double currentComponentLeftMostX = 0;
        // currentRowBottomMostY contains the bottom-most y coordinate of all the
        // connected components on the current row.
        // this indicates where the next row of components should be placed
        // (100 pixels below).
        double currentRowBottomMostY = 0;
        // currentRowTopMostY is a variable which keeps track of where the
        // components in the current row should be placed.
        // each component should have its top edge inline with this coordinate.
        double currentRowTopMostY = 0;

        // iterate through all the connected components, and lay them out.
        for (int i = 0; i < connectedComponents.size(); i++) {

            // the index of the first node in the component
            int firstNodeIndex = networkNodes.indexOf(connectedComponents.get(i).get(0));

            // if, when the current component is laid-out in the current row,
            // the row will be too wide, a new row of components is begun
            double currentComponentWidth = componentRightMostXs[firstNodeIndex] - componentLeftMostXs[firstNodeIndex];
            // rowWidth + currentComponentWidth / 2 is used instead of just
            // rowWidth, so that the average width of the rows will tend more
            // towards rowWidth rather than all the rows being less than
            // rowWidth
            if (currentComponentLeftMostX + currentComponentWidth > averageComponentRowWidth + currentComponentWidth / 2) {
                currentComponentLeftMostX = 0;
                currentRowTopMostY = currentRowBottomMostY + 100;
            }

            // if the current component has an equal or smaller area to 1 tenth
            // of the previous component's area, a new row of components is
            // begun.
            // this simply separates the components into groups based on their
            // area.
            if (i > 0) {
                // 100 is added to the width and height in computing the area.
                // this is mainly to stop components with one node and no area
                // starting a new line every time.
                double currentComponentArea = (componentRightMostXs[firstNodeIndex] - componentLeftMostXs[firstNodeIndex] + 100) * (componentBottomMostYs[firstNodeIndex] - componentTopMostYs[firstNodeIndex] + 100);
                int firstNodePreviousComponentIndex = networkNodes.indexOf(connectedComponents.get(i - 1).get(0));
                double previousComponentArea = (componentRightMostXs[firstNodePreviousComponentIndex] - componentLeftMostXs[firstNodePreviousComponentIndex] + 100) * (componentBottomMostYs[firstNodePreviousComponentIndex] - componentTopMostYs[firstNodePreviousComponentIndex] + 100);
                if (currentComponentArea <= previousComponentArea / 10) {
                    currentComponentLeftMostX = 0;
                    currentRowTopMostY = currentRowBottomMostY + 100;
                }
            }

            // iterate through the nodes of the component and lay them out
            // in their new positions
            for (int j = 0; j < connectedComponents.get(i).size(); j++) {

                Node node = connectedComponents.get(i).get(j);
                // currentComponentLeftMostX represents the
                // left-most x coordinate of the current component.
                // node.getGraphicalNodeXCoordinate() - componentLeftMostXs[firstNodeIndex]
                // represents the x coordinate of the current node relative to
                // the left-most x coordinate of the current component.
                // the next expression for y coordinates is analagous.
                node.setGraphicalNodeXCoordinate(currentComponentLeftMostX + node.getGraphicalNodeXCoordinate() - componentLeftMostXs[firstNodeIndex]);
                node.setGraphicalNodeYCoordinate(currentRowTopMostY + node.getGraphicalNodeYCoordinate() - componentTopMostYs[firstNodeIndex]);

            }

            // these variables are updated accordingly, ready for the next
            // component.
            // componentRightMostXs[firstNodeIndex] - componentLeftMostXs[firstNodeIndex]
            // gives the width of the current component.
            // the additional 100 is the gap between components.
            currentComponentLeftMostX += componentRightMostXs[firstNodeIndex] - componentLeftMostXs[firstNodeIndex] + 100;
            // componentBottomMostYs[firstNodeIndex] - componentTopMostYs[firstNodeIndex]
            // gives the height of the current component
            currentRowBottomMostY = Math.max(currentRowBottomMostY, currentRowTopMostY + componentBottomMostYs[firstNodeIndex] - componentTopMostYs[firstNodeIndex]);

        }
    }

    // create an ArrayList of all the nodes in the same connected
    // component as the current node.
    // this method is recursive.
    public ArrayList<Node> getConnectedComponent(ArrayList<Node> connectedComponent, Node currentNode) {

        // add the currentNode to the component
        connectedComponent.add(currentNode);

        // for each neighbour of the current node, if the neighbour has not been
        // added to the component, add it, then call this method again to check
        // all of its neighbours
        for (Edge edge : currentNode.edges) {

            // if the network has been clustered, edges between nodes which are
            // not in the same cluster should be ignored because clusters are
            // represented by edges connecting their nodes.
            // this code checks networkClusters != null, which means that the
            // network has been clustered.
            if (networkClusters != null) {
                if (edge.node1.cluster != edge.node2.cluster) {
                    continue;
                }
            }

            // the two outer if statements are to check which node is the
            // current node and which is the neighbouring node for the edge
            if (edge.node1.equals(currentNode)) {
                if (!connectedComponent.contains(edge.node2)) {
                    getConnectedComponent(connectedComponent, edge.node2);
                }
            } else if (edge.node2.equals(currentNode)) {
                if (!connectedComponent.contains(edge.node1)) {
                    getConnectedComponent(connectedComponent, edge.node1);
                }
            }
        }

        return connectedComponent;

    }
}
