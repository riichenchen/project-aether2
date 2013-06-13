/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.SpiritBombSkill;
import Spatial.Spatial;

/**
 * @author Shiyang
 * The spiritbombcast class. Fairly straight forward. Simply implements
 * all of it's requirements from abstractcast
 */
public class SpiritBombCast extends AbstractCast{

    public SpiritBombCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "spiritbomb";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new SpiritBombSkill(0,0);
    }
    
}
