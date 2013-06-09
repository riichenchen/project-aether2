package GameSource.GUI;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class InputManager {
    public static boolean[] keys;
    public static boolean[] heldKeys;
    
    public static void keyDown(int keyId){
        if (keyId==KeyEvent.VK_SHIFT){
            heldKeys[keyId]=true;
        }
        if (keys[keyId]){
            heldKeys[keyId] = true;
            keys[keyId]=false;
        }
        else if (heldKeys[keyId]==false)
            keys[keyId]=true;
    }
    public static void keyUp(int keyId){
            keys[keyId]=false;
            heldKeys[keyId] = false;
        
    }
    public static void keyTyped(int keyId){
//        keys[keyId]=true;
//        if (keyId==KeyEvent.VK_I)
//            System.out.println("i!");
    }
    public static void clearTyped(){
        Arrays.fill(keys,false);
    }
    public static void clearKey(int key){
        keys[key]=false;
        heldKeys[key]=false;
    }
    public static boolean down (int key){
        return keys[key]||heldKeys[key];
    }
    public static void init(){
    	keys = new boolean[KeyEvent.KEY_LAST+1];
        heldKeys = new boolean[KeyEvent.KEY_LAST+1];
    	Arrays.fill(keys,false);
        Arrays.fill(heldKeys,false);
    }
}
