package clemdcz.rpg;

public class Mage extends SpellCaster {

    public Mage() {
        this.setLifePoints(5);
    }

    @Override
    public boolean attack(Fighter enemy) {
        System.out.println(this + " attacks " + enemy);
        return enemy.receiveAttack(2);
    }

    public String toString() {
        return "Mage(" + getLifePoints() + ")";
    }
}
