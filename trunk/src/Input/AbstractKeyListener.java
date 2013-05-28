/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Controls.AbstractControl;
import java.util.HashMap;

/**
 * This is the keylistener class. All listeners are essentially controls
 * that belong to spatials. The abstractkeylistener provides a way for
 * the main Swing application to communicate with the game engine.
 * 
 * @author Shiyang
 */
public abstract class AbstractKeyListener extends AbstractControl{
    private boolean[] keys;
    private boolean[] pressedKeys;
    protected HashMap<Integer,Boolean> prevPressed;
    private int listenerID;
    
    public AbstractKeyListener(){
        InputManager.addKeyListener(this);
    }
    
    public void bindTo(boolean[] keys,boolean[] pressedKeys,int id){
        this.keys = keys;
        this.listenerID = id;
        this.pressedKeys = pressedKeys;
        this.prevPressed = new HashMap<>();
    }
    
    public boolean eventKeyDown(int key){
        if (keys[key] && !prevPressed.containsKey(key)){
            prevPressed.put(key, Boolean.TRUE);
            return true;
        } else {
            return false;
        }
    }
    public boolean keyDown(int key){
        return keys[key];
    }
    
    public void removeBind(){
        keys = null;
    }
    
    //Each listener must provide a way to take care of key events
    public abstract void resolveKeyEvents();
    
    @Override
    public void update(){
        if (keys == null){
            System.out.println("WARNING: KEYLISTENER "+listenerID+" HAS NO KEYSET BINDING!");
            return;
        }
        if (boundTo == null){
            System.out.println("WARNING: NO BOUND SPATIAL TO MOVE!");
            return;
        }
        //allow key to be counted for again if it was released
        for (int key: prevPressed.keySet().toArray(new Integer[0])){
            if (!pressedKeys[key]){
                prevPressed.remove(key);
            }
        }
        
        resolveKeyEvents();
    }
}
