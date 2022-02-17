import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Graph {

	private LinkedList<Integer>[ ] adj;
	private int V; // number of vertices
	private int E; // number of edges
	int prev_Node; //Previous Node
	int current_Node=0; //Current Node
	int time=0;
	int countBridge=0;
	
	public Graph(int nodes) {
		this.V = nodes;
		this.E = 0;
		this.adj = new LinkedList[nodes];
		for(int v = 0; v < V; v++) {
			adj[v] = new LinkedList<>();
		}
	}
	
	public Graph (Graph g) {
		this.adj = g.adj;
		this.V = g.V;
		this.E = g.E;
	}
	public void addEdge(int u, int v) {
		
		if(!adj[u].contains(v) && !adj[v].contains(u)) {
		adj[u].add(v);
		adj[v].add(u);
		E++;
		}
	}

	public void addDirectedEdge(int u, int v) {

		if(!adj[u].contains(v)) {
			adj[u].add(v);
			E++;
		}
	}
	
	public void addReverseEdge(int u, int v) {
		if(!adj[v].contains(u))
		     adj[v].add(u);
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


	
	public boolean Connected(int s) {
		boolean[] visited = new boolean[V];
		Stack<Integer> stack = new Stack<>();
		stack.push(s);

		boolean connected = false;
		while(!stack.isEmpty()) {
			int u = stack.pop();
			if(!visited[u]) {
				visited[u] = true;
				//System.out.print(u + " ");

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
	
	public void directed_graph_maker() {
		
	}
	
	// A recursive function that finds and prints bridges 
    // using DFS traversal 
    // u --> The vertex to be visited next 
    // visited[] --> keeps tract of visited vertices 
    // disc[] --> Stores discovery times of visited vertices 
    // parent[] --> Stores parent vertices in DFS tree 
    int bridgeUtil(int u, boolean visited[], int disc[], 
                    int low[], int parent[]) 
    { 
    	
        // Mark the current node as visited 
        visited[u] = true; 
  
        // Initialize discovery time and low value 
        disc[u] = low[u] = ++time; 
  
        // Go through all vertices aadjacent to this 
        Iterator<Integer> i = adj[u].iterator(); 
        while (i.hasNext()) 
        { 
            int v = i.next();  // v is current adjacent of u 
  
            // If v is not visited yet, then make it a child 
            // of u in DFS tree and recur for it. 
            // If v is not visited yet, then recur for it 
            if (!visited[v]) 
            { 
                parent[v] = u; 
                bridgeUtil(v, visited, disc, low, parent); 
  
                // Check if the subtree rooted with v has a 
                // connection to one of the ancestors of u 
                low[u]  = Math.min(low[u], low[v]); 
  
                // If the lowest vertex reachable from subtree 
                // under v is below u in DFS tree, then u-v is 
                // a bridge 
                if (low[v] > disc[u]) 
                {
                	countBridge++;
                    System.out.println(u+" "+v); 
               	}
            } 
  
            // Update low value of u for parent function calls. 
            else if (v != parent[u]) 
                low[u]  = Math.min(low[u], disc[v]); 
        } 
        
        return countBridge;
    } 
  
  
    // DFS based function to find all bridges. It uses recursive 
    // function bridgeUtil() 
    int bridge() 
    { 
        // Mark all the vertices as not visited 
        boolean visited[] = new boolean[V]; 
        int disc[] = new int[V]; 
        int low[] = new int[V]; 
        int parent[] = new int[V]; 
  
  
        // Initialize parent and visited, and ap(articulation point) 
        // arrays 
        for (int i = 0; i < V; i++) 
        { 
            parent[i] = 0; 
            visited[i] = false; 
        } 
  
        // Call the recursive helper function to find Bridges 
        // in DFS tree rooted with vertex 'i' 
        int bridge_total = 0;
        for (int i = 0; i < V; i++) 
        {
            if (visited[i] == false) 
            	bridge_total = bridge_total+ bridgeUtil(i, visited, disc, low, parent); 
        }
        System.out.println(bridge_total+ " bridges");
        return bridge_total;
    } 

	public Graph Orientation (int s){
		Graph g1 = new Graph(V);
		boolean[] visited = new boolean[V];
		Stack<Integer> stack = new Stack<>();
		stack.push(s);
		prev_Node = 0;

		while(!stack.isEmpty()) {
			int u = stack.pop();
			if (current_Node == 0){}
			else if(current_Node==V-1 && prev_Node==V-1) {
				prev_Node=current_Node;
				current_Node=s;
				g1.addDirectedEdge(prev_Node, current_Node);
			}
			else{
				g1.addDirectedEdge(prev_Node, current_Node);
			}
				if (!visited[u]) {
					visited[u]=true;
					prev_Node=u;
					for (int v : adj[u]) {
							if (!visited[v]) {
								stack.push(v);
								current_Node=v;

							}
						}
					}
				}
		return g1;
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
	
	    System.out.println(g.Connected(0));
		System.out.println(g);
		//int bridges= g.bridge();
		
		boolean feasible = g.Connected(0) && (g.bridge() == 0);
		System.out.println("Feasible? = "+ feasible);
		Graph g2 = new Graph(noOfVertices);
		g2=g.Orientation(0);
		System.out.println(g2);
		//System.out.println("DFS Traversal: \n");
	//	boolean y = g.Connected(0);
	//	System.out.println("\n"+y);
	//	if (y==true){
	//		Graph grp = g.Orientation(0);
	//		System.out.println(grp);
//		}
		
		
		//CASE 2:
		
		
		
		
		System.out.println("Enter the large corridor: ");
		int start = sc.nextInt();
		int end = sc.nextInt();
		for(int i=start;i<end;i++) {
			g2.addReverseEdge(i, i+1);
		}
		System.out.println("This is G2:\n"+g2);
		
		//CASE 3:
		Graph g3 = new Graph(noOfVertices);
		g3=g.Orientation(0);
		System.out.println("Enter the neutral zones: ");
		String neutral = sc.next();
		int[] numbers = Arrays.stream(neutral.split(",")).mapToInt(Integer::parseInt).toArray();
		for(int i=0;i<numbers.length-1;i++) {
			g3.addReverseEdge(numbers[i],numbers[i+1]);
		}
		
		System.out.println("Enter the red zone: ");
		String red = sc.next();
		int[] red_Zone = Arrays.stream(red.split(",")).mapToInt(Integer::parseInt).toArray();
		for(int i=0;i<red_Zone.length-1;i++) {
			g3.addReverseEdge(red_Zone[i],red_Zone[i+1]);
		}
		//For Green Zone all the vertices should be bidirectional so simply call addEdge function
		
		sc.close();

	}

}
