package clemdcz.rpg;

import clemdcz.util.InputParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;


public class Game {

    public static Game context;

    public static void playGame() {
        if (Game.context != null) {
            throw new RuntimeException
                    ("Impossible de lancer plusieurs fois la partie...");
        }
        Game.context = new Game();
        Game.context.generateHeroes();
        Game.context.startCombat();
    }

    public static enum Status {START_COMBAT, HERO_TURN, ENEMY_TURN, END_GAME }
    public Status status;



    private List<Hero> heroes;
    public List<String> getHeroesStatus() {
        List<String> heroesStatus = new ArrayList<>();
        for (Hero hero: this.heroes) {
            heroesStatus.add
                ( hero.getClass().getSimpleName()
                + "(" + hero.getLifePoints() + ")"
                );
        }
        return heroesStatus;
    }
    private List<Enemy> enemies;
    public List<String> getEnemiesStatus() {
        List<String> enemyStatus = new ArrayList<>();
        for (Enemy enemy: this.enemies) {
            enemyStatus.add
                ( enemy.getClass().getSimpleName()
                + "(" + enemy.getLifePoints() + ")"
                );
        }
        return enemyStatus;
    }
    // "fighters" : Les héros et les ennemis mélangés
    private List<Fighter> fighters;
    ListIterator<Fighter> fightersIterator;

    private Fighter currentFighter;

    private int playerTurn;
    private InputParser inputParser;

    // L'instanciation de "Game" ne peut se faire que par "playGame"
    private Game() {}

    public void startCombat() {
        // Combat avec de nouveaux ennemis tant qu'il y a des héros actifs
        if (this.heroes.size() > 0) {
            this.status = Status.START_COMBAT;
            generateCombat();
        } else {
            this.status = Game.Status.END_GAME;
        }
    }

    public void generateCombat() {
        generateEnemies();
        shuffleFighters();
        // Initialise un "curseur" pour parcourir la liste des combattants
        fightersIterator = fighters.listIterator();
    }

    private void generateHeroes() {
        this.heroes = new ArrayList<>();
        Hero hero = new Warrior(); //--> un seul héro pour l'instant !
        this.heroes.add(hero);
    }

    private void generateEnemies() {
        this.enemies = new ArrayList<>();
        enemies.add( new BasicEnemy() ); //--> un seul ennemi pour l'instant !
    }

    // Mélange les héros avec les ennemis dans une liste pour le combat
    private void shuffleFighters() {
        this.fighters = new ArrayList<>();
        this.fighters.addAll(this.heroes);
        this.fighters.addAll(this.enemies);
        Collections.shuffle(this.fighters); //--> google "java shuffle list"
    }

    public void startNextFighterTurn() {

        if (this.heroes.size() == 0) {
            this.status = Game.Status.END_GAME;
        } else if (enemies.size() == 0) {
            this.status = Game.Status.START_COMBAT;
            generateCombat();
        } else {

            // Récupère le combattant suivant en déplaçant le curseur de liste
            if (!fightersIterator.hasNext()) {
                // Si on est à la fin de la liste, l'itérateur est réinitialisé
                fightersIterator = fighters.listIterator();
            }
            this.currentFighter = fightersIterator.next();

            if (this.currentFighter instanceof Hero) {
                //--> "instanceof" permet de valider si la variable "fighter",
                //    qui est de type "Fighter", contient bien une instance de la
                //    sous-classe "Hero".
                this.status = Game.Status.HERO_TURN;
            } else {
                this.status = Game.Status.ENEMY_TURN;
            }

        }
    }

    public void startHeroTurn() {
        // Pour l'instant --> le joueur ne décide pas de l'action du héro
        //                --> le joueur attaque l'ennemi
        Fighter ennemy = this.enemies.get(0); //--> 1 seul ennemi pour l'instant...
        boolean ennemyDefeated = this.currentFighter.attack(ennemy);
        if (ennemyDefeated) {
            this.enemies.remove(ennemy);
            this.fighters.remove(ennemy);
        }
    }

    public void startEnemyTurn() {
        // L'ennemi attaque au hasard un des héros encore vivant
        Fighter hero = this.heroes.get(0); //--> 1 seul héro pour l'instant...
        boolean heroDefeated = this.currentFighter.attack(hero);
        if (heroDefeated) {
            this.heroes.remove(hero);
            this.fighters.remove(hero);
        }
    }

}
