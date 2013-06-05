/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.UI;

import Animation.SpriteSet;
import Controls.CharacterAnimControl;

/**
 *
 * @author Shiyang
 */
public class DamageAnimControl extends CharacterAnimControl{
    int counter = 0;
    public DamageAnimControl(SpriteSet spriteSet) {
        super(spriteSet);
    }
    @Override
    public void update(){
        counter+=1;
        boundTo.move(0, 0, -1);
        if (counter > 100){
            boundTo.getMap().removeBackgroundSpatial(boundTo);
        }
    }
}
