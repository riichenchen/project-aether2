/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.Timer;




/**
 *
 * @author Shiyang
 */
public class RenderTest extends JFrame implements ActionListener{
    Timer myTimer;   
    GamePanel myGame;
		
    public RenderTest(){
        super("Testing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);

        myTimer = new Timer(10, this);	 // trigger every 10 ms
        myTimer.start();
        
        myGame = new GamePanel();
        add(myGame);

        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource().equals(myTimer) && myGame!= null && myGame.ready){
            myGame.move();
            myGame.update();
            myGame.repaint(); //show current point in time
        }
    }

    public static void main(String[] arguments) throws IOException{
        RenderTest frame = new RenderTest();		
    }
}