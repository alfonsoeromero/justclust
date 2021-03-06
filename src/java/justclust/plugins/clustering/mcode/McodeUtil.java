/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package justclust.plugins.clustering.mcode;

// TODO refactor: remove circular dependencies
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import justclust.datastructures.Data;
import justclust.datastructures.Edge;
import justclust.datastructures.Node;

/**
 * Utilities for MCODE
 */
public class McodeUtil {

//    private final RenderingEngineFactory<CyNetwork> renderingEngineFactory;
//    private final CyNetworkViewFactory networkViewFactory;
//    private final CyRootNetworkManager rootNetworkMgr;
//    private final CyApplicationManager applicationMgr;
//    private final CyNetworkViewManager networkViewMgr;
//    private final CyNetworkManager networkMgr;
//    private final VisualStyleFactory visualStyleFactory;
//    private final VisualMappingManager visualMappingMgr;
//    private final CySwingApplication swingApplication;
//    private final CyEventHelper eventHelper;
//    private final VisualMappingFunctionFactory discreteMappingFactory;
//    private final VisualMappingFunctionFactory continuousMappingFactory;
//    private final FileUtil fileUtil;
//    private final Properties props;
    private boolean interrupted;
    private Image placeHolderImage;
//    private VisualStyle clusterStyle;
//    private VisualStyle appStyle;
//    private MCODECurrentParameters currentParameters;
    private McodeCurrentParameters currentParameters;
    // Keeps track of networks (id is key) and their respective algorithms
//    private Map<Long, MCODEAlgorithm> networkAlgorithms;
    private Map<Long, McodeAlgorithm> networkAlgorithms;
    // Keeps track of networks (id is key) and their respective results (list of result ids)
    private Map<Long, Set<Integer>> networkResults;
    private int currentResultId;
//    private Map<CyRootNetwork, Set<CySubNetwork>> createdSubNetworks;
    private Map<ArrayList<Node>, Set<ArrayList<Node>>> createdSubNetworks;
//    private static final Logger logger = LoggerFactory.getLogger(MCODEUtil.class);

//    public MCODEUtil(final RenderingEngineFactory<CyNetwork> renderingEngineFactory,
//            final CyNetworkViewFactory networkViewFactory,
//            final CyRootNetworkManager rootNetworkMgr,
//            final CyApplicationManager applicationMgr,
//            final CyNetworkManager networkMgr,
//            final CyNetworkViewManager networkViewMgr,
//            final VisualStyleFactory visualStyleFactory,
//            final VisualMappingManager visualMappingMgr,
//            final CySwingApplication swingApplication,
//            final CyEventHelper eventHelper,
//            final VisualMappingFunctionFactory discreteMappingFactory,
//            final VisualMappingFunctionFactory continuousMappingFactory,
//            final FileUtil fileUtil) {
    public McodeUtil() {

        this.reset();
    }

