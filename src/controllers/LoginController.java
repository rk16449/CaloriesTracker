package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* Import javafx, java, mainPackage */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController implements Initializable {
	@FXML
	private Text actiontarget;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField userField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
	protected void handleBtnLogin(ActionEvent event) throws IOException {
		System.out.println("Login btn pressed");
	}
	
	@FXML
	protected void handleBtnCreate(ActionEvent event) {
		System.out.println("Create new account btn pressed");
	}
}
