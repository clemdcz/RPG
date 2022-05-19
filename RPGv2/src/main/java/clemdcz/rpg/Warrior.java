package clemdcz.rpg;

public class Warrior extends Hero {
    public Warrior() { this.setLifePoints(8); }
    @Override
    public boolean attack(Fighter enemy) {
        System.out.println(this + " attacks " + enemy);
        return enemy.receiveAttack(4);
    }

    public String toString() {
        return "Warrior(" + getLifePoints() + ")";
    }
}
