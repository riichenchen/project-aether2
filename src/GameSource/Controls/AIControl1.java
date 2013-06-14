//AIControl1
//Supports Mobs with -attack1, stand, and walk commands
//Only key differences will be commeneted on from AIControl2 - AIControl7

package GameSource.Controls;


//Import required packages
import ArtificialIntelligence.Pathfinding;
import ArtificialIntelligence.AIControl;
import Controls.CharacterAnimControl;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.User.CharacterHandler;
import GameSource.game.GameMap;
import Testing.CowAICalculation;
import java.util.ArrayList;
import java.util.Random;

public class AIControl1 extends AIControl {
    
    protected GameMap curMap; //Stores current map
    protected Pathfinding pf; //Helper pathfinding class
    
    private int curX, curY; //Mob location
    private int tarX, tarY; //Player location
    private int lastX, lastY; //Mob last location
    
    private double aggroRadius; //Aggro radius of mob (becomes active when player is in range)
    
    private double attack1Range; //range of attack1
    private int attack1Delay; //delay of attack1
   
    private int slow; //counter to slow down frames
    private int delay; //delay for movement
    
    private double dist; //distance from player
    private Random rnd; //random number generator (used to decide if attacking is okay if given a choice)
    
    private boolean isAttacking1; //mob is in process of attack1
    private boolean isMoving; //mob is in process of moving
    private boolean hasMoved; //mob has moved since spawn
    private String state, direction; //state of the mob and direction it is facing
    
    private boolean loadedAnimControl; //stores if animator control is loaded
    private CharacterAnimControl animControl; //animator control
    
    public AIControl1(GameMap map) { //Constructor if nothing about the mob is passed
        super(new CowAICalculation()); //does nothing, here so old code doesn't break
        curMap = map; //stores the current map
		//use default settings
        aggroRadius = DEFAULT_AGGRO_RADIUS; 
        attack1Range = DEFAULT_ATTACK1_RANGE;
        attack1Delay = DEFAULT_ATTACK1_DELAY;
        delay = DEFAULT_DELAY;
        init();
    }
    
    public AIControl1(GameMap map, double _aggroRadius, int _delay, double _attack1Range, int _attack1Delay) { //constructor with mob aggro radius, delay, and list of attack / cast ranges with their delay
        super(new CowAICalculation());
        curMap = map;
		//store the information passed as parameters
        aggroRadius = _aggroRadius;
        delay = _delay;
        attack1Range = _attack1Range;
        attack1Delay = _attack1Delay;
        init();
    }
    private void init() {
        rnd = new Random(); //make random number generator
        curX = curY = tarX = tarY = -(1 << 30); //target and mob is off the map initially
        pf = new Pathfinding(curMap.getCharMap(), curX, curY, tarX, tarY); //throw in the gamemap in character map format to pathfinder
        slow = 0; //counter to slow down frames
        isAttacking1 = false; //mob is not attacking
        isMoving = false; //mob is not moving
        loadedAnimControl = false; //animator control not loaded
        hasMoved = false; //mob has not moved
    }
 
    @Override
    public void update(Object returnValue) {
        if (!loadedAnimControl) { //if the animator control is not loaded, load it and update loadedAnimControl to true
            animControl = (CharacterAnimControl)boundTo.getControl(CharacterAnimControl.class);
            loadedAnimControl = true;
        }
        
        if ( (!isAttacking1 && (slow == delay)) || (isAttacking1 && (slow == attack1Delay)) ) { //if it is not attacking check to see if counter has reached move delay, otherwise check if counter has moved attacks delay
            
            getLocations(); //grab new location of player and mob and compute the distance
            if (dist > aggroRadius) //if the player is too far away, do nothing
                return;
            if (!hasMoved) { //set mob to stand forward if it has not moved yet
                state = "stand";
                getDirection(tarX - curX, tarY - curY);
            }
            
			//set move and attack to false before ai makes decision
            isAttacking1 = false;
            isMoving = false;
            
            if (dist <= attack1Range) { //if mob is in attack range, attack otherwise move
                System.out.println("attack1\n");
                isAttacking1 = true;
                state = "attack1"; //update state
            } else
                    move();
            System.out.println(state + direction);
            animControl.swapAnim(state + direction); //update image
            slow = 0; //reset counter
        }
        slow++; //increase counter
    }
    
    private void getLocations() {
        curX = (int)boundTo.getX(); //get mob x
        curY = (int)boundTo.getZ(); //get mob y
        tarX = (int)CharacterHandler.getPlayer().getX(); //get player x
        tarY = (int)CharacterHandler.getPlayer().getZ(); //get player y   
        dist = Math.sqrt((tarX - curX) * (tarX - curX) + (tarY - curY) * (tarY - curY)); //compute distance
    }
    
    private void move() {
        hasMoved = true; //has moved
        state = "walk"; //walking
        pf.updateLocations(curX, curY, tarX, tarY); //update pathfinding with new locations
        int dx = pf.getToX() - curX; //get deltax from next step
        int dy = pf.getToY() - curY; //get deltay from next step
        boundTo.move(dx, 0, dy); //move accordingly
        getDirection(dx, dy); //get direction
    }
    
    private void getDirection(int dx, int dy) { //get direction depending on where the mob was last at (how the mob moved)
        if (dx == 0 && dy > 0) //facing down
            direction = "down";
        else if (dx < 0 && dy > 0) //facing downleft
            direction = "downleft";
        else if (dx > 0 && dy > 0) //facing downright
            direction = "downright";
        else if (dx < 0 && dy == 0) //facing left
            direction = "left";
        else if (dx > 0 && dy == 0) //facing right
            direction = "right";
        else if (dx == 0 && dy < 0) //facing up
            direction = "up";
        else if (dx < 0 && dy < 0) //facing upleft
            direction = "upleft";
        else if (dx > 0 && dy < 0) //facing upright
            direction = "upright";
    }
}
