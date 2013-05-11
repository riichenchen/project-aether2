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
import Renderer.Renderer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public class GamePanel extends JPanel implements MouseMotionListener,KeyListener{
    private boolean []keys;
    public boolean ready=false,inputstate = true;
    //private int boxx= 200,boxy=200;
    private Renderer myRender;
    private Other_Block myBlock,yourBlock;
    private MyKeyListener mykeycontrol,yourkeycontrol;
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
        yourBlock = new Other_Block(350,1,200,ids++);
        mykeycontrol = new MyKeyListener();
        yourkeycontrol = new MyKeyListener();
        myBlock.addControl(mykeycontrol);
        yourBlock.addControl(yourkeycontrol);
        myRender = new Renderer(new GameMap("Mymap",1));
        myRender.addSpatial(myBlock);
        myRender.addSpatial(yourBlock);
        myRender.setCam(myCam);
        Random myrand = new Random();
        for (int i = 0; i < 5000; i++){
            myRender.addSpatial(new Dirt_Block(myrand.nextInt(1600/50)*50,0,myrand.nextInt(1200/28)*28,ids++));
        }
        for (int i = 0; i < 50; i++){
            myRender.addSpatial(new Dirt_Block(myrand.nextInt(1600/50)*50,100,myrand.nextInt(1200/28)*28,ids++));
        }
        setSize(800,600);
    }
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }
    public void update(){
        updateSpatials();
        myRender.update();
    }
    
    private void updateSpatials(){
        myBlock.update();
        yourBlock.update();
    }
    public void move(){
        if (keys[KeyEvent.VK_D]){
            myRender.translateCamLocation(5,0);
        } if (keys[KeyEvent.VK_A]){
            myRender.translateCamLocation(-5,0);
        } if (keys[KeyEvent.VK_W]){
            myRender.translateCamLocation(0,-5);
        } if (keys[KeyEvent.VK_S]){
            myRender.translateCamLocation(0,5);
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
                System.out.println("Control off!");
            } else {
                myBlock.addControl(mykeycontrol);
                System.out.println("Control on!");
            }
            inputstate = !inputstate;
        }
    }
}
