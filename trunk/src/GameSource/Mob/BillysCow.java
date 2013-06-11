/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Mob;

import ArtificialIntelligence.AIControl;
import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.Controls.CowAIControl;
import GameSource.Skills.AbstractActiveSkill;
import GameSource.User.CharacterHandler;
import GameSource.User.ItemFactory;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class BillysCow extends AbstractMob{
    
    public BillysCow(float x, float y, float z) {
        super(x, y, z,78f,95f,20f, 10000);
    }

    @Override
    public AIControl getAIControl() {
        return new CowAIControl(CharacterHandler.getPlayer().getMap());
    }

    @Override
    public CharacterAnimControl getAnimControl() {
        return new CharacterAnimControl(AssetManager.getSpriteSet("MyTestAnimation"));
    }
    int n = 0;
    @Override
    public void collideEffect(Spatial s) {
    }

    @Override
    public void dropItems() {
        ItemFactory.spawnItem("trollbaithelm", this, boundMap);
    }

    @Override
    public String getName() {
        return "billyscow";
    }
    
    @Override
    public int getExp(){
        return 1000;
    }
}
