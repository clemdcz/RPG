package clemdcz.rpg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

}
