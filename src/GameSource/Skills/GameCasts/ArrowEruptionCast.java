/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.ArrowEruptionSkill;
import Spatial.Spatial;

/**
 * @author Shiyang
 * The arroweruptioncast class. Fairly straight forward. Simply implements
 * all of it's requirements from abstractcast
 */
public class ArrowEruptionCast extends AbstractCast{

    public ArrowEruptionCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "arroweruption";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new ArrowEruptionSkill(0,0);
    }
    
}
