/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

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
//        BufferedImage out=new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
        BufferedImage out=new BufferedImage(fm.stringWidth(text),fm.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg= out.createGraphics();
        gImg.setColor(new Color(255,255,255));
        gImg.setFont(new Font ("Arial",Font.PLAIN,10));
        gImg.drawString(text,0,out.getHeight(null)-2);
//        gImg.drawRect(1,1,out.getWidth(null)-2,out.getHeight(null)-2);
        System.out.println(text);
//        g.drawImage(out, 0,0, null);
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
}
