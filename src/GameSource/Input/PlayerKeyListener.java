/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Input;

import Input.AbstractKeyListener;
import PhysicsSync.SpatActionMessage;
import java.awt.event.KeyEvent;

/**
 *
 * @author Shiyang
 */
public class PlayerKeyListener extends AbstractKeyListener{

    @Override
    public void resolveKeyEvents() {
        if (keys[KeyEvent.VK_RIGHT]){
            boundTo.move(5,0,0);
            world.addSpatAction(boundTo,SpatActionMessage.MOVERIGHT);
        } if (keys[KeyEvent.VK_LEFT]){
            boundTo.move(-5,0,0);
            world.addSpatAction(boundTo,SpatActionMessage.MOVELEFT);
        } if (keys[KeyEvent.VK_UP]){
            boundTo.move(0,0,-5);
            world.addSpatAction(boundTo,SpatActionMessage.MOVEUP);
        } if (keys[KeyEvent.VK_DOWN]){
            boundTo.move(0,0,5);
            world.addSpatAction(boundTo,SpatActionMessage.MOVEDOWN);
        }
    }
    
}
