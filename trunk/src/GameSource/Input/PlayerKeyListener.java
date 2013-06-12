/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Input;

import Controls.CharacterAnimControl;
import Input.AbstractKeyListener;
import PhysicsSync.SpatActionMessage;
import java.awt.event.KeyEvent;

/**
 * @author Shiyang
 * This keyListener was once used for the multiplayer beta and an example
 * implementation of abstractkeylistener.
 * 
 * Not used anymore.
 */
public class PlayerKeyListener extends AbstractKeyListener{

    @Override
    public void resolveKeyEvents() {
        Object charCont = boundTo.getControl(CharacterAnimControl.class);
        CharacterAnimControl animControl = null;
        if (charCont != null){
            animControl = (CharacterAnimControl)charCont;
        }
        String currentAnim = "Stand";
        
        if (keyDown(KeyEvent.VK_RIGHT)){
            boundTo.move(5,0,0);
            currentAnim = "WalkRight";
        } if (keyDown(KeyEvent.VK_LEFT)){
            boundTo.move(-5,0,0);
            currentAnim = "WalkLeft";
        } if (keyDown(KeyEvent.VK_UP)){
            boundTo.move(0,0,-5);
        } if (keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(0,0,5);
        }
        if (animControl != null){
            animControl.swapAnim(currentAnim);
        }
    }
}
