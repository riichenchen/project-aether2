/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Game;

import GameSource.Assets.AssetManager;
import GameSource.game.GameMap;
import Input.InputManager;
import Renderer.AetherCam;
import Sound.SoundManager;
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
public class AetherGamePanel extends JPanel implements MouseMotionListener,KeyListener{
    private boolean []keys;
    public boolean ready=false,inputstate = true,visibility = true;

    private GameMap gameMap;
    private AetherCam camera;
    private ClientWorldHandler handler;
    
    public void setMap(GameMap gameMap){
        this.gameMap = gameMap;
    }
    
    public void setCamera(){
        this.camera = gameMap.getCamera();
    }
    
    public void setHandler(ClientWorldHandler handler){
        this.handler = handler;
    }
    public AetherGamePanel(){ //initialize game variables, assign map to UI
        addMouseMotionListener(this);
        addKeyListener(this);
        keys = new boolean[KeyEvent.KEY_LAST+1];
        AssetManager.init();
        SoundManager.init();
        setSize(800,600);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }
    
    public void update(){
        handler.update();
    }
    
    public void move(){
        if (keys[KeyEvent.VK_D]){
            camera.translateLocation(5,0);
        } if (keys[KeyEvent.VK_A]){
            camera.translateLocation(-5,0);
        } if (keys[KeyEvent.VK_W]){
            camera.translateLocation(0,-5);
        } if (keys[KeyEvent.VK_S]){
            camera.translateLocation(0,5);
        }
    }
    @Override
    public void paintComponent(Graphics g){//display the UI
        g.setColor(Color.GRAY);
        g.fillRect(0,0,800,600);
	gameMap.render(g,this);
    }
    
    // ---------- MouseMotionListener ------------------------------------------
    @Override
    public void mouseDragged(MouseEvent e){
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
    }
}
