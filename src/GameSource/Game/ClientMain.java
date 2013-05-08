import javax.swing.*;

public class ClientMain {
	
	private static void startGame() {
		JFrame f = new JFrame("GAME_NAME");
		GamePanel gp = new GamePanel();
		f.getContentPane().add(gp);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		f.setVisible(true);
	}
	
	public static void main(String [] args) {
		startGame();
	}
}