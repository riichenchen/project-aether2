/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author Shiyang
 * The input manager provides a way for the gamepanel to
 * communicate input with all input controls in the game
 */
public class InputManager {
    private static HashMap<Integer,AbstractKeyListener> listeners = new HashMap<>();
    private static boolean[] keys = new boolean[KeyEvent.KEY_LAST+1];
    private static boolean[] pressedKeys = new boolean[KeyEvent.KEY_LAST+1];
    
    private static int IDs = 0;//used to track number of listeners
    
    public static void keyDown(int keyId){
        keys[keyId] = true;
        pressedKeys[keyId] = true;
    }
    
    public static void keyUp(int keyId){
        keys[keyId] = false;
        pressedKeys[keyId] = false;
    }
    
    //Gives a pointer to keys to each listener
    public static void addKeyListener(AbstractKeyListener listener){
        listeners.put(IDs,listener);
        listener.bindTo(keys,pressedKeys,IDs++);
    }
    
}
