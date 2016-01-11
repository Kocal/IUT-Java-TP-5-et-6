package fr.kocal.graphstream;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Hugo Alliaume on 11/01/16.
 */
public class DSat implements Algorithm {

    Graph graph;

    private ArrayList<Node> nodes;

    private int nodesSize;

    private int coloredNodes;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        this.coloredNodes = 0;
    }

    @Override
    public void compute() {

        this.nodes = Toolkit.degreeMap(this.graph);
        this.nodesSize = nodes.size();

        Node node = null;
        Node firstNode = nodes.get(0);
        nodes.remove(0);

        int color = 0;
        int maxDegree = firstNode.getDegree();

        this.setColor(firstNode, color);

        while(!nodesAreAllColored()) {
            node = this.getMaxDSAT(nodes);
            break;
        }
    }

    private void setColor(Node node, int color) {
        this.coloredNodes++;
        node.setAttribute("ui.class", "color" + color);
    }

    private boolean nodesAreAllColored() {
        return this.nodesSize == this.coloredNodes;
    }

    private Node getMaxDSAT(ArrayList<Node> nodes) {

        Iterator<Node> neighborNodeIterator;
        Node neighbour;

        for(Node node: nodes) {
            System.out.println(node + " : ");

            neighborNodeIterator = node.getNeighborNodeIterator();
            HashMap<Node, Integer> neighborWithColors = new HashMap<Node, Integer>();

            int value = 0;

            while(neighborNodeIterator.hasNext()) {
                neighbour = neighborNodeIterator.next();

                if(this.nodeIsColored(neighbour)) {
                    value++;
                }
            }

            neighborWithColors.put(node, value);

            System.out.println("Node<" + node + "> = " + neighborWithColors.get(node));
        }

        return null;
    }

    private boolean nodeIsColored(Node node) {
        return node.hasAttribute("ui.class") && node.getAttribute("ui.class").toString().matches("/color/");
    }
}
