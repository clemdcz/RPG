package clemdcz.rpg;

public class Healer extends SpellCaster {

    public Healer() {
        this.setLifePoints(5);
    }

    @Override
    public boolean attack(Fighter enemy) {
        System.out.println(this + " attacks " + enemy);
        return enemy.receiveAttack(2);
    }

    public void heal(Hero hero) {
        hero.healed(3);
    }

    public String toString() {
        return "Healer(" + getLifePoints() + ")";
    }
}
