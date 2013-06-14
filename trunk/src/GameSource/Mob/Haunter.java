/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Mob;

/**
 *
 * @author Angus
 */

import ArtificialIntelligence.AIControl;
import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.Controls.AIControl1;
import GameSource.User.CharacterHandler;
import GameSource.User.ItemFactory;
import Spatial.Spatial;

public class Haunter extends AbstractMob{
    
    public Haunter(float x, float y, float z) {
        super(x, y, z, 128f, 128f, 128f, 10000);
    }

    @Override
    public AIControl getAIControl() {
        return new AIControl1(CharacterHandler.getPlayer().getMap());
    }

    @Override
    public CharacterAnimControl getAnimControl() { // just use a simple test animation
        return new CharacterAnimControl(AssetManager.getSpriteSet("haunter"));
    }
    int n = 0;
    @Override
    public void collideEffect(Spatial s) {
    }

    @Override
    public void dropItems() {
    }

    @Override
    public String getName() {
        return "haunter";
    }
    
    @Override
    public int getExp(){
        return 1000; // how much exp this mob gives
    }
}
