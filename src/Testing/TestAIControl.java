/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import ArtificialIntelligence.AIControl;
import ArtificialIntelligence.AIHandler;
import GameSource.game.GameMap;

/**
 *
 * @author Shiyang
 */
public class TestAIControl extends AIControl{
    public TestAIControl(GameMap map){
        super(map,new TestAICalculation());
    }
    @Override
    public void update(Object returnValue) {
        //this.boundTo.move(controlId, controlId, controlId)
        System.out.println((Integer)returnValue+controlId);
    }
    public static void main(String[] args){
        TestAIControl mytest = new TestAIControl(new GameMap("MAP",0.1,1600,1200,800,600,false));
        TestAIControl mytest2 = new TestAIControl(new GameMap("MAP",0.1,1600,1200,800,600,false));
        TestAIControl mytest3 = new TestAIControl(new GameMap("MAP",0.1,1600,1200,800,600,false));
        TestAIControl mytest4 = new TestAIControl(new GameMap("MAP",0.1,1600,1200,800,600,false));
        
        AIHandler handle1 = new AIHandler();
        AIHandler handle2 = new AIHandler();
        AIHandler handle3 = new AIHandler();
        handle1.start();
        //handle2.start();
        //handle3.start();
        while (true){
            AIHandler.update();
            mytest.update();
            mytest2.update();
            mytest3.update();
            mytest4.update();
        }
    }
}
