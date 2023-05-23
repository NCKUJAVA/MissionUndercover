module MissionUndercover {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	
	opens RoomChoice to javafx.graphics, javafx.fxml, javafx.base;
	opens Room to javafx.graphics, javafx.fxml, javafx.base;

    
    opens application to javafx.graphics, javafx.fxml;
    opens mainwindow_page to javafx.graphics, javafx.fxml;
    opens maininfo_page to javafx.graphics, javafx.fxml;
    opens mainshop_page to javafx.graphics, javafx.fxml;

	
	opens start_page to javafx.graphics, javafx.fxml;
	opens sign_up_page to javafx.graphics, javafx.fxml;
	opens authentification_question_page to javafx.graphics, javafx.fxml;
	opens forgot_page to javafx.graphics, javafx.fxml;
	opens change_password_page to javafx.graphics, javafx.fxml;
}
