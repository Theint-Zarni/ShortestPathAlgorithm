import java.util.InputMismatchException;
import java.util.Scanner;

public class Johnson {
	private int SOURCE_NODE;
	private int numberOfNodes;
	private int augmentedMatrix[][];
	private int potential[];
	private int distances[];
	private int numberofvertices;
	private DijkstraShortesPath dijsktraShortesPath;
	private int[][] allPairShortestPath;

	public static final int MAX_VALUE = 999;
	static int space_count = 0;

	public Johnson(int numberofvertices) {
		this.numberofvertices = numberofvertices;
		distances = new int[numberofvertices + 1];
	}

	public void johnsonsAlgorithms(int adjacencyMatrix[][]) {
		computeAugmentedGraph(adjacencyMatrix);

		dijsktraShortesPath.dijkstraShortestPath(SOURCE_NODE, augmentedMatrix);
		potential = dijsktraShortesPath.getDistances();

		int reweightedGraph[][] = reweightGraph(adjacencyMatrix);
		for (int i = 1; i <= numberOfNodes; i++) {
			for (int j = 1; j <= numberOfNodes; j++) {
				System.out.print(reweightedGraph[i][j] + "\t");
			}
			System.out.println();
		}

		for (int source = 1; source <= numberOfNodes; source++) {
			dijsktraShortesPath.dijkstraShortestPath(source, reweightedGraph);
			int[] result = dijsktraShortesPath.getDistances();
			for (int destination = 1; destination <= numberOfNodes; destination++) {
				allPairShortestPath[source][destination] = result[destination]
						+ potential[destination] - potential[source];
			}
		}

		System.out.println();
		for (int i = 1; i <= numberOfNodes; i++) {
			System.out.print("\t" + i);
		}
		System.out.println();
		for (int source = 1; source <= numberOfNodes; source++) {
			System.out.print(source + "\t");
			for (int destination = 1; destination <= numberOfNodes; destination++) {
				System.out.print(allPairShortestPath[source][destination] + "\t");
			}
			System.out.println();
		}
	}

	private void computeAugmentedGraph(int adjacencyMatrix[][]) {
		for (int source = 1; source <= numberOfNodes; source++) {
			for (int destination = 1; destination <= numberOfNodes; destination++) {
				augmentedMatrix[source][destination] = adjacencyMatrix[source][destination];
			}
		}
		for (int destination = 1; destination <= numberOfNodes; destination++) {
			augmentedMatrix[SOURCE_NODE][destination] = 0;
		}
	}

	private int[][] reweightGraph(int adjacencyMatrix[][]) {
		int[][] result = new int[numberOfNodes + 1][numberOfNodes + 1];
		for (int source = 1; source <= numberOfNodes; source++) {
			for (int destination = 1; destination <= numberOfNodes; destination++) {
				result[source][destination] = adjacencyMatrix[source][destination]
						+ potential[source] - potential[destination];
			}
		}
		return result;
	}

