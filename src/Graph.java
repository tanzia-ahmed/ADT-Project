import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Graph {

	private LinkedList<Integer>[ ] adj;
	private int V; // number of vertices
	private int E; // number of edges
	int prev_Node; //Previous Node
	int current_Node; //Current Node
	
	public Graph(int nodes) {
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

//	void DFSUtil(int v, boolean visited[]) {
//		visited[v]=true;
//		System.out.print(v + " ");
//
//		Iterator<Integer> i = adj[v].listIterator();
//
////		if() //If it's the 0th node then don't add direction
////			// Prev Node = Current Node
////		else
////		{
//			// Current node
//			// We can call edge function for prev node -> Current Node
//			//Prev node = current node
////		}
//		while(i.hasNext()) {
//			int n=i.next();
//			if(!visited[n]) {
//				DFSUtil(n, visited);
//
//			}
//		}
//	}
//
//	void DFS(int v) {
//		boolean visited[]=new boolean[V];
//		DFSUtil(v,visited);
//	}
	
	public boolean dfs(int s) {
		boolean[] visited = new boolean[V];
		Stack<Integer> stack = new Stack<>();
		stack.push(s);
//		int top = stack.peek();
//		System.out.println(prev_Node);
//
//		if(top==s) {
//			prev_Node=s;
//		}
//		else {
//			current_Node= top;
//
//		}

		boolean connected = false;
		while(!stack.isEmpty()) {
			int u = stack.pop();
			if(!visited[u]) {
				visited[u] = true;
				System.out.print(u + " ");

				for(int v : adj[u]) {
					if(!visited[v]) {
						stack.push(v);
					}
				}
			}
		}

		int visitedcount = 0;
		for(int i=0; i< visited.length; i++){
			if(visited[i]==true)
				visitedcount++;
		}

		if(V == visitedcount)
			connected = true;

		return connected;
	}

	public Graph Orientation (){

		Graph g1 = new Graph(V);

		boolean[] visited = new boolean[V];
		Stack<Integer> stack = new Stack<>();
		stack.push(s);

		while(!stack.isEmpty()) {
			int u = stack.pop();
			if(!visited[u]) {
				visited[u] = true;
				System.out.print(u + " ");

				for(int v : adj[u]) {
					if(!visited[v]) {
						stack.push(v);
					}
				}
			}
		}

		return null;
	}



	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of file ");
		String fileName = sc.next();
		
		 int noOfVertices = checkNoOfLines(fileName);
		
		 Graph g = new Graph(noOfVertices);
		
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
		boolean y = g.dfs(0);
		System.out.println(y);
		if (y==true){
			g.Orientation();
		}
	}

	

}
