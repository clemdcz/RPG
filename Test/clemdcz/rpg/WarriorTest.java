package clemdcz.rpg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest {

    // Ce test vérifie la vie du warrior
    @Test
    void TestVie(){
        var warrior = new Warrior();
        assertEquals(8,warrior.getLifePoints());
    }

    // Ce test vérife qu'il met bien les dégâts à l'ennemi
    @Test
    void TestDegats(){
        var warrior = new Warrior();
        var ennemy = new BasicEnemy();
        warrior.attack(ennemy);
        assertEquals(2,ennemy.getLifePoints());
    }





}