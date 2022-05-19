package clemdcz.rpg;

import clemdcz.util.InputParser;

import java.util.*;


public class Game {

    public static Game context;
    public static List<Hero> heroes;

    public static int playGame() {

        if (Game.context != null) {
            throw new RuntimeException
                    ("Impossible de lancer plusieurs fois la partie...");
        }
        Game.context = new Game();

        return Game.context.startCombat();
    }

    public static enum Status {START_COMBAT, HERO_TURN, ENEMY_TURN, END_GAME }
    public Status status;

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

    public Fighter currentFighter;

    private int playerTurn;
    private InputParser inputParser;

    // L'instanciation de "Game" ne peut se faire que par "playGame"
    private Game() {}

    public int startCombat() {
        int num = -1;
        // Combat avec de nouveaux ennemis tant qu'il y a des héros actifs
        if (this.heroes.size() > 0) {
            this.status = Status.START_COMBAT;
            num = generateCombat();
        } else {
            this.status = Game.Status.END_GAME;
        }

        return num;
    }

    public int generateCombat() {
        generateEnemies();
        int size = shuffleFighters();
        // Initialise un "curseur" pour parcourir la liste des combattants
        fightersIterator = fighters.listIterator();

        return size;
    }

    private void generateEnemies() {
        this.enemies = new ArrayList<>();

        // generation aleatoire d'un nombre d'ennemies entre 1 et 4
        int numberEnnemies = new Random().nextInt(3) + 1;

        System.out.println("Generation de " + numberEnnemies + " ennemies.");

        for (int i = 0; i < numberEnnemies; i++) {
            enemies.add( new BasicEnemy() );
        }
    }

    // Mélange les héros avec les ennemis dans une liste pour le combat
    private int shuffleFighters() {
        this.fighters = new ArrayList<>();
        this.fighters.addAll(this.heroes);
        this.fighters.addAll(this.enemies);
        Collections.shuffle(this.fighters); //--> google "java shuffle list"

        return fighters.size();
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
            System.out.println("Combattant actuel = " + currentFighter);
            System.out.println("Combattants = " +  fighters);

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

    public Hero startHeroTurn(int i, boolean healAction) {

        if (healAction) {
            Hero heroSoigne = heroes.get(i);
            ((Healer) currentFighter).heal(heroSoigne);
        } else {

            if(currentFighter instanceof Hunter && ((Hunter) currentFighter).getArrows() <= 0) {
                ((Hunter) currentFighter).setArrows(-99);
            } else {
                Fighter ennemy = this.enemies.get(i); //--> 1 seul ennemi pour l'instant...
                boolean ennemyDefeated = this.currentFighter.attack(ennemy);


                if (ennemyDefeated) {
                    Fighter fighter = null;
                    fightersIterator = fighters.listIterator();

                    while (fightersIterator.hasNext()) {
                        fighter = fightersIterator.next();

                        if (fighter.equals(ennemy)) {
                            System.out.println("Ennemi enlevé ...");
                            fightersIterator.remove();
                            break;
                        }
                    }

                    this.enemies.remove(ennemy);

                    fightersIterator = fighters.listIterator();
                    Fighter f = fightersIterator.next();
                    while(!currentFighter.equals(f) && fightersIterator.hasNext()) {
                        f = fightersIterator.next();
                    }

                }
            }

        }
        return (Hero) currentFighter;
    }

    public void startEnemyTurn(int indexHero) {
        // L'ennemi attaque au hasard un des héros encore vivant
        Fighter hero = this.heroes.get(indexHero); //--> 1 seul héro pour l'instant...
        boolean heroDefeated = this.currentFighter.attack(hero);
        if (heroDefeated) {
            Fighter fighter = null;
            fightersIterator = fighters.listIterator();
            while (fightersIterator.hasNext()) {
                fighter = fightersIterator.next();

                if (fighter.equals(hero)) {
                    System.out.println("Héro enlevé ...");
                    fightersIterator.remove();
                    break;
                }
            }
            this.heroes.remove(hero);
            fightersIterator = fighters.listIterator();
            Fighter f = fightersIterator.next();
            while(!currentFighter.equals(f) && fightersIterator.hasNext()) {
                f = fightersIterator.next();
            }

        }
    }


    /**
     * cette méthode genére un hero selon type demandé.
     * @param typeHero
     * @return
     */
    public static Hero generateHero(String typeHero) {
        Hero hero;
        switch (typeHero) {
            case "Healer":
                hero = new Healer();
                break;

            case "Hunter":
                hero = new Hunter();
                break;

            case "Mage":
                hero = new Mage();
                break;

            case "Warrior":
                hero = new Warrior();
                break;
            default:
                hero = null;
        }

        return hero;
    }

}
