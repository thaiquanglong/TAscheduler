module TAscheduler {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens application to javafx.controls, javafx.graphics, javafx.fxml;
}
