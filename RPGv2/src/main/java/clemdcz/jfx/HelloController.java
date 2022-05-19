package clemdcz.jfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.IOException;

public class HelloController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button BackToMenu;

    @FXML
    private Button QuitButton;

    @FXML
    private Button RulesButton;

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void LeaveGame(ActionEvent event) {
        Stage stage = (Stage) QuitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void GoToRules(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("rule-window.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Règles du RPG");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private ImageView MenuImage;

    @FXML
    private ImageView ImageRule1;

    @FXML
    private ImageView ImageRule2;

    @FXML
    private Button ButtonToGame;


    @FXML
    void GoToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("choix1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("RPG");
        stage.setScene(scene);
        stage.show();
    }


}
