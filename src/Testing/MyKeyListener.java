/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Controls.CharacterAnimControl;
import Input.AbstractKeyListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author Shiyang
 */
public class MyKeyListener extends AbstractKeyListener{

    @Override
    public void resolveKeyEvents() {
        Object charCont = boundTo.getControl(CharacterAnimControl.class);
        CharacterAnimControl animControl;
        if (charCont != null){
            animControl = (CharacterAnimControl)charCont;
        } else {
            System.out.println("Warning: no animation control :C unable to move character!");
            return;
        }
        String currentAnim = "Stand";
        if (keys[KeyEvent.VK_RIGHT]){
            boundTo.move(3,0,0);
            currentAnim = "WalkLeft";
        } if (keys[KeyEvent.VK_LEFT]){
            boundTo.move(-3,0,0);
            currentAnim = "WalkLeft";
        } if (keys[KeyEvent.VK_UP]){
            boundTo.move(0,0,-3);
            currentAnim = "WalkLeft";
        } if (keys[KeyEvent.VK_DOWN]){
            boundTo.move(0,0,3);
            currentAnim = "WalkLeft";
        }
        animControl.swapAnim(currentAnim);
    }
    
}
