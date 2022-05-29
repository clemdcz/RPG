package clemdcz.jfx;

import clemdcz.rpg.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Choix2Controller {

    private Stage stage;
    private Scene scene;
    private int nombreHero;
    private List<Hero> heroes;

    @FXML
    private Button BoutonNext2;

    @FXML
    void Suivant(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("game-window.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("RPG");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private ComboBox<String> combobox;

    public void initialize(){
        ObservableList<String> list = FXCollections.observableArrayList("Healer","Hunter","Mage","Warrior");
        combobox.setItems(list);
        combobox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (combobox.getSelectionModel().isEmpty()) {
                    valider2.setDisable(true);
                } else {
                    valider2.setDisable(false);
                }
            }
        });

        valider2.setDisable(true);
        heroes = new ArrayList<>();
    }


    @FXML
    private Label labelChoix2;

    public void displayChoiceHero(int nombreHero){
        labelChoix2.setText("Veuillez maintenant choisir le type de vos "+ nombreHero + " héro(s) !");

        // on passe le nombreHero depuis Choix1Controller à cet controleur
        this.nombreHero = nombreHero;
    }

    int nombreValider;

    @FXML
    private Button valider2;

    @FXML
    private Label labelchoix;

    @FXML
    void ValiderPlus(ActionEvent event) {
        nombreValider++;
        for (int i=0; i <= nombreHero; i++){
            if (i==nombreValider){
                labelchoix.setText(i + "/" + nombreHero +" héro(s) choisi !");

                Hero hero = Game.generateHero(combobox.getSelectionModel().getSelectedItem());
                heroes.add(hero);
            }

        }
        if (nombreValider == nombreHero){
            valider2.setDisable(true);
            BoutonNext2.setVisible(true);
            Game.heroes = heroes;
        }
    }

}