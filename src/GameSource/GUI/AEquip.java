/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.Assets.AssetManager;
import GameSource.User.CharacterHandler;
import GameSource.User.EquipHandler;
import GameSource.User.Inventory.EquipItem;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*AEquip.java           @Chen~
 * A window to display the equipment status of all part of the body, and
 * offer a description of each when moused over.
 */
public class AEquip extends AWindow{
    public AEquip(){
        super();
        setName("equipment");
        setImage(AImageFactory.getImage("equipment"));
        setSize(233,214);
        setMoveBar(0,0,233,20);
        loadImages();
    }
    public void loadImages(){
        subComponents.clear();
        String [] parts={"head","body","feet","weapon"};
        for (int i=3;i>=0;i--){         //Add bottom-up so that hovering images are drawn on top
            EquipItem item= (EquipItem)EquipHandler.getItem(parts[i]);
            if (item!=null){
                Image back=TextImageFactory.createEquipLabel(item,parts[i]);
                String des= AssetManager.getItemData(item.getKey()).itemDescription;
                Image front=TextImageFactory.createDes(item.getItemId(),des);
                add(new AImage(parts[i],16,34+42*i,back,front));
            }
        }
    }
    public void update(){
        super.update();
        loadImages();
    }
}
