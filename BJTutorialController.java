package com.example.finalcis;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * CIS254 Final
 * Description: in this program you will be able to play a game of blackjack
 * the interface will start at the main menu where you will be able to either
 * see the rules, play the game, or quit the program all together
 * @author Gabriel Salinas-Carter
 * @since 005/05/25
 */

public class BJTutorialController {

    @FXML
    /**
     * Changes scene from tutorial to main menu
     * @param event
     * @throws IOException
     */
    protected void onGoBackClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("blackjack-mainmenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
