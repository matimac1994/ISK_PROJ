package isk.controller;

import isk.Main;
import isk.service.CrackService;
import isk.service.CrackServiceImpl;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Controller {

    protected CrackService crackService;

    public Controller() {
        crackService = new CrackServiceImpl();
    }

    protected void setSceneToRootStage(Parent parent){
        Main.getRootStage().setScene(new Scene(parent,Main.getAppWidth(), Main.getAppHeight()));
    }

    protected void backToMainScene(){
        Main.getRootStage().setScene(Main.getMainScene());
    }
}
