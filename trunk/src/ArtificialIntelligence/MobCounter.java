package ArtificialIntelligence;

public class MobCounter {
	
	public int X_MAX, Y_MAX;
	private int [][] tree;
	
	
	public MobCounter(char [][] grid) {
		
		X_MAX = grid.length;
		Y_MAX = grid[0].length;
		
		X_MAX++; Y_MAX++;
		tree = new int[X_MAX + 1][Y_MAX + 1];
		
		for (int i = 1; i < X_MAX; i++)
			for (int j = 1; j < Y_MAX; j++)
				if (grid[i - 1][j - 1] == 1)
					update(i, j, 1);
	}
	
	public void removeMob(int x, int y) {
		update(x + 1, y + 1, -1);
	}
	
	public void addMob(int x, int y) {
		update(x + 1, y + 1, 1);
	}
	
	public void moveMob(int x1, int y1, int x2, int y2) {
		removeMob(x1, y1);
		addMob(x2, y2);
	}
		
	public int getNumMobs(int x1, int y1, int x2, int y2) {
		return query(x2 + 1, y2 + 1) - query(x1 , y2 + 1) - query(x2 + 1, y1) + query(x1, y1);
	}
	
	private void update(int x, int y, int d) {
		while (x < X_MAX + 1) {
			int y1 = y;
			while (y1 < Y_MAX + 1) {
				tree[x][y1] += d;
				y1 += (y1 & -y1);
			}
			x += (x & -x);
		}
	}
	
	private int query(int x, int y) {
		int sum = 0;
		while (x > 0) {
			int y1 = y;
			while (y1 > 0) {
				sum += tree[x][y1];
				y1 -= (y1 & -y1); 
			}
			x -= (x & -x);
		}
		return sum;
	}
	
	public static void main(String [] args) {
		char [][] testGrid = { {1, 1, 1, 1},
								{1, 0, 1, 1},
								{1, 1, 0, 1},
								{1, 0, 1, 0},
								{0, 1, 1, 0},
		};
		MobCounter testCounter = new MobCounter(testGrid);
		System.out.println(testCounter.getNumMobs(1, 1, 3, 2));
		
	}
	
	
}