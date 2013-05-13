/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Shiyang
 */
public class AIHandler extends Thread{
    private static int IDs = 0;
    private static ConcurrentHashMap<Integer,AbstractAICalculation> allCalculations = new ConcurrentHashMap<>();
    private static ConcurrentLinkedQueue<AbstractAICalculation> calculationList = new ConcurrentLinkedQueue<>();
    private int AIHandleId;
    private static int ready = 0;
    
    public AIHandler(){
        this.AIHandleId = IDs++;
    }
    
    public int getAIHandlerId(){
        return AIHandleId;
    }
    
    public static void addCalculation(AbstractAICalculation calculation){
        allCalculations.put(calculation.getId(),calculation);
    }
    
    public static void removeCalculation(AbstractAICalculation calculation){
        allCalculations.remove(calculation.getId());
    }
    public static boolean go = false;
    
    public static void update(){
        if (ready==0){
            calculationList = new ConcurrentLinkedQueue<>(Arrays.asList(allCalculations.values().toArray(new AbstractAICalculation[0])));
            go = true;
            System.out.println("NULL NOW :DDD"+(count++));
        }
    }
    static int count = 0;
    @Override
    public void run(){
        while (true){
            while (calculationList.peek()!=null){
                go = false;
                ready++;//For each handle thread, we must wait for it to finish before we can move 1 step in the game world
                AbstractAICalculation currentCalc = calculationList.poll();
                currentCalc.calculate();
                currentCalc.setReady(true);
                ready--;
            }
        }
    } 
}
