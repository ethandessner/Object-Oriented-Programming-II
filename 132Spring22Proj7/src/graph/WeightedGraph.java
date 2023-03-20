package graph;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import org.w3c.dom.Node;

/**
 * <P>This class represents a general "directed graph", which could 
 * be used for any purpose.  The graph is viewed as a collection 
 * of vertices, which are sometimes connected by weighted, directed
 * edges.</P> 
 * 
 * <P>This graph will never store duplicate vertices.</P>
 * 
 * <P>The weights will always be non-negative integers.</P>
 * 
 * <P>The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.</P>
 * 
 * <P>The Weighted Graph will maintain a collection of 
 * "GraphAlgorithmObservers", which will be notified during the
 * performance of the graph algorithms to update the observers
 * on how the algorithms are progressing.</P>
 */
public class WeightedGraph<V> {

	/* STUDENTS:  You decide what data structure(s) to use to
	 * implement this class.
	 * 
	 * You may use any data structures you like, and any Java 
	 * collections that we learned about this semester.  Remember 
	 * that you are implementing a weighted, directed graph.
	 */






	/* Collection of observers.  Be sure to initialize this list
	 * in the constructor.  The method "addObserver" will be
	 * called to populate this collection.  Your graph algorithms 
	 * (DFS, BFS, and Dijkstra) will notify these observers to let 
	 * them know how the algorithms are progressing. 
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;
	Map<V, Map<V, Integer>> nestedHash;
	//hashmap from vertex to map, vertex to int
	//nested hashmap


	/** Initialize the data structures to "empty", including
	 * the collection of GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		nestedHash = new HashMap<>();
		observerList = new ArrayList<>();
	}

	/** Add a GraphAlgorithmObserver to the collection maintained
	 * by this graph (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/** Add a vertex to the graph.  If the vertex is already in the
	 * graph, throw an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in
	 * the graph
	 */
	public void addVertex(V vertex) {
		if(nestedHash.containsKey(vertex)) {
			throw new IllegalArgumentException();
		} else {
			nestedHash.put(vertex, new HashMap<V, Integer>());
		}
	}

