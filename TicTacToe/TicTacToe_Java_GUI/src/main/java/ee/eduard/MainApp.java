package ee.eduard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    private final Scene mainScene = new Scene((Parent) loadControls("Main.fxml", new MainController()));

    private Stage stage = new Stage();

    public MainApp() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        mainScreen();
    }

    public void mainScreen() {
        stage.setTitle("TicTacToe");
        stage.setScene(mainScene);
        stage.show();
    }

    public void onePlayerScreen() throws IOException {
        // Loads board with AI
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Go first?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        Scene onePlayerScene;
        if (alert.getResult() == ButtonType.YES) {
            onePlayerScene = new Scene((Parent) loadControls("Board.fxml", new BoardController(true, true)));
        } else if (alert.getResult() == ButtonType.NO) {
            onePlayerScene = new Scene((Parent) loadControls("Board.fxml", new BoardController(true, false)));
        } else {
            onePlayerScene = mainScene;
        }
        stage.setTitle("Versus Computer");
        stage.setScene(onePlayerScene);
        stage.show();
    }

    public void multiplayerScreen() throws IOException {
        // Loads board for two players
        Scene multiplayer = new Scene((Parent) loadControls("Board.fxml", new BoardController(false, true)));
        stage.setTitle("Multiplayer");
        stage.setScene(multiplayer);
        stage.show();
    }

    private Node loadControls(String fxml, Initializable controller) throws IOException {
        // Loads fxml file
        URL resource = getClass().getResource(fxml);
        if (resource == null) {
            throw new IllegalArgumentException(fxml + " not found");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        fxmlLoader.setController(controller);

        return fxmlLoader.load();
    }
}
