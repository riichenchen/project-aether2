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
    public TestAIControl(){
        super(new TestAICalculation());
    }
    @Override
    public void update(Object returnValue) {
        //this.boundTo.move(controlId, controlId, controlId)
        System.out.println((Integer)returnValue+controlId);
    }
    public static void main(String[] args){
        TestAIControl mytest = new TestAIControl();
        TestAIControl mytest2 = new TestAIControl();
        TestAIControl mytest3 = new TestAIControl();
        TestAIControl mytest4 = new TestAIControl();
        GameMap mymap = new GameMap("MAP",3,1600,1200,800,600,false);
        mytest.bindToMap(mymap);
        mytest2.bindToMap(mymap);
        mytest3.bindToMap(mymap);
        mytest4.bindToMap(mymap);
        
        AIHandler handle1 = new AIHandler();
        AIHandler handle2 = new AIHandler();
        AIHandler handle3 = new AIHandler();
        handle1.start();
        handle2.start();
        handle3.start();
        while (true){
            mytest.update();
            mytest2.update();
            mytest3.update();
            mytest4.update();
            AIHandler.update();
        }
    }
}
