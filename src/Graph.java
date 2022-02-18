import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


public class Graph {
private LinkedList<Integer>[ ] adj;
	public int V; // number of vertices
	public int E; // number of edges
	int prev_Node; //Previous Node
	int current_Node=0; //Current Node
	int time=0;
	int countBridge=0;
	Stack<Integer> bridge_edges = new Stack<>();
	
	
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

		if(!adj[u].contains(v) && u!=v) {
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

	public static int checkNoOfLines(String fileName) {

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

    int bridgeUtil(int u, boolean visited[], int disc[], 
                    int low[], int parent[]) 
    { 
        visited[u] = true; 
  
        disc[u] = low[u] = ++time; 
  
        Iterator<Integer> i = adj[u].iterator(); 
        while (i.hasNext()) 
        { 
            int v = i.next();   
  

            if (!visited[v]) 
            { 
                parent[v] = u; 
                bridgeUtil(v, visited, disc, low, parent); 
  
                
                low[u]  = Math.min(low[u], low[v]); 

                if (low[v] > disc[u]) 
                {
                	countBridge++;
                    System.out.println(u+" "+v);
                    bridge_edges.push(u);
                    bridge_edges.push(v);
//                    addReverseEdge(u,v);
               	}
            } 
  
            // Update low value of u for parent function calls. 
            else if (v != parent[u]) 
                low[u]  = Math.min(low[u], disc[v]); 
        } 
        
        return countBridge;
    } 
  
    int bridge() 
    { 
        boolean visited[] = new boolean[V]; 
        int disc[] = new int[V]; 
        int low[] = new int[V]; 
        int parent[] = new int[V]; 
  
        for (int i = 0; i < V; i++) 
        { 
            parent[i] = 0; 
            visited[i] = false; 
        } 
  

        int bridge_total = 0;
        for (int i = 0; i < V; i++) 
        {
            if (visited[i] == false) 
            	bridge_total = bridge_total+ bridgeUtil(i, visited, disc, low, parent); 
        }
        System.out.println(bridge_total+ " bridges");
        return bridge_total;
    } 

	public Graph Orientation1 (int s){
		Graph g = new Graph(V);
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
				g.addDirectedEdge(prev_Node, current_Node);
			}
			else{
				g.addDirectedEdge(prev_Node, current_Node);
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
		return g;
	}
	
	public Graph Orientation2 (int s){
		Graph g = new Graph(V);
		boolean[] visited = new boolean[V];
		Stack<Integer> stack = new Stack<>();
		stack.push(s);
		prev_Node = 0;

		bridge();
		while (!bridge_edges.isEmpty()){
			int v = bridge_edges.pop();
			int u = bridge_edges.pop();
			g.addEdge(u,v);
			//g.addDirectedEdge(u,v);
		}

		while(!stack.isEmpty()) {
			int u = stack.pop();
			if (current_Node == 0){}
			else if(current_Node==V-1 && prev_Node==V-1) {
				prev_Node=current_Node;
				current_Node=s;
				g.addDirectedEdge(prev_Node, current_Node);
			}
			else{
				g.addDirectedEdge(prev_Node, current_Node);
			}
				if (!visited[u]) {
					
					visited[u] = true;
					
						prev_Node=u;
						for (int v : adj[u]) {
								if (!visited[v]) {
									stack.push(v);
									current_Node=v;

								}
							}
				
					
					}

		
	}
		return g;
	}
	public Graph Orientation3 (int s){
		Graph g = new Graph(V);
		ArrayList<Integer> covid_Path = new ArrayList();
		ArrayList<Integer> nonCovid_Path = new ArrayList();
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
					g.addDirectedEdge(prev_Node, current_Node);
					covid_Path.add(prev_Node);
					covid_Path.add(current_Node);
				}
				else{
					g.addDirectedEdge(prev_Node, current_Node);
					covid_Path.add(prev_Node);
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
			System.out.println("The RED path for covid patients is: " + covid_Path);

			int iteration =0;
			stack.push(s);
			//reset visited[]
			for(int c=0; c< visited.length; c++){
				visited[c] = false;
			}
			while(!stack.isEmpty()) {
				iteration++;
				int u = stack.pop();
				if (current_Node == 0){}
				else if(current_Node==1 && prev_Node==1) {
					prev_Node=current_Node;
					current_Node=s;
					g.addDirectedEdge(prev_Node, current_Node);
					nonCovid_Path.add(prev_Node);
					nonCovid_Path.add(current_Node);
					
				}
				else {
					g.addDirectedEdge(prev_Node, current_Node);
					nonCovid_Path.add(prev_Node);
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
						
						if(iteration == 1) {
							Stack stack2 = new Stack();
							while (!stack.isEmpty()) {
								int v = stack.pop();
								stack2.push(v); //stack2-> 1,12 (top)
								current_Node=v;
							}
							stack = stack2;
						}
						
				}
		}
		System.out.println("The Green path for non-covid patients is: " + nonCovid_Path);
		
	return g;
	}
	

}
