package clemdcz.rpg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MageTest {

    // Ce test vérifie la vie du warrior
    @Test
    void TestVie(){
        var mage = new Mage();
        assertEquals(5,mage.getLifePoints());
    }

    // Ce test vérife qu'il met bien les dégâts à l'ennemi
    @Test
    void TestDegats(){
        var mage = new Mage();
        var ennemy = new BasicEnemy();
        mage.attack(ennemy);
        assertEquals(4,ennemy.getLifePoints());
    }

}