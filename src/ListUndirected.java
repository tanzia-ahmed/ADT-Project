import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class ListUndirected {

	private LinkedList<Integer>[ ] adj;
	private int V; // number of vertices
	private int E; // number of edges
	
	public ListUndirected(int nodes) {
		this.V = nodes;
		this.E = 0;
		this.adj = new LinkedList[nodes];
		for(int v = 0; v < V; v++) {
			adj[v] = new LinkedList<>();
		}
	}
	
	public void addEdge(int u, int v) {
		
		if(!adj[u].contains(v) && !adj[v].contains(u)) {
		adj[u].add(v);
		adj[v].add(u);
		E++;
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(V + " vertices, " + E + " edges " + "\n");
		for(int v = 0; v < V; v++) {
			sb.append(v + ": ");
			for(int w : adj[v]) {
				sb.append(w + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private static int checkNoOfLines(String fileName) {

		int lines = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			
			while (br.readLine() != null) lines++;
			
		} catch (IOException e) {
			System.out.println("File not found or does not exist");
			
		}
		return lines;
	}
	
	void DFSUtil(int v, boolean visited[]) {
		visited[v]=true;
		System.out.print(v + " ");
		Iterator<Integer> i = adj[v].listIterator();
		while(i.hasNext()) {
			int n=i.next();
			if(!visited[n])
				DFSUtil(n,visited);
		}
	}
	
	void DFS(int v) {
		boolean visited[]=new boolean[V];
		DFSUtil(v,visited);
	}
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of file ");
		String fileName = sc.next();
		
		 int noOfVertices = checkNoOfLines(fileName);
		
		 ListUndirected g = new ListUndirected(noOfVertices);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			String s = br.readLine();
			while(s!=null) {
				
				String[] components = s.split(" ");		

				for(int i=1;i<components.length;i++) {
					
					g.addEdge(Integer.parseInt(components[0]),Integer.parseInt(components[i]));			
					}
			
				s= br.readLine();
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not found or does not exist");
			
		}
	
		System.out.println(g);
		System.out.println("DFS Traversal: \n");
		g.DFS(1);
	}

	

}