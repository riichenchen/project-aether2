package GameSource.GUI;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class InputManager {
    private boolean[] keys;
    
    public void keyDown(int keyId){
        keys[keyId] = true;
    }
    public void keyUp(int keyId){
        keys[keyId] = false;
    }
    
    public InputManager(){
    	keys = new boolean[KeyEvent.KEY_LAST+1];
    	Arrays.fill(keys,false);
    }
    
    public boolean [] get_keys(){
    	return keys;
    }
}
