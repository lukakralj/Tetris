package gameTetris;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the UI components and calls appropriate methods.
 *
 * @author Luka Kralj
 * @version 10 June 2018
 *
 * TODO: dissable buttons when appropriate.
 */
public class Controller implements Initializable {
    private GameManager gameManager;
    private Thread currentThread;

    @FXML private Label score;
    @FXML private Button startButton;
    @FXML private Button pauseButton;
    @FXML private Button resetButton;
    @FXML private BorderPane mainPane;

    /**
     * Initialise the grid of the game and add it to the GUI.
     *
     * @see Initializable
     */
    @FXML
    public void initialize(URL arg0, ResourceBundle res) {
        MyGrid gridPane = new MyGrid();
        gameManager = new GameManager(Main.getMainScene(), gridPane);
        mainPane.setCenter(gridPane);
    }

    /**
     * This method is called when Start button is pressed.
     * It starts a new game or continues the game currently played.
     *
     * @param event
     */
    @FXML
    private void handleStartButton(ActionEvent event) {
        if (currentThread != null) {
            gameManager.resume();
        }
        else {
            gameManager.resetGame();
            currentThread = new Thread(gameManager);
            currentThread.start();
        }

    }

    /**
     * This method is called when pause button is pressed.
     * It paused the game currently played.
     *
     * @param event
     */
    @FXML
    private void handlePauseButton(ActionEvent event) {
        if (currentThread != null) {
            gameManager.pause();
        }
    }

    /**
     * This method is called when reset buttons is pressed.
     * It stops the current thread and clears the grid.
     *
     * @param event
     */
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
