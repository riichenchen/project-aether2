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
 */
public class InputManager {
    private static HashMap<Integer,AbstractKeyListener> listeners = new HashMap<>();
    private static boolean[] keys = new boolean[KeyEvent.KEY_LAST+1];
//    private static boolean[] eventKeys = new boolean[KeyEvent.KEY_LAST+1];
    private static boolean[] pressedKeys = new boolean[KeyEvent.KEY_LAST+1];
    
    private static int IDs = 0;//used to track number of listeners
    
    public static void keyDown(int keyId){
        keys[keyId] = true;
//        if (!pressedKeys[keyId]){
//        eventKeys[keyId] = true;
        pressedKeys[keyId] = true;
            //System.out.println("hai");
//        } 
    }
    
    public static void keyUp(int keyId){
        keys[keyId] = false;
        pressedKeys[keyId] = false;
//        System.out.println("AHHHI");
    }
    
    //Gives a pointer to keys to each listener
    public static void addKeyListener(AbstractKeyListener listener){
        listeners.put(IDs,listener);
        listener.bindTo(keys,pressedKeys,IDs++);
    }
    
}
