package GameSource.Game;

public class GamePoint {
        
        float x, y, z;
        
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
        public void translate(float x,float y,float z){
            this.x+=x;
            this.y+=y;
            this.z+=z;
        }
}