module clemdcz.jfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens clemdcz.jfx to javafx.fxml;
    exports clemdcz.jfx;
}