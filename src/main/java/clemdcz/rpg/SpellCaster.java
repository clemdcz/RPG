package clemdcz.rpg;

public abstract class SpellCaster extends Hero {

    private int manaPoints;

    public int getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(int manaPoints) {
        this.manaPoints = manaPoints;
    }
}
