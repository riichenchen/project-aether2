package GameSource.GUI;

/*APoint.java           @Chen~
 *Class storing a Rectangle defined by top-left corner and size.
 */
public class ARect {
    private int x,y,h,w;
    public ARect(int xx,int yy,int ww,int hh){
        x=xx; y=yy; h=hh; w=ww;
    }
    public boolean collidepoint(int mx,int my,int ox,int oy){
        //This method checks for a collision given offset values from a
        //parent AComponent (parameters ox and oy)
        return ox+x<=mx && mx<=ox+x+w && oy+y<=my && my<=oy+y+h;
    }
}
