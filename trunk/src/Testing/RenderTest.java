/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import GameSource.Assets.AssetManager;
import GameSource.Assets.TerrainBlocks.Blocks.DirtBlock.Dirt_Block;
import GameSource.Assets.TerrainBlocks.Blocks.otherblock.Other_Block;
import GameSource.Renderer.AetherCam;
import GameSource.Renderer.RenderMessage;
import GameSource.Renderer.Renderer;
import GameSource.game.GameMap;
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
class GamePanel extends JPanel implements MouseMotionListener,KeyListener{
    private boolean []keys;
    public boolean ready=false;
    //private int boxx= 200,boxy=200;
    private Renderer myRender;
    private Other_Block myBlock;
    private int ids = 0;
    private AetherCam myCam = new AetherCam(800,600);
    public GamePanel(){ //initialize game variables, assign map to UI
            addMouseMotionListener(this);
            addKeyListener(this);
            keys = new boolean[KeyEvent.KEY_LAST+1];
            //BloonMap gameMap = new BloonMap("monkeyStream");
            //GameUI= new BloonInterface(gameMap);
            //new SpriteSet();
            AssetManager.init();
            myBlock = new Other_Block(300,1,200,ids++);
            myRender = new Renderer(new GameMap("Mymap",1));
            myRender.addSpatial(myBlock);
            myRender.setCam(myCam);
            Random myrand = new Random();
            for (int i = 0; i < 5000; i++){
                myRender.addSpatial(new Dirt_Block(myrand.nextInt(1600/50)*50,0,myrand.nextInt(1200/28)*28,ids++));
            }
            setSize(800,600);
    }
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }
    public void update(){
        myRender.update();
    }
    public void move(){
        
        if (keys[KeyEvent.VK_RIGHT]){
            myBlock.move(5,0,0);
            myRender.updateSpat(new RenderMessage(myBlock,RenderMessage.UPDATE));
        } if (keys[KeyEvent.VK_LEFT]){
            myBlock.move(-5,0,0);
            myRender.updateSpat(new RenderMessage(myBlock,RenderMessage.UPDATE));
        } if (keys[KeyEvent.VK_UP]){
            myBlock.move(0,0,-5);
            myRender.updateSpat(new RenderMessage(myBlock,RenderMessage.UPDATE));
        } if (keys[KeyEvent.VK_DOWN]){
            myBlock.move(0,0,5);
            myRender.updateSpat(new RenderMessage(myBlock, RenderMessage.UPDATE));
        }
        
        if (keys[KeyEvent.VK_D]){
            myRender.translateCamLocation(5,0);
            //myBlock.move(5,0,0);
            //myRender.updateSpat(new RenderMessage(myBlock,RenderMessage.UPDATE));
        } if (keys[KeyEvent.VK_A]){
            myRender.translateCamLocation(-5,0);
            //myBlock.move(-5,0,0);
            //myRender.updateSpat(new RenderMessage(myBlock,RenderMessage.UPDATE));
        } if (keys[KeyEvent.VK_W]){
            myRender.translateCamLocation(0,-5);
           // myBlock.move(0,0,-5);
            //myRender.updateSpat(new RenderMessage(myBlock,RenderMessage.UPDATE));
        } if (keys[KeyEvent.VK_S]){
            myRender.translateCamLocation(0,5);
           /// myBlock.move(0,0,5);
            //myRender.updateSpat(new RenderMessage(myBlock, RenderMessage.UPDATE));
        }
    }
    @Override
    public void paintComponent(Graphics g){//display the UI
        g.setColor(Color.GRAY);
        g.fillRect(0,0,800,600);
	myRender.render(g,this);
    }
    
    // ---------- MouseMotionListener ------------------------------------------
    @Override
    public void mouseDragged(MouseEvent e){
//    	GameUI.moveMouse(e.getX(),e.getY());
    }
    @Override    
    public void mouseMoved(MouseEvent e){
//    	GameUI.moveMouse(e.getX(),e.getY());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
