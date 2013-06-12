package GameSource.Game;

import java.io.Serializable;

/* @author Shiyang Han
 * This class packages the x y and z components
 * of a location on the map. It's also serialized and sent back and forth
 * to exchange location information
 */
public class GamePoint implements Serializable{
        
        private float x, y, z;
        
        public GamePoint(float _x, float _y, float _z) {
                x = _x;
                y = _y;
                z = _z;
        }
        
        public float getX() {
                return x;
        }
        
        public float getY() {
                return y;
        }
        
        public float getZ() {
                return z;
        }
        //Standard translate method 
        public void translate(float nx,float ny,float nz){
            this.x+=nx;
            this.y+=ny;
            this.z+=nz;
        }
        
        //Debugging purposes (displays component information)
        @Override
        public String toString(){
            return this.x+" "+this.y+" "+this.z;
        }
        //distance squared from this point to the provided point (rooting is costly)
        public float distanceSquared(GamePoint p){
            return (float)(Math.pow(x-p.x,2)+Math.pow(y-p.y,2)+Math.pow(z-p.z,2));
        }
        public void setX(float x){
            this.x = x;
        }
        public void setY(float y){
            this.y = y;
        }
        public void setZ(float z){
            this.z = z;
        }
}