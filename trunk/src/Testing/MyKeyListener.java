/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Input.AbstractKeyListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author Shiyang
 */
public class MyKeyListener extends AbstractKeyListener{

    @Override
    public void resolveKeyEvents() {
        if (boundTo == null){
            System.out.println("WARNING: NO BOUND SPATIAL TO MOVE!");
            return;
        }
        if (keys[KeyEvent.VK_RIGHT]){
            boundTo.move(5,0,0);
        } if (keys[KeyEvent.VK_LEFT]){
            boundTo.move(-5,0,0);
        } if (keys[KeyEvent.VK_UP]){
            boundTo.move(0,0,-5);
        } if (keys[KeyEvent.VK_DOWN]){
            boundTo.move(0,0,5);
        }
    }
    
}
