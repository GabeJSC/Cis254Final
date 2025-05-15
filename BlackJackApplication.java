package com.example.finalcis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Parent;

/**
 * CIS254 Final
 * Description: in this program you will be able to play a game of blackjack
 * the interface will start at the main menu where you will be able to either
 * see the rules, play the game, or quit the program all together
 * @author Gabriel Salinas-Carter
 * @since 005/05/25
 */
public class BlackJackApplication extends Application {
    @Override
    /**
     * Start of program
     * starts at main menu where you can choose what you want to do
     */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/finalcis/blackjack-mainmenu.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Welcome to BlackJack!");
        stage.show();
    }

    /**
     * launches program
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}
