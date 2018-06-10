package gameTetris;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Scene scene;
    private MyGrid grid;
    private GameManager gameManager;
    private Thread currentThread;
    private boolean playing;

    @FXML private Label score;
    @FXML private Button startButton;
    @FXML private Button pauseButton;
    @FXML private Button resetButton;
    @FXML private BorderPane mainPane;

    @FXML
    public void initialize(URL arg0, ResourceBundle res) {
        MyGrid gridPane = new MyGrid();
        scene = Main.getMainScene();
        grid = gridPane;
        mainPane.setCenter(gridPane);
        playing = false;
    }

    @FXML
    private void handleStartButton(ActionEvent event) {
        if (playing) {
            gameManager.play();
        }
        else {
            gameManager = new GameManager(scene, grid);
            currentThread = new Thread(gameManager);
            currentThread.start();
            playing = true;
        }

    }

    @FXML
    private void handlePauseButton(ActionEvent event) {
        gameManager.pause();
    }

    @FXML
    private void handleResetButton(ActionEvent event) {
        if (playing) {
            playing = false;
            gameManager.resetGame();
            try {
                currentThread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gameManager.resetGame();
    }
}
