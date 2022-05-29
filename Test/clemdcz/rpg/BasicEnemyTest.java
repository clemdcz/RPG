package clemdcz.rpg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicEnemyTest {

    // Ce test vérifie la vie du warrior
    @Test
    void TestVie(){
        var ennemy = new BasicEnemy();
        assertEquals(6,ennemy.getLifePoints());
    }

    // Ce test vérife qu'il met bien les dégâts à l'ennemi
    @Test
    void TestDegats(){
        var mage = new Mage();
        var ennemy = new BasicEnemy();
        ennemy.attack(mage);
        assertEquals(3,mage.getLifePoints());
    }

}

