import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Case2 {

	public static void main(String...args) {
		
		System.out.println("*********************************");
		System.out.println("CASE 2 - ONE-WAY/TWO (Some corridors may have multiple lanes)");
		System.out.println("***************************");
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of file ");
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
		
		
		boolean feasible = inputGraph.Connected(0);
		
		if(feasible) {
	    	System.out.println("Graph is feasible for One-Way/Two-Way Street problem.");
	    	Graph DirectedGraph = new Graph(inputGraph.V);
	    	DirectedGraph =	inputGraph.Orientation2(0);
//	    	System.out.println(DirectedGraph);
//	    	int n = DirectedGraph.bridge();
	    	//Graph OrientedGraph = new Graph(noOfVertices);
	    	
	    	//OrientedGraph.bridge();
	    	
	    	System.out.println("The graph with one-way/two-way after orientation algorithm is: ");
            System.out.println(DirectedGraph);
			DirectedGraph.bridge();
            
	    }
	    else {
	    	System.out.println("Graph is not feasible for One-Way Street problem. Cannot provide Orientation");
	    }
	
	
		/*
		System.out.println("Enter the large corridor: ");
		int start = sc.nextInt();
		int end = sc.nextInt();
		for(int i=start;i<end;i++) {
			g2.addReverseEdge(i, i+1);
		}
		//System.out.println("This is G2:\n"+g2);
*/
	}

}
