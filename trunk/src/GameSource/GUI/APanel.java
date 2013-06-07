/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joy
 */
public class APanel extends AComponent{
    private Map<String, AComponent> panes;
    private String activePane;
    
    public APanel(String name){
        super();
        setName(name);
        panes=new HashMap<>();
        activePane="";
    }
    class APanelButton extends AButton{
        private APanel panel;
        public APanelButton(String name, int mtype, String mcontent, APanel p){
            super(name,mtype, mcontent);
            panel=p;
        }
        public void call(){
            panel.setActivePane(message.content());
        }
    }
    public void setActivePane(String name){
        activePane=name;
    }
    public void add(AComponent a){
        panes.put(a.getName(),a);
    }
    public void call(){
        panes.get(activePane).call();
    }
    public void draw(Graphics g){
        panes.get(activePane).draw(g);
    }
    public void update(){
        panes.get(activePane).update();
    }
}
