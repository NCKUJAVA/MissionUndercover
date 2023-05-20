module MissionUndercover {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens RoomChoice to javafx.graphics, javafx.fxml, javafx.base;
	opens Room to javafx.graphics, javafx.fxml, javafx.base;

}
