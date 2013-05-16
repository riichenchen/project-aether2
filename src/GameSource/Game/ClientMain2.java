package GameSource.Game;
import GameSource.game.GameMap;
import java.util.ArrayList;
import javax.swing.*;

public class ClientMain2 {
        
    public static JFrame f;
    private int X_MAX = 800;
    private int Y_MAX = 600;
    private ArrayList maps;


    private static void login() {
            f = new JFrame("LOGIN TO GAME_NAME");
            LoginFrame2 lf = new LoginFrame2();
            f.getContentPane().add(lf);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(LoginFrame2.X, LoginFrame2.Y);
            f.setVisible(true);
    }

    public static void startGame() {
        throw new UnsupportedOperationException("Not supported yet.");
//	    f.dispose();
//	    f = new JFrame("GAME_NAME");
//		GamePanel gp = new GamePanel();
//		f.getContentPane().add(gp);
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setSize(GamePanel.X, GamePanel.Y);
//		System.out.println("Start Game");
//		loadMaps();
//		findMap(0, 0, X_MAX, Y_MAX);
//		f.setVisible(true);
    }

    private static void loadMaps() {
        throw new UnsupportedOperationException("Not supported yet.");
//		maps = new ArrayList<GameMap>();
            //Load maps
    }

    private static int findMap(int lx, int ly, int ux, int uy) {
        throw new UnsupportedOperationException("Not supported yet.");
        //throw new Exception("WRONG!");
            //Look for map to render
    }

    public static void main(String [] args) {
            login();
    }
}