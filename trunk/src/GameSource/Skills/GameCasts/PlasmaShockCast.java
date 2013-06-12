/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.PlasmaShockSkill;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class PlasmaShockCast extends AbstractCast{

    public PlasmaShockCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "icicle";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new PlasmaShockSkill(0,0);
    }
    
}
