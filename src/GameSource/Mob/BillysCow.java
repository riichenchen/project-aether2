/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Mob;

import ArtificialIntelligence.AIControl;
import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.Controls.AIControl1;
import GameSource.User.CharacterHandler;
import GameSource.User.ItemFactory;
import Spatial.Spatial;

/**
 * @author Shiyang
 * The very first mob ever. :DD A test implementation of abstract mob.
 * May or may not be used in the actual game itself.
 *
 
Mobs classified by type: Refer to Shiyang's BillyCow.java for comments
type 1 - attack1 stand walk
lucario
magmortar
murkrow
spheal
zubat
arcanine
absol
empoleon
type 2 - attack1 cast1 stand walk
haunter
type 3 - cast1 stand walk
duskull 
glalie
type 4 - cast1 cast2 stand walk
dusknoir
type 5 - attack1 attack2 stand walk
heatran
type 6 - stand walk
magcargo
type 7 - attack1 stand
darkrai
 */
public class BillysCow extends AbstractMob{
    
    public BillysCow(float x, float y, float z) {
        super(x, y, z,78f,95f,20f, 10000);
    }

    @Override
    public AIControl getAIControl() {
        return new AIControl1(CharacterHandler.getPlayer().getMap());
    }

    @Override
    public CharacterAnimControl getAnimControl() { // just use a simple test animation
        return new CharacterAnimControl(AssetManager.getSpriteSet("empoleon"));
    }
    int n = 0;
    @Override
    public void collideEffect(Spatial s) {
    }

    @Override
    public void dropItems() { // drop trollbaithelm at a 100% rate for now
        ItemFactory.spawnItem("trollbaithelm", this, boundMap);
    }

    @Override
    public String getName() {
        return "billyscow"; // return the data name
    }
    
    @Override
    public int getExp(){
        return 1000; // how much exp this mob gives
    }
}
