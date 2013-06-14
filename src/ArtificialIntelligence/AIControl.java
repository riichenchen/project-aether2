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
    protected final double DEFAULT_AGGRO_RADIUS = 512;
    protected final int DEFAULT_DELAY = 8;
    
    protected final double DEFAULT_ATTACK1_RANGE = 32;
    protected final int DEFAULT_ATTACK1_DELAY = 32;
    
    protected final double DEFAULT_ATTACK2_RANGE = 64;
    protected final int DEFAULT_ATTACK2_DELAY = 64;
    
    protected final double DEFAULT_CAST1_RANGE = 128;
    protected final int DEFAULT_CAST1_DELAY = 128;
    
    protected final double DEFAULT_CAST2_RANGE = 256;
    protected final int DEFAULT_CAST2_DELAY = 256;
    
    
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
