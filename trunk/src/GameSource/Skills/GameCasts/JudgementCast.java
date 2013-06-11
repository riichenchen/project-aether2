/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameCasts;

import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.AbstractCast;
import GameSource.Skills.GameSkills.JudgementSkill;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class JudgementCast extends AbstractCast{

    public JudgementCast(Spatial spat) {
        super(spat);
    }

    @Override
    public String getCastName() {
        return "judgement";
    }

    @Override
    public AbstractActiveSkill getSkill() {
        return new JudgementSkill(0,0);
    }
    
}
