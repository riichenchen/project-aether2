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
        BufferedImage out=new BufferedImage(fm.stringWidth(text),fm.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg=out.createGraphics();
        gImg.setColor(new Color(0,0,0));
        gImg.setFont(new Font ("Arial",Font.PLAIN,10));
        gImg.drawString(text,0,0);
        System.out.println(text);
        gImg.dispose();
        return out;
    }
}
