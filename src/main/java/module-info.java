module clemdcz.jfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens clemdcz.jfx to javafx.fxml;
    exports clemdcz.jfx;
}