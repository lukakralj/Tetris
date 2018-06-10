package gameTetris;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private GameManager gameManager;
    private Thread currentThread;

    @FXML private Label score;
    @FXML private Button startButton;
    @FXML private Button pauseButton;
    @FXML private Button resetButton;
    @FXML private BorderPane mainPane;

    @FXML
    public void initialize(URL arg0, ResourceBundle res) {
        MyGrid gridPane = new MyGrid();
        gameManager = new GameManager(Main.getMainScene(), gridPane);
        mainPane.setCenter(gridPane);
    }

    @FXML
    private void handleStartButton(ActionEvent event) {
        if (currentThread != null) {
            gameManager.play();
        }
        else {
            currentThread = new Thread(gameManager);
            currentThread.start();
        }

    }

    @FXML
    private void handlePauseButton(ActionEvent event) {
        if (currentThread != null) {
            gameManager.pause();
        }
    }

    @FXML
    private void handleResetButton(ActionEvent event) {
        if (currentThread != null) {
            gameManager.stopGame();
            try {
                currentThread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentThread = null;
        }
        gameManager.resetGame();
    }
}
