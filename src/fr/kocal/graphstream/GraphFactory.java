package fr.kocal.graphstream;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Created by Hugo Alliaume on 09/01/16.
 */
public class GraphFactory {

    private GraphFactory() {

    }

    public static Graph from(TypeGraph type, int params[]) {

        Graph graph;

        switch (type) {
            case CHAIN:
                graph = GraphFactory.generateChainGraph(params[0]);
                break;

            case CYCLE:
                graph = GraphFactory.generateCycleGraph(params[0]);
                break;

            case NTREE:
                graph = GraphFactory.generateNTreeGraph(params[0], params[1]);
                break;

            case RANDOM:
                graph = GraphFactory.generateRandomGraph(params[0], params[1]);
                break;

            case SQUARE_GRID:
                graph = GraphFactory.generateSquareGridGraph(params[0]);
                break;

            case TORUS:
                graph = GraphFactory.generateTorusGraph(params[0]);
                break;

            default:
                graph = null;
        }

        return graph;
    }

    public static Graph generateChainGraph(int summits) {
        Graph graph = new SingleGraph("Chain");
        String root = "0";

        if(summits < 2) {
            return graph;
        }

        graph.addNode(root);
        recursiveAddingNode(graph, root, summits - 2, 1);

        return graph;
    }

    public static Graph generateCycleGraph(int summits) {
        Graph graph = new SingleGraph("Chain");
        String nodes[] = new String[summits];

        if(summits < 2) {
            return graph;
        }

        for(int i = 0; i < summits; i++) {
            int j = i + 1;

            if(nodes[i] == null) {
                nodes[i] = Integer.toString(i);
                graph.addNode(nodes[i]);
            }

            if(j < summits && nodes[j] == null) {
                nodes[j] = Integer.toString(j);
                graph.addNode(nodes[j]);
            }

            if(j == summits) {
                graph.addEdge(nodes[i] + nodes[0], nodes[i], nodes[0]);
            } else {
                graph.addEdge(nodes[i] + nodes[j], nodes[i], nodes[j]);
            }
        }

        return graph;
    }

    public static Graph generateNTreeGraph(int height, int childrenPerNodes) {
        Graph graph = new SingleGraph("NTree");
        String root = "0";

        graph.addNode(root);
        recursiveAddingNode(graph, root, height - 1, childrenPerNodes);

        return graph;
    }

    public static Graph generateRandomGraph(int summits, int degrees) {
        Graph graph = new SingleGraph("Random");
        Generator gen = new RandomGenerator(degrees);

        if(summits <= 0 || degrees <= 0) {
            return graph;
        }

        gen.addSink(graph);
        gen.begin();

        for (int i = 0; i < summits; i++) {
            gen.nextEvents();
        }

        gen.end();

        return graph;
    }

    public static Graph generateSquareGridGraph(int size) {
        Graph graph = new SingleGraph("grid");
        Generator gen = new GridGenerator();

        gen.addSink(graph);
        gen.begin();

        for (int i = 0; i < size; i++) {
            gen.nextEvents();
        }

        gen.end();
        return graph;
    }

    public static Graph generateTorusGraph(int size) {
        Graph graph = new SingleGraph("grid");
        Generator gen = new GridGenerator(false, true);

        gen.addSink(graph);
        gen.begin();

        for (int i = 0; i < size; i++) {
            gen.nextEvents();
        }

        gen.end();
        return graph;
    }

    private static void recursiveAddingNode(Graph graph, String parent, int height, int childrenPerNodes) {

        String nodes[] = new String[childrenPerNodes];

        for(int i = 0; i < childrenPerNodes; i++) {
            nodes[i] = parent + Integer.toString(i);
            graph.addNode(nodes[i]);
            graph.addEdge(parent + nodes[i], parent, nodes[i]);

            if(height > 0) {
                recursiveAddingNode(graph, nodes[i], height - 1, childrenPerNodes);
            }
        }
    }
}
