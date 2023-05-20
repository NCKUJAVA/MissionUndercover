module MissionUndercover {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    
    opens application to javafx.graphics, javafx.fxml;
    opens mainwindow_page to javafx.graphics, javafx.fxml;
    opens maininfo_page to javafx.graphics, javafx.fxml;
    opens mainshop_page to javafx.graphics, javafx.fxml;


	
	opens start_page to javafx.graphics, javafx.fxml;
	opens sign_up_page to javafx.graphics, javafx.fxml;
	opens email_verification_page to javafx.graphics, javafx.fxml;
}
