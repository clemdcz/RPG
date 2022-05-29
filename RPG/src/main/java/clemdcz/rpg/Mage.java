package clemdcz.rpg;

public class Mage extends SpellCaster {

    public Mage() {
        this.setLifePoints(5);
        this.setManaPoints(5);
    }

    @Override
    public boolean attack(Fighter enemy) {
        //System.out.println(this + " attaque " + enemy);
        if(getManaPoints() < 0){
            return enemy.receiveAttack(1);
        }

        setManaPoints(getManaPoints()-1);
        return enemy.receiveAttack(2);
    }

    public String toString() {
        return "Mage(" + getLifePoints() + ")";
    }
}
