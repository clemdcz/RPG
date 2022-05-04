package clemdcz.rpg;

public abstract class Fighter {

    private int lifePoints;
    public int getLifePoints() { return lifePoints; }
    public void setLifePoints(int lifePoints) { this.lifePoints = lifePoints; }

//    public abstract boolean attack(Fighter fighter);
    public boolean attack(Fighter fighter) { return false; }

    public boolean receiveAttack(int lifePoints) {
        this.lifePoints -= lifePoints;
        return this.lifePoints <= 0; // Vrai si le combattant est mort
    }
}
