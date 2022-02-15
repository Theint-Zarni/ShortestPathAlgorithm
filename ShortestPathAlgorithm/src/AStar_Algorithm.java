
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class AStar_Algorithm {
	static int count=0;
	// h scores is the stright-line distance from the current city to Bucharest
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// for calculate time


		for (;;) {
			long startTime = System.nanoTime();
			int numberofvertices = 0;
			int source, goal;
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter the number of vertices");
			numberofvertices = scanner.nextInt();
			int adjacencymatrix[][] = new int[numberofvertices + 1][numberofvertices + 1];
			System.out.println("Enter the adjacency matrix");
			for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
				for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
					adjacencymatrix[sourcenode][destinationnode] = scanner.nextInt();
					if (sourcenode == destinationnode) {
						adjacencymatrix[sourcenode][destinationnode] = 0;
						continue;
					}
					if (adjacencymatrix[sourcenode][destinationnode] == 0) {
						adjacencymatrix[sourcenode][destinationnode] = MAX_VALUE;
					}
				}
			}
			System.out.println("Enter the source vertex");
			source = scanner.nextInt();
			System.out.println("Enter the goal vertex: ");
			goal = scanner.nextInt();
			AStar_Algorithm astar = new AStar_Algorithm(numberofvertices);

			astar.AStar_InputMatrix(source, goal, adjacencymatrix);

			Node node1 = new Node("source1", goal);
			Node node2 = new Node("source2", goal);
			AstarSearch(node1, node2);

			System.out.println(adjacencymatrix);
			long endTime = System.nanoTime();
			long runTime = (endTime - startTime) / 1000000000;
			System.out.println();
			System.out.println("Space :"+space_count);
			System.out.println("Running Time  :  " + runTime + "  miliseconds.");

			System.out.println("Exit Program?");
			System.out.println("Y / N");
			String condition = scanner.next();
			if (condition.equals("Y") || condition.equals("y")) {
				System.exit(0);
				scanner.close();
			}
		}


	}

	public static List<Node> printPath(Node target) {
		List<Node> path = new ArrayList<Node>();

		for (Node node = target; node != null; node = node.parent) {
			path.add(node);
		}

		Collections.reverse(path);

		return path;
	}


	private int distances[];
	private int numberofvertices;
	public static final int MAX_VALUE = 999;
	static int space_count=0;

	public AStar_Algorithm(int numberofvertices) {
		this.numberofvertices = numberofvertices;
		distances = new int[numberofvertices + 1];
	}
	public void AStar_InputMatrix(int source, int destination, int adjacencymatrix[][]) {
		//space_count++;
		for (int node = 1; node <= numberofvertices; node++) {
			distances[node] = MAX_VALUE;
		}
		distances[source] = 0;
		for (int node = 1; node <= numberofvertices - 1; node++) {
			for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
				for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
					if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
						if (distances[destinationnode] > distances[sourcenode]
								+ adjacencymatrix[sourcenode][destinationnode])
							distances[destinationnode] = distances[sourcenode]
									+ adjacencymatrix[sourcenode][destinationnode];
					}
				}
			}
		}
		for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
			for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
				if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
					if (distances[destinationnode] > distances[sourcenode]
							+ adjacencymatrix[sourcenode][destinationnode])
						System.out.println("The Graph contains negative egde cycle");
				}
			}
		}
		for (int vertex = 1; vertex <= numberofvertices; vertex++) {
			if (vertex == destination)
				System.out.println("Shortest path :" + "[ " + source + " , " + vertex + " ]" + "( Distance is : "
						+ distances[vertex] + " )");
		}
		space_count++;
	}

	public static void AstarSearch(Node source, Node goal) {

		Set<Node> explored = new HashSet<Node>();

		PriorityQueue<Node> queue = new PriorityQueue<Node>(20, new Comparator<Node>() {
			// override compare method
			public int compare(Node i, Node j) {
				if (i.f_scores > j.f_scores) {
					return 1;
				}

				else if (i.f_scores < j.f_scores) {
					return -1;
				}

				else {
					return 0;
				}
			}

		});

		// cost from start
		source.g_scores = 0;

		queue.add(source);

		boolean found = false;

		while ((!queue.isEmpty()) && (!found)) {

			// the node in having the lowest f_score value
			Node current = queue.poll();

			explored.add(current);

			// goal found
			if (current.value.equals(goal.value)) {
				found = true;
			}

			// check every child of current node
			for (Edge e : current.adjacencies) {
				Node child = e.target;
				double cost = e.cost;
				double temp_g_scores = current.g_scores + cost;
				double temp_f_scores = temp_g_scores + child.h_scores;

				/*
				 * if child node has been evaluated and the newer f_score is higher, skip
				 */

				if ((explored.contains(child)) && (temp_f_scores >= child.f_scores)) {
					continue;
				}

				/*
				 * else if child node is not in queue or newer f_score is lower
				 */

				else if ((!queue.contains(child)) || (temp_f_scores < child.f_scores)) {

					child.parent = current;
					child.g_scores = temp_g_scores;
					child.f_scores = temp_f_scores;

					if (queue.contains(child)) {
						queue.remove(child);
					}

					queue.add(child);

				}

			}

			count++;
		}
		System.out.println("Runing Space "+count);
	}

}



class Node {

	public final String value;
	public double g_scores;
	public final double h_scores;
	public double f_scores = 0;
	public Edge[] adjacencies;
	public Node parent;

	public Node(String val, double hVal) {
		value = val;
		h_scores = hVal;
	}

	public static Node item(String n) {
		return null;
	}

	public String toString() {
		return value;
	}

}

class Edge {
	public final double cost;
	public final Node target;

	public Edge(Node targetNode, double costVal) {
		target = targetNode;
		cost = costVal;
	}
}
