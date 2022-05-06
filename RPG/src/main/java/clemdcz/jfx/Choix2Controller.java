package clemdcz.jfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class Choix2Controller {

    private Stage stage;
    private Scene scene;

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
    }


    @FXML
    private Label labelChoix2;

    public void displayChoiceHero(int nombreHero){
        labelChoix2.setText("Veuillez maintenant choisir le type de vos "+ nombreHero + " héro(s) !");
    }

    int nombreValider;

    @FXML
    private Button valider2;

    @FXML
    private Label labelchoix;

    @FXML
    void ValiderPlus(ActionEvent event) {
        nombreValider++;
        for (int i=0; i <= 5; i++){
            if (i==nombreValider){
                labelchoix.setText(i + "/5 héro(s) choisi !");
            }
        }
        if (nombreValider == 5){
            valider2.setDisable(true);
            BoutonNext2.setVisible(true);
        }
    }
    //// 5 est à remplacer par la valeur nombreHero du controlleur précédent

}