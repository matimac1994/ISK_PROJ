package isk.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends Controller implements Initializable{

    @FXML
    private Label labelField;

    public void backToMain(ActionEvent actionEvent) throws IOException, InterruptedException {
        backToMainScene();
    }

    public SettingsController() {

    }

    public void showInterface(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            labelField.setText(crackService.listOfWebInterfaces().trim());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
