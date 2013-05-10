/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Renderer;

import Spatial.BoundingBox;
import GameSource.Game.GamePoint;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Shiyang
 */
public interface RenderSpatial {
    public abstract GamePoint getLocation();
    public abstract void render(Graphics g,JPanel pane,AetherCam cam);
    public abstract int getId();
    public abstract LinkedList<RenderChunk> getChunks();
    public abstract BoundingBox getDimensions();
}
