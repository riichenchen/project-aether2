/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.StaticVortexSkill;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class StaticVortexCast extends AbstractCast{

    public StaticVortexCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "icicle";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new StaticVortexSkill(0,0);
    }
    
}
