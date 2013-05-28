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
    int n = 0;
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
        if (keyDown(KeyEvent.VK_RIGHT) && keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(3, 0, 3);
            currentAnim = "walkdownright";
        } else if (keyDown(KeyEvent.VK_RIGHT) && keyDown(KeyEvent.VK_UP)){
            boundTo.move(3,0,-3);
            currentAnim = "walkupright";
        } else if (keyDown(KeyEvent.VK_LEFT) && keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(-3,0,3);
            currentAnim = "walkdownleft";
        } else if (keyDown(KeyEvent.VK_LEFT) && keyDown(KeyEvent.VK_UP)){
            boundTo.move(-3,0,-3);
            currentAnim = "walkupleft";
        }  else if (keyDown(KeyEvent.VK_RIGHT)){
            boundTo.move(3,0,0);
            currentAnim = "walkright";
        } else if (keyDown(KeyEvent.VK_LEFT)){
            boundTo.move(-3,0,0);
            currentAnim = "walkleft";
        } else if (keyDown(KeyEvent.VK_UP)){
            boundTo.move(0,0,-3);
            currentAnim = "walkup";
        } else if (keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(0,0,3);
            currentAnim = "walkdown";
        }
        if (eventKeyDown(KeyEvent.VK_DOWN)){
            System.out.println("Times pressed: "+(n++));
        }
        animControl.swapAnim(currentAnim);
    }
    
}
