package graph;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GraphTester {

	private static Random rand = new Random();
	private static DiGraph graph;
	
	public static void main(String[] args) {

		graph = new DiGraphImpl();

		//add nodes

		GraphNode graphNode1 = new GraphNode("1");
		GraphNode graphNode2 = new GraphNode("2");
		GraphNode graphNode3 = new GraphNode("3");
		GraphNode graphNode4 = new GraphNode("4");
		GraphNode graphNode5 = new GraphNode("5");
		GraphNode graphNode6 = new GraphNode("6");
		GraphNode graphNode7 = new GraphNode("7");
		GraphNode graphNode8 = new GraphNode("8");
		GraphNode graphNode9 = new GraphNode("9");
		GraphNode graphNode10 = new GraphNode("10");
		GraphNode graphNode11 = new GraphNode("11");
		GraphNode graphNode12 = new GraphNode("12");
		GraphNode graphNode13 = new GraphNode("13");
		GraphNode graphNode14 = new GraphNode("14");
		GraphNode graphNode15 = new GraphNode("15");

		graph.addNode(graphNode1);
		graph.addNode(graphNode2);
		graph.addNode(graphNode3);
		graph.addNode(graphNode4);
		graph.addNode(graphNode5);
		graph.addNode(graphNode6);
		graph.addNode(graphNode7);
		graph.addNode(graphNode8);
		graph.addNode(graphNode9);
		graph.addNode(graphNode10);
		graph.addNode(graphNode11);
		graph.addNode(graphNode12);
		graph.addNode(graphNode13);
		graph.addNode(graphNode14);
		graph.addNode(graphNode15);
		//add edges

		// 1-5 are connected
		graph.addEdge(graphNode1, graphNode2, rand.nextInt(100));
		graph.addEdge(graphNode2, graphNode3, rand.nextInt(100));
		graph.addEdge(graphNode3, graphNode4, rand.nextInt(100));
		graph.addEdge(graphNode4, graphNode5, rand.nextInt(100));
		// 5 cycles to itself
		graph.addEdge(graphNode5, graphNode5, rand.nextInt(100));
		// 6-10 are connected
		graph.addEdge(graphNode6, graphNode7, rand.nextInt(100));
		graph.addEdge(graphNode7, graphNode8, rand.nextInt(100));
		graph.addEdge(graphNode8, graphNode9, rand.nextInt(100));
		graph.addEdge(graphNode9, graphNode10, rand.nextInt(100));
		// 11-15 are connected
		graph.addEdge(graphNode11, graphNode12, rand.nextInt(100));
		graph.addEdge(graphNode12, graphNode13, rand.nextInt(100));
		graph.addEdge(graphNode13, graphNode14, rand.nextInt(100));
		graph.addEdge(graphNode14, graphNode15, rand.nextInt(100));
		// 11 can jump to 14
		graph.addEdge(graphNode11, graphNode14, rand.nextInt(100));

		String graphNodes = graph.getNodes().stream().map(GraphNode::getValue)
				.collect(Collectors.joining(", "));
		System.out.println("graphNodes = " + graphNodes);
		System.out.println("Cyclic = " + graphNode5.getValue());
		//

		//describe

		//test reachablity
//		System.out.println("graph.nodeIsReachable(graphNode11,graphNode15) " + graph.nodeIsReachable(graphNode11, graphNode15));
//		System.out.println("graph.nodeIsReachable(graphNode1,graphNode6) " + graph.nodeIsReachable(graphNode1,graphNode6));
//		System.out.println("graph.nodeIsReachable(graphNode1,graphNode10) " + graph.nodeIsReachable(graphNode1,graphNode10));
//		System.out.println("graph.nodeIsReachable(graphNode5,graphNode1) " + graph.nodeIsReachable(graphNode5,graphNode1));
//		System.out.println("graph.nodeIsReachable(graphNode5,graphNode5) " + graph.nodeIsReachable(graphNode5,graphNode5));

		//test hasCycles
//		System.out.println("graph.hasCycles() = " + graph.hasCycles());
		System.out.println("graph.removeNode(graphNode5) = " + graph.removeNode(graphNode5));
//		System.out.println("graph.hasCycles() = " + graph.hasCycles());
		
		//test fewest hops
		System.out.println("graph.fewestHops(graphNode11, graphNode15) = " + graph.fewestHops(graphNode11, graphNode15));
		
		//test shortest path
		
		
		
	}
	
}
