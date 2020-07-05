package ee.eduard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button onePlayer;

    @FXML
    private Button twoPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onePlayer.setOnAction(event -> aiGame());
        twoPlayer.setOnAction(event -> multiplayer());
    }

    private void closeWindow(Button button) {
        // Closes current window
        Stage scene = (Stage) button.getScene().getWindow();
        scene.close();
    }

    private void aiGame() {
        // Selects game with AI
        closeWindow(onePlayer);
        try {
            new MainApp().onePlayerScreen();
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong!");
        }
    }

    private void multiplayer() {
        // Selects game for two players
        closeWindow(twoPlayer);
        try {
            new MainApp().multiplayerScreen();
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong!");
        }
    }
}
