import java.util.Scanner;

public class FloydWarshall_Algorithm {
	private int distancematrix[][];
	private int numberofvertices;
	public static final int MAX_VALUE = 999;
	public static final int INFINITY = 999;
	static int space_count = 0;
	private int distances[];

	public FloydWarshall_Algorithm(int numberofvertices) {
		this.numberofvertices = numberofvertices;
		distances = new int[numberofvertices + 1];
	}

	public void floydwarshall(int adjacencymatrix[][]) {
		for (int source = 1; source <= numberofvertices; source++) {
			for (int destination = 1; destination <= numberofvertices; destination++) {
				distancematrix[source][destination] = adjacencymatrix[source][destination];
			}
		}

		for (int intermediate = 1; intermediate <= numberofvertices; intermediate++) {
			for (int source = 1; source <= numberofvertices; source++) {
				for (int destination = 1; destination <= numberofvertices; destination++) {
					if (distancematrix[source][intermediate]
							+ distancematrix[intermediate][destination] < distancematrix[source][destination])
						distancematrix[source][destination] = distancematrix[source][intermediate]
								+ distancematrix[intermediate][destination];
				}
			}
		}

		for (int source = 1; source <= numberofvertices; source++)
			System.out.print("\t" + source);

		System.out.println();
		for (int source = 1; source <= numberofvertices; source++) {
			System.out.print(source + "\t");
			for (int destination = 1; destination <= numberofvertices; destination++) {
				System.out.print(distancematrix[source][destination] + "\t");
			}
			System.out.println();
		}
		space_count++;
	}

	public static void main(String... arg) {

		for (;;) {
			int adjacency_matrix[][];
			int numberofvertices;
			int source, destination;
			long startTime = System.nanoTime();
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter the number of vertices");
			numberofvertices = scan.nextInt();

			adjacency_matrix = new int[numberofvertices + 1][numberofvertices + 1];
			System.out.println("Enter the Weighted Matrix for the graph");
			for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
				for (destination = 1; destination <= numberofvertices; destination++) {
					adjacency_matrix[sourcenode][destination] = scan.nextInt();
					if (sourcenode == destination) {
						adjacency_matrix[sourcenode][destination] = 0;
						continue;
					}
					if (adjacency_matrix[sourcenode][destination] == 0) {
						adjacency_matrix[sourcenode][destination] = INFINITY;
					}
				}
			}

			System.out.println("Enter the source vertex");
			source = scan.nextInt();
			System.out.println("Enter the destination vertex: ");
			destination = scan.nextInt();
			FloydWarshall_Algorithm floydwarshall = new FloydWarshall_Algorithm(numberofvertices);
			floydwarshall.floydwarshall_algorithm(source, destination, adjacency_matrix);

			long endTime = System.nanoTime();
			long runTime = (endTime - startTime) / 1000000000;
			System.out.println();
			System.out.println("Space :" + space_count);
			System.out.println("Running Time  :  " + runTime + "  miliseconds.");

			System.out.println("Exit Program?");
			System.out.println("Y / N");
			String condition = scan.next();
			if (condition.equals("Y") || condition.equals("y")) {
				System.exit(0);
				scan.close();
			}
		}
	}

	public void floydwarshall_algorithm(int source, int destination, int adjacencymatrix[][]) {
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

}