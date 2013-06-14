/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Game;

import GameSource.Assets.AssetManager;
import GameSource.GUI.AImageFactory;
import GameSource.GUI.AMouseInput;
import GameSource.GUI.MyGUI;
import GameSource.Quest.QuestManager;
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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 * The main game panel of the game and the medium that gets input from then
 * keyboard and mouse as well as outputting content to the player.
 * 
 * @author Shiyang
 */
public class AetherGamePanel extends JPanel implements MouseMotionListener,KeyListener, MouseListener{
    public boolean ready=false,inputstate = true,visibility = true;

    private GameMap gameMap; //gamepanel has a handle to the main map for rendering
    private ClientWorldHandler handler;
    
    public void setMap(GameMap gameMap){ // standard setMap method
        this.gameMap = gameMap;
    }
    
    public void setHandler(ClientWorldHandler handler){ 
        this.handler = handler; // called by clientMain to attach a handler
    }
    public AetherGamePanel(){ //initialize game variables, assign map to UI
        addMouseMotionListener(this); //add in input listeners, initialize static classes
        addKeyListener(this);
        addMouseListener(this);
        AssetManager.init();
        SoundManager.init();
        CharacterHandler.init();
        EquipHandler.init();
        AImageFactory.init();
        QuestManager.init();
        setSize(800,600);
        
        MyGUI.init(800,600);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true; // make sure our panel is prepared before rendering
    }
    
    public void update(){
        handler.update(); //update the game and gui components
        MyGUI.update();
    }
    
    @Override
    public void paintComponent(Graphics g){//display the UI
        if (ready){
            g.setColor(Color.GRAY); 
            g.fillRect(0,0,800,600);//this should never actually be seen
            gameMap.render(g,this);//show the map and gui
            MyGUI.draw(g);
        }
    }
    
    // ---------- MouseMotionListener ------------------------------------------
    @Override
    public void mouseDragged(MouseEvent e){
        AMouseInput.update(e);
    }
    @Override    
    public void mouseMoved(MouseEvent e){
        AMouseInput.update(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        try{
            GameSource.GUI.InputManager.keyTyped(e.getKeyCode());
        }catch (ArrayIndexOutOfBoundsException gg){}
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        InputManager.keyDown(e.getKeyCode()); // todo: restrict keyinput from the game when using shop
        try{
            GameSource.GUI.InputManager.keyDown(e.getKeyCode());
        }catch (ArrayIndexOutOfBoundsException gg){}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try{
            InputManager.keyUp(e.getKeyCode());
            GameSource.GUI.InputManager.keyUp(e.getKeyCode());
        } catch (ArrayIndexOutOfBoundsException gg) {}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //AMouseInput.click(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        AMouseInput.press(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        AMouseInput.release(e);
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
