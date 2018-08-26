package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* Import javafx, java, mainPackage */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController extends BaseLoginController implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
	protected void handleBtnLogin(ActionEvent event) throws IOException {
		System.out.println("Login btn pressed");
		// Authenticate here
		
		
		// Load next page
		loadFXML(event, "/view/dashboard.fxml");
	}
	
	@FXML
	protected void handleBtnCreate(ActionEvent event) throws IOException {
		System.out.println("Create new account btn pressed");
		
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/createAccount.fxml"));
		Scene scene = new Scene(parent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("Create Account");
		appStage.setWidth(944);
		appStage.setHeight(600);
		appStage.show();
	}
}
