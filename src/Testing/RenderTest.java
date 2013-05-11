/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import GameSource.Assets.AssetManager;
import GameSource.Assets.TerrainBlocks.Blocks.DirtBlock.Dirt_Block;
import GameSource.Assets.TerrainBlocks.Blocks.otherblock.Other_Block;
import GameSource.game.GameMap;
import Input.InputManager;
import Renderer.AetherCam;
import Renderer.RenderMessage;
import Renderer.Renderer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
                    //myGame.clockTick();//tick time
                    myGame.move();
                    myGame.update();
                    myGame.repaint(); //show current point in time
            }
    }

    public static void main(String[] arguments) throws IOException{
        RenderTest frame = new RenderTest();		
    }
}