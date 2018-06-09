package gameTetris;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.LongAccumulator;

public class Controller implements Initializable {
    private static final int CELL_DIMENS = 15;
    @FXML private Label score;
    @FXML private Button startButton;
    @FXML private Button pauseButton;
    @FXML private Button resetButton;
    @FXML private BorderPane mainPane;

    @FXML
    public void initialize(URL arg0, ResourceBundle res) {
        TilePane gridPane = new TilePane();
        gridPane.setVgap(1);
        gridPane.setHgap(1);
        gridPane.setPrefColumns(20);
        gridPane.setPrefRows(40);
        for (int col = 0; col < 20; col++) {
            for (int row = 0; row < 40; row++) {
                Label label = new MyLabel(col, row);
                label.setMinSize(CELL_DIMENS, CELL_DIMENS);
                label.setMaxSize(CELL_DIMENS, CELL_DIMENS);
                label.setStyle("-fx-background-color: white;");
                gridPane.getChildren().add(label);
            }
        }
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

    }
}
