package GameSource.Game;

public class GamePoint {
        
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
        public void translate(float nx,float ny,float nz){
            this.x+=nx;
            this.y+=ny;
            this.z+=nz;
        }
        @Override
        public String toString(){
            return this.x+" "+this.y+" "+this.z;
        }
}