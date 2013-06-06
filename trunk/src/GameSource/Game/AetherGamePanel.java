/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Game;

import GameSource.Assets.AssetManager;
import GameSource.User.CharacterHandler;
import GameSource.User.EquipHandler;
import GameSource.game.GameMap;
import Input.InputManager;
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
    public boolean ready=false,inputstate = true,visibility = true;

    private GameMap gameMap;
    private ClientWorldHandler handler;
    
    public void setMap(GameMap gameMap){
        this.gameMap = gameMap;
    }
    
    public void setHandler(ClientWorldHandler handler){
        this.handler = handler;
    }
    public AetherGamePanel(){ //initialize game variables, assign map to UI
        addMouseMotionListener(this);
        addKeyListener(this);
        AssetManager.init();
        SoundManager.init();
        CharacterHandler.init();
        EquipHandler.init();
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
    
    @Override
    public void paintComponent(Graphics g){//display the UI
        if (ready){
            g.setColor(Color.GRAY);
            g.fillRect(0,0,800,600);
            gameMap.render(g,this);
        }
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
        InputManager.keyDown(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        InputManager.keyUp(e.getKeyCode());
    }
}
