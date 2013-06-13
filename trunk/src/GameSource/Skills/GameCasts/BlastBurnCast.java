/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.BlastBurnSkill;
import Spatial.Spatial;

/**
 * @author Shiyang
 * The blastburncast class. Fairly straight forward. Simply implements
 * all of it's requirements from abstractcast
 */
public class BlastBurnCast extends AbstractCast{

    public BlastBurnCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "blastburn";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new BlastBurnSkill(0,0);
    }
    
}
