package graph;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;


public class DiGraphImpl implements DiGraph{

	private List<GraphNode> nodeList = new ArrayList<>();

	@Override
	public Boolean addNode(GraphNode node) {
		if (getNode(node.getValue()) == null) { // if it doesn't exist, make it
			nodeList.add(node);
			return true;
		}
		return false; // otherwise the node is there, and cannot be added.
	}

	@Override
	public Boolean removeNode(GraphNode node) {
		// Remove all edges then remove node probably.
		Iterator<GraphNode> graphNodeIterator = nodeList.iterator();
		int removed = 0;
		while(graphNodeIterator.hasNext()) {
			GraphNode curNode = graphNodeIterator.next();
			if (curNode.getNeighbors().contains(node)) {
				removeEdge(curNode, node);
				curNode.removeNeighbor(node);
				graphNodeIterator.remove();
				removed += 1;
			}
		}
		return removed != 0;
	}

	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		if (getNode(newNodeValue) == null) { // as long as there is no node with that value, change it
			GraphNode targetNode = getNode(node.getValue());
			targetNode.setValue(newNodeValue);
			return true;
		}
		return false; // couldn't update; a node already had that value;
	}

	@Override
	public String getNodeValue(GraphNode node) {
		return node.getValue();
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
//		GraphNode targetFromNode = getNode(fromNode.getValue());
//		GraphNode targetToNode = getNode(toNode.getValue());

		if (fromNode == null || toNode == null) return false; // nodes don't exist, can't make the edge

		return fromNode.addNeighbor(toNode, weight);
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		return null;
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		return null;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		return null;
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		return node.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		return fromNode.getNeighbors().contains(toNode) && toNode.getNeighbors().contains(fromNode);
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		if (fromNode.getNeighbors().contains(toNode)) return true;
		Set<GraphNode> result = getPathToNode(fromNode, toNode, new ArrayList<>());
		return result.contains(toNode);
	}
	private Set<GraphNode> getPathToNode(GraphNode start, GraphNode dest, List<GraphNode> alreadyVisited) {
		System.out.println("start.getValue() = " + start.getValue());
		Set<GraphNode> nodes = new HashSet<>();
		alreadyVisited.add(start);
		List<GraphNode> neighbors = start.getNeighbors();
		if (neighbors.size() > 0) {
			System.out.println("neighbors.contains(dest) = " + neighbors.contains(dest));
			if (neighbors.contains(dest)) return nodes; // Returning here effects the recursion. Find out the interaction
			nodes.addAll(start.getNeighbors());
			for (GraphNode neighbor : start.getNeighbors()) {
				if (!alreadyVisited.contains(neighbor))
					nodes.addAll(getPathToNode(neighbor, dest, alreadyVisited));
			}
		}
		String graphNodes = nodes.stream().map(GraphNode::getValue)
				.collect(Collectors.joining(", "));
		System.out.println("graphNodes = " + graphNodes);

		return nodes;
	}

	@Override
	public Boolean hasCycles() {
		return nodeList.stream().anyMatch(node -> nodeIsReachable(node,node));
	}

	@Override
	public List<GraphNode> getNodes() {
		return nodeList;
	}
	@Override
	public GraphNode getNode(String nodeValue) {
		for (GraphNode thisNode : nodeList) {
			if (thisNode.getValue().equals(nodeValue))
				return thisNode;
		}
		return null;
	}

	@Override
	public int fewestHops(GraphNode fromNode, GraphNode toNode) {
//		if (fromNode.getNeighbors().contains(toNode)) return 1;
		Set<GraphNode> result = getPathToNode(fromNode, toNode, new ArrayList<>());
		return result.size();
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		if (nodeIsReachable(fromNode, toNode)) {
			int sum = 0;

		}
		return -1;
	}
}
