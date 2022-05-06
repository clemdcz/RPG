package clemdcz.jfx;

import clemdcz.rpg.Enemy;
import clemdcz.rpg.Game;
import clemdcz.rpg.Hero;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class GameController {

    @FXML
    ListView<String> heroList;
    @FXML
    ListView<String> enemyList;
    @FXML
    Button fightButton;

    // "initialize()" est appelé par JavaFX à l'affichage de la fenêtre
    @FXML
    public void initialize() {
        Game.playGame();
        updateListViews();
        updateFightButton();
    }

    private void updateListViews() {
        heroList.getItems().setAll(Game.context.getHeroesStatus());
        enemyList.getItems().setAll(Game.context.getEnemiesStatus());
    }

    // L'action du bouton change en fonction de l'état du jeu
    private void updateFightButton() {
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
                    Game.context.startHeroTurn();
                    updateListViews();
                    Game.context.startNextFighterTurn();
                    updateFightButton();
                } );
                break;
            case ENEMY_TURN:
                fightButton.setText("Attaque de l'ennemi...");
                fightButton.setOnAction( event -> {
                    Game.context.startEnemyTurn();
                    updateListViews();
                    Game.context.startNextFighterTurn();
                    updateFightButton();
                } );
                break;
            case END_GAME:
                fightButton.setDisable(true);
                break;
        }
    }

}
