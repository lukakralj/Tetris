package gameTetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private static Scene mainScene;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainLayout.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles/tetrisStyle.css").toString());
        mainScene = scene;

        stage.setScene(scene);
        stage.setTitle("Tetris");
        stage.setResizable(false);
        stage.show();
    }

    public static Scene getMainScene() {
        return mainScene;
    }


    public static void main(String[] args) {
        launch(args);
    }


}