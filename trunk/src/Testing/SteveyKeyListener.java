/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Controls.CharacterAnimControl;
import GameSource.Effects.IceyEffect;
import Input.AbstractKeyListener;
import Math.Point2D;
import Spatial.Spatial;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author Shiyang
 */
public class SteveyKeyListener extends AbstractKeyListener{
    float speed = 3f;
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
        float diagonal = (float)Math.sin(Math.toRadians(45));
        if (keyDown(KeyEvent.VK_RIGHT) && keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(speed*diagonal, 0, speed*diagonal);
            boundTo.rotate(Math.toRadians(135));
            currentAnim = "walkdownright";
        } else if (keyDown(KeyEvent.VK_RIGHT) && keyDown(KeyEvent.VK_UP)){
            boundTo.move(speed*diagonal,0,-speed*diagonal);
            boundTo.rotate(Math.toRadians(45));
            currentAnim = "walkupright";
        } else if (keyDown(KeyEvent.VK_LEFT) && keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(-speed*diagonal,0,speed*diagonal);
            boundTo.rotate(Math.toRadians(225));
            currentAnim = "walkdownleft";
        } else if (keyDown(KeyEvent.VK_LEFT) && keyDown(KeyEvent.VK_UP)){
            boundTo.move(-speed*diagonal,0,-speed*diagonal);
            boundTo.rotate(Math.toRadians(315));
            currentAnim = "walkupleft";
        }  else if (keyDown(KeyEvent.VK_RIGHT)){
            boundTo.move(speed,0,0);
            boundTo.setRotation(90);
            currentAnim = "walkright";
        } else if (keyDown(KeyEvent.VK_LEFT)){
            boundTo.move(-speed,0,0);
            boundTo.setRotation(270);
            currentAnim = "walkleft";
        } else if (keyDown(KeyEvent.VK_UP)){
            boundTo.move(0,0,-speed);
            boundTo.setRotation(0);
            currentAnim = "walkup";
        } else if (keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(0,0,speed);
            boundTo.setRotation(180);
            currentAnim = "walkdown";
        }
        if (eventKeyDown(KeyEvent.VK_SHIFT)){
            Point2D[] pts = boundTo.getPoints();
            Point2D pt1 = pts[2];
            Point2D pt2 = pts[3];
            float nx = (float)(pt1.x+300*Math.cos(Math.toRadians(boundTo.getRotation())));
            float nz = (float)(pt1.y+300*Math.sin(Math.toRadians(boundTo.getRotation())));
            Spatial[] hit = boundTo.getMap().getSpace().rayCast(pt1.x,pt1.y,nx,nz);
            HashMap<Integer,Spatial> allhit = new HashMap<>();
            for (Spatial spat: hit){
                if (!allhit.containsKey(spat.getId())){
                    allhit.put(spat.getId(), spat);
                }
            }
            nx = (float)(pt2.x+300*Math.cos(Math.toRadians(boundTo.getRotation())));
            nz = (float)(pt2.y+300*Math.sin(Math.toRadians(boundTo.getRotation())));
            hit = boundTo.getMap().getSpace().rayCast(pt2.x,pt2.y,nx,nz);
            for (Spatial spat: hit){
                if (!allhit.containsKey(spat.getId())){
                    allhit.put(spat.getId(), spat);
                }
            }
            hit = allhit.values().toArray(new Spatial[0]);
            for (Spatial spat: hit){
                if (spat != boundTo)
                    boundTo.getMap().addSpatial(new IceyEffect(spat.getX(),1,spat.getZ()));
            }
        }
        animControl.swapAnim(currentAnim);        
    }
}
