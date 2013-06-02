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
public class CowAICalculation extends AbstractAICalculation {

    @Override
    public void calculate() {
        Object blah = 0;
        for (int i = 0; i < 100000000;i++){
            blah = i;
        }
        this.endValue = blah;
    }
    
}
