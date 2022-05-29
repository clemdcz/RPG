package clemdcz.rpg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HunterTest {

    // Ce test vérifie la vie du warrior
    @Test
    void TestVie(){
        var hunter = new Hunter();
        assertEquals(6,hunter.getLifePoints());
    }

    // Ce test vérife qu'il met bien les dégâts à l'ennemi
    @Test
    void TestDegats(){
        var hunter = new Hunter();
        var ennemy = new BasicEnemy();
        hunter.attack(ennemy);
        assertEquals(3,ennemy.getLifePoints());
    }
}