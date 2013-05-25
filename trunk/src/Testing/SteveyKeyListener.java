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
public class SteveyKeyListener extends AbstractKeyListener{

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
        String currentAnim = "walkdown";
        if (keys[KeyEvent.VK_RIGHT] && keys[KeyEvent.VK_DOWN]){
            boundTo.move(3, 0, 3);
            currentAnim = "walkdownright";
        } else if (keys[KeyEvent.VK_RIGHT] && keys[KeyEvent.VK_UP]){
            boundTo.move(3,0,-3);
            currentAnim = "walkupright";
        } else if (keys[KeyEvent.VK_LEFT] && keys[KeyEvent.VK_DOWN]){
            boundTo.move(-3,0,3);
            currentAnim = "walkdownleft";
        } else if (keys[KeyEvent.VK_LEFT] && keys[KeyEvent.VK_UP]){
            boundTo.move(-3,0,-3);
            currentAnim = "walkupleft";
        }  else if (keys[KeyEvent.VK_RIGHT]){
            boundTo.move(3,0,0);
            currentAnim = "walkright";
        } else if (keys[KeyEvent.VK_LEFT]){
            boundTo.move(-3,0,0);
            currentAnim = "walkleft";
        } else if (keys[KeyEvent.VK_UP]){
            boundTo.move(0,0,-3);
            currentAnim = "walkup";
        } else if (keys[KeyEvent.VK_DOWN]){
            boundTo.move(0,0,3);
            currentAnim = "walkdown";
        }
        animControl.swapAnim(currentAnim);
    }
    
}
