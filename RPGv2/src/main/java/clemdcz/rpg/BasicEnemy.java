package clemdcz.rpg;

public class BasicEnemy extends Enemy {
    public BasicEnemy() { this.setLifePoints(6); }
    @Override
    public boolean attack(Fighter hero) {
        System.out.println(this + " attacks " + hero);
        return hero.receiveAttack(2);
    }

    public String toString() {
        return "BasicEnemy(" + getLifePoints() + ")";
    }
}
