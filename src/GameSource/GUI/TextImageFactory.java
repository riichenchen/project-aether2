/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.Assets.AssetManager;
import GameSource.Skills.ActiveSkillData;
import GameSource.User.CharacterHandler;
import GameSource.User.Inventory.InventoryItem;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joy
 */
public class TextImageFactory {
    public static BufferedImage create(Graphics g, String text){
        Graphics2D g2=(Graphics2D)g;
        FontMetrics fm=g2.getFontMetrics();
        BufferedImage out=new BufferedImage(fm.stringWidth(text),fm.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg= out.createGraphics();
        gImg.setColor(new Color(255,255,255));
        gImg.setFont(new Font ("Arial",Font.PLAIN,10));
        gImg.drawString(text,0,out.getHeight(null)-2);
        System.out.println(text);
        gImg.dispose();
        return out;
    }
    
    public static BufferedImage merge(Image a, Image b){
        BufferedImage out=new BufferedImage(b.getWidth(null),b.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg= out.createGraphics();
        gImg.drawImage(a,0,0,null);
        gImg.drawImage(b,0,0,null);
        gImg.dispose();
        return out;
    }
    public static BufferedImage createDes(InventoryItem i){
        return createDes(i.getItemId(),AssetManager.getItemData(i.getKey()).itemDescription);
    }
    public static BufferedImage createDes(String name, String des){
        BufferedImage out=new BufferedImage(240,138,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg= out.createGraphics();
        gImg.drawImage(AImageFactory.getImage("empty"),0,0,null);
        gImg.setColor(new Color(255,255,255));
        gImg.setFont(new Font("Arial",Font.BOLD,11));
        gImg.drawString(name, 20, 30);
        gImg.setFont(new Font ("Arial",Font.PLAIN,10));
        FontMetrics fm=gImg.getFontMetrics();
        
        String [] lines=des.split("\\r?\\n");
        int drawY=36+fm.getHeight(); 
        for (String line: lines){
            String [] words=line.split(" ");
            String dline="";
            int lineWidth=0;
            for (String word:words){
    		lineWidth+=fm.stringWidth(word);
                if (lineWidth>200){
                    gImg.drawString(dline,22,drawY);
                    lineWidth=fm.stringWidth(word);
                    dline="";
                    drawY+=fm.getHeight();
                }
                lineWidth+=fm.stringWidth(" ");
                dline+=word+" ";
            }
            gImg.drawString(dline,22,drawY);
        }
        gImg.dispose();
        return out;
    }

    public static BufferedImage createSkillDes(String key, String des){
        BufferedImage out=new BufferedImage(240,138,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg= out.createGraphics();
        gImg.drawImage(AImageFactory.getImage("empty"),0,0,null);
        gImg.setColor(new Color(255,255,255));
        gImg.setFont(new Font("Arial",Font.BOLD,11));
        gImg.drawString(AssetManager.getSkillData(key).getName(), 20, 30);
        gImg.setFont(new Font ("Arial",Font.PLAIN,10));
        FontMetrics fm=gImg.getFontMetrics();
        
        String [] lines=des.split("\\r?\\n");
        int drawY=36+fm.getHeight(); 
        for (String line: lines){
            String [] words=line.split(" ");
            String dline="";
            int lineWidth=0;
            for (String word:words){
    		lineWidth+=fm.stringWidth(word);
                if (lineWidth>200){
                    gImg.drawString(dline,22,drawY);
                    lineWidth=fm.stringWidth(word);
                    dline="";
                    drawY+=fm.getHeight();
                }
                lineWidth+=fm.stringWidth(" ");
                dline+=word+" ";
            }
            gImg.drawString(dline,22,drawY);
        }
        drawY+=fm.getHeight()*2;
        
        int level=0;
        int mp=0;
        int castTime=0;
        int range=0;
        try{
            level=CharacterHandler.getSkillLevel(key);
        }
        catch(NullPointerException e){} 
        
        try{
        ActiveSkillData dat=(ActiveSkillData)AssetManager.getSkillData(key);
        if (level>0){
        gImg.drawString("MP cost: "+dat.getMpCost(level),22,drawY);
        gImg.drawString("Cast Time: "+dat.getCastTime(level)/100+"s",22,drawY+fm.getHeight());
        gImg.drawString("Range: "+dat.getRange(level),22,drawY+fm.getHeight()*2);
        }
        if (level<10){
            int subtract=0;
            if (level==0)
                subtract=90;
            gImg.drawString("Next MP cost: "+dat.getMpCost(level+1),112-subtract,drawY);
            gImg.drawString("Next Cast Time: "+dat.getCastTime(level+1)/100+"s",112-subtract,drawY+fm.getHeight());
            gImg.drawString("Next Range: "+dat.getRange(level+1),112-subtract,drawY+fm.getHeight()*2);
        }
        }catch(NullPointerException e){}
        gImg.dispose();
        return out;
    }
    
    public static BufferedImage createEquipLabel(InventoryItem i, String title){
        BufferedImage out=new BufferedImage(202,35,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg= out.createGraphics();
        gImg.drawImage(i.getImage(),0,0,null);
        gImg.setColor(new Color (0,0,0));
        gImg.setFont(new Font("Arial", Font.BOLD, 10));
        gImg.drawString(title,39,14);
        gImg.setFont(new Font("Arial", Font.PLAIN, 10));
        gImg.drawString(i.getItemId(),38,32);
        gImg.dispose();
        return out;
    }
    
    public static BufferedImage createShopLabel(InventoryItem i, int buy){
        String [] tmp={"","Sell Price: ","Buy Price: "};
        BufferedImage out=new BufferedImage(202,35,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg= out.createGraphics();
        gImg.drawImage(i.getImage(),0,0,null);
        gImg.setColor(new Color (0,0,0));
        gImg.setFont(new Font("Arial", Font.BOLD, 10));
        gImg.drawString(i.getItemId(),39,14);
        gImg.setFont(new Font("Arial", Font.PLAIN, 10));
        gImg.drawString(tmp[buy]+buy*AssetManager.getItemData(i.getKey()).sellPrice,38,32);
        gImg.dispose();
        return out;
    }
    
    public static BufferedImage createSkillsLabel(String key){
        BufferedImage out=new BufferedImage(202,35,BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg= out.createGraphics();
        gImg.drawImage(AssetManager.getImage(key),0,0,null);
        gImg.setColor(new Color (0,0,0));
        gImg.setFont(new Font("Arial", Font.BOLD, 10));
        gImg.drawString(AssetManager.getSkillData(key).getName(),39,14);
        gImg.setFont(new Font("Arial", Font.PLAIN, 10));
        
        int c=0;
        try{
            c=CharacterHandler.getSkillLevel(key);
        }
        catch(NullPointerException e){}
        gImg.drawString(""+c,38,32);
        gImg.dispose();
        return out;
    }
}
