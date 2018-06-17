package isk.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MainController extends Controller {

    public void handleWepPasswordBtn(ActionEvent actionEvent) throws IOException {
        java.net.URL url = getClass().getResource("/wep-password-view.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        Parent parent = loader.load();
        setSceneToRootStage(parent);
    }

    @FXML
    public void handleSettingsBtn(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/settings-view.fxml"));
        setSceneToRootStage(parent);
    }
}