	public void johnson_algorithm(int source, int destination, int adjacencymatrix[][]) {
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

	public static void main(String... arg) {
		for (;;) {
			int adjacency_matrix[][];
			int number_of_vertices;
			int source, destination;
			long startTime = System.nanoTime();
			Scanner scanner = new Scanner(System.in);

			try {
				System.out.println("Enter the number of vertices");
				number_of_vertices = scanner.nextInt();
				adjacency_matrix = new int[number_of_vertices + 1][number_of_vertices + 1];

				System.out.println("Enter the Jhonson Matrix for the graph");
				for (int i = 1; i <= number_of_vertices; i++) {
					for (int j = 1; j <= number_of_vertices; j++) {
						adjacency_matrix[i][j] = scanner.nextInt();
						if (i == j) {
							adjacency_matrix[i][j] = 0;
							continue;
						}
						if (adjacency_matrix[i][j] == 0) {
							adjacency_matrix[i][j] = MAX_VALUE;
						}
					}
				}

				System.out.println("Enter the source vertex");
				source = scanner.nextInt();
				System.out.println("Enter the destination vertex: ");
				destination = scanner.nextInt();
				Johnson johnsonsAlgorithm = new Johnson(number_of_vertices);
				johnsonsAlgorithm.johnson_algorithm(source, destination, adjacency_matrix);
			} catch (InputMismatchException inputMismatch) {
				System.out.println("Wrong Input Format");
			}

			long endTime = System.nanoTime();
			long runTime = (endTime - startTime) / 1000000;
			System.out.println();
			System.out.println("Space :" + space_count);
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
}

class BellmanFord {
	private int distances[];
	private int numberofvertices;

	public static final int MAX_VALUE = 999;

	public BellmanFord(int numberofvertices) {
		this.numberofvertices = numberofvertices;
		distances = new int[numberofvertices + 1];
	}

	public void BellmanFordEvaluation(int source, int adjacencymatrix[][]) {
		for (int node = 1; node <= numberofvertices; node++) {
			distances[node] = MAX_VALUE;
		}

		distances[source] = 0;

		for (int node = 1; node <= numberofvertices - 1; node++) {
			for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
				for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
					if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
						if (distances[destinationnode] > distances[sourcenode]
								+ adjacencymatrix[sourcenode][destinationnode]) {
							distances[destinationnode] = distances[sourcenode]
									+ adjacencymatrix[sourcenode][destinationnode];
						}
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
	}

	public int[] getDistances() {
		return distances;
	}
}

class DijkstraShortesPath {
	private boolean settled[];
	private boolean unsettled[];
	private int distances[];
	private int adjacencymatrix[][];
	private int numberofvertices;

	public static final int MAX_VALUE = 999;

	public DijkstraShortesPath(int numberofvertices) {
		this.numberofvertices = numberofvertices;
	}

	public void dijkstraShortestPath(int source, int adjacencymatrix[][]) {
		this.settled = new boolean[numberofvertices + 1];
		this.unsettled = new boolean[numberofvertices + 1];
		this.distances = new int[numberofvertices + 1];
		this.adjacencymatrix = new int[numberofvertices + 1][numberofvertices + 1];

		int evaluationnode;
		for (int vertex = 1; vertex <= numberofvertices; vertex++) {
			distances[vertex] = MAX_VALUE;
		}

		for (int sourcevertex = 1; sourcevertex <= numberofvertices; sourcevertex++) {
			for (int destinationvertex = 1; destinationvertex <= numberofvertices; destinationvertex++) {
				this.adjacencymatrix[sourcevertex][destinationvertex] = adjacencymatrix[sourcevertex][destinationvertex];
			}
		}

		unsettled[source] = true;
		distances[source] = 0;
		while (getUnsettledCount(unsettled) != 0) {
			evaluationnode = getNodeWithMinimumDistanceFromUnsettled(unsettled);
			unsettled[evaluationnode] = false;
			settled[evaluationnode] = true;
			evaluateNeighbours(evaluationnode);
		}
	}

	public int getUnsettledCount(boolean unsettled[]) {
		int count = 0;
		for (int vertex = 1; vertex <= numberofvertices; vertex++) {
			if (unsettled[vertex] == true) {
				count++;
			}
		}
		return count;
	}

	public int getNodeWithMinimumDistanceFromUnsettled(boolean unsettled[]) {
		int min = MAX_VALUE;
		int node = 0;
		for (int vertex = 1; vertex <= numberofvertices; vertex++) {
			if (unsettled[vertex] == true && distances[vertex] < min) {
				node = vertex;
				min = distances[vertex];
			}
		}
		return node;
	}

	public void evaluateNeighbours(int evaluationNode) {
		int edgeDistance = -1;
		int newDistance = -1;

		for (int destinationNode = 1; destinationNode <= numberofvertices; destinationNode++) {
			if (settled[destinationNode] == false) {
				if (adjacencymatrix[evaluationNode][destinationNode] != MAX_VALUE) {
					edgeDistance = adjacencymatrix[evaluationNode][destinationNode];
					newDistance = distances[evaluationNode] + edgeDistance;
					if (newDistance < distances[destinationNode]) {
						distances[destinationNode] = newDistance;
					}
					unsettled[destinationNode] = true;
				}
			}
		}
	}

	public int[] getDistances() {
		return distances;
	}

}