/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.SheolFearSkill;
import Spatial.Spatial;

/**
 * @author Shiyang
 * The sheolfearcast class. Fairly straight forward. Simply implements
 * all of it's requirements from abstractcast
 */
public class SheolFearCast extends AbstractCast{

    public SheolFearCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "sheolfear";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new SheolFearSkill(0,0);
    }
    
}
