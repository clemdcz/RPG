package clemdcz.rpg;

import java.util.List;

public abstract class Hero extends Fighter {

    private int armor;
    private int weaponDamage;
    private List<Potion> potions;
    private List<Food> lembas;

    public void defend() {}
    public void useConsumable(Consumable consumable) {}

    /**
     * fonction pour se soigner on ajoutant des points de vie.
     * @param p
     */
    protected void healed(int p) {
        System.out.println(this + " heald by " + p + " points.");
        int lifePoint = this.getLifePoints();
        lifePoint += p;

        if(lifePoint > 7) {
            p = 5;
        }

        this.setLifePoints(lifePoint);
    }

}
