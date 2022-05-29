package clemdcz.jfx;

import clemdcz.rpg.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.Random;

import static clemdcz.rpg.Game.Status.START_COMBAT;

public class GameController {

    @FXML
    ListView<String> heroList;
    @FXML
    ListView<String> enemyList;
    @FXML
    Button fightButton;
    @FXML
    Button healButton;

    @FXML
    Button defenseButton;
    @FXML
    Button manaButton;
    @FXML
    Button arrowButton;

    @FXML
    ImageView heroIMG;
    @FXML
    ImageView ennemyIMG;
    @FXML
    Label combatText;
    @FXML
    Label round;

    int tour = 1;
    int n = 0;
    int nombreFighters;
    int indexEnemy = 0;

    // "initialize()" est appelé par JavaFX à l'affichage de la fenêtre
    @FXML
    public void initialize() {
        nombreFighters = Game.playGame();
        updateListViews();
        updateFightButton();
        checkHealerTurn();
        checkDefenseTurn();
        manaButton.setVisible(false);
        arrowButton.setVisible(false);

        healButton.setOnAction( event -> {
            int indexHero = heroList.getSelectionModel().getSelectedIndex();
            if(indexHero < 0) {
                indexHero = new Random().nextInt(heroList.getItems().size());
            }
            Hero hero = Game.context.startHeroTurn(indexHero, true,false);

            updateListViews();
            Game.context.startNextFighterTurn();

            updateImagesCombat(hero, null, true,false);
            updateFightButton();

        } );

        defenseButton.setOnAction(actionEvent -> {
            int indexHero = heroList.getSelectionModel().getSelectedIndex();
            if(indexHero < 0) {
                indexHero = new Random().nextInt(heroList.getItems().size());
            }

            Hero hero = Game.context.startHeroTurn(indexHero, false,true);

            updateListViews();
            Game.context.startNextFighterTurn();

            updateImagesCombat(hero, null, false,true);
            updateFightButton();
        });

        manaButton.setOnAction(actionEvent -> {
            for(int i = 0 ; i < Game.heroes.size();i++){
                Hero hero = Game.heroes.get(i);
                if(hero instanceof SpellCaster){
                    ((SpellCaster)hero).setManaPoints(((SpellCaster)hero).getManaPoints() + 3);
                }
            }
            manaButton.setVisible(false);
            arrowButton.setVisible(false);
            Game.context.status = START_COMBAT;
            Game.context.generateCombat();
            updateListViews();
            updateFightButton();
        });
        arrowButton.setOnAction(actionEvent -> {
            for(int i = 0 ; i < Game.heroes.size();i++){
                Hero hero = Game.heroes.get(i);
                if(hero instanceof Hunter){
                    ((Hunter)hero).setArrows(5);
                }
            }
            manaButton.setVisible(false);
            arrowButton.setVisible(false);
            Game.context.status = START_COMBAT;
            Game.context.generateCombat();
            updateListViews();
            updateFightButton();
        });
    }

    private void updateListViews() {
        heroList.getItems().setAll(Game.context.getHeroesStatus());
        enemyList.getItems().setAll(Game.context.getEnemiesStatus());
    }

    // L'action du bouton change en fonction de l'état du jeu
    private void updateFightButton() {
        nombreFighters = heroList.getItems().size() + enemyList.getItems().size();
        checkHealerTurn();
        checkDefenseTurn();

        switch (Game.context.status) {
            case WINNING:
                manaButton.setVisible(true);
                arrowButton.setVisible(true);
                fightButton.setVisible(false);
                defenseButton.setVisible(false);
                break;
            case START_COMBAT:
                fightButton.setVisible(true);
                fightButton.setText("Lancer le combat !");
                fightButton.setOnAction( event -> {
                    updateListViews();
                    Game.context.startNextFighterTurn();
                    updateFightButton();
                } );
                break;
            case HERO_TURN:
                fightButton.setText("Attaque du héro...");
                fightButton.setOnAction( event -> {
                    indexEnemy = enemyList.getSelectionModel().getSelectedIndex();
                    if(indexEnemy < 0) {
                        indexEnemy = new Random().nextInt(enemyList.getItems().size());
                    }

                    Hero hero = Game.context.startHeroTurn(indexEnemy, false,false);

                    updateListViews();
                    Game.context.startNextFighterTurn();

                    updateImagesCombat(hero, null, false,false);
                    updateFightButton();
                } );
                break;
            case ENEMY_TURN:

                fightButton.setText("Attaque de l'ennemi...");
                fightButton.setOnAction( event -> {
                    // attaquer un hero aleatoirement choisi
                    int indexHero = new Random().nextInt(heroList.getItems().size());

                    Game.context.startEnemyTurn(indexHero);
                    Hero hero = null;
                    String heroName = heroList.getItems().get(indexHero).substring(0,
                            heroList.getItems().get(indexHero).indexOf("("));
                    switch (heroName) {
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
                    }

                    updateListViews();
                    Game.context.startNextFighterTurn();

                    updateImagesCombat(hero, hero, false,false);
                    updateFightButton();
                } );
                break;
            case END_GAME:
                fightButton.setDisable(true);
                break;

        }
        n++;

        if(n > nombreFighters) {
            n = 1;
            tour++;
        }
        round.setText("Round " + tour);

    }

    private void checkHealerTurn() {

        if(Game.context.currentFighter instanceof Healer) {
            if(((SpellCaster)Game.context.currentFighter).getManaPoints() > 0){
                healButton.setVisible(true);
            }
        } else {
            healButton.setVisible(false);
        }
    }

    private void checkDefenseTurn() {
        if(Game.context.currentFighter instanceof Hero) {
            defenseButton.setVisible(true);
        } else {
            defenseButton.setVisible(false);
        }
    }


    private void updateImagesCombat(Hero hero, Hero heroAttacked, boolean healAction,boolean defenseAction) {
        if(hero instanceof Healer) {

            heroIMG.setImage(new Image(getClass().getResource("/clemdcz/images/healer.png").toExternalForm()));

        } else if(hero instanceof Hunter) {

            heroIMG.setImage(new Image(getClass().getResource("/clemdcz/images/hunter.png").toExternalForm()));

        } else if(hero instanceof Mage) {

            heroIMG.setImage(new Image(getClass().getResource("/clemdcz/images/mage.png").toExternalForm()));

        } else if(hero instanceof Warrior) {

            heroIMG.setImage(new Image(getClass().getResource("/clemdcz/images/warrior512px.png").toExternalForm()));

        }

        if(defenseAction){
            combatText.setText("Le héro sélectionné se défendra");
            combatText.setTextFill(Color.BLUE);
        }
        else if(healAction) {
            combatText.setText(hero + " a fait des traitements");
            combatText.setTextFill(Color.GREEN);
        } else {
            ennemyIMG.setImage(new Image(getClass().getResource("/clemdcz/images/enemy.png").toExternalForm()));

            if (heroAttacked == null) {
                if(hero instanceof Hunter && ((Hunter) hero).getArrows() == -99) {
                    combatText.setText("Oops! Hunter n'a plus de flèches!");
                    combatText.setTextFill(Color.GRAY);
                } else {
                    combatText.setText(hero + " attaque l'ennemi ");
                    combatText.setTextFill(Color.BLUE);
                }

            } else {
                combatText.setText("L'ennemi attaque " + heroAttacked);
                combatText.setTextFill(Color.RED);
            }
        }

    }

}
