/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Globals;
import GameSource.User.CharacterHandler;
import GameSource.game.GameMap;
import Input.InputManager;
import Renderer.AetherCam;
import Sound.SoundChannel;
import Sound.SoundManager;
import Spatial.CharacterSpatial;
import Spatial.NPC;
import Spatial.Spatial;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public class GamePanel extends JPanel implements MouseMotionListener,KeyListener,MouseListener{
    private boolean []keys;
    public boolean ready=false,inputstate = true,visibility = true;
    //private int boxx= 200,boxy=200;
    
    private MyTestCharacter myBlock;
    private PlayerSpatial yourBlock;
    private MyKeyListener mykeycontrol;
    private SteveyKeyListener yourkeycontrol;
    private GameMap myMap;
    private AetherCam myCam;
    private NPC mynpc;
    
    public GamePanel(){ //initialize game variables, assign map to UI
        CharacterHandler.init();
        addMouseMotionListener(this);
        addKeyListener(this);
        addMouseListener(this);
        
        keys = new boolean[KeyEvent.KEY_LAST+1];
        //BloonMap gameMap = new BloonMap("monkeyStream");
        //GameUI= new BloonInterface(gameMap);
        //new SpriteSet();
        AssetManager.init();
        SoundManager.init();
        SoundManager.addChannel("Effects", false);
        SoundManager.getChannel("Effects").setNumberTracks(6);
        SoundManager.addChannel("Background",true);
        SoundChannel bg = SoundManager.getChannel("Background");
        bg.addTrack("soundTest1");
        myBlock = new MyTestCharacter(300,1,200);
        yourBlock = new PlayerSpatial(350,1,420);
        mykeycontrol = new MyKeyListener();
        yourkeycontrol = new SteveyKeyListener();
        myBlock.addControl(mykeycontrol);
//        myBlock.rotate(Math.toRadians(45));
        yourBlock.addControl(yourkeycontrol);
        myMap = AssetManager.getMap("testMap");
//        myMap.addSpatial(myBlock);
        myMap.addSpatial(yourBlock);
        myCam = myMap.getCamera();
        mynpc = new NPC(400,1,400,"johnny",new CharacterAnimControl(AssetManager.getSpriteSet("npc/john")));
        myMap.addPermanentSpatial(mynpc);
        Globals.theMouse.bindToMap(myMap);
        myMap.addBackgroundSpatial(new CharacterSpatial(350,0.5f,200,64,64,64,0,0,0) {

            @Override
            public CharacterAnimControl getAnimControl() {
                return new CharacterAnimControl(AssetManager.getSpriteSet("spellRings"));
            }

            @Override
            public void collideEffect(Spatial s) {
            }
        });
//        Portal myport = AssetManager.getPortal("testPort1", 400,1,300);
//        myMap.addPermanentSpatial(myport);
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
//        Globals.theMouse.setLocation(e.getX(),Globals.theMouse.getY(),e.getY());
    }
    @Override    
    public void mouseMoved(MouseEvent e){
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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Globals.theMouse.setLocation(e.getX(),Globals.theMouse.getY(),e.getY());
//        Globals.theMouse.click();
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("DERP");
//        Globals.theMouse.click();
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("MERP");
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
