package graph;

import java.util.*;

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
		return fromNode.removeNeighbor(toNode);
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		if (fromNode == null || toNode == null) return false; // nodes don't exist, can't set the edge
		// Remove the neighbor, if success, re-add the neighbor with the new weight
		if (removeEdge(fromNode, toNode)) {
			fromNode.addNeighbor(toNode,newWeight);
			return true;
		}
		return false;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		return fromNode.getDistanceToNeighbor(toNode);
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		return node.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		return fromNode.getNeighbors().contains(toNode) || toNode.getNeighbors().contains(fromNode);
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		if (fromNode.getNeighbors().contains(toNode)) return true;
		Set<GraphNode> result = beginIterateBestPath(fromNode, toNode, new HashSet<>(), fromNode);
		return result.contains(toNode);
	}

	Set<GraphNode> bestPath = null;
	private Set<GraphNode> beginIterateBestPath(GraphNode start, GraphNode dest, Set<GraphNode> visited,  GraphNode firstValue ) {
		// Create a set we can track outside the recursion
		bestPath = new HashSet<>();
		// Recursion results in the shortest hop path from start to dest
		Set<GraphNode> pathResult = new HashSet<>(iterateBestHopPath(start, dest, visited, firstValue));
		// GCollection
		bestPath = null;
		return pathResult;
	}

	private Set<GraphNode> iterateBestHopPath(GraphNode start, GraphNode dest, Set<GraphNode> visited, GraphNode firstValue) {

		List<GraphNode> neighbors = start.getNeighbors();
		// Add the start node to the visited list. start IS UPDATED RECURSIVELY
		visited.add(start);
		if (neighbors.size() > 0) {
			// If the neighbors of the start node contains the dest node we can stop.
			if (neighbors.contains(dest)) {
				visited.add(dest);
				// If the successful visited set is smaller than the previous bestPath then we have a new bestPath
				if (bestPath.size() == 0 || bestPath.size() > visited.size()) bestPath = new HashSet<>(visited);
			}
			for (GraphNode neighbor : neighbors) {
				// Prevent infinite loops on cyclic nodes
				if (neighbor.equals(start)) continue;
				// Iterate through this function again with neighbor as the start node
				iterateBestHopPath(neighbor, dest, visited, firstValue);
			}
		} else {
			// There are no more neighbors, clear the visitor list and insert the first node entered and let the iterations continue.
			visited.clear();
			visited.add(firstValue);
		}
		return bestPath;
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
		if (fromNode.equals(toNode)) return 0;
		if (fromNode.getNeighbors().contains(toNode)) return 1;
		Set<GraphNode> bestPath = beginIterateBestPath(fromNode, toNode, new HashSet<>(), fromNode);
		return bestPath.size()-1;
	}

	private int sumWeight = 0;
	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		if (fromNode.equals(toNode)) return 0;
		if (fromNode.getNeighbors().contains(toNode)) return fromNode.getDistanceToNeighbor(toNode);
		int result = iterateBestWeightPath(fromNode, toNode, 0);
		sumWeight = 0;
		return result;
	}
	private int iterateBestWeightPath(GraphNode start, GraphNode dest, int weight) {
		List<GraphNode> neighbors = start.getNeighbors();
		if (neighbors.size() > 0) {
			// If the neighbors of the start node contains the dest node we can stop.
			if (neighbors.contains(dest)) {
				// If the successful visited set is smaller than the previous bestPath then we have a new bestPath
				if (sumWeight == 0 || sumWeight > weight) sumWeight = weight;
			}
			for (GraphNode neighbor : neighbors) {
				// Prevent infinite loops on cyclic nodes
				if (neighbor.equals(start)) continue;
				weight += start.getDistanceToNeighbor(neighbor);
				// Iterate through this function again with neighbor as the start node
				iterateBestWeightPath(neighbor, dest, weight);
			}
		}
		return sumWeight;
	}

}
