/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtificialIntelligence;

import java.util.Comparator;

/**
 *
 * @author Angus
 */
public class NodeComparator implements Comparator<Node> {
        public int compare (Node x, Node y) {
                if (x.cost < y.cost)
                        return -1;

                if (x.cost > y.cost)
                        return 1;

                return 0;
        }
}