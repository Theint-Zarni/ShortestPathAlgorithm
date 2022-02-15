import java.util.Scanner;

public class BellMan_Algorithm {
	private int distances[];
	private int numberofvertices;
	public static final int MAX_VALUE = 999;
	static int space_count=0;

	public BellMan_Algorithm(int numberofvertices) {
		this.numberofvertices = numberofvertices;
		distances = new int[numberofvertices + 1];
	}
	public void BellmanFordEvaluation(int source, int destination, int adjacencymatrix[][]) {
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

	public static void main(String... arg) {
		for (;;) {
			int numberofvertices = 0;
			int source, destination;
			long startTime = System.nanoTime();
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
			System.out.println("Enter the destination vertex: ");
			destination = scanner.nextInt();
			BellMan_Algorithm bellmanford = new BellMan_Algorithm(numberofvertices);
			bellmanford.BellmanFordEvaluation(source, destination, adjacencymatrix);

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
}