/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSync;

import java.util.Comparator;

/**
 *
 * @author Shiyang
 */
public class TimePriorityComparator implements Comparator<PhysicSyncMessage>{

    @Override
    public int compare(PhysicSyncMessage A, PhysicSyncMessage B) {
        if (A.getReceivedTime() < B.getReceivedTime()){
            return -1;
        }
        if (A.getReceivedTime() > B.getReceivedTime()){
            return 1;
        }
        return 0;
    }
    
}
