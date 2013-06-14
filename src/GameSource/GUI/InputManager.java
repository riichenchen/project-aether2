package GameSource.GUI;
import java.awt.event.KeyEvent;
import java.util.Arrays;
/* InputManager.java            @Chen~
 * This class is in charge of storing keyboard input states.
 * It contains two booleans arrays for the state of each key,
 * one recording whether the key has just been typed, and the
 * other recording whether the key is being held.
 */
public class InputManager {
    public static boolean[] keys;
    public static boolean[] heldKeys;
    
    public static void init(){
    	keys = new boolean[KeyEvent.KEY_LAST+1];
        heldKeys = new boolean[KeyEvent.KEY_LAST+1];
    	Arrays.fill(keys,false);
        Arrays.fill(heldKeys,false);
    }
    
    public static void keyDown(int keyId){
        //Invoked upon a KeyListener.keyPressed() method call. The method adjusts
        //the boolean arrays as follows: 
        //      -If the key was previously typed, the key is now being held and not typed
        //      -If the key was previously neither typed nor held, the key is now being typed
        //      -If the key was previously held but not typed, the key is still being held.
        if (keyId==KeyEvent.VK_SHIFT){
            heldKeys[keyId]=true;       //Shift is always held down to prevent lag in typing
        }
        if (keys[keyId]){
            heldKeys[keyId] = true;
            keys[keyId]=false;
        }
        else if (heldKeys[keyId]==false)
            keys[keyId]=true;
    }
    public static void keyUp(int keyId){
        //Invoked on a KeyListener.keyReleased() call; clears the key to false in both arrays.
            keys[keyId]=false;
            heldKeys[keyId] = false;
        
    }
    public static void keyTyped(int keyId){}
    
    public static void clearKey(int key){           //"Release" a key
        keys[key]=false;
        heldKeys[key]=false;
    }
    public static boolean down (int key){           //Check if a key is either typed or held
        return keys[key]||heldKeys[key];
    }

}
