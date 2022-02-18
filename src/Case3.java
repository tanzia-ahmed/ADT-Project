import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Case3 {

	public static void main(String[] args) {
		System.out.println("**************************************************************************");
		System.out.println("CASE 3 - COVID patients are allowed. (All corridors have multiple lanes)");
		System.out.println("**************************************************************************");
		
		
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
		Graph DirectedGraph = new Graph(inputGraph.V);
		
    	DirectedGraph =	inputGraph.Orientation3(0);
    	System.out.println(DirectedGraph);

	}

}
