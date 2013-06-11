/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.Portals;

/**
 * The data class for portals. Contains information required to generate a portal
 * Loaded in from the AssetManager
 * @author Shiyang
 */
public class PortalData {
    private String toMap;
    private float tx,ty,tz;
    
    public PortalData(String toMap, float tx,float ty,float tz) {
        this.toMap = toMap;
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
    }

    public String getToMap() {
        return toMap;
    }

    public float getTx() {
        return tx;
    }

    public float getTy() {
        return ty;
    }

    public float getTz() {
        return tz;
    }
    
}
