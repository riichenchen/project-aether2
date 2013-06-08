/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.Globals;
import GameSource.User.CharacterHandler;
import PhysicsSpace.DistanceComparator;
import Spatial.CharacterSpatial;
import Spatial.Spatial;
import Testing.PlayerSpatial;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author Shiyang
 */
public abstract class AbstractCast extends CharacterSpatial {
    private SkillData data;
    private String skillName;
    private int time = 0;
    private Spatial boundSpat;
    
    public AbstractCast(Spatial spat) {
        super(spat.getX(), 0.5f, spat.getZ(), 128, 100, 128, 0, 0, 0);
        this.skillName = getCastName();
        this.data = AssetManager.getSkillData(skillName);
        this.boundSpat = spat;
        CharacterHandler.disableUserControls();
    }
    
    @Override
    public CharacterAnimControl getAnimControl(){
        CharacterAnimControl currentControl = new CharacterAnimControl(AssetManager.getSpriteSet("spellRings"));
        currentControl.swapAnim(getCastName());
        return currentControl;
    }
    
    public abstract String getCastName();
    public abstract AbstractActiveSkill getSkill();
    
    @Override
    public void update(){
        super.update();
        time++;
        int level = CharacterHandler.getSkillLevel(skillName);
        if ((data instanceof ActiveSkillData)){
            ActiveSkillData tempdata = ((ActiveSkillData)data);
            if (time >= tempdata.getCastTime(level)){
                doSkill();
                CharacterHandler.enableUserControls();
//                boundMap.addSpatial(getSkill());
                boundMap.removeBackgroundSpatial(this);
            }
        }
    }
    private void doSkill(){
        if (data instanceof ActiveSkillData){
            ActiveSkillData tempdata = (ActiveSkillData)data;
            if (tempdata.getActiveType().equals("single")){
                Spatial[] hits = rayCast();
                for (Spatial s: hits){
                    if ((s instanceof AbstractMob)){
                        Spatial skill = getSkill();
                        skill.setLocation(s.getX(),skill.getY(),s.getZ());
                        boundMap.addSpatial(skill);
                        break;
                    }
                }
            } else if (tempdata.getActiveType().equals("ray")){
                Spatial[] hits = rayCast();
                for (Spatial s: hits){
                    Spatial skill = getSkill();
                    if (s != null && (s instanceof AbstractMob)){
                        skill.setLocation(s.getX(),skill.getY(),s.getZ());
                        boundMap.addSpatial(skill);
                    }
                }
            } else if (tempdata.getActiveType().equals("area")){
                Spatial[] hits = areaEffect();
                for (Spatial s: hits){
                    Spatial skill = getSkill();
                    if (s != null && (s instanceof AbstractMob)){
                        skill.setLocation(s.getX(),skill.getY(),s.getZ());
                        boundMap.addSpatial(skill);
                    }
                }
            }
        }
    }
    @Override
    public void collideEffect(Spatial spat){
        //shouldn't do anything ever.
    }
    
    protected Spatial[] rayCast(){
        PlayerSpatial p = CharacterHandler.getPlayer();
        double range = ((ActiveSkillData)data).getRange(CharacterHandler.getSkillLevel(skillName));
        float nx = (float)(getX()+range*Math.cos(Math.toRadians(p.getRotation())));
        float nz = (float)(getZ()+range*Math.sin(Math.toRadians(p.getRotation())));
        Spatial[] hit = boundMap.getSpace().rayCast(getX(),getZ(),nx,nz);
        HashMap<Integer,Spatial> allhit = new HashMap<>();
        DistanceComparator comp = new DistanceComparator();
        PriorityQueue<Spatial> organizeByDistance = new PriorityQueue(10,comp);
        for (Spatial spat: hit){
            if (!allhit.containsKey(spat.getId())){
                allhit.put(spat.getId(), spat);
                organizeByDistance.add(spat);
            }
        }
        
        return organizeByDistance.toArray(new Spatial[0]);
    }
    
    protected Spatial[] areaEffect(){
        length += ((ActiveSkillData)data).getRange(CharacterHandler.getSkillLevel(skillName));
        height += Globals.__PROJECTION_SCALE__*((ActiveSkillData)data).getRange(CharacterHandler.getSkillLevel(skillName));
        Spatial[] allspats = boundMap.getSpace().grabSpatialsAround(this)[0];
        length -= ((ActiveSkillData)data).getRange(CharacterHandler.getSkillLevel(skillName));
        height -= Globals.__PROJECTION_SCALE__*((ActiveSkillData)data).getRange(CharacterHandler.getSkillLevel(skillName));
        return allspats;
    }
}
