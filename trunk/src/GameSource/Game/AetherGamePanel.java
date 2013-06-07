/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Game;

import GameSource.Assets.AssetManager;
import GameSource.GUI.AImageFactory;
import GameSource.GUI.AMouseInput;
import GameSource.GUI.MyGUI;
import GameSource.User.CharacterHandler;
import GameSource.User.EquipHandler;
import GameSource.User.ItemFactory;
import GameSource.game.GameMap;
import Input.InputManager;
import Sound.SoundManager;
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
public class AetherGamePanel extends JPanel implements MouseMotionListener,KeyListener, MouseListener{
    public boolean ready=false,inputstate = true,visibility = true;

    private GameMap gameMap;
    private ClientWorldHandler handler;
    
    private MyGUI myGUI;
    private GameSource.GUI.InputManager in;
    private AMouseInput mouse;
    
    public void setMap(GameMap gameMap){
        this.gameMap = gameMap;
    }
    
    public void setHandler(ClientWorldHandler handler){
        this.handler = handler;
    }
    public AetherGamePanel(){ //initialize game variables, assign map to UI
        addMouseMotionListener(this);
        addKeyListener(this);
        addMouseListener(this);
        AssetManager.init();
        SoundManager.init();
        CharacterHandler.init();
        EquipHandler.init();
        AImageFactory.init();
        setSize(800,600);
        
        in = new GameSource.GUI.InputManager();
	mouse = new AMouseInput ();
        myGUI = new MyGUI(in,mouse, 800,600);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }
    
    public void update(){
        handler.update();
        myGUI.update();
    }
    
    @Override
    public void paintComponent(Graphics g){//display the UI
        if (ready){
            g.setColor(Color.GRAY);
            g.fillRect(0,0,800,600);
            gameMap.render(g,this);
            myGUI.draw(g);
        }
    }
    
    // ---------- MouseMotionListener ------------------------------------------
    @Override
    public void mouseDragged(MouseEvent e){
        mouse.update(e);
    }
    @Override    
    public void mouseMoved(MouseEvent e){
        mouse.update(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        InputManager.keyDown(e.getKeyCode());
        in.keyDown(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        InputManager.keyUp(e.getKeyCode());
        in.keyUp(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse.press(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse.release(e);
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
