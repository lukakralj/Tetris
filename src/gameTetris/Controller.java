package gameTetris;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class Controller {
    @FXML private Label score;
    @FXML private Button startButton;
    @FXML private Button pauseButton;
    @FXML private Button resetButton;

    @FXML
    private void handleStartButton(ActionEvent event) {
        System.out.println("ello");
    }

    @FXML
    private void handlePauseButton(ActionEvent event) {

    }

    @FXML
    private void handleResetButton(ActionEvent event) {

    }
}
