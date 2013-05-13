/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Controls.AbstractControl;

/**
 * This is the keylistener class. All listeners are essentially controls
 * that belong to spatials. The abstractkeylistener provides a way for
 * the main Swing application to communicate with the game engine.
 * 
 * @author Shiyang
 */
public abstract class AbstractKeyListener extends AbstractControl{
    protected boolean[] keys;
    private int listenerID;
    
    public AbstractKeyListener(){
        InputManager.addKeyListener(this);
    }
    
    public void bindTo(boolean[] keys,int id){
        this.keys = keys;
        this.listenerID = id;
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
        resolveKeyEvents();
    }
}
