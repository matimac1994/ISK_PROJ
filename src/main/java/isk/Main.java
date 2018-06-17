package isk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage rootStage;
    private static Scene mainScene;
    private static Integer appWidth = 800;
    private static Integer appHeight = 800;

    @Override
    public void start(Stage primaryStage) throws Exception{
        rootStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/main-view.fxml"));
        primaryStage.setTitle("ISK Cracker");
        mainScene = new Scene(root, appWidth, appHeight);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {

        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getRootStage() {
        return rootStage;
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static Integer getAppWidth() {
        return appWidth;
    }

    public static Integer getAppHeight() {
        return appHeight;
    }
}
