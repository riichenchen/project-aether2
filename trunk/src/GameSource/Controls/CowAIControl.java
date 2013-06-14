//Testing AI Control 
//OLD Broken down into 7 different controls

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

public class CowAIControl extends AIControl {

    private final double DEFAULT_AGGRO_RADIUS = 256;
    private final double DEFAULT_SHORT_ATTACK_RANGE = 32;
    private final double DEFAULT_LONG_ATTACK_RANGE = 256;
    private final int DEFAULT_DELAY = 8;
    private final int DEFAULT_ATTACK_DELAY = 64;
    private final int SHORT_ATTACK = 0, LONG_ATTACK = 1;
    
    protected GameMap curMap;
    protected Pathfinding pf;
    protected int curX, curY;
    protected int tarX, tarY;
    protected int lastX, lastY;
    protected double shortAttackRange, longAttackRange;
    protected double aggroRadius;
    protected double dist;
    protected Random rnd;
    protected int slow;
    protected int delay;
    protected int attackDelay;
    protected boolean isAttacking;
    protected boolean isMoving;
    private boolean hasMoved;
    protected String state, direction;
    
    private boolean loadedAnimControl;
    private CharacterAnimControl animControl;
    
    public CowAIControl(GameMap map) {
        super(new CowAICalculation());
        curMap = map;
        aggroRadius = DEFAULT_AGGRO_RADIUS;
        shortAttackRange = DEFAULT_SHORT_ATTACK_RANGE;
        longAttackRange = DEFAULT_LONG_ATTACK_RANGE;
        delay = DEFAULT_DELAY;
        attackDelay = DEFAULT_ATTACK_DELAY;
        init();
    }
    
    public CowAIControl(GameMap map, double _aggroRadius, double _shortAttackRange, double _longAttackRange, int _delay, int _attackDelay) {
        super(new CowAICalculation());
        curMap = map;
        aggroRadius = _aggroRadius;
        shortAttackRange = _shortAttackRange;
        longAttackRange = _longAttackRange;
        delay = _delay;
        attackDelay = _attackDelay;
        init();
    }
    private void init() {
        rnd = new Random();
        curX = curY = tarX = tarY = -(1 << 30);
        pf = new Pathfinding(curMap.getCharMap(), curX, curY, tarX, tarY);
        slow = 0;
        isAttacking = false;
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
        if (!hasMoved) {
            state = "stand";
            direction = "down";
        }
        
        if ((!isAttacking && slow == delay) || slow == attackDelay) {
            
            getLocations();
            
            isAttacking = false;
            isMoving = false;
            
            if (dist <= shortAttackRange) {
                System.out.println("Mob is atttacking with SHORT ATTACK\n");
                isAttacking = true;
                //boundTo.attack(CharacterHandler.getPlayer(), SHORT_ATTACK);
                state = "attack1";
            } else if (dist <= longAttackRange) {
                int canAttack = rnd.nextInt() % 2;
                if (canAttack == 1) {
                    System.out.println("Mob is attacking with LONG ATTACK\n");
                    isAttacking = true;
                    //boundTo.attack(CharacterHandler.getPlayer(), LONG_ATTACK);
                    state = "attack1";
                } else
                    move();
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
        state = "stand";
        //System.out.printf("curx %d cury %d tarx %d tary %d\n", curX, curY, tarX, tarY);
        pf.updateLocations(curX, curY, tarX, tarY);
        //System.out.printf("%d %d\n", pf.getToX(), pf.getToY());
        int dx = pf.getToX() - curX;
        int dy = pf.getToY() - curY;
        boundTo.move(dx, 0, dy);
        getDirection(dx, dy);
        //System.out.printf("Moved x: %d y: %d(%d - %d)\n", tarX - curX, tarY - curY, tarY, curY);
        //System.out.printf("Current: %d %d %d\nTarget: %d %d %d\n", (int)boundTo.getX(), (int)boundTo.getY(), (int)boundTo.getZ(), (int)CharacterHandler.getPlayer().getX(), (int)CharacterHandler.getPlayer().getY(), (int)CharacterHandler.getPlayer().getZ());
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
    
    private int countSpatials() {
        ArrayList curSpatials = curMap.getSpatials();
        int size = curSpatials.size();
        int numMob = 0;
        for (int i = 0; i < size; i++) {
            if (curSpatials.get(i) instanceof AbstractMob) {
                numMob++;
            }
        }
        return numMob;
    }
}
