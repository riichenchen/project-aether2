package GameSource.game;

import javax.swing.ImageIcon;

public class GameMap {
        
        private ImageIcon mapImage;
        private double mobDensity; 
        private int dimx = 1600,dimy = 1200;        
        
        public GameMap(String mapName, double _mobDensity) {
                mapImage = new ImageIcon(mapName);
                mobDensity = _mobDensity;
        }
        
        public double getMobDensity() {
                return mobDensity;
        }
        
        public void setDimX(int val){
            dimx = val;
        }
        public void setDimY(int val){
            dimy = val;
        }
        public int getDimX(){
            return dimx;
        }
        public int getDimY(){
            return dimy;
        }
}
