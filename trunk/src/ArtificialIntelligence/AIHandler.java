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
//        if (ready==0 && !go){
        if (ready == 0 && waitingOutput == 0){
            System.out.println("NULL NOW :DDD"+(ThreadCount++));
            calculationList = new ConcurrentLinkedQueue<>(Arrays.asList(allCalculations.values().toArray(new AbstractAICalculation[0])));
//            go = true;
            ready = calculationList.size();
            waitingOutput = ready;
        }
    }
    private static int waitingOutput = 0;
    private static int ThreadCount = 0;
    public static void collectOutput(){
        waitingOutput--;
    }
    @Override
    public void run(){
        while (true){
            //synchronized (AIControl.class){
                if (calculationList.peek()!=null && ready > 0){
    //                go = false;
                    AbstractAICalculation currentCalc = null;
//                    synchronized(AIHandler.class){
                        currentCalc = calculationList.poll();
//                    }
                    if (currentCalc != null){
                        currentCalc.calculate();
                        currentCalc.setReady(true);
                        ready--;
                    }
                }
            //}
        }
    } 
}