	/** Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		if(nestedHash.containsKey(vertex)) {
			return true;
		}
		return false;
	}

	/** 
	 * <P>Add an edge from one vertex of the graph to another, with
	 * the weight specified.</P>
	 * 
	 * <P>The two vertices must already be present in the graph.</P>
	 * 
	 * <P>This method throws an IllegalArgumentExeption in three
	 * cases:</P>
	 * <P>1. The "from" vertex is not already in the graph.</P>
	 * <P>2. The "to" vertex is not already in the graph.</P>
	 * <P>3. The weight is less than 0.</P>
	 * 
	 * @param from the vertex the edge leads from
	 * @param to the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex
	 * is not in the graph, or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if((!(nestedHash.containsKey(from)) || (!(nestedHash.containsKey(to))) || weight < 0)){
			throw new IllegalArgumentException();
		}else {
			nestedHash.get(from).put(to, weight);
		}
	}

	/** 
	 * <P>Returns weight of the edge connecting one vertex
	 * to another.  Returns null if the edge does not
	 * exist.</P>
	 * 
	 * <P>Throws an IllegalArgumentException if either
	 * of the vertices specified are not in the graph.</P>
	 * 
	 * @param from vertex where edge begins
	 * @param to vertex where edge terminates
	 * @return weight of the edge, or null if there is
	 * no edge connecting these vertices
	 * @throws IllegalArgumentException if either of
	 * the vertices specified are not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		if((!(nestedHash.containsKey(from)) || (!(nestedHash.containsKey(to))))){
			throw new IllegalArgumentException();
		}else if(!(nestedHash.get(from).containsKey(to))){
			return null;
		}
		return nestedHash.get(from).get(to);
	}

	/** 
	 * <P>This method will perform a Breadth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyBFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without processing further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoBFS(V start, V end) {
		ArrayList<V> visitedSet = new ArrayList<V>();
		Queue<V> priority = new LinkedList<V>();

		priority.add(start);

		for(GraphAlgorithmObserver<V> element : observerList) {
			element.notifyBFSHasBegun();
		}

		while(priority.size() != 0) {
			V currVert = priority.element();
			priority.remove(currVert);
			if(!(visitedSet.contains(currVert))) {
				for(GraphAlgorithmObserver<V> x : observerList) {
					x.notifyVisit(currVert);
				}
				visitedSet.add(currVert);
				Map<V, Integer> updatedList = nestedHash.get(currVert);
				for(V neighbor : updatedList.keySet()) {
					if(!(visitedSet.contains(neighbor))) {
						priority.add(neighbor);
					}
				}
			}
			if(currVert.equals(end)) {
				for(GraphAlgorithmObserver<V> x : observerList) {
					x.notifySearchIsOver();
				}
				break;
			}
		}
	}

	/** 
	 * <P>This method will perform a Depth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyDFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without visiting further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoDFS(V start, V end) {
		ArrayList<V> depthList = new ArrayList<V>();
		Stack<V> priority = new Stack<V>();
		priority.push(start);
		for(GraphAlgorithmObserver<V> element : observerList) {
			element.notifyDFSHasBegun();
		}
		while(!priority.isEmpty()) {
			V current = priority.pop();
			if(!(depthList.contains(current))) {
				for(GraphAlgorithmObserver<V> x : observerList) {
					x.notifyVisit(current);
				}
				depthList.add(current);
				Map<V, Integer> updatedList = nestedHash.get(current);
				for(V neighbor : updatedList.keySet()) {
					if(!(depthList.contains(neighbor))) {
						priority.push(neighbor);
					}
				}
			}
			if(current.equals(end)) {
				for(GraphAlgorithmObserver<V> x : observerList) {
					x.notifySearchIsOver();
				}
				break;
			}
		}

	}

	/** 
	 * <P>Perform Dijkstra's algorithm, beginning at the "start"
	 * vertex.</P>
	 * 
	 * <P>The algorithm DOES NOT terminate when the "end" vertex
	 * is reached.  It will continue until EVERY vertex in the
	 * graph has been added to the finished set.</P>
	 * 
	 * <P>Before the algorithm begins, this method goes through 
	 * the collection of Observers, calling notifyDijkstraHasBegun 
	 * on each Observer.</P>
	 * 
	 * <P>Each time a vertex is added to the "finished set", this 
	 * method goes through the collection of Observers, calling 
	 * notifyDijkstraVertexFinished on each one (passing the vertex
	 * that was just added to the finished set as the first argument,
	 * and the optimal "cost" of the path leading to that vertex as
	 * the second argument.)</P>
	 * 
	 * <P>After all of the vertices have been added to the finished
	 * set, the algorithm will calculate the "least cost" path
	 * of vertices leading from the starting vertex to the ending
	 * vertex.  Next, it will go through the collection 
	 * of observers, calling notifyDijkstraIsOver on each one, 
	 * passing in as the argument the "lowest cost" sequence of 
	 * vertices that leads from start to end (I.e. the first vertex
	 * in the list will be the "start" vertex, and the last vertex
	 * in the list will be the "end" vertex.)</P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end special vertex used as the end of the path 
	 * reported to observers via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		for(GraphAlgorithmObserver<V> element : observerList) {
			element.notifyDijkstraHasBegun();
		}
		Map<V, Integer> cost = new HashMap<V, Integer>();
		Map<V, V> predecessor = new HashMap<V, V>();
		ArrayList<V> visited = new ArrayList<V>();
		
		cost.put(start, 0);
	
		for(V current : nestedHash.keySet()) {
			if(!(current.equals(start))) {
				cost.put(current, 2147483647);
			}
		}
		for(V current : nestedHash.keySet()) {
			predecessor.put(current, null);
		}
		while(visited.size() != nestedHash.size()) {
			V vertex = null;
			int min = 2147483647;
			for(V current : cost.keySet()) {
				if(!visited.contains(current)) {
					if(cost.get(current) < min) {
						min = cost.get(current);
						vertex = current;
					}
				}
			}
			visited.add(vertex);
			for(GraphAlgorithmObserver<V> element : observerList) {
				element.notifyDijkstraVertexFinished(vertex, cost.get(vertex));
			}
			for(V current : nestedHash.get(vertex).keySet()) {
				if(!visited.contains(current)) {
					if(cost.get(vertex) + this.getWeight(vertex, current) < cost.get(current)) {
						cost.put(current, cost.get(vertex) + this.getWeight(vertex, current));
						predecessor.put(current, vertex);
					}
				}
			}
		}
		V endPoint = end;
		ArrayList<V> fastest = new ArrayList<V>();
		while(endPoint != null) {
			fastest.add(0, endPoint);
			endPoint = predecessor.get(fastest.get(0));
		}
		for(GraphAlgorithmObserver<V> element : observerList) {
			element.notifyDijkstraIsOver(fastest);
		}
	}

}
