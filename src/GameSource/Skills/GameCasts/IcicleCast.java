/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.IcicleSkill;
import Spatial.Spatial;

/**
 * @author Shiyang
 * The iciclecast class. Fairly straight forward. Simply implements
 * all of it's requirements from abstractcast
 */
public class IcicleCast extends AbstractCast{

    public IcicleCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "icicle";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new IcicleSkill(0,0);//Note: location will be set by the abstract cast's doSkill
    }
    
}
