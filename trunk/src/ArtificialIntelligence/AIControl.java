/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

import Controls.AbstractControl;
import GameSource.game.GameMap;

/**
 *
 * @author Shiyang
 */
public abstract class AIControl extends AbstractControl{
    protected GameMap map = null;
    protected AbstractAICalculation calculator;
    
    public AIControl(AbstractAICalculation calculator){
        this.calculator = calculator;
        this.calculator.bindTo(this);
    }
    
    //must be bound before use
    public void bindToMap(GameMap map){
        this.map = map;
    }
    public void unbindFromMap(){
        this.map = null;
        AIHandler.removeCalculation(calculator);
        AIHandler.collectOutput();
    }
    //Must be overridden by the custom AI control
    //Note: Unlike the previous update loop, this one is only
    //called when the calculation is ready
    public abstract void update(Object returnValue);
    
    @Override
    public void update(){
        if (calculator.isReady() && map != null){ // Make sure the response has been computed before attempting to retrieve info
            update(calculator.getCalcResult());
            calculator.setReady(false);
            AIHandler.collectOutput();
        }
    }
}
