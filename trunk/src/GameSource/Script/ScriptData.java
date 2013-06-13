/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Script;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Shiyang
 * The ScriptData class manages all frames and
 * all cases in this specific npc script. It provides
 * basic methods such as get frame number, which, given a current frame
 * number, returns the frame to direct the player to using the casedata.
 */
public class ScriptData {
    private HashMap<Integer,ScriptFrame> allFrames;
    private ArrayList<FrameCase> allCases;
    private int finalCase;
    
    private int numberFrames = 0;
    
    public ScriptData(){
        this.allFrames = new HashMap<>();
        this.allCases = new ArrayList<>();
    }
    
    //standard add and get methods
    public void addFrame(ScriptFrame frame){
        allFrames.put(numberFrames++,frame);
    }
    
    public void addCase(FrameCase Case){
        this.allCases.add(Case);
    }
    
    public void setFinalCase(int finalCase){
        this.finalCase = finalCase;
    }
    
    public int getFrameNumber(int currentFrame){
        for (FrameCase c: allCases){
            if (c.checkCaseData(currentFrame)){
                return c.getToFrame();
            }
        }
        return finalCase;
    }
    
    public ScriptFrame getFrame(int frameNumber){
        return allFrames.get(frameNumber);
    }
    
    //The final case is called if no cases have been satisfied
    public int getFinalFrame(){
        return finalCase;
    }
    
}
