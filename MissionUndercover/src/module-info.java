module MissionUndercover {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    opens application to javafx.graphics, javafx.fxml;
    opens mainwindow_page to javafx.graphics, javafx.fxml;
    opens maininfo_page to javafx.graphics, javafx.fxml;
    opens mainshop_page to javafx.graphics, javafx.fxml;
}
