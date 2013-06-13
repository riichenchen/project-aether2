/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import Controls.AbstractControl;
import GameSource.Game.GamePoint;
import GameSource.game.GameMap;
import Math.Line2D;
import Math.Point2D;
import PhysicsSpace.PhysicsChunk;
import PhysicsSpace.PhysicsSpace;
import java.util.HashMap;
import java.awt.*;
import java.util.LinkedList;

/**
 *
 * @author Robert
 */
public abstract class Spatial {
    protected GamePoint location;
    protected float length, height, width;
    protected static int IDs = 0;
    protected int id;

    public final float mass;
    public final float cof;      //coefficient of friction
    protected double rotation;        //in degrees clockwise
    public final int collidable;    
    //                          0 -> not collidable
    //                          1 -> totally collidable
    //                          2 -> monster collidable
    //                          3 -> player collidable
    protected float percentscale;
    protected HashMap<Integer,AbstractControl> controls;
    protected HashMap<Integer,PhysicsChunk> physicsChunks;
    protected PhysicsSpace space;
    protected GameMap boundMap;
    protected HashMap<String,Object> properties;
    
    //Constructs a spatial out of the provided properties
    public Spatial(float x, float y, float z, float l, float h, float w, float m, float c, int collidable){
        this.location = new GamePoint(x,y,z);
        length = l;
        height = h;
        width = w;
        controls = new HashMap<>();
        physicsChunks = new HashMap<>();
        mass = m;
        cof = c;
        rotation = 0;
        this.id = IDs++;
        this.collidable = collidable;
        percentscale = 1f;
        properties = new HashMap<>();
    }
    //Any additional properties can be defined by set property and get property
    public void setProperty(String key,Object obj){
        properties.put(key, obj);
    }
    
    public Object getProperty(String key){
        return properties.get(key);
    }
    //checks if the key exists
    public boolean hasProperty(String key){
        return properties.containsKey(key);
    }
    
    public void removeProperty(String key){
        properties.remove(key);
    }
    
    //standard bind and unbind methods
    public void bindToMap(GameMap map){
        this.boundMap = map;
    }
    public void unbindFromMap(){
        this.boundMap = null;
    }
    
    public void bindToSpace(PhysicsSpace space){
        this.space = space;
    }
    public void unbindFromSpace(){
        this.space = null;
    }
    
    /*SHOULD ONLY BE CALLED BY SERVER REGISTER!!!!!!!!!!!*/
    public void setId(int id){
        this.id = id;
    }
    
    //Translates this spatial if it doesn't have a space.
    //Otherwise space handles the operation
    public void move(float x, float y, float z){
        if (space == null){
            location.translate(x,y,z);
        } else {
            space.addMoveMessage(x,z,this);
        }
    }
    
    public abstract void collideEffect(Spatial s);
    
    //the collide method checks if this spatial collides with a passed in one
    //using rotation, length, width, etc.
    public boolean collide(Spatial s){
        boolean collided = false;
        boolean rotated = false;
        double arot = rotation;
        
        float aYmin = location.getY() + height;
        float aYmax = location.getY();
        
        float bYmin = s.getY() + s.getHeight();
        float bYmax = s.getY();
        
        if (arot != 0){
            s.setLocalRotation(arot);
            rotated = true;
        }
        if (s.getShape().intersects((double)(location.getX()-length/2),(double)(location.getZ()-width/2),(double)length,(double)width) || s.getShape().contains((double)(location.getX()-length/2),(double)(location.getZ()-width/2),(double)length,(double)width)){
            collided = true;
        } 
        if (rotated){
            s.setLocalRotation(-arot);
        } 
        return collided;
    }
    
    //Standard get and set location,rotation methods
    public GamePoint getLocation(){
        return location;
    }
    public double getRotation(){
        return Math.toDegrees(rotation)-90;
    }
    public double getLocalRotation(){
        return Math.toDegrees(rotation);
    }
    public void setRotation(double r){
        rotation = Math.toRadians(r) % (2*Math.PI);
    }
    public void setLocalRotation(double r){
        rotation = (rotation + Math.toRadians(r)) % (2*Math.PI);
    }
    
    //Returns a polygon representing the rotated rectangle
    public Polygon getShape(){
        double angle = Math.atan(width/length);
        double dis = Math.hypot(width/2,length/2);
        
        float x = location.getX();
        float z = location.getZ();
        
        //rotate each of the points to the desired angle
        Point p1 = new Point((int)(x + dis * Math.cos(angle+rotation)),(int)(z + dis * Math.sin(angle+rotation)));
        Point p2 = new Point((int)(x + dis * Math.cos(Math.PI-angle+rotation)),(int)(z + dis * Math.sin(Math.PI-angle+rotation)));
        Point p3 = new Point((int)(x + dis * Math.cos(Math.PI+angle+rotation)),(int)(z + dis * Math.sin(Math.PI+angle+rotation)));
        Point p4 = new Point((int)(x + dis * Math.cos(2*Math.PI-angle+rotation)),(int)(z + dis * Math.sin(2*Math.PI-angle+rotation)));
      
        int[] xpoints = new int[] {p1.x,p2.x,p3.x,p4.x};
        int[] zpoints = new int[] {p1.y,p2.y,p3.y,p4.y};
        
        Polygon shape = new Polygon(xpoints, zpoints, 4);
        return shape;
    }
    
    //Retuns the chunks bound to this spat
    public HashMap<Integer,PhysicsChunk> getPhysicsChunks(){
        return physicsChunks;
    }
    