    public int getCurrentResultId() {
        return currentResultId;
    }

//    public String getProperty(String key) {
//        return props.getProperty(key);
//    }
    public void reset() {
        currentResultId = 1;
//        currentParameters = new MCODECurrentParameters();
        currentParameters = new McodeCurrentParameters();
//        networkAlgorithms = new HashMap<Long, MCODEAlgorithm>();
        networkAlgorithms = new HashMap<Long, McodeAlgorithm>();
        networkResults = new HashMap<Long, Set<Integer>>();
//        createdSubNetworks = new HashMap<CyRootNetwork, Set<CySubNetwork>>();
        createdSubNetworks = new HashMap<ArrayList<Node>, Set<ArrayList<Node>>>();
    }

//    public synchronized void destroyUnusedNetworks(final CyNetwork network, final List<MCODECluster> clusters) {
    public synchronized void destroyUnusedNetworks(final ArrayList<Node> networkNodes, final ArrayList<Edge> networkEdges, final List<McodeCluster> clusters) {
//        final Map<CySubNetwork, Boolean> clusterNetworks = new HashMap<CySubNetwork, Boolean>();
        final Map<ArrayList<Node>, Boolean> clusterNetworks = new HashMap<ArrayList<Node>, Boolean>();

        if (clusters != null) {
//            for (MCODECluster c : clusters) {
            for (McodeCluster c : clusters) {
                clusterNetworks.put(c.getNetwork(), Boolean.TRUE);
            }
        }

//        final CyRootNetwork rootNet = rootNetworkMgr.getRootNetwork(network);
        final ArrayList<Node> rootNet = networkNodes;
//        final Set<CySubNetwork> snSet = createdSubNetworks.get(rootNet);
        final Set<ArrayList<Node>> snSet = createdSubNetworks.get(rootNet);

        if (snSet != null) {
//            final Set<CySubNetwork> disposedSet = new HashSet<CySubNetwork>();
            final Set<ArrayList<Node>> disposedSet = new HashSet<ArrayList<Node>>();

//            for (final CySubNetwork sn : snSet) {
            for (final ArrayList<Node> sn : snSet) {
                // Only remove the subnetwork if it is not registered
//                if (!clusterNetworks.containsKey(sn) && !networkMgr.networkExists(sn.getSUID())) {
                if (!clusterNetworks.containsKey(sn)) {
                    try {
                        destroy(sn);
                        disposedSet.add(sn);
                    } catch (Exception e) {
//                        logger.error("Error disposing: " + sn, e);
                    }
                }
            }

            snSet.removeAll(disposedSet);
        }
    }

//    public void destroy(final CySubNetwork net) {
    public void destroy(final ArrayList<Node> net) {
        if (net != null) {
//            final CyRootNetwork rootNet = rootNetworkMgr.getRootNetwork(net);
            final ArrayList<Node> rootNet = (ArrayList<Node>) McodeClusteringAlgorithm.classInstance.networkNodes.clone();

//            if (rootNet.containsNetwork(net)) {
            if (rootNet.contains(net)) {
//                rootNet.removeSubNetwork(net);
                rootNet.remove(net);
//                net.dispose();
                net.clear();
            }
        }
    }
//    public MCODECurrentParameters getCurrentParameters() {

    public McodeCurrentParameters getCurrentParameters() {
        return currentParameters;
    }

    public boolean containsNetworkAlgorithm(final long suid) {
        return networkAlgorithms.containsKey(suid);
    }

//    public MCODEAlgorithm getNetworkAlgorithm(final long suid) {
    public McodeAlgorithm getNetworkAlgorithm(final long suid) {
        return networkAlgorithms.get(suid);
    }

//    public void addNetworkAlgorithm(final long suid, final MCODEAlgorithm alg) {
    public void addNetworkAlgorithm(final long suid, final McodeAlgorithm alg) {
        networkAlgorithms.put(suid, alg);
    }

    public void removeNetworkAlgorithm(final long suid) {
        networkAlgorithms.remove(suid);
    }

    public boolean containsNetworkResult(final long suid) {
        return networkResults.containsKey(suid);
    }

    public Set<Integer> getNetworkResults(final long suid) {
        Set<Integer> ids = networkResults.get(suid);

        return ids != null ? ids : new HashSet<Integer>();
    }

    public void addNetworkResult(final long suid) {
        Set<Integer> ids = networkResults.get(suid);

        if (ids == null) {
            ids = new HashSet<Integer>();
            networkResults.put(suid, ids);
        }

        ids.add(currentResultId++);
    }

