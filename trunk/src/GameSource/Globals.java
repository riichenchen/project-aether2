/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource;

import Spatial.AetherMouse;

/**
 *
 * @author Shiyang
 */
public class Globals {
    public static final int __PORT__ = 4186;
    public static final float __SERVERVERSION__ = 1.00f;
    public static final String __IP__ = "24.57.40.105";//"24.57.115.97"; "localhost";
    public static final String __SQLUSER__ = "root";
    public static final String __SQLPASS__ = "";
    public static final String DBIP = "localhost";  //"localhost";
    public static final boolean RENDER_DEBUG = false;
    public static final boolean P_SYNC_DEBUG = false;
    public static final int __PHYSICSDEBUG__ = 5;
    public static final float __PROJECTION_SCALE__ = (float)Math.cos(Math.toRadians(60));
    public static final int __CAMX__ = 800,__CAMY__ = 600;
    public static AetherMouse theMouse = new AetherMouse(0,0);
}
