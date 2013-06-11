/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.GUI.MyGUI;
import GameSource.Script.NPCFrame;
import GameSource.Skills.ActiveSkillData;
import GameSource.Skills.GameCasts.ArrowEruptionCast;
import GameSource.Skills.GameCasts.BlastBurnCast;
import GameSource.Skills.GameCasts.DivineSaberCast;
import GameSource.Skills.GameCasts.IcicleCast;
import GameSource.Skills.GameCasts.JudgementCast;
import GameSource.Skills.GameCasts.LightningStrikeCast;
import GameSource.Skills.GameCasts.MagicClawCast;
import GameSource.User.CharacterHandler;
import Input.AbstractKeyListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author Shiyang
 */
public class SteveyKeyListener extends AbstractKeyListener{
    float speed = 3f;
    String state = "stand";
    String direction = "down";
    NPCFrame myFrame = new NPCFrame("john");
    @Override
    public void resolveKeyEvents() {
        Object charCont = boundTo.getControl(CharacterAnimControl.class);
        CharacterAnimControl animControl;
        if (charCont != null){
            animControl = (CharacterAnimControl)charCont;
        } else {
            System.out.println("Warning: no animation control :C unable to move character!");
            return;
        }
        state = "stand";
        float diagonal = (float)Math.sin(Math.toRadians(45));
        if (keyDown(KeyEvent.VK_RIGHT) && keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(speed*diagonal, 0, speed*diagonal);
            boundTo.rotate(Math.toRadians(135));
            state = "walk";
            direction = "downright";
        } else if (keyDown(KeyEvent.VK_RIGHT) && keyDown(KeyEvent.VK_UP)){
            boundTo.move(speed*diagonal,0,-speed*diagonal);
            boundTo.rotate(Math.toRadians(45));
            state = "walk";
            direction = "upright";
        } else if (keyDown(KeyEvent.VK_LEFT) && keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(-speed*diagonal,0,speed*diagonal);
            boundTo.rotate(Math.toRadians(225));
            state = "walk";
            direction = "downleft";
        } else if (keyDown(KeyEvent.VK_LEFT) && keyDown(KeyEvent.VK_UP)){
            boundTo.move(-speed*diagonal,0,-speed*diagonal);
            boundTo.rotate(Math.toRadians(315));
            state = "walk";
            direction = "upleft";
        }  else if (keyDown(KeyEvent.VK_RIGHT)){
            boundTo.move(speed,0,0);
            boundTo.setRotation(90);
            state = "walk";
            direction = "right";
        } else if (keyDown(KeyEvent.VK_LEFT)){
            boundTo.move(-speed,0,0);
            boundTo.setRotation(270);
            state = "walk";
            direction = "left";
        } else if (keyDown(KeyEvent.VK_UP)){
            boundTo.move(0,0,-speed);
            boundTo.setRotation(0);
            state = "walk";
            direction = "up";
        } else if (keyDown(KeyEvent.VK_DOWN)){
            boundTo.move(0,0,speed);
            boundTo.setRotation(180);
            state = "walk";
            direction = "down";
        }
        if (eventKeyDown(KeyEvent.VK_SHIFT)){
            state = "cast";
            ActiveSkillData dat = ((ActiveSkillData)AssetManager.getSkillData("icicle"));
            int cost = dat.getMpCost(CharacterHandler.getSkillLevel("icicle"));
            if (CharacterHandler.getStat("mp") > cost){
                CharacterHandler.addStat("mp", -cost);
                CharacterHandler.getPlayer().getMap().addBackgroundSpatial(new IcicleCast(CharacterHandler.getPlayer()));
            }
        }
        if (eventKeyDown(KeyEvent.VK_B) && CharacterHandler.getSkillLevel("blastburn")>0){
            state = "cast";
            ActiveSkillData dat = ((ActiveSkillData)AssetManager.getSkillData("blastburn"));
            int cost = dat.getMpCost(CharacterHandler.getSkillLevel("blastburn"));
            if (CharacterHandler.getStat("mp") > cost){
                CharacterHandler.addStat("mp", -cost);
                CharacterHandler.getPlayer().getMap().addBackgroundSpatial(new BlastBurnCast(CharacterHandler.getPlayer()));
            }
        }
        if (eventKeyDown(KeyEvent.VK_A) && CharacterHandler.getSkillLevel("arroweruption")>0){
            state = "cast";
            ActiveSkillData dat = ((ActiveSkillData)AssetManager.getSkillData("arroweruption"));
            int cost = dat.getMpCost(CharacterHandler.getSkillLevel("arroweruption"));
            if (CharacterHandler.getStat("mp") > cost){
                CharacterHandler.addStat("mp", -cost);
                CharacterHandler.getPlayer().getMap().addBackgroundSpatial(new ArrowEruptionCast(CharacterHandler.getPlayer()));
            }
        }
        if (eventKeyDown(KeyEvent.VK_T) && CharacterHandler.getSkillLevel("divinesaber")>0){
            state = "cast";
            ActiveSkillData dat = ((ActiveSkillData)AssetManager.getSkillData("divinesaber"));
            int cost = dat.getMpCost(CharacterHandler.getSkillLevel("divinesaber"));
            if (CharacterHandler.getStat("mp") > cost){
                CharacterHandler.addStat("mp", -cost);
                CharacterHandler.getPlayer().getMap().addBackgroundSpatial(new DivineSaberCast(CharacterHandler.getPlayer()));
            }
        }
        if (eventKeyDown(KeyEvent.VK_J) && CharacterHandler.getSkillLevel("judgement")>0){
            state = "cast";
            ActiveSkillData dat = ((ActiveSkillData)AssetManager.getSkillData("judgement"));
            int cost = dat.getMpCost(CharacterHandler.getSkillLevel("judgement"));
            if (CharacterHandler.getStat("mp") > cost){
                CharacterHandler.addStat("mp", -cost);
                CharacterHandler.getPlayer().getMap().addBackgroundSpatial(new JudgementCast(CharacterHandler.getPlayer()));
            }
        }
        if (eventKeyDown(KeyEvent.VK_C) && CharacterHandler.getSkillLevel("magicclaw")>0){
            state = "cast";
            ActiveSkillData dat = ((ActiveSkillData)AssetManager.getSkillData("magicclaw"));
            int cost = dat.getMpCost(CharacterHandler.getSkillLevel("magicclaw"));
            if (CharacterHandler.getStat("mp") > cost){
                CharacterHandler.addStat("mp", -cost);
                CharacterHandler.getPlayer().getMap().addBackgroundSpatial(new MagicClawCast(CharacterHandler.getPlayer()));
            }
        }
        if (eventKeyDown(KeyEvent.VK_R) && CharacterHandler.getSkillLevel("lightningstrike")>0){
            state = "cast";
            ActiveSkillData dat = ((ActiveSkillData)AssetManager.getSkillData("lightningstrike"));
            int cost = dat.getMpCost(CharacterHandler.getSkillLevel("lightningstrike"));
            if (CharacterHandler.getStat("mp") > cost){
                CharacterHandler.addStat("mp", -cost);
                CharacterHandler.getPlayer().getMap().addBackgroundSpatial(new LightningStrikeCast(CharacterHandler.getPlayer()));
            }
        }
        if (eventKeyDown(KeyEvent.VK_SPACE)){
            world.enterPortal();
        }
        if (eventKeyDown(KeyEvent.VK_Z)){
            CharacterHandler.pickUpItem();
        }
        if (eventKeyDown(KeyEvent.VK_S)){
            System.out.println("Name: "+CharacterHandler.getName());
            System.out.println("Attack: "+CharacterHandler.getStat("attack"));
            System.out.println("Defense: "+CharacterHandler.getStat("defense"));
            System.out.println("Hp: "+CharacterHandler.getStat("hp"));
            System.out.println("MaxHp: "+CharacterHandler.getStat("maxhp"));
            System.out.println("Mp: "+CharacterHandler.getStat("mp"));
            System.out.println("MaxMp: "+CharacterHandler.getStat("maxmp"));
            System.out.println("Money: "+CharacterHandler.getStat("money"));
            System.out.println("Level: "+CharacterHandler.getStat("level"));
            System.out.println("Exp: "+CharacterHandler.getStat("exp"));
        }
        if (eventKeyDown(KeyEvent.VK_1)){
            CharacterHandler.addStat("hp", 50);
        }
        if (eventKeyDown(KeyEvent.VK_2)){
            CharacterHandler.addStat("mp",50);
        }
        if (eventKeyDown(KeyEvent.VK_3)){
            CharacterHandler.addStat("statPoints", 10);
        }
        if (eventKeyDown(KeyEvent.VK_4)){
            CharacterHandler.addStat("skillPoints", 10);
        }
        if (eventKeyDown(KeyEvent.VK_5)){
            myFrame = new NPCFrame("john");
            MyGUI.showNPC(myFrame,"john");
        }
//        if (eventKeyDown(KeyEvent.VK_6)){
//            MyGUI.showNPC(myFrame.next());
//        }
        animControl.swapAnim(state+direction);        
    }
}