    public boolean removeNetworkResult(final int resultId) {
        boolean removed = false;
        Long networkId = null;

        for (Entry<Long, Set<Integer>> entries : networkResults.entrySet()) {
            Set<Integer> ids = entries.getValue();

            if (ids.remove(resultId)) {
                if (ids.isEmpty()) {
                    networkId = entries.getKey();
                }

                removed = true;
                break;
            }
        }

        if (networkId != null) {
            networkResults.remove(networkId);
        }

        getCurrentParameters().removeResultParams(resultId);

        return removed;
    }

//    /**
//     * Convert a network to an image. This is used by the MCODEResultsPanel.
//     *
//     * @param cluster Input network to convert to an image
//     * @param height Height that the resulting image should be
//     * @param width Width that the resulting image should be
//     * @param layouter Reference to the layout algorithm
//     * @param layoutNecessary Determinant of cluster size growth or shrinkage,
//     * the former requires layout
//     * @param loader Graphic loader displaying progress and process
//     * @return The resulting image
//     */
//    public Image createClusterImage(final MCODECluster cluster,
//            final int height,
//            final int width,
//            SpringEmbeddedLayouter layouter,
//            boolean layoutNecessary,
//            final MCODELoader loader) {
//        final CyNetwork net = cluster.getNetwork();
//
//        // Progress reporters.
//        // There are three basic tasks, the progress of each is calculated and then combined
//        // using the respective weighting to get an overall progress global progress
//        int weightSetupNodes = 20; // setting up the nodes and edges is deemed as 25% of the whole task
//        int weightSetupEdges = 5;
//        double weightLayout = 75.0; // layout it is 70%
//        double goalTotal = weightSetupNodes + weightSetupEdges;
//
//        if (layoutNecessary) {
//            goalTotal += weightLayout;
//        }
//
//        // keeps track of progress as a percent of the totalGoal
//        double progress = 0;
//
//        final VisualStyle vs = getClusterStyle();
//        final CyNetworkView clusterView = createNetworkView(net, vs);
//
//        clusterView.setVisualProperty(NETWORK_WIDTH, new Double(width));
//        clusterView.setVisualProperty(NETWORK_HEIGHT, new Double(height));
//
//        for (View<CyNode> nv : clusterView.getNodeViews()) {
//            if (interrupted) {
//                logger.debug("Interrupted: Node Setup");
//                // before we short-circuit the method we reset the interruption so that the method can run without
//                // problems the next time around
//                if (layouter != null) {
//                    layouter.resetDoLayout();
//                }
//                resetLoading();
//
//                return null;
//            }
//
//            // Node position
//            final double x;
//            final double y;
//
//            // First we check if the MCODECluster already has a node view of this node (posing the more generic condition
//            // first prevents the program from throwing a null pointer exception in the second condition)
//            if (cluster.getView() != null && cluster.getView().getNodeView(nv.getModel()) != null) {
//                //If it does, then we take the layout position that was already generated for it
//                x = cluster.getView().getNodeView(nv.getModel()).getVisualProperty(NODE_X_LOCATION);
//                y = cluster.getView().getNodeView(nv.getModel()).getVisualProperty(NODE_Y_LOCATION);
//            } else {
//                // Otherwise, randomize node positions before layout so that they don't all layout in a line
//                // (so they don't fall into a local minimum for the SpringEmbedder)
//                // If the SpringEmbedder implementation changes, this code may need to be removed
//                // size is small for many default drawn graphs, thus +100
//                x = (clusterView.getVisualProperty(NETWORK_WIDTH) + 100) * Math.random();
//                y = (clusterView.getVisualProperty(NETWORK_HEIGHT) + 100) * Math.random();
//
//                if (!layoutNecessary) {
//                    goalTotal += weightLayout;
//                    progress /= (goalTotal / (goalTotal - weightLayout));
//                    layoutNecessary = true;
//                }
//            }
//
//            nv.setVisualProperty(NODE_X_LOCATION, x);
//            nv.setVisualProperty(NODE_Y_LOCATION, y);
//
//            // Node shape
//            if (cluster.getSeedNode() == nv.getModel().getSUID()) {
//                nv.setLockedValue(NODE_SHAPE, NodeShapeVisualProperty.RECTANGLE);
//            } else {
//                nv.setLockedValue(NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);
//            }
//
//            // Update loader
//            if (loader != null) {
//                progress += 100.0 * (1.0 / (double) clusterView.getNodeViews().size())
//                        * ((double) weightSetupNodes / (double) goalTotal);
//                loader.setProgress((int) progress, "Setup: nodes");
//            }
//        }
//
//        if (clusterView.getEdgeViews() != null) {
//            for (int i = 0; i < clusterView.getEdgeViews().size(); i++) {
//                if (interrupted) {
//                    logger.error("Interrupted: Edge Setup");
//                    if (layouter != null) {
//                        layouter.resetDoLayout();
//                    }
//                    resetLoading();
//
//                    return null;
//                }
//
//                if (loader != null) {
//                    progress += 100.0 * (1.0 / (double) clusterView.getEdgeViews().size())
//                            * ((double) weightSetupEdges / (double) goalTotal);
//                    loader.setProgress((int) progress, "Setup: edges");
//                }
//            }
//        }
//
//        if (layoutNecessary) {
//            if (layouter == null) {
//                layouter = new SpringEmbeddedLayouter();
//            }
//
//            layouter.setGraphView(clusterView);
//
//            // The doLayout method should return true if the process completes without interruption
//            if (!layouter.doLayout(weightLayout, goalTotal, progress, loader)) {
//                // Otherwise, if layout is not completed, set the interruption to false, and return null, not an image
//                resetLoading();
//
//                return null;
//            }
//        }
//
//        final Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        final Graphics2D g = (Graphics2D) image.getGraphics();
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final Dimension size = new Dimension(width, height);
//
//                    JPanel panel = new JPanel();
//                    panel.setPreferredSize(size);
//                    panel.setSize(size);
//                    panel.setMinimumSize(size);
//                    panel.setMaximumSize(size);
//                    panel.setBackground((Color) vs.getDefaultValue(NETWORK_BACKGROUND_PAINT));
//
//                    JWindow window = new JWindow();
//                    window.getContentPane().add(panel, BorderLayout.CENTER);
//
//                    RenderingEngine<CyNetwork> re = renderingEngineFactory.createRenderingEngine(panel, clusterView);
//
//                    vs.apply(clusterView);
//                    clusterView.fitContent();
//                    clusterView.updateView();
//                    window.pack();
//                    window.repaint();
//
//                    re.createImage(width, height);
//                    re.printCanvas(g);
//                    g.dispose();
//
//                    if (clusterView.getNodeViews().size() > 0) {
//                        cluster.setView(clusterView);
//                    }
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        });
//
//        layouter.resetDoLayout();
//        resetLoading();
//
//        return image;
//    }
//    public MCODEGraph createGraph(final CyNetwork net, final Collection<CyNode> nodes) {
    public McodeGraph createGraph(ArrayList<Node> networkNodes, ArrayList<Edge> networkEdges, final Collection<Node> nodes) {
//        final CyRootNetwork root = rootNetworkMgr.getRootNetwork(net);
//        final Set<CyEdge> edges = new HashSet<CyEdge>();
        final Set<Edge> edges = new HashSet<Edge>();

//        for (final CyNode n : nodes) {
        for (final Node n : nodes) {
//            final Set<CyEdge> adjacentEdges = new HashSet<CyEdge>(net.getAdjacentEdgeList(n, CyEdge.Type.ANY));
            final ArrayList<Edge> adjacentEdgesArrayList = new ArrayList<Edge>();
            for (Edge edge : n.edges) {
                if (edge.node1 == n && McodeClusteringAlgorithm.classInstance.networkNodes.contains(n) && McodeClusteringAlgorithm.classInstance.networkEdges.contains(edge) && edge.node1 != edge.node2) {
                    adjacentEdgesArrayList.add(edge);
                }
                if (edge.node2 == n && McodeClusteringAlgorithm.classInstance.networkNodes.contains(n) && McodeClusteringAlgorithm.classInstance.networkEdges.contains(edge) && edge.node1 != edge.node2) {
                    adjacentEdgesArrayList.add(edge);
                }
            }
            final Set<Edge> adjacentEdges = new HashSet<Edge>(adjacentEdgesArrayList);

            // Get only the edges that connect nodes that belong to the subnetwork:
//            for (final CyEdge e : adjacentEdges) {
            for (final Edge e : adjacentEdges) {
//                if (nodes.contains(e.getSource()) && nodes.contains(e.getTarget())) {
                if (nodes.contains(e.node1) && nodes.contains(e.node2) && !edges.contains(e)) {
                    edges.add(e);
                }
            }
        }

//        final MCODEGraph graph = new MCODEGraph(root, nodes, edges, this); // TODO remove circular dependency MCODEUtil/MCODEGraph
        final McodeGraph graph = new McodeGraph(networkNodes, networkEdges, nodes, edges, this); // TODO remove circular dependency MCODEUtil/MCODEGraph

        return graph;
    }

