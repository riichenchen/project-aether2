/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import ArtificialIntelligence.AbstractAICalculation;

/**
 *
 * @author Shiyang
 */
public class TestAICalculation<Integer> extends AbstractAICalculation{
    public TestAICalculation(){
        super();
    }
    @Override
    public void calculate() {
        Object blah = 0;
        for (int i = 0; i < 100000000;i++){
            blah = i;
        }
        //mandatory to have. endvalue is returned to the control to move the spatial
        this.endValue = blah;
    }
}
