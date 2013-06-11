/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.LightningStrikeSkill;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class LightningStrikeCast extends AbstractCast{

    public LightningStrikeCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "lightningstrike";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new LightningStrikeSkill(0,0);
    }
    
}
