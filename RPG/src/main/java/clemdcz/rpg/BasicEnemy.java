package clemdcz.rpg;

public class BasicEnemy extends Enemy {
    public BasicEnemy() { this.setLifePoints(2); }
    @Override
    public boolean attack(Fighter hero) {
        return hero.receiveAttack(1);
    }
}
