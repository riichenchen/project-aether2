import javax.swing.*;

public class ClientMain {
        
        public static JFrame f;
    
	private static void login() {
		f = new JFrame("GAME_NAME");
                LoginFrame lf = new LoginFrame();
		f.getContentPane().add(lf);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(LoginFrame.X, LoginFrame.Y);
		f.setVisible(true);
	}
        
        public static void startGame() {
            System.out.println("startGame");
        }
	
	public static void main(String [] args) {
		login();
	}
}