package ee.eduard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    private final boolean ai;
    private final boolean goFirst;
    private Logic logic;

    private final HashMap<Integer, Button> buttonMap = new HashMap<>();

    @FXML
    private Button back;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    public BoardController(boolean ai, boolean goFirst) {
        this.ai = ai;
        this.goFirst = goFirst;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logic = new Logic(ai, goFirst);

        populateMap();

        if (logic.isAi() && !logic.isFirstPlayer()) { // AI makes a move if it goes first and is  selected AI game
            buttonMap.get(logic.aiMove()).setText(logic.getCOMPUTER());
        }

        button1.setOnAction(event -> buttonPressed(button1.getAccessibleText()));
        button2.setOnAction(event -> buttonPressed(button2.getAccessibleText()));
        button3.setOnAction(event -> buttonPressed(button3.getAccessibleText()));
        button4.setOnAction(event -> buttonPressed(button4.getAccessibleText()));
        button5.setOnAction(event -> buttonPressed(button5.getAccessibleText()));
        button6.setOnAction(event -> buttonPressed(button6.getAccessibleText()));
        button7.setOnAction(event -> buttonPressed(button7.getAccessibleText()));
        button8.setOnAction(event -> buttonPressed(button8.getAccessibleText()));
        button9.setOnAction(event -> buttonPressed(button9.getAccessibleText()));

        back.setOnAction(event -> goBack());
    }

    private void goBack() {
        // Goes to main screen
        Stage scene = (Stage) back.getScene().getWindow();
        scene.close();
        try {
            new MainApp().mainScreen();
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong!");
        }
    }

    private void makeMove(int cell) {
        if (logic.checkCell(cell)) { // Checks if cell is empty
            if (logic.isAi()) { // Checks if is selected game with AI
                logic.setOnBoardAIGame(logic.cellToPair(cell));
                buttonMap.get(cell).setText(logic.getHUMAN());
                if (!checkIfGameOver(logic.gameOver())) {
                    buttonMap.get(logic.aiMove()).setText(logic.getCOMPUTER());
                    checkIfGameOver(logic.gameOver());
                }
            } else {
                logic.setOnBoard(logic.cellToPair(cell));
                if (logic.isFirstPlayer()) { // Alternating between players
                    buttonMap.get(cell).setText("X");
                    logic.setFirstPlayer(false);
                } else {
                    buttonMap.get(cell).setText("O");
                    logic.setFirstPlayer(true);
                }
                checkIfGameOver(logic.gameOver());
            }
        }
    }

    private boolean checkIfGameOver(String result) {
        // Checks if somebody won
        if (result != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Result");
            alert.setHeaderText(null);
            if (!result.equals("tie")) {
                alert.setContentText("Congratulations, " + result + " has won!");
            } else {
                alert.setContentText("Nobody won!");
            }

            alert.showAndWait();
            goBack();
            return true;
        }
        return false;
    }

    private void buttonPressed(String cell) {
        makeMove(Integer.parseInt(cell));
    }


    private void populateMap() {
        // Populating map
        buttonMap.put(1, button1);
        buttonMap.put(2, button2);
        buttonMap.put(3, button3);
        buttonMap.put(4, button4);
        buttonMap.put(5, button5);
        buttonMap.put(6, button6);
        buttonMap.put(7, button7);
        buttonMap.put(8, button8);
        buttonMap.put(9, button9);
    }
}
