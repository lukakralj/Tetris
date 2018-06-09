package gameTetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainLayout.fxml"));
        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add(getClass().getResource("styles/tetrisStyle.css").toString());
        stage.setScene(scene);
        stage.setTitle("Tetris");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}