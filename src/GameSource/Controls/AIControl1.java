package GameSource.Controls;

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
    
    protected GameMap curMap;
    protected Pathfinding pf;
    
    private int curX, curY;
    private int tarX, tarY;
    private int lastX, lastY;
    
    private double aggroRadius;
    
    private double attack1Range;
    private int attack1Delay;
   
    private int slow;
    private int delay;
    
    private double dist;
    private Random rnd;
    
    private boolean isAttacking1;
    private boolean isMoving;
    private boolean hasMoved;
    private String state, direction;
    
    private boolean loadedAnimControl;
    private CharacterAnimControl animControl;
    
    public AIControl1(GameMap map) {
        super(new CowAICalculation());
        curMap = map;
        aggroRadius = DEFAULT_AGGRO_RADIUS;
        attack1Range = DEFAULT_ATTACK1_RANGE;
        attack1Delay = DEFAULT_ATTACK1_DELAY;
        delay = DEFAULT_DELAY;
        init();
    }
    
    public AIControl1(GameMap map, double _aggroRadius, int _delay, double _attack1Range, int _attack1Delay) {
        super(new CowAICalculation());
        curMap = map;
        aggroRadius = _aggroRadius;
        delay = _delay;
        attack1Range = _attack1Range;
        attack1Delay = _attack1Delay;
        init();
    }
    private void init() {
        rnd = new Random();
        curX = curY = tarX = tarY = -(1 << 30);
        pf = new Pathfinding(curMap.getCharMap(), curX, curY, tarX, tarY);
        slow = 0;
        isAttacking1 = false;
        isMoving = false;
        loadedAnimControl = false;
        hasMoved = false;
    }
 
    @Override
    public void update(Object returnValue) {
        if (!loadedAnimControl) {
            animControl = (CharacterAnimControl)boundTo.getControl(CharacterAnimControl.class);
            loadedAnimControl = true;
        }
        
        if ( (!isAttacking1 && (slow == delay)) || (isAttacking1 && (slow == attack1Delay)) ) {
            
            getLocations();
            if (dist > aggroRadius)
                return;
            if (!hasMoved) {
                state = "stand";
                getDirection(tarX - curX, tarY - curY);
            }
            
            isAttacking1 = false;
            isMoving = false;
            
            if (dist <= attack1Range) {
                System.out.println("attack1\n");
                isAttacking1 = true;
                state = "attack1";
            } else
                    move();
            System.out.println(state + direction);
            animControl.swapAnim(state + direction);
            slow = 0;
        }
        slow++;
    }
    
    private void getLocations() {
        curX = (int)boundTo.getX();
        curY = (int)boundTo.getZ();
        tarX = (int)CharacterHandler.getPlayer().getX();
        tarY = (int)CharacterHandler.getPlayer().getZ();    
        dist = Math.sqrt((tarX - curX) * (tarX - curX) + (tarY - curY) * (tarY - curY));
    }
    
    private void move() {
        hasMoved = true;
        state = "walk";
        pf.updateLocations(curX, curY, tarX, tarY);
        int dx = pf.getToX() - curX;
        int dy = pf.getToY() - curY;
        boundTo.move(dx, 0, dy);
        getDirection(dx, dy);
    }
    
    private void getDirection(int dx, int dy) {
        if (dx == 0 && dy > 0)
            direction = "down";
        else if (dx < 0 && dy > 0)
            direction = "downleft";
        else if (dx > 0 && dy > 0)
            direction = "downright";
        else if (dx < 0 && dy == 0)
            direction = "left";
        else if (dx > 0 && dy == 0)
            direction = "right";
        else if (dx == 0 && dy < 0)
            direction = "up";
        else if (dx < 0 && dy < 0)
            direction = "upleft";
        else if (dx > 0 && dy < 0)
            direction = "upright";
    }
}
