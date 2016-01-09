package fr.kocal.graphstream;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
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
        return null;
    }

    public static Graph generateCycleGraph(int summits) {
        return null;
    }

    public static Graph generateNTreeGraph(int height, int childrenPerNodes) {
        return null;
    }

    public static Graph generateRandomGraph(int summits, int degrees) {
        return null;
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
}
