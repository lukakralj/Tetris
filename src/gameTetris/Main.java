package gameTetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class starts the application and loads the main scene.
 *
 * @author Luka Kralj
 * @version 9 June 2018
 */
public class Main extends Application {
    private static Scene mainScene;

    /**
     * @see Application
     */
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

    /**
     *
     * @return Main scene of the game.
     */
    public static Scene getMainScene() {
        return mainScene;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}