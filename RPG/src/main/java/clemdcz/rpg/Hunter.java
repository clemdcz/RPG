package clemdcz.rpg;

public class Hunter extends Hero {

    // nombre fleches
    private int arrows = 3;

    public Hunter() {
        this.setLifePoints(6);
    }

    @Override
    public boolean attack(Fighter enemy) {
        //System.out.println(this + " attaque " + enemy);
        arrows--;
        return enemy.receiveAttack(3);
    }

    public int getArrows() {
        return arrows;
    }

    public void setArrows(int arrows) {
        this.arrows = arrows;
    }

    public String toString() {
        return "Hunter(" + getLifePoints() + ")";
    }

}
