package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BaseLoginController {
	@FXML
	protected Text actiontarget;
	@FXML
	protected PasswordField passwordField;
	@FXML
	protected TextField userField;
	
	protected void loadFXML(ActionEvent event, String fxml) throws IOException {
		// Go to main program dashboard
		Parent parent = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(parent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("Calories Tracker");
		appStage.setWidth(944);
		appStage.setHeight(600);
		appStage.show();
	}
}
