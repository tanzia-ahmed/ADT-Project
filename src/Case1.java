import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Case1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("*********************************");
		System.out.println("CASE 1 - ONE-WAY PROBLEM");
		System.out.println("*********************************");
		
		System.out.println("Enter the name of file: ");
		String fileName = sc.next();
		
		 int noOfVertices = Graph.checkNoOfLines(fileName);
		
		 Graph inputGraph = new Graph(noOfVertices);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			String s = br.readLine();
			while(s!=null) {
				
				String[] components = s.split(" ");		

				for(int i=1;i<components.length;i++) {
					
					inputGraph.addEdge(Integer.parseInt(components[0]),Integer.parseInt(components[i]));			
					}
			
				s= br.readLine();
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not found or does not exist");
			
		}
 System.out.println("The Graph read from the file is: ");
 System.out.println(inputGraph);
		
		
	    boolean feasible = inputGraph.Connected(0) && (inputGraph.bridge() == 0);
		
	    if(feasible) {
	    	System.out.println("Graph is feasible for One-Way Street problem.");
	    	Graph OrientedGraph = new Graph(inputGraph.V);
	    	OrientedGraph =	inputGraph.Orientation1(0);
	    	System.out.println("The graph with one-way after orientation algorithm is: ");
            System.out.println(OrientedGraph);
	    }
	    else {
	    	System.out.println("Graph is not feasible for One-Way Street problem. Cannot provide Orientation");
	    }
	
		
		

	}
	
}
