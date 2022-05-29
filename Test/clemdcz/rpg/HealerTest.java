package clemdcz.rpg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealerTest {

    // Ce test vérifie la vie du warrior
    @Test
    void TestVie(){
        var healer = new Healer();
        assertEquals(5,healer.getLifePoints());
    }

    // Ce test vérife qu'il met bien les dégâts à l'ennemi
    @Test
    void TestDegats(){
        var healer = new Healer();
        var ennemy = new BasicEnemy();
        healer.attack(ennemy);
        assertEquals(4,ennemy.getLifePoints());
    }

    // Ce test vérifie que le healer soigne bien
    @Test
    void TestSoin(){
        var healer = new Healer();
        var warrior = new Warrior();
        healer.heal(warrior);
        assertEquals(11,warrior.getLifePoints());
    }

}