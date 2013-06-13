/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.Game.GamePoint;
import GameSource.Globals;
import GameSource.User.CharacterHandler;
import PhysicsSpace.DistanceComparator;
import Spatial.CharacterSpatial;
import Spatial.Spatial;
import Testing.PlayerSpatial;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * @author Shiyang
 * The AbstractCast class provides a skeleton for all casts to be built upon
 * It takes in skill data from the name defined by a skill and adds the skill
 * onto the map when the appropriate time has been lapsed.
 * Before then, it continues to animate a corresponding spell ring.
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
        //Do not allow the use to move while casting
        CharacterHandler.disableUserControls();
    }
    
    @Override
    public CharacterAnimControl getAnimControl(){
        CharacterAnimControl currentControl = new CharacterAnimControl(AssetManager.getSpriteSet("spellRings"));
        currentControl.swapAnim(getCastName());
        return currentControl;
    }
    
    public abstract String getCastName(); //requires a spellring key
    public abstract AbstractActiveSkill getSkill(); // requires the active skill to spawn
    
    @Override
    public void update(){
        super.update();
        time+=2; // turns out that lagg factor seems to double the actual time
        int level = CharacterHandler.getSkillLevel(skillName);
        if ((data instanceof ActiveSkillData)){ // Future: Add support for buff and passive skill types
            ActiveSkillData tempdata = ((ActiveSkillData)data);
            if (time >= tempdata.getCastTime(level)){ // if ample time has elapsed, cast the spell, return user controls
                doSkill();
                CharacterHandler.enableUserControls();
                boundMap.removeBackgroundSpatial(this);
            }
        }
    }
    //Performs the skill based upon its type configuration
    //Ex: single only casts a ray and takes the first hit mob
    private void doSkill(){
        if (data instanceof ActiveSkillData){
            ActiveSkillData tempdata = (ActiveSkillData)data;
            //hits the first mob captured by the ray
            if (tempdata.getActiveType().equals("single")){
                Spatial[] hits = rayCast();
                for (Spatial s: hits){
                    if (s instanceof AbstractMob){
                        Spatial skill = getSkill();
                        skill.setLocation(s.getX(),skill.getY(),s.getZ());
                        boundMap.addSpatial(skill);
                        break;
                    }
                }
                //hits all mobs within the ray
            } else if (tempdata.getActiveType().equals("ray")){
                Spatial[] hits = rayCast();
                for (Spatial s: hits){
                    Spatial skill = getSkill();
                    if (s != null && (s instanceof AbstractMob)){
                        skill.setLocation(s.getX(),skill.getY(),s.getZ());
                        boundMap.addSpatial(skill);
                    }
                }
                //hits all mobs within the specified radius
            } else if (tempdata.getActiveType().equals("area")){
                Spatial[] hits = areaEffect();
                for (Spatial s: hits){
                    Spatial skill = getSkill();
                    if (s != null && (s instanceof AbstractMob)){
                        skill.setLocation(s.getX(),skill.getY(),s.getZ());
                        boundMap.addSpatial(skill);
                    }
                }
                //the spell is cast at where the player is and damage is dealt
                //to all mobs in the area
            } else if (tempdata.getActiveType().equals("player")){
                Spatial skill = getSkill();
                GamePoint loc = CharacterHandler.getPlayer().getLocation();
                skill.setLocation(new GamePoint(loc.getX(),loc.getY()+1,loc.getZ()));
                boundMap.addSpatial(skill);
            }
        }
    }
    
    @Override
    public void collideEffect(Spatial spat){
        //shouldn't do anything ever.
    }
    
    //performs a raycast based upon the range data passed in and the current
    //skill level. Returns all mobs that've been hit and filters out the player
    //himself
    protected Spatial[] rayCast(){
        PlayerSpatial p = CharacterHandler.getPlayer();
        double range = ((ActiveSkillData)data).getRange(CharacterHandler.getSkillLevel(skillName));
        float nx = (float)(getX()+range*Math.cos(Math.toRadians(p.getRotation()))); // direction based on rotation
        float nz = (float)(getZ()+range*Math.sin(Math.toRadians(p.getRotation())));
        Spatial[] hit = boundMap.getSpace().rayCast(getX(),getZ(),nx,nz);//grab all spatials around
        HashMap<Integer,Spatial> allhit = new HashMap<>();
        DistanceComparator comp = new DistanceComparator();// organize by distance from the player
        PriorityQueue<Spatial> organizeByDistance = new PriorityQueue(10,comp);
        for (Spatial spat: hit){ // filters out the player
            if (!allhit.containsKey(spat.getId())){
                allhit.put(spat.getId(), spat);
                organizeByDistance.add(spat);
            }
        }
        
        return organizeByDistance.toArray(new Spatial[0]);
    }
    
    //grabs all sptails within the area specified by a range (dependant on skill level)
    protected Spatial[] areaEffect(){
        length += ((ActiveSkillData)data).getRange(CharacterHandler.getSkillLevel(skillName));
        height += Globals.__PROJECTION_SCALE__*((ActiveSkillData)data).getRange(CharacterHandler.getSkillLevel(skillName)); // we must project onto the xz plane
        Spatial[] allspats = boundMap.getSpace().grabSpatialsAround(this)[0];
        length -= ((ActiveSkillData)data).getRange(CharacterHandler.getSkillLevel(skillName));
        height -= Globals.__PROJECTION_SCALE__*((ActiveSkillData)data).getRange(CharacterHandler.getSkillLevel(skillName));
        return allspats;
    }
}
