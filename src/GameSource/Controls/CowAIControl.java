package GameSource.Controls;

import ArtificialIntelligence.Pathfinding;
import ArtificialIntelligence.AIControl;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.User.CharacterHandler;
import GameSource.game.GameMap;
import Testing.CowAICalculation;
import java.util.ArrayList;
import java.util.Random;

public class CowAIControl extends AIControl {

    private final double DEFAULT_AGGRO_RADIUS = 128;
    private final double DEFAULT_SHORT_ATTACK_RANGE = 5;
    private final double DEFAULT_LONG_ATTACK_RANGE = 15;
    private final int DEFAULT_DELAY = 8;
    
    private final int SHORT_ATTACK = 0, LONG_ATTACK = 1;
    
    protected GameMap curMap;
    protected Pathfinding pf;
    protected int curX, curY;
    protected int tarX, tarY;
    protected double shortAttackRange, longAttackRange;
    protected double aggroRadius;
    protected double dist;
    protected Random rnd;
    protected int slow;
    protected int delay;
    
    public CowAIControl(GameMap map) {
        super(new CowAICalculation());
        curMap = map;
        aggroRadius = DEFAULT_AGGRO_RADIUS;
        shortAttackRange = DEFAULT_SHORT_ATTACK_RANGE;
        longAttackRange = DEFAULT_LONG_ATTACK_RANGE;
        delay = DEFAULT_DELAY;
        init();
    }
    
    public CowAIControl(GameMap map, double _aggroRadius, double _shortAttackRange, double _longAttackRange, int _delay) {
        super(new CowAICalculation());
        curMap = map;
        aggroRadius = _aggroRadius;
        shortAttackRange = _shortAttackRange;
        longAttackRange = _longAttackRange;
        delay = _delay;
        init();
    }
    private void init() {
        rnd = new Random();
        pf = new Pathfinding(curMap.getCharMap(), -1, -1, -1, -1);
        slow = 0;     
    }
    @Override
    public void update(Object returnValue) {
        if (slow++ == delay) {
            getLocations();
            int attackType = rnd.nextInt() % 2;
            if (attackType == SHORT_ATTACK) {
                if (dist <= shortAttackRange) {
                    //boundTo.attack(CharacterHandler.getPlayer(), SHORT_ATTACK);
                } else
                    move();
            } else {
                if (dist <= longAttackRange) {
                    //boundTo.attack(CharacterHandler.getPlayer(), LONG_ATTACK);
                } else
                    move();
            }
            slow = 0;
        }
    }
    
    private void getLocations() {
        curX = (int)boundTo.getX();
        curY = (int)boundTo.getZ();
        tarX = (int)CharacterHandler.getPlayer().getX();
        tarY = (int)CharacterHandler.getPlayer().getZ();    
        dist = Math.sqrt((tarX - curX) * (tarX - curX) + (tarY - curY) * (tarY - curY));
    }
    
    private void move() {
        //System.out.printf("curx %d cury %d tarx %d tary %d\n", curX, curY, tarX, tarY);
        pf.updateLocations(curX, curY, tarX, tarY);
        //System.out.printf("%d %d\n", pf.getToX(), pf.getToY());
        boundTo.move(pf.getToX() - curX, 0, pf.getToY() - curY);
        //System.out.printf("Moved x: %d y: %d(%d - %d)\n", tarX - curX, tarY - curY, tarY, curY);
        //System.out.printf("Current: %d %d %d\nTarget: %d %d %d\n", (int)boundTo.getX(), (int)boundTo.getY(), (int)boundTo.getZ(), (int)CharacterHandler.getPlayer().getX(), (int)CharacterHandler.getPlayer().getY(), (int)CharacterHandler.getPlayer().getZ());
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
