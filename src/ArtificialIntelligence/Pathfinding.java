package ArtificialIntelligence;

/*This class takes in a map of 0s and 1s (0 for passable terrain and 1 for impassable terrain)
 * 
 * The graph is constructed when the character map is first passed to the class
 * It creates a path from the current location to the target location and the most desirable move can be queried from this result
 */

import java.util.*;

public class Pathfinding {
    public static final int K = 10; //Scaling factor 
	protected static int INF, X_MAX, Y_MAX; //INF = can't reach here, X_MAX, Y_MAX are dimensions of the graph 
	private char [][] grid; //character map of 0s and 1s
	private int [] dist, prev; //dist stores the best distance to node and prev stores the last node
	private int [] dx = {1, 0, -1, 0}; //delta x 
	private int [] dy = {0, 1, 0, -1}; //delta y
	private boolean [] visited; //check if Node was visited
	private int cur_x, cur_y, tar_x, tar_y; //current x, current y, target x, target y
	private int maxSize; //maximum node num + 1, (X_MAX * Y_MAX + 1)
	private ArrayList [] edges; //stores the edges of the graph
	private ArrayList best_path; //stores the best path from current location to target location
	private PriorityQueue q; //heap for dijkstra's algorithm
	private Comparator nodeComparator; //Comparator to compare nodes
	
	public Pathfinding(char [][] _grid, int _cur_x, int _cur_y, int _tar_x, int _tar_y) {
		INF = (1 << 30) - 1; //INF is ~1 billion, no distance will ever exceed this amount
		grid = _grid; //stores character map
		X_MAX = grid.length; //get x dimension
		Y_MAX = grid[0].length; //get y dimension
		maxSize = X_MAX * (Y_MAX + 1); //compute max size
		//init data structures
		dist = new int [maxSize];
		edges = new ArrayList[maxSize];
		visited = new boolean[maxSize];
		prev = new int [maxSize];
		best_path = new ArrayList();
	 	nodeComparator = new NodeComparator();
                createEdges();
		//scale down stuff by factor of K
		cur_x = _cur_x / K;
		cur_y = _cur_y / K;
		tar_x = _tar_x / K;
		tar_y = _tar_y / K;
		//Only solve if co-ords are in bounds
        if (inBounds())
				solve();
	}
        
        private void createEdges() {
            //create lists for edges 	
            for (int i = 0; i < maxSize; i++)
			edges[i] = new ArrayList<Edge>();
                
            for (int x = 0; x < X_MAX; x++) {
                    for (int y = 0; y < Y_MAX; y++) {
							//if the current location is passable and the new location (consider all 8 directions) is passable, create an edge with cost 1
                            if (grid[x][y] == 0) {
                                    if (x - 1 >= 0 && grid[x - 1][y] == 0) 
                                            edges[x * Y_MAX + y].add(new Edge((x - 1) * Y_MAX + y, 1));

                                    if (x + 1 < X_MAX && grid[x + 1][y] == 0)
                                            edges[x * Y_MAX + y].add(new Edge((x + 1) * Y_MAX + y, 1));

                                    if (y - 1 >= 0 && grid[x][y - 1] == 0)
                                            edges[x * Y_MAX + y].add(new Edge((x) * Y_MAX + y - 1, 1));

                                    if (y + 1 < Y_MAX && grid[x][y + 1] == 0) 
                                            edges[x * Y_MAX + y].add(new Edge((x) * Y_MAX + y + 1, 1));
                                    
                                    if (x - 1 >= 0 && y - 1 >= 0 && grid[x - 1][y - 1] == 0) 
                                            edges[x * Y_MAX + y].add(new Edge((x - 1) * Y_MAX + y - 1, 1));

                                    if (x + 1 < X_MAX && y - 1 >= 0 && grid[x + 1][y - 1] == 0)
                                            edges[x * Y_MAX + y].add(new Edge((x + 1) * Y_MAX + y - 1, 1));

                                    if (x - 1 >= 0 && y + 1 < Y_MAX && grid[x - 1][y + 1] == 0) 
                                            edges[x * Y_MAX + y].add(new Edge((x - 1) * Y_MAX + y + 1, 1));

                                    if (x + 1 < X_MAX && y + 1 < Y_MAX && grid[x + 1][y + 1] == 0) 
                                            edges[x * Y_MAX + y].add(new Edge((x + 1) * Y_MAX + y + 1, 1));
                                    
                            }
                    }
            }
        }
		
	//returns the manhattan distance for heuristic
	private int getManDist(int v1, int v2) {
		int x1 = v1 / Y_MAX; //get x1
		int y1 = v1 % Y_MAX; //get y1
		int x2 = v1 / Y_MAX; //get x2
		int y2 = v1 % Y_MAX; //get y2
		
		int ret = 0; //return value
		
		//add difference in x
		if (x1 < x2)
			ret += x2 - x1; 
			
		else 
			ret += x1 - x2;
		
		//add difference in y
		if (y1 < y2)
			ret += y2 - y1;
		
		else
			ret += y1 - y2;
			
		return ret;
	}	
		
