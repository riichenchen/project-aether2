/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Script;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Shiyang
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
    
    public int getFinalFrame(){
        return finalCase;
    }
    
}
