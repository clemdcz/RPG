package clemdcz.rpg;

public class Warrior extends Hero {
    public Warrior() { this.setLifePoints(5); }
    @Override
    public boolean attack(Fighter enemy) {
        return enemy.receiveAttack(5);
    }
}
