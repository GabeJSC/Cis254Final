package com.example.finalcis;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
/**
 * CIS254 Final
 * Description: in this program you will be able to play a game of blackjack
 * the interface will start at the main menu where you will be able to either
 * see the rules, play the game, or quit the program all together
 * @author Gabriel Salinas-Carter
 * @since 005/05/25
 */
public class BJMainMenuController {

    /**
     * Confirmation message to confirm quit of program
     * @param ConfirmMsg
     * @return
     */
    private boolean generateConfirmationAlert(String ConfirmMsg) {
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, ConfirmMsg, yesButton, noButton);
            alert.setTitle("Confirm");
            alert.setContentText(ConfirmMsg);
            alert.showAndWait();
            return alert.getResult() == yesButton;
    }
    @FXML
    /**
     * Changes scene so user can play BlackJack
     */
    private void onPlayGameClick (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("blackjack-game.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    /**
     * Ends the program
     */
    private void onQuitClick (ActionEvent event) throws IOException {
            if (generateConfirmationAlert("Are you sure you want to quit?")) {
                Platform.exit(); // Ends program if yes is clicked
            }
        }
    @FXML
    /**
     * Changes scene to the tutorial so user can see how to play BlackJack
     */
    private void onTutorialClick (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("blackjack-tutorial.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static class BJGameController extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {

        }
    }
}

