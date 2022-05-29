package clemdcz.rpg;

public class Healer extends SpellCaster {

    public Healer() {
        this.setLifePoints(5);
        this.setManaPoints(5);
    }

    @Override
    public boolean attack(Fighter enemy) {
        //System.out.println(this + " attaque " + enemy);
        return enemy.receiveAttack(2);
    }

    public void heal(Hero hero) {
        hero.healed(3);
        setManaPoints(getManaPoints() - 1);
    }

    public String toString() {
        return "Healer(" + getLifePoints() + ")";
    }
}
