/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import GameSource.Assets.AssetManager;
import GameSource.Effects.IceyEffect;
import GameSource.game.GameMap;
import Input.InputManager;
import Renderer.AetherCam;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public class GamePanel extends JPanel implements MouseMotionListener,KeyListener{
    private boolean []keys;
    public boolean ready=false,inputstate = true,visibility = true;
    //private int boxx= 200,boxy=200;
    
    private MyTestCharacter myBlock;
    private Stevey yourBlock;
    private MyKeyListener mykeycontrol;
    private SteveyKeyListener yourkeycontrol;
    private GameMap myMap;
    private AetherCam myCam;
    
    public GamePanel(){ //initialize game variables, assign map to UI
        addKeyListener(this);
        addMouseMotionListener(this);
        
        keys = new boolean[KeyEvent.KEY_LAST+1];
        //BloonMap gameMap = new BloonMap("monkeyStream");
        //GameUI= new BloonInterface(gameMap);
        //new SpriteSet();
        AssetManager.init();
        myBlock = new MyTestCharacter(300,1,200);
        yourBlock = new Stevey(350,1,400);
        mykeycontrol = new MyKeyListener();
        yourkeycontrol = new SteveyKeyListener();
        myBlock.addControl(mykeycontrol);
//        myBlock.rotate(Math.toRadians(45));
        yourBlock.addControl(yourkeycontrol);
        myMap = AssetManager.getMap("testMap");
//        myMap.addSpatial(myBlock);
        myMap.addSpatial(yourBlock);
        myCam = myMap.getCamera();
        Portal myport = new Portal(400,1,300);
        myMap.addPermanentSpatial(myport);
//        Random myrand = new Random();
//        for (int i = 0; i < 5000; i++){
//            myMap.addSpatial(new Dirt_Block(myrand.nextInt(1600/50)*50,0,myrand.nextInt(1200/28)*28));
//        }
//        for (int i = 0; i < 50; i++){
//            myMap.addSpatial(new Dirt_Block(myrand.nextInt(1600/50)*50,100,myrand.nextInt(1200/28)*28));
//        }
        setSize(800,600);
    }
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }
    
    public void update(){
        myMap.update();
    }
    
    public void move(){
        if (keys[KeyEvent.VK_D]){
            myCam.translateLocation(5,0);
        } if (keys[KeyEvent.VK_A]){
            myCam.translateLocation(-5,0);
        } if (keys[KeyEvent.VK_W]){
            myCam.translateLocation(0,-5);
        } if (keys[KeyEvent.VK_S]){
            myCam.translateLocation(0,5);
        }
    }
    @Override
    public void paintComponent(Graphics g){//display the UI
        g.setColor(Color.GRAY);
        g.fillRect(0,0,800,600);
	myMap.render(g,this);
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
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        InputManager.keyDown(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        InputManager.keyUp(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            if (inputstate) {
                myBlock.removeControl(mykeycontrol);
                //System.out.println("Control off!");
            } else {
                myBlock.addControl(mykeycontrol);
                //System.out.println("Control on!");
            }
            inputstate = !inputstate;
        }
        
         
        if (e.getKeyCode() == KeyEvent.VK_V){
            if (visibility) {
                myMap.hideSpatial(myBlock);
                //System.out.println("Invisible!");
            } else {
                myMap.revealSpatial(myBlock);
                //System.out.println("Visible!!");
            }
            visibility = !visibility;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT){
            myMap.addSpatial(new IceyEffect(yourBlock.getX(),yourBlock.getY(),yourBlock.getZ()+10));
        }
    }
}
