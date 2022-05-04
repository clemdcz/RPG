package clemdcz.jfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class Choix1Controller {

    private Stage stage;
    private Scene scene;

    @FXML
    private Button BoutonNext;

    @FXML
    private TextField ChoixNombreHero;

    @FXML
    void Suivant(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("choix2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("RPG");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private Label LabelHero;

    int nombreHero;

    @FXML
    void validation(ActionEvent event) {
        try{
            nombreHero = Integer.parseInt(ChoixNombreHero.getText());

            if (nombreHero > 0 && nombreHero < 7){
                LabelHero.setText(" Félications ! Vous avez choisi "+nombreHero+" héro(s)");
                LabelHero.setStyle("-fx-text-fill: green;");
                BoutonNext.setVisible(true);
            }
            else{
                LabelHero.setText("Veuillez choisir un nombre de héros entre 1 et 6");
                LabelHero.setStyle("-fx-text-fill: red;");
            }
        }
        catch (NumberFormatException e){
            LabelHero.setText("Veuiller entrer un chiffre entre 1 et 6");
            LabelHero.setStyle("-fx-text-fill: red;");
        }
        catch (Exception e){
            LabelHero.setText("erreur");
            LabelHero.setStyle("-fx-text-fill: red;");
        }
    }

}
