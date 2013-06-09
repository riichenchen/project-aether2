import GameSource.Assets.MobData.AbstractMob;
import Spatial.Spatial;
import java.util.*;

public class FindClosestEnemy {
        
        protected int bestIdx;
        protected double best, curX, curY;
        protected ArrayList spats;
        protected final double EPS = 1e-10;
    
        private double calcDist(Spatial spat) {
            double x = spat.getX();
            double y = spat.getY();
            return Math.sqrt((x - curX) * (x - curX) + (y - curY) * (y - curY));
        }
        
	public FindClosestEnemy (double _curX, double _curY, ArrayList _spats) {
            bestIdx = -1;
            curX = _curX;
            curY = _curY;
            spats = _spats;
            best = 1 << 30;
            
            for (int i = 0; i < spats.size(); i++) {
                Spatial curSpat = (Spatial) spats.get(i);
		if (!(curSpat instanceof AbstractMob)) {
                    double curDist = calcDist(curSpat);
                    if (curDist < best) {
                        best = curDist;
                        bestIdx = i;
                    }
                }
            }
	}
        
        public Spatial getTarget() {
            return (Spatial)spats.get(bestIdx);
        }
}