/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.MagicClawSkill;
import Spatial.Spatial;

/**
 * @author Shiyang
 * The magicclawcastt class. Fairly straight forward. Simply implements
 * all of it's requirements from abstractcast
 */
public class MagicClawCast extends AbstractCast{

    public MagicClawCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "magicclaw";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new MagicClawSkill(0,0);
    }
    
}
