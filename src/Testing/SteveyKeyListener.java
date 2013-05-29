/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Controls.CharacterAnimControl;
import GameSource.Effects.IceyEffect;
import Input.AbstractKeyListener;
import Spatial.Spatial;
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
        if (keyDown(KeyEvent.VK_RIGHT) && keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(3, 0, 3);
            boundTo.rotate(Math.toRadians(135));
            currentAnim = "walkdownright";
        } else if (keyDown(KeyEvent.VK_RIGHT) && keyDown(KeyEvent.VK_UP)){
            boundTo.move(3,0,-3);
            boundTo.rotate(Math.toRadians(45));
            currentAnim = "walkupright";
        } else if (keyDown(KeyEvent.VK_LEFT) && keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(-3,0,3);
            boundTo.rotate(Math.toRadians(225));
            currentAnim = "walkdownleft";
        } else if (keyDown(KeyEvent.VK_LEFT) && keyDown(KeyEvent.VK_UP)){
            boundTo.move(-3,0,-3);
            boundTo.rotate(Math.toRadians(315));
            currentAnim = "walkupleft";
        }  else if (keyDown(KeyEvent.VK_RIGHT)){
            boundTo.move(3,0,0);
            boundTo.setRotation(90);
            currentAnim = "walkright";
        } else if (keyDown(KeyEvent.VK_LEFT)){
            boundTo.move(-3,0,0);
            boundTo.setRotation(270);
            currentAnim = "walkleft";
        } else if (keyDown(KeyEvent.VK_UP)){
            boundTo.move(0,0,-3);
            boundTo.setRotation(0);
            currentAnim = "walkup";
        } else if (keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(0,0,3);
            boundTo.setRotation(180);
            currentAnim = "walkdown";
        }
        if (eventKeyDown(KeyEvent.VK_SHIFT)){
            float x = boundTo.getX();
            float y= boundTo.getY();
            float z = boundTo.getZ();
            float nx = (float)(x+300*Math.cos(Math.toRadians(boundTo.getRotation())));
            float nz = (float)(z+300*Math.sin(Math.toRadians(boundTo.getRotation())));
            Spatial[] hit = boundTo.getMap().getSpace().rayCast(x,z,nx,nz);
            for (Spatial spat: hit){
                boundTo.getMap().addSpatial(new IceyEffect(spat.getX(),1,spat.getZ()));
            }
        }
        animControl.swapAnim(currentAnim);        
    }
}
