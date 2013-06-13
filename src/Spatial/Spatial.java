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
//    protected float mass;
//=======
//     private int entity;
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
    
    public void setProperty(String key,Object obj){
        properties.put(key, obj);
    }
    
    public Object getProperty(String key){
        return properties.get(key);
    }
    
    public boolean hasProperty(String key){
        return properties.containsKey(key);
    }
    
    public void removeProperty(String key){
        properties.remove(key);
    }
    
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
    
    public void move(float x, float y, float z){
        if (space == null){
            location.translate(x,y,z);
        } else {
            space.addMoveMessage(x,z,this);
        }
    }
    public abstract void collideEffect(Spatial s);
    
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
        if (collided){
            return true;
//            if (aYmin >= bYmax && aYmax <= bYmin){
//                return true;
//            } else if (aYmin <= bYmin && aYmax >= bYmax){
//                return true;
//            }
//            return false;
        }
        return false;
    }
    
    /*not used atm...*/
    public boolean contains(Spatial s){
        Rectangle axz = new Rectangle ((int)(location.getX() - length/2), (int)(location.getZ() - width/2), (int)length, (int)width);
        Rectangle bxz = new Rectangle ((int)(s.getX() - s.getLength()/2), (int)(s.getZ() - s.getWidth()/2), (int)s.getLength(), (int)s.getWidth());
        
        float aYmin = location.getY();
        float aYmax = location.getY() + height;
        
        float bYmin = s.getY();
        float bYmax = s.getY() + s.getHeight();
        
        if (axz.contains(bxz)){
            if (aYmin >= bYmin && aYmax <= bYmax)
            {return true;}
            else{}return false;
        }
        else{}return false;
    }
    
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
    public Polygon getShape(){
        double angle = Math.atan(width/length);
//        if (this instanceof Stevey)
//            System.out.println(Math.atan(width/length));
//            System.out.println(Math.cos(angle));
//        double angle2 = rotation - Math.atan(width/length);
        double dis = Math.hypot(width/2,length/2);
        
        float x = location.getX();
        float z = location.getZ();
//        double cosA = Math.cos(rotation);
//        double sinA = Math.sin(rotation);
        Point p1 = new Point((int)(x + dis * Math.cos(angle+rotation)),(int)(z + dis * Math.sin(angle+rotation)));
        Point p2 = new Point((int)(x + dis * Math.cos(Math.PI-angle+rotation)),(int)(z + dis * Math.sin(Math.PI-angle+rotation)));
        Point p3 = new Point((int)(x + dis * Math.cos(Math.PI+angle+rotation)),(int)(z + dis * Math.sin(Math.PI+angle+rotation)));
        Point p4 = new Point((int)(x + dis * Math.cos(2*Math.PI-angle+rotation)),(int)(z + dis * Math.sin(2*Math.PI-angle+rotation)));
      
        int[] xpoints = new int[] {p1.x,p2.x,p3.x,p4.x};
        int[] zpoints = new int[] {p1.y,p2.y,p3.y,p4.y};
        
        Polygon shape = new Polygon(xpoints, zpoints, 4);
        return shape;
    }
    public HashMap<Integer,PhysicsChunk> getPhysicsChunks(){
        return physicsChunks;
    }
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
    
    public void addControl(AbstractControl control){
        control.bindToSpatial(this);
        controls.put(control.getControlId(),control);
    }
    
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
    
    public AbstractControl getControl(){
    	if (controls.isEmpty()){
            return null;
    	}
    	else{
            return controls.values().toArray(new AbstractControl[0])[0];
    	}
    }
    
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
    
    public void scale(float percent){
        percentscale = percent;
    	length *= percentscale;
    	width *= percentscale;
    	height *= percentscale;
    }
    public void scaleLocal(float percent){
    	percentscale += percent;
    	length *= percentscale;
    	width *= percentscale;
    	height *= percentscale;
    }
    
    public void update(){
        AbstractControl[] conts = controls.values().toArray(new AbstractControl[0]);
        for (AbstractControl c: conts){
            c.update();
        }
    }
    public float distSquared(Spatial spat){
        return spat.location.distanceSquared(location);
    }
    
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
    
    public Line2D[] getBoundingLines(){
        Point2D[] points = getPoints();
        return new Line2D[]{new Line2D(points[3],points[2]),
                            new Line2D(points[0],points[3]),
                            new Line2D(points[1],points[2]),
                            new Line2D(points[0],points[1])};
    }
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
    public Point2D[] intersectLine(Line2D line){
        Line2D[] allLines = getBoundingLines();
        LinkedList<Point2D> intersections = new LinkedList<>();
//        for (Line2D ln: allLines){
        Point2D intersection = allLines[0].intersect(line);
        if (intersection != null && allLines[1].distanceTo(intersection) < length && allLines[2].distanceTo(intersection) < length){
//            System.out.println("Front!");
            intersections.add(intersection);
        }
        intersection = allLines[3].intersect(line);
        if (intersection != null && allLines[1].distanceTo(intersection) < length && allLines[2].distanceTo(intersection) < length){
//            System.out.println("BACK!");
            intersections.add(intersection);
        }
        
        intersection = allLines[1].intersect(line);
//        if (this instanceof Portal){
//        System.out.println(intersection.x+" "+intersection.y);
//        System.out.println(allLines[0].distanceTo(intersection));
//        System.out.println(allLines[3].distanceTo(intersection));
//        }
        if (intersection != null && allLines[0].distanceTo(intersection) < width && allLines[3].distanceTo(intersection) < width){
//            System.out.println("LEFT");
            intersections.add(intersection);
        }
        intersection = allLines[2].intersect(line);
        if (intersection != null && allLines[0].distanceTo(intersection) < width && allLines[3].distanceTo(intersection) < width){
//            System.out.println("RIGHT!");
            intersections.add(intersection);
        }
        
//        }
        return intersections.toArray(new Point2D[0]);
    }
    public void setLocation(GamePoint p){
        this.location = p;
    }
    public void setLocation(float x,float y,float z){
        this.location.setX(x);
        this.location.setY(y);
        this.location.setZ(z);
    }
}
