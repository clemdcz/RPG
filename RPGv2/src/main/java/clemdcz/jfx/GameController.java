package clemdcz.jfx;

import clemdcz.rpg.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.Random;

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

        healButton.setOnAction( event -> {
            int indexHero = heroList.getSelectionModel().getSelectedIndex();
            if(indexHero < 0) {
                indexHero = new Random().nextInt(heroList.getItems().size());
            }
            Hero hero = Game.context.startHeroTurn(indexHero, true);

            updateListViews();
            Game.context.startNextFighterTurn();

            updateImagesCombat(hero, null, true);
            updateFightButton();

        } );
    }

    private void updateListViews() {
        heroList.getItems().setAll(Game.context.getHeroesStatus());
        enemyList.getItems().setAll(Game.context.getEnemiesStatus());
    }

    // L'action du bouton change en fonction de l'état du jeu
    private void updateFightButton() {
        nombreFighters = heroList.getItems().size() + enemyList.getItems().size();
        checkHealerTurn();
        switch (Game.context.status) {
            case START_COMBAT:
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

                    Hero hero = Game.context.startHeroTurn(indexEnemy, false);

                    updateListViews();
                    Game.context.startNextFighterTurn();

                    updateImagesCombat(hero, null, false);
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

                    updateImagesCombat(hero, hero, false);
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
            healButton.setVisible(true);
        } else {
            healButton.setVisible(false);
        }
    }

    private void updateImagesCombat(Hero hero, Hero heroAttacked, boolean healAction) {
        if(hero instanceof Healer) {

            heroIMG.setImage(new Image(getClass().getResource("/clemdcz/images/healer.png").toExternalForm()));

        } else if(hero instanceof Hunter) {

            heroIMG.setImage(new Image(getClass().getResource("/clemdcz/images/hunter.png").toExternalForm()));

        } else if(hero instanceof Mage) {

            heroIMG.setImage(new Image(getClass().getResource("/clemdcz/images/mage.png").toExternalForm()));

        } else if(hero instanceof Warrior) {

            heroIMG.setImage(new Image(getClass().getResource("/clemdcz/images/warrior512px.png").toExternalForm()));

        }

        if(healAction) {
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