	private void solve() {
		
		Arrays.fill(visited, false); //nothing is visited in the beginning
		
		Arrays.fill(prev, -1); //set prev of every node to -1 (means no prev node)
		
		for (int x = 0; x < X_MAX; x++)
			for (int y = 0; y < Y_MAX; y++)
				dist[x * Y_MAX + y] = INF; //set best distance to every node as infinity
				
		dist[cur_x * Y_MAX + cur_y] = 0; //distance to current node is 0
           
		q = new PriorityQueue<Node>(11, nodeComparator); //heap that compares objects with nodeComparator (11 is default size)
		q.add(new Node(cur_x * Y_MAX + cur_y, 0)); //Add current node to heap
		while (q.peek() != null) {
			
			Node curNode = (Node)q.poll(); //look at current node
			int cur_v = curNode.location;
			int cur_cost = curNode.cost;
			
			if (visited[cur_v]) //don't revisit nodes
				continue;
			
			visited[cur_v] = true; //mark off node as visited
                        
			
			int cx = cur_v / Y_MAX; //current x
			int cy = cur_v % Y_MAX; //current y
                        
                        if (cx == tar_x && cy == tar_y) //if we reached the target location, we are done
                            break;
			
			//System.out.printf("%d %d %d\n", cx, cy, cur_cost);
			
			int len = edges[cur_v].size(); //number of vertices that the current one is connected to
			
			for (int i = 0; i < len; i++) {
				Edge nEdge = (Edge) edges[cur_v].get(i);
				int new_v = nEdge.to; //new vertex
				int new_cost = cur_cost + nEdge.cost + getManDist(cur_v, new_v); //new cost is computed by adding current cost to edge cost and the manhattan distance between the two nodes
				
				if (dist[new_v] > new_cost) { //relax edge, if new cost is better, update distance and push it in the heap
					dist[new_v] = new_cost;
					q.add(new Node(new_v, new_cost));
					prev[new_v] = cur_v; //update prev
				}
			}
		}
		
		genPath(); //create path
		
	}
	
	private void genPath() {
		
		best_path = new ArrayList();
		//start from the end and create the lists
		int c_node = prev[tar_x * Y_MAX + tar_y];
		if (c_node != -1) { //if we are not at the beginning
			while (prev[c_node] != -1) {
				best_path.add(c_node); 
				c_node = prev[c_node];
			}
			Collections.reverse(best_path); //list was created in reverse order so reverse it to fix it
		}
	}
	
	public ArrayList getPath() {
		return best_path; //return the best_path
	}
	
	public void updateCurrentLocation(int x, int y) { //solve graph given updated current location
		cur_x = x / K; //scale down by K
		cur_y = y / K; //scale down by K
                if (inBounds()) //if things are in bound
                    solve();
	}
	
	public void updateTargetLocation(int x, int y) { //solve graph given updated target location
		tar_x = x / K; //scale down by K
		tar_y = y / K; //scale down by K
                if (inBounds()) //if things are in bound
                    solve();
	}
        
        public int getToX() { //get next move (y) if it exists otherwise stay where he is
            if (!best_path.isEmpty()) 
                return (int)(best_path.get(0)) / Y_MAX * K; //scale it back by K
            return cur_x * K; 
        }
        
        public int getToY() { //gets next move (y) if it exists otherwise stay where he is
            if (!best_path.isEmpty())
                return (int)(best_path.get(0)) % Y_MAX * K; //scale it back by K
            return cur_y * K;
        }
        
        public void updateLocations(int x1, int y1, int x2, int y2) { //solve graph given updated current and target location
            cur_x = x1 / K; //scale down by K
            cur_y = y1 / K;
            tar_x = x2 / K;
            tar_y = y2 / K;
            if (inBounds())
                solve();
        }
        
        private boolean inBounds() { //checks if current location and target location are in bounds
            return cur_x >= 0 && cur_x < X_MAX && cur_y >= 0 && cur_y < Y_MAX && tar_x >= 0 && tar_x < X_MAX && tar_y >= 0 && tar_y < Y_MAX;
        } 
	/*
	public static void main(String [] args) {
		char [][] test_grid = {
		{0, 0, 0, 0},
		{0, 1, 1, 0},
		{1, 0, 0, 0},
		}; 
		
                
		Pathfinding test = new Pathfinding(test_grid, 0, 0, 2, 2);
		//test.updateCurrentLocation(0, 2);
		//test.updateTargetLocation(1, 2);
                test.updateLocations(1, 0, 2, 1);
		ArrayList bPath = test.getPath();
                int bPathLen = bPath.size();
		//System.out.printf("%d %d\n", test.getToX(), test.getToY());
		for (int i = 0; i < bPathLen; i++)
			System.out.printf("%d %d\n", (int)bPath.get(i) / test.Y_MAX, (int)bPath.get(i) % test.Y_MAX);
	}
	*/
}