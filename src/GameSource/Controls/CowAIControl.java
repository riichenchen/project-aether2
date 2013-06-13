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
    
    private final int SHORT_ATTACK = 0, LONG_ATTACK = 1;
    
    protected GameMap curMap;
    protected Pathfinding pf;
    protected int curX, curY;
    protected int tarX, tarY;
    protected double shortAttackRange, longAttackRange;
    protected double aggroRadius;
    protected double dist;
    protected Random rnd;
    
    public CowAIControl(GameMap map) {
        super(new CowAICalculation());
        curMap = map;
        aggroRadius = DEFAULT_AGGRO_RADIUS;
        shortAttackRange = DEFAULT_SHORT_ATTACK_RANGE;
        longAttackRange = DEFAULT_LONG_ATTACK_RANGE;
        rnd = new Random();
        getLocations();
        pf = new Pathfinding(map.getCharMap(), curX, curY, tarX, tarY);
    }
    
    public CowAIControl(GameMap map, double _aggroRadius, double _shortAttackRange, double _longAttackRange) {
        super(new CowAICalculation());
        curMap = map;
        aggroRadius = _aggroRadius;
        shortAttackRange = _shortAttackRange;
        longAttackRange = _longAttackRange;
        
    }

    @Override
    public void update(Object returnValue) {
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
    }
    
    private void getLocations() {
        curX = (int)boundTo.getX();
        curY = (int)boundTo.getZ();
        tarX = (int)CharacterHandler.getPlayer().getX();
        tarY = (int)CharacterHandler.getPlayer().getY();    
        dist = Math.sqrt((tarX - curX) * (tarX - curX) + (tarY - curY) * (tarY - curY));
    }
    
    private void move() {
        pf.updateLocations(curX, curY, tarX, tarY);
        boundTo.move(pf.getToX() - curX, 0, pf.getToY() - curY);
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