    //some shortcut get methods
    public float getX(){
        return location.getX();
    }
    public float getY(){
        return location.getY();
    }
    public float getZ(){
        return location.getZ();
    }
    public float getLength(){
        return length;
    }
    public float getWidth(){
        return width;
    }
    public float getHeight(){
        return height;
    }
    public int getId(){
        return id;
    }
    
    //Adds a control to this spatial and binds the control to this spat.
    public void addControl(AbstractControl control){
        control.bindToSpatial(this);
        controls.put(control.getControlId(),control);
    }
    
    //Removes a specific control
    public void removeControl(AbstractControl control){
        controls.remove(control.getControlId());
    }
    
    //Removes the first instance of a specific class seen.
    public void removeControl(Class<?> controlType){
        for (AbstractControl c: controls.values().toArray(new AbstractControl[0])){
            if (controlType.isInstance(c)){
                controls.remove(c.getControlId());
                return;
            }
        }
    }
    
    //Returns the first control seen 
    public AbstractControl getControl(){
    	if (controls.isEmpty()){
            return null;
    	}
    	else{
            return controls.values().toArray(new AbstractControl[0])[0];
    	}
    }
    
    //returns a control with the specified control key
    public AbstractControl getControl(int id){
    	if (controls.containsKey(id)){
            return controls.get(id);
    	}
    	else{
            return null;
    	}
    }
    
    //Based on a class type, returns the first instance of the controltype found or null if it doesn't exist
    public Object getControl(Class<?> controlType){
        for (AbstractControl c: controls.values().toArray(new AbstractControl[0])){
            if (controlType.isInstance(c)){
                return controlType.cast(c);
            }
        }
        return null;
    }
    
    //The update method updates each control bound to this spatial
    public void update(){
        AbstractControl[] conts = controls.values().toArray(new AbstractControl[0]);
        for (AbstractControl c: conts){
            c.update();
        }
    }
    
    //Returns the distance squared from the given spatial
    public float distSquared(Spatial spat){
        return spat.location.distanceSquared(location);
    }
    
    //Standard rotate method
    public void rotate(double radians){
        rotation = radians;
    }
    
    public GameMap getMap(){
        return boundMap;
    }
    
    /*This method returns the culling boundary of each item based
     on their length, width and location. This boundary is used
     to cull for rendering as well as physics calculations.*/
    public int[] getCullBounds(float S_QUAD){
        if (boundMap == null)
            System.out.println("No map");
        double tx = Math.max(Math.min(getX()-getLength()/2,boundMap.getDimX()),0);//keep x and y within boundaries by using max and mins
        double ty = Math.max(Math.min(getZ()-getHeight(),boundMap.getDimY()),0);
        int x = (int)(tx*S_QUAD);//convert to int
        int y = (int)(ty*S_QUAD);
        double tsizex = getLength()+Math.min(Math.min(getX()-getLength()/2,boundMap.getDimX()-(getX()+getLength()/2)),0);// keep the width and height within bounds as well
        double tsizey = getHeight()+Math.min(Math.min(getZ()-getHeight()/2,boundMap.getDimY()-(getZ()+getHeight()/2)),0);// in accordance to the char's location
        int sizex = (int)(tsizex*S_QUAD);
        int sizey = (int)(tsizey*S_QUAD);
        return new int[]{x,y,sizex,sizey};
    }
    
    //Returns the lines that define this spatial's bounding rect
    public Line2D[] getBoundingLines(){
        Point2D[] points = getPoints();
        return new Line2D[]{new Line2D(points[3],points[2]),
                            new Line2D(points[0],points[3]),
                            new Line2D(points[1],points[2]),
                            new Line2D(points[0],points[1])};
    }
    
    //Returns the points that define this spatials bounding rect
    public Point2D[] getPoints(){
        Polygon p = getShape();
        int[] xs = p.xpoints;
        int[] ys = p.ypoints;
        Point2D[] points = new Point2D[4];
        for (int i = 0; i < 4; i++){
            points[i] = new Point2D(xs[i],ys[i]);
        }
        return points;
    }
    
    //this method intersects a given line with the 4 lines that define this spatials
    //bounding rect
    public Point2D[] intersectLine(Line2D line){
        Line2D[] allLines = getBoundingLines();
        LinkedList<Point2D> intersections = new LinkedList<>();
        Point2D intersection = allLines[0].intersect(line);
        if (intersection != null && allLines[1].distanceTo(intersection) < length && allLines[2].distanceTo(intersection) < length){
            intersections.add(intersection);
        }
        intersection = allLines[3].intersect(line);
        if (intersection != null && allLines[1].distanceTo(intersection) < length && allLines[2].distanceTo(intersection) < length){
            intersections.add(intersection);
        }
        
        intersection = allLines[1].intersect(line);
        if (intersection != null && allLines[0].distanceTo(intersection) < width && allLines[3].distanceTo(intersection) < width){
            intersections.add(intersection);
        }
        intersection = allLines[2].intersect(line);
        if (intersection != null && allLines[0].distanceTo(intersection) < width && allLines[3].distanceTo(intersection) < width){
            intersections.add(intersection);
        }
        
        return intersections.toArray(new Point2D[0]);
    }
    
    //Sets location to a new point (with new pointer)
    public void setLocation(GamePoint p){
        this.location = p;
    }
    //Sets the location's x y and z coordinates
    public void setLocation(float x,float y,float z){
        this.location.setX(x);
        this.location.setY(y);
        this.location.setZ(z);
    }
}
