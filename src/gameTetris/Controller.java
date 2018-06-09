package gameTetris;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private MyGrid grid;

    @FXML private Label score;
    @FXML private Button startButton;
    @FXML private Button pauseButton;
    @FXML private Button resetButton;
    @FXML private BorderPane mainPane;

    @FXML
    public void initialize(URL arg0, ResourceBundle res) {
        MyGrid gridPane = new MyGrid();
        grid = gridPane;
        mainPane.setCenter(gridPane);
    }
    @FXML
    private void handleStartButton(ActionEvent event) {

    }

    @FXML
    private void handlePauseButton(ActionEvent event) {

    }

    @FXML
    private void handleResetButton(ActionEvent event) {
        //TODO: stop and reset the game
        grid.reset();
    }
}
