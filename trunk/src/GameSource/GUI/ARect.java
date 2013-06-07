/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

/**
 *
 * @author Joy
 */
public class ARect {
    private int x,y,h,w;
    public ARect(int xx,int yy,int ww,int hh){
        x=xx; y=yy; h=hh; w=ww;
    }
    public boolean collidepoint(int mx,int my,int ox,int oy){      //ox, oy : parent offset values
        return ox+x<=mx && mx<=ox+x+w && oy+y<=my && my<=oy+y+h;
    }
}
