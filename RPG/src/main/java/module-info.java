module clemdcz.rpg {
    requires javafx.controls;
    requires javafx.fxml;


    opens clemdcz.rpg to javafx.fxml;
    exports clemdcz.rpg;
}