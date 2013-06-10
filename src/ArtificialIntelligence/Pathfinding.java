package ArtificialIntelligence;

import java.util.*;

public class Pathfinding {
	public final int INF, X_MAX, Y_MAX;
	
	private char [][] grid;
	private int [] dist, prev;
	private int [] dx = {1, 0, -1, 0};
	private int [] dy = {0, 1, 0, -1};
	private boolean [] visited;
	private int cur_x, cur_y, tar_x, tar_y;
	private int maxSize;
	private ArrayList [] edges;
	private ArrayList best_path;
	private PriorityQueue q;
	private Comparator nodeComparator;
	
	public Pathfinding(char [][] _grid, int _cur_x, int _cur_y, int _tar_x, int _tar_y) {
		INF = (1 << 30) - 1;
		grid = _grid;
		X_MAX = grid.length;
		Y_MAX = grid[0].length;
		maxSize = X_MAX * (Y_MAX + 1);
		dist = new int [maxSize]; 
		edges = new ArrayList[maxSize];
		visited = new boolean[maxSize];
		prev = new int [maxSize];
		best_path = new ArrayList();
	 	nodeComparator = new NodeComparator(); 
		cur_x = _cur_x;
		cur_y = _cur_y;
		tar_x = _tar_x;
		tar_y = _tar_y;
 		solve();
	}
	
	private int getManDist(int v1, int v2) {
		int x1 = v1 / Y_MAX;
		int y1 = v1 % Y_MAX;
		int x2 = v1 / Y_MAX;
		int y2 = v1 % Y_MAX;
		
		int ret = 0;
		
		if (x1 < x2)
			ret += x2 - x1;
			
		else 
			ret += x1 - x2;
			
		if (y1 < y2)
			ret += y2 - y1;
		
		else
			ret += y1 - y2;
			
		return ret;
	}	
		
	private void solve() {
		
		Arrays.fill(visited, false);
		
		Arrays.fill(prev, -1);
		
		for (int x = 0; x < X_MAX; x++)
			for (int y = 0; y < Y_MAX; y++)
				dist[x * Y_MAX + y] = INF;
				
		dist[cur_x * Y_MAX + cur_y] = 0;
		
		for (int i = 0; i < X_MAX * (Y_MAX + 1); i++)
			edges[i] = new ArrayList<Edge>();
			
		for (int x = 0; x < X_MAX; x++) {
			for (int y = 0; y < Y_MAX; y++) {
				if (grid[x][y] == 0) {
					if (x - 1 >= 0 && grid[x - 1][y] == 0) 
						edges[x * Y_MAX + y].add(new Edge((x - 1) * Y_MAX + y, 1));
					
					if (x + 1 < X_MAX && grid[x + 1][y] == 0)
						edges[x * Y_MAX + y].add(new Edge((x + 1) * Y_MAX + y, 1));
					
					if (y - 1 >= 0 && grid[x][y - 1] == 0)
						edges[x * Y_MAX + y].add(new Edge((x) * Y_MAX + y - 1, 1));
					
					if (y + 1 < Y_MAX && grid[x][y + 1] == 0) 
						edges[x * Y_MAX + y].add(new Edge((x) * Y_MAX + y + 1, 1));
				}
			}
		}
		
		q = new PriorityQueue<Node>(11, nodeComparator);
		q.add(new Node(cur_x * Y_MAX + cur_y, 0));
		while (q.peek() != null) {
			
			Node curNode = (Node)q.poll();
			int cur_v = curNode.location;
			int cur_cost = curNode.cost;
			
			if (visited[cur_v])
				continue;
			
			visited[cur_v] = true;
                        
			
			int cx = cur_v / Y_MAX;
			int cy = cur_v % Y_MAX;
                        
                        if (cx == tar_x && cy == tar_y)
                            break;
			
			//System.out.printf("%d %d %d\n", cx, cy, cur_cost);
			
			int len = edges[cur_v].size();
			
			for (int i = 0; i < len; i++) {
				Edge nEdge = (Edge) edges[cur_v].get(i);
				int new_v = nEdge.to;
				int new_cost = cur_cost + nEdge.cost + getManDist(cur_v, new_v);
				
				if (dist[new_v] > new_cost) {
					dist[new_v] = new_cost;
					q.add(new Node(new_v, new_cost));
					prev[new_v] = cur_v;
				}
			}
		}
		
		genPath();
		
	}
	
	private void genPath() {
		
		best_path = new ArrayList();
		
		int c_node = tar_x * Y_MAX + tar_y;
		while (prev[c_node] != -1) {
			c_node = prev[c_node];
			best_path.add(c_node);
		}
	}
	
	public ArrayList getPath() {
		return best_path;
	}
	
	public void updateCurrentLocation(int x, int y) {
		cur_x = x;
		cur_y = y;
		solve();
	}
	
	public void updateTargetLocation(int x, int y) {
		tar_x = x;
		tar_y = y;
		solve();
	}
	
	/*public static void main(String [] args) {
		char [][] test_grid = {
		{0, 0, 0, 0},
		{0, 0, 1, 0},
		{1, 0, 0, 0},
		}; 
		
		Pathfinding test = new Pathfinding(test_grid, 0, 0, 2, 2);
		test.updateCurrentLocation(0, 2);
		test.updateTargetLocation(1, 2);
		
		ArrayList bPath = test.getPath();
		int bPathLen = bPath.size();
		
		for (int i = 0; i < bPathLen; i++)
			System.out.printf("%d %d\n", (int)bPath.get(i) / test.Y_MAX, (int)bPath.get(i) % test.Y_MAX);
	}*/
	
}