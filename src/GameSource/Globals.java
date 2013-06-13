/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource;

import Spatial.AetherMouse;

/**
 * Contains information that are accessed either globally or need to be
 * periodically changed. The physics debug for example, determines how much
 * debug detail is shown regarding physics in the game
 * @author Shiyang
 */
public class Globals {
    public static final int __PORT__ = 4186;// the port being used to host the game
    public static final float __SERVERVERSION__ = 1.00f; // protocol version (for future use)
    public static final String __IP__ = "192.168.2.135";//"24.57.115.97"; //"localhost"; // ip to connect to
    public static final String __SQLUSER__ = "root"; // database user and pass
    public static final String __SQLPASS__ = "";
    public static final String DBIP = "localhost";//database access ip
    public static final boolean RENDER_DEBUG = false; // displays information about the current render operations
    public static final boolean P_SYNC_DEBUG = false; // used by the physics sync for debugging (if network synchronization is required)
    public static final int __PHYSICSDEBUG__ = 4;
    public static final float __PROJECTION_SCALE__ = (float)Math.cos(Math.toRadians(60)); // the renderer should render on a 60 degree rotation
    public static final int __CAMX__ = 800,__CAMY__ = 600; // the cameras dimensions
    public static AetherMouse theMouse = new AetherMouse(0,0);
}
