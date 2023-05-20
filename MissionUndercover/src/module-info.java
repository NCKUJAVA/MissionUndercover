module MissionUndercover {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	
	opens RoomChoice to javafx.graphics, javafx.fxml, javafx.base;
	opens Room to javafx.graphics, javafx.fxml, javafx.base;


	
	opens start_page to javafx.graphics, javafx.fxml;
	opens sign_up_page to javafx.graphics, javafx.fxml;
	opens email_verification_page to javafx.graphics, javafx.fxml;
}
