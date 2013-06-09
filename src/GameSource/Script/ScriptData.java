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
    private HashMap<Integer,ScriptFrame> frameData;
    private ArrayList<FrameCase> allCases;
    private int finalCase;
    
    private int numberFrames = 0;
    public ScriptData(){
        this.frameData = new HashMap<>();
        this.allCases = new ArrayList<>();
    }
    
    public void addFrame(ScriptFrame frame){
        frameData.put(numberFrames++,frame);
    }
    
    public void addCase(FrameCase Case){
        this.allCases.add(Case);
    }
    
    public void setFinalCase(int finalCase){
        this.finalCase = finalCase;
    }
    
    public HashMap<Integer,ScriptFrame> getFrameData(){
        return frameData;
    }
    
    public int getNumberFrames(){
        return numberFrames;
    }
    
    public ArrayList<FrameCase> getAllCases(){
        return allCases;
    }
    
}
