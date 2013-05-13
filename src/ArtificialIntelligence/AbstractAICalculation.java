/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

import Controls.AbstractControl;

/**
 *
 * @author Shiyang
 */
public abstract class AbstractAICalculation<T> {
    protected T endValue;
    private static int IDs = 0;
    protected int id;
    protected AbstractControl control;
    protected boolean ready = false;
    
    public AbstractAICalculation(){
        
        this.id = IDs++;
        AIHandler.addCalculation(this);
    }
    
    public void bindTo(AbstractControl bound){
        this.control = bound;
    }
    
    public int getId(){
        return id;
    }
    //Must be a way to calculate value before returning value
    public abstract void calculate();
    
    public T getCalcResult(){
        return endValue;
    }
    
    public void setReady(boolean b){
        ready = b;
    }
    public boolean isReady(){
        return ready;
    }
}