//    public CySubNetwork createSubNetwork(final CyNetwork net, final Collection<CyNode> nodes, final SavePolicy policy) {
//        final CyRootNetwork root = rootNetworkMgr.getRootNetwork(net);
//        final Set<CyEdge> edges = new HashSet<CyEdge>();
//
//        for (CyNode n : nodes) {
//            Set<CyEdge> adjacentEdges = new HashSet<CyEdge>(net.getAdjacentEdgeList(n, CyEdge.Type.ANY));
//
//            // Get only the edges that connect nodes that belong to the subnetwork:
//            for (CyEdge e : adjacentEdges) {
//                if (nodes.contains(e.getSource()) && nodes.contains(e.getTarget())) {
//                    edges.add(e);
//                }
//            }
//        }
//
//        final CySubNetwork subNet = root.addSubNetwork(nodes, edges, policy);
//
//        // Save it for later disposal
//        Set<CySubNetwork> snSet = createdSubNetworks.get(root);
//
//        if (snSet == null) {
//            snSet = new HashSet<CySubNetwork>();
//            createdSubNetworks.put(root, snSet);
//        }
//
//        snSet.add(subNet);
//
//        return subNet;
//    }
//    public CyNetworkView createNetworkView(final CyNetwork net, VisualStyle vs) {
//        final CyNetworkView view = networkViewFactory.createNetworkView(net);
//
//        if (vs == null) {
//            vs = visualMappingMgr.getDefaultVisualStyle();
//        }
//        visualMappingMgr.setVisualStyle(vs, view);
//        vs.apply(view);
//        view.updateView();
//
//        return view;
//    }
//    public void displayNetworkView(final CyNetworkView view) {
//        networkMgr.addNetwork(view.getModel());
//        networkViewMgr.addNetworkView(view);
//
//        view.fitContent();
//        view.updateView();
//    }
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public VisualStyle getClusterStyle() {
//        if (clusterStyle == null) {
//            clusterStyle = visualStyleFactory.createVisualStyle("MCODE Cluster");
//
//            clusterStyle.setDefaultValue(NODE_SIZE, 40.0);
//            clusterStyle.setDefaultValue(NODE_WIDTH, 40.0);
//            clusterStyle.setDefaultValue(NODE_HEIGHT, 40.0);
//            clusterStyle.setDefaultValue(NODE_PAINT, Color.RED);
//            clusterStyle.setDefaultValue(NODE_FILL_COLOR, Color.RED);
//            clusterStyle.setDefaultValue(NODE_BORDER_WIDTH, 0.0);
//
//            clusterStyle.setDefaultValue(EDGE_WIDTH, 5.0);
//            clusterStyle.setDefaultValue(EDGE_PAINT, Color.BLUE);
//            clusterStyle.setDefaultValue(EDGE_UNSELECTED_PAINT, Color.BLUE);
//            clusterStyle.setDefaultValue(EDGE_STROKE_UNSELECTED_PAINT, Color.BLUE);
//            clusterStyle.setDefaultValue(EDGE_SELECTED_PAINT, Color.BLUE);
//            clusterStyle.setDefaultValue(EDGE_STROKE_SELECTED_PAINT, Color.BLUE);
//            clusterStyle.setDefaultValue(EDGE_STROKE_SELECTED_PAINT, Color.BLUE);
//
//            VisualLexicon lexicon = applicationMgr.getCurrentRenderingEngine().getVisualLexicon();
//            VisualProperty vp = lexicon.lookup(CyEdge.class, "edgeTargetArrowShape");
//
//            if (vp != null) {
//                Object arrowValue = vp.parseSerializableString("ARROW");
//                if (arrowValue != null) {
//                    clusterStyle.setDefaultValue(vp, arrowValue);
//                }
//            }
//        }
//
//        return clusterStyle;
//    }
//    public VisualStyle getAppStyle(double maxScore) {
//        if (appStyle == null) {
//            appStyle = visualStyleFactory.createVisualStyle("MCODE");
//
//            // Node Shape:
//            DiscreteMapping<String, NodeShape> nodeShapeDm = (DiscreteMapping<String, NodeShape>) discreteMappingFactory
//                    .createVisualMappingFunction("MCODE_Node_Status", String.class, NODE_SHAPE);
//
//            nodeShapeDm.putMapValue("Clustered", NodeShapeVisualProperty.ELLIPSE);
//            nodeShapeDm.putMapValue("Seed", NodeShapeVisualProperty.RECTANGLE);
//            nodeShapeDm.putMapValue("Unclustered", NodeShapeVisualProperty.DIAMOND);
//
//            // Set node width/height lock
//            for (VisualPropertyDependency<?> dep : appStyle.getAllVisualPropertyDependencies()) {
//                if (dep.getParentVisualProperty() == BasicVisualLexicon.NODE_SIZE
//                        && dep.getVisualProperties().contains(BasicVisualLexicon.NODE_WIDTH)
//                        && dep.getVisualProperties().contains(BasicVisualLexicon.NODE_HEIGHT)) {
//                    dep.setDependency(true);
//                }
//            }
//
//            appStyle.addVisualMappingFunction(nodeShapeDm);
//        }
//
//        // Node Color:
//        appStyle.setDefaultValue(NODE_FILL_COLOR, Color.WHITE);
//
//        // Important: Always recreate this mapping function with the new score.
//        appStyle.removeVisualMappingFunction(NODE_FILL_COLOR);
//
//        // The lower the score the darker the color
//        ContinuousMapping<Double, Paint> nodeColorCm = (ContinuousMapping<Double, Paint>) continuousMappingFactory
//                .createVisualMappingFunction("MCODE_Score", Double.class, NODE_FILL_COLOR);
//
//        final Color MIN_COLOR = Color.BLACK;
//        final Color MAX_COLOR = Color.RED;
//
//        // First we state that everything below or equaling 0 (min) will be white, and everything above that will
//        // start from black and fade into the next boundary color
//        nodeColorCm.addPoint(0.0, new BoundaryRangeValues<Paint>(Color.WHITE, Color.WHITE, MIN_COLOR));
//        // Now we state that anything anything below the max score will fade into red from the lower boundary color
//        // and everything equal or greater than the max (never occurs since this is the upper boundary) will be red
//        // The max value is set by MCODEVisualStyleAction based on the current result set's max score
//        nodeColorCm.addPoint(maxScore, new BoundaryRangeValues<Paint>(MAX_COLOR, MAX_COLOR, MAX_COLOR));
//
//        appStyle.addVisualMappingFunction(nodeColorCm);
//
//        return appStyle;
//    }
//    public VisualStyle getNetworkViewStyle(CyNetworkView view) {
//        return view != null ? visualMappingMgr.getVisualStyle(view) : null;
//    }
//    public void registerVisualStyle(VisualStyle style) {
//        // Add it once only!
//        if (!visualMappingMgr.getAllVisualStyles().contains(style)) {
//            visualMappingMgr.addVisualStyle(style);
//        }
//    }
//    public void setSelected(final Collection<? extends CyIdentifiable> elements, CyNetwork network) {
//        final Collection<CyIdentifiable> allElements = new ArrayList<CyIdentifiable>(network.getNodeList());
//        allElements.addAll(network.getEdgeList());
//
//        for (final CyIdentifiable nodeOrEdge : allElements) {
//            boolean select = elements.contains(nodeOrEdge);
//            network.getRow(nodeOrEdge).set(CyNetwork.SELECTED, select);
//        }
//
//        eventHelper.flushPayloadEvents();
//
//        final Collection<CyNetworkView> netViews = networkViewMgr.getNetworkViews(network);
//
//        for (final CyNetworkView view : netViews) {
//            view.updateView();
//        }
//
//        swingApplication.getJFrame().repaint(); // TODO: remove this ugly hack!!!
//    }
    public void interruptLoading() {
        interrupted = true;
    }

    public void resetLoading() {
        interrupted = false;
    }

//    /**
//     * Generates an image of a place holder saying "Too big to show".
//     *
//     * @param width width of the image
//     * @param height height of the image
//     * @return place holder
//     */
//    public Image getPlaceHolderImage(int width, int height) {
//        // We only want to generate a place holder image once so that memory is not eaten up
//        if (placeHolderImage == null) {
//            placeHolderImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//            Graphics2D g2 = (Graphics2D) placeHolderImage.getGraphics();
//
//            int fontSize = 10;
//            g2.setFont(new Font("Arial", Font.PLAIN, fontSize));
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            Font f = g2.getFont();
//            FontMetrics fm = g2.getFontMetrics(f);
//
//            // Place Holder text
//            String placeHolderText = "Too big to show";
//            // We want to center the text vertically in the top 20 pixels
//            height = 20;
//            // White outline
//            g2.setColor(Color.WHITE);
//            g2.drawString(placeHolderText, (width / 2) - (fm.stringWidth(placeHolderText) / 2) - 1, (height / 2)
//                    + (fontSize / 2) - 1);
//            g2.drawString(placeHolderText, (width / 2) - (fm.stringWidth(placeHolderText) / 2) - 1, (height / 2)
//                    + (fontSize / 2) + 1);
//            g2.drawString(placeHolderText, (width / 2) - (fm.stringWidth(placeHolderText) / 2) + 1, (height / 2)
//                    + (fontSize / 2) - 1);
//            g2.drawString(placeHolderText, (width / 2) - (fm.stringWidth(placeHolderText) / 2) + 1, (height / 2)
//                    + (fontSize / 2) + 1);
//            //Red text
//            g2.setColor(Color.RED);
//            g2.drawString(placeHolderText, (width / 2) - (fm.stringWidth(placeHolderText) / 2), (height / 2)
//                    + (fontSize / 2));
//        }
//
//        return placeHolderImage;
//    }
    /**
     * Sorts a list of MCODE generated clusters by the score.
     *
     * @param clusters List of MCODE generated clusters
     */
//    public void sortClusters(final List<MCODECluster> clusters) {
    public void sortClusters(final List<McodeCluster> clusters) {
//        Collections.sort(clusters, new Comparator<MCODECluster>() {
        Collections.sort(clusters, new Comparator<McodeCluster>() {
            @Override
//            public int compare(MCODECluster c1, MCODECluster c2) {
            public int compare(McodeCluster c1, McodeCluster c2) {
                //sorting clusters by decreasing score
                double d1 = c1.getScore();
                double d2 = c2.getScore();

                if (d1 == d2) {
                    return 0;
                } else if (d1 < d2) {
                    return 1;
                }
                return -1;
            }
        });
    }
//    /**
//     * Utility method to get the names of all the nodes in a CyNetwork
//     *
//     * @param network The input graph network to get the names from
//     * @return A concatenated set of all node names (separated by a comma)
//     */
//    public String getNodeNameList(CyNetwork network) {
//        StringBuffer sb = new StringBuffer();
//
//        for (CyNode node : network.getNodeList()) {
//            CyRow row = network.getRow(node);
//            String id = "" + node.getSUID();
//
//            if (row.isSet(CyNetwork.NAME)) {
//                id = row.get(CyNetwork.NAME, String.class);
//            }
//
//            sb.append(id);
//            sb.append(", ");
//        }
//
//        if (sb.length() > 2) {
//            sb.delete(sb.length() - 2, sb.length());
//        }
//
//        return sb.toString();
//    }
//    /**
//     * Save MCODE results to a file
//     *
//     * @param alg The algorithm instance containing parameters, etc.
//     * @param clusters The list of clusters
//     * @param network The network source of the clusters
//     * @param fileName The file name to write to
//     * @return True if the file was written, false otherwise
//     */
//    public boolean exportMCODEResults(final MCODEAlgorithm alg, final List<MCODECluster> clusters, CyNetwork network) {
//        if (alg == null || clusters == null || network == null) {
//            return false;
//        }
//
//        final String lineSep = System.getProperty("line.separator");
//        String fileName = null;
//        FileWriter fout = null;
//
//        try {
//            // Call save method in MCODE get the file name
//            Collection<FileChooserFilter> filters = new ArrayList<FileChooserFilter>();
//            filters.add(new FileChooserFilter("Text format", "txt"));
//            File file = fileUtil.getFile(swingApplication.getJFrame(),
//                    "Export Graph as Interactions",
//                    FileUtil.SAVE,
//                    filters);
//
//            if (file != null) {
//                fileName = file.getAbsolutePath();
//                fout = new FileWriter(file);
//
//                // Write header
//                fout.write("MCODE App Results" + lineSep);
//                fout.write("Date: " + DateFormat.getDateTimeInstance().format(new Date()) + lineSep + lineSep);
//                fout.write("Parameters:" + lineSep + alg.getParams().toString() + lineSep);
//                fout.write("Cluster	Score (Density*#Nodes)\tNodes\tEdges\tNode IDs" + lineSep);
//
//                // Get sub-networks for all clusters, score and rank them
//                // convert the ArrayList to an array of CyNetworks and sort it by cluster score
//                for (int i = 0; i < clusters.size(); i++) {
//                    final MCODECluster c = clusters.get(i);
//                    final CyNetwork clusterNetwork = c.getNetwork();
//                    fout.write((i + 1) + "\t"); //rank
//                    NumberFormat nf = NumberFormat.getInstance();
//                    nf.setMaximumFractionDigits(3);
//                    fout.write(nf.format(c.getScore()) + "\t");
//                    // cluster size - format: (# prot, # intx)
//                    fout.write(clusterNetwork.getNodeCount() + "\t");
//                    fout.write(clusterNetwork.getEdgeCount() + "\t");
//                    // create a string of node names - this can be long
//                    fout.write(getNodeNameList(clusterNetwork) + lineSep);
//                }
//                return true;
//            }
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null,
//                    e.toString(),
//                    "Error Writing to \"" + fileName + "\"",
//                    JOptionPane.ERROR_MESSAGE);
//        } finally {
//            if (fout != null) {
//                try {
//                    fout.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return false;
//    }
//    /**
//     * @return Cytoscape's control panel
//     */
//    public CytoPanel getControlCytoPanel() {
//        return swingApplication.getCytoPanel(CytoPanelName.WEST);
//    }
//    /**
//     * @return Cytoscape's results panel
//     */
//    public CytoPanel getResultsCytoPanel() {
//        return swingApplication.getCytoPanel(CytoPanelName.EAST);
//    }
//    /**
//     * @return The main panel of the app if it is opened, and null otherwise
//     */
//    public MCODEMainPanel getMainPanel() {
//        CytoPanel cytoPanel = getControlCytoPanel();
//        int count = cytoPanel.getCytoPanelComponentCount();
//
//        for (int i = 0; i < count; i++) {
//            if (cytoPanel.getComponentAt(i) instanceof MCODEMainPanel) {
//                return (MCODEMainPanel) cytoPanel.getComponentAt(i);
//            }
//        }
//
//        return null;
//    }
//    /**
//     * @return The result panels of the app if it is opened, or an empty
//     * collection otherwise
//     */
//    public Collection<MCODEResultsPanel> getResultPanels() {
//        Collection<MCODEResultsPanel> panels = new ArrayList<MCODEResultsPanel>();
//        CytoPanel cytoPanel = getResultsCytoPanel();
//        int count = cytoPanel.getCytoPanelComponentCount();
//
//        for (int i = 0; i < count; i++) {
//            if (cytoPanel.getComponentAt(i) instanceof MCODEResultsPanel) {
//                panels.add((MCODEResultsPanel) cytoPanel.getComponentAt(i));
//            }
//        }
//
//        return panels;
//    }
//    public MCODEResultsPanel getResultPanel(final int resultId) {
//        for (MCODEResultsPanel panel : getResultPanels()) {
//            if (panel.getResultId() == resultId) {
//                return panel;
//            }
//        }
//
//        return null;
//    }
//    /**
//     * @return true if the app is opened and false otherwise
//     */
//    public boolean isOpened() {
//        return getMainPanel() != null;
//    }
//    private static Properties loadProperties(String name) {
//        Properties props = new Properties();
//
//        try {
//            InputStream in = CyActivator.class.getResourceAsStream(name);
//
//            if (in != null) {
//                props.load(in);
//                in.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return props;
//    }
}
