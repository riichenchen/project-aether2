import javax.swing.*;

public class ClientMain {
        
	public static JFrame f;
    private int X_MAX = 800;
	private int Y_MAX = 600;
	private ArrayList maps;
	
	
	private static void login() {
		f = new JFrame("LOGIN TO GAME_NAME");
		LoginFrame lf = new LoginFrame();
		f.getContentPane().add(lf);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(LoginFrame.X, LoginFrame.Y);
		f.setVisible(true);
	}
        
	public static void startGame() {
	    f.dispose();
	    f = new JFrame("GAME_NAME");
		GamePanel gp = new GamePanel();
		f.getContentPane().add(gp);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(GamePanel.X, GamePanel.Y);
		System.out.println("Start Game");
		loadMaps();
		findMap(0, 0, X_MAX, Y_MAX);
		f.setVisible(true);
	}
	
	private static void loadMaps() {
		maps = new ArrayList<GameMap>();
		//Load maps
	}
	
	private static int findMap(int lx, int ly, int ux, int uy) {
		//Look for map to render
	}
	
	public static void main(String [] args) {
		login();
	}
}