/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Assets.Portals.Portal;
import GameSource.User.CharacterHandler;
import Spatial.CharacterSpatial;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 * The player spatial class, which the main game 
 * uses the track and move the player
 */
public class PlayerSpatial extends CharacterSpatial{
    
    public PlayerSpatial(float x,float y,float z){
        super(x,y,z,51f,77f,36f,1f,1f,1);
        CharacterHandler.setCurrentPortal(null);
    }
    
    int n = 0;
    @Override
    public void collideEffect(Spatial s) {
    }
    
    @Override
    public void update(){
        super.update();
        CharacterHandler.setCurrentPortal(null); // reset the collided portal each loop
    }

    @Override
    public CharacterAnimControl getAnimControl() {
        //for now, every player uses Sola
        return new CharacterAnimControl(AssetManager.getSpriteSet("Sola"));
    }
    
}
