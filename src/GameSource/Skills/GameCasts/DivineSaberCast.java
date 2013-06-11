/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.DivineSaberSkill;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class DivineSaberCast extends AbstractCast{

    public DivineSaberCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "arroweruption";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new DivineSaberSkill(0,0);
    }
    
}
