/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import java.awt.Graphics;
import java.util.ArrayList;

public class AContainer extends AComponent{
    public ArrayList<AComponent> content;
    public AContainer(int xx,int yy){
        super();
        setLocation(xx,yy);
        content=new ArrayList<>();
    }
    @Override
    public void add(AComponent c){
        content.add(c);
    }
    
    public void draw(Graphics g){
        for (AComponent a: content){
            a.setVisible(true);
            a.draw(g);
        }
    }
    public AComponent get(int i){
        return content.get(i);
    }
    public ArrayList<AComponent> content(){
        return content;
    }
    public void clear(){
        content.clear();
    }
    public int size(){
        return content.size();
    }
    
    public void call(){
        for (AComponent a: content){
            if (a.collidepoint(MyGUI.mx, MyGUI.my)){
                a.call();
            }
        }
    }
    public void update(){}
}
