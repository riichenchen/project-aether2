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
    protected GameMap map;
    protected AbstractAICalculation calculator;
    
    public AIControl(GameMap map,AbstractAICalculation calculator){
        this.map = map;
        this.calculator = calculator;
        this.calculator.bindTo(this);
    }
    
    //Must be overridden by the custom AI control
    //Note: Unlike the previous update loop, this one is only
    //called when the calculation is ready
    public abstract void update(Object returnValue);
    
    @Override
    public void update(){
        if (calculator.isReady()){ // Make sure the response has been computed before attempting to retrieve info
            update(calculator.getCalcResult());
            calculator.setReady(false);
            AIHandler.collectOutput();
        }
    }
}
