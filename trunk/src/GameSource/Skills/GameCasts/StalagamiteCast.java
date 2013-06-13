/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.StalagamiteSkill;
import Spatial.Spatial;

/**
 * @author Shiyang
 * The stalagamitecast class. Fairly straight forward. Simply implements
 * all of it's requirements from abstractcast
 */
public class StalagamiteCast extends AbstractCast{

    public StalagamiteCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "stalagamite";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new StalagamiteSkill(0,0);
    }
    
}
