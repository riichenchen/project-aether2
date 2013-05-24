/*)import java.util.*;

public class Pathfinding {
	
	public class Edge {
		int to, cost;
		public Edge(int _to, int _cost) {
			to = _to;
			cost = _cost;
		}
	}
	
	public class EdgeComparator implements Comparator<Edge> {
		public int compare (Edge x, Edge y) {
			if (x.cost < y.cost)
				return -1;
			
			if (x.cost > y.cost)
				return 1;
			
			return 0;
		}
	}
	public final int INF, X_MAX, Y_MAX;
	
	private char [][] grid;
	private int [] dist, prev;
	private int [] dx = {1, 0, -1, 0};
	private int [] dy = {0, 1, 0, -1};
	private boolean [] visited;
	private int cur_x, cur_y, tar_x, tar_y;
	private ArrayList [] edges;
	private ArrayList best_path;
	private PriorityQueue q;
	
	
	public Pathfinding(char [][] _grid, int _cur_x, int _cur_y, int _tar_x, int _tar_y) {
		INF = (1 << 30) + ((1 << 30) - 1);
		grid = _grid;
		X_MAX = grid.length;
		Y_MAX = grid[0].length;
		dist = new int [X_MAX * (Y_MAX + 1)]; 
		edges = new ArrayList[X_MAX * (Y_MAX + 1)];
		visited = new boolean[X_MAX * (Y_MAX + 1)];
		prev = new int [X_MAX * (Y_MAX + 1)];
		cur_x = _cur_x;
		cur_y = _cur_y;
		tar_x = _tar_x;
		tar_y = _tar_y;
 		init();
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
		
	private void init() {
		
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
					
					if (y - 1 >= 0 && grid[x][y - 1] == 0)
						edges[x * Y_MAX + y].add(new Edge((x) * Y_MAX + y - 1, 1));
					
					if (x + 1 < X_MAX && grid[x + 1][y] == 0)
						edges[x * Y_MAX + y].add(new Edge((x + 1) * Y_MAX + y, 1));
					
					if (y + 1 < Y_MAX && grid[x][y + 1] == 0) 
						edges[x * Y_MAX + y].add(new Edge((x) * Y_MAX + y + 1, 1));
				}
			}
		}
		Comparator edgeComparator = new EdgeComparator(); 
		q = new PriorityQueue<Edge>(11, edgeComparator);
		q.add(new Edge(cur_x, 0));
		while (q.peek() != null) {
			
			Edge curEdge = (Edge)q.poll();
			int cur_v = curEdge.to;
			int cur_cost = curEdge.cost;
			
			if (visited[cur_v])
				continue;
			
			visited[cur_v] = true;
			
			int cx = cur_v / Y_MAX;
			int cy = cur_v % Y_MAX;
			
			//System.out.printf("%d %d %d\n", cx, cy, cur_cost);
			
			int len = edges[cur_v].size();
			
			for (int i = 0; i < len; i++) {
				Edge nEdge = (Edge) edges[cur_v].get(i);
				int new_v = nEdge.to;
				int new_cost = cur_cost + nEdge.cost + getManDist(cur_v, new_v);
				
				if (dist[new_v] > new_cost) {
					dist[new_v] = new_cost;
					q.add(new Edge(new_v, new_cost));
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
	
	public static void main(String [] args) {
		char [][] test_grid = {
		{0, 0, 0, 0},
		{0, 0, 1, 0},
		{1, 0, 0, 0},
		}; 
		
		Pathfinding test = new Pathfinding(test_grid, 0, 0, 2, 2);
		
		ArrayList bPath = test.getPath();
		int bPathLen = bPath.size();
		
		for (int i = 0; i < bPathLen; i++)
			System.out.printf("%d %d\n", (int)bPath.get(i) / test.Y_MAX, (int)bPath.get(i) % test.Y_MAX);
	}
	
}*/