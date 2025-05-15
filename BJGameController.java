package com.example.finalcis;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.event.ActionEvent;
/**
 * CIS254 Final
 * Description: in this program you will be able to play a game of blackjack
 * the interface will start at the main menu where you will be able to either
 * see the rules, play the game, or quit the program all together
 * @author Gabriel Salinas-Carter
 * @since 005/05/25
 */
public class BJGameController {
    private BlackJackApp game;

    @FXML private FlowPane housePane;
    @FXML private FlowPane playerPane;
    @FXML private Label dealerScore;
    @FXML private Label playerScore;

    /**
     * popup error message
     * @param message message displayed when alert shows
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Oops!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    /**
     * handles stand button click
     * shows error message if game hasn't started
     * on players turn, keeps current cards, completes dealers play, shows final hand, ends round
     */
    protected void onStandClick() {
        try {
            if (game == null) {
                showError("Please start the game first.");
                return;
            }
            if (game.isGameOver()) return;
            game.dealerPlay();
            renderHands();
            endRound();
        } catch (Exception e) {
            showError("Stand click failed");
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * handles hit button click
     * shows error message if game hasn't started
     * on players turn, draws card, ends round
     */
    protected void onHitClick() {
        try {
            if (game == null) {
                showError("Please start the game first.");
                return;
            }
            if (game.isGameOver()) return;

            game.playerHit();
            renderHands();
            if (game.isGameOver()) endRound();
        } catch (Exception e) {
            showError("Hit click failed");
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * changes scene to main menu when clicked
     */
    protected void onGoBackClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("blackjack-mainmenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * starts game when clicked
     */
    protected void onStartClick() {
        try {
            game = new BlackJackApp(); //creates new game
            game.startGame(); // starts new round
            renderHands(); //shows cards on screen
            playerScore.setText(": " + game.getPlayerSum());
            dealerScore.setText(": " + game.getDealerSum());
        } catch (Exception e) {
            showError("Failed to start game");
            e.printStackTrace();
        }
    }

    /**
     * Renders card image and attaches tooltips to cards
     */
    private void renderHands() {
        playerPane.getChildren().clear();//clears cards from playerPane
        housePane.getChildren().clear();//clears cards from housePane

        int index = 0;
        for (ImageView view : game.getPlayerCardImages()) {
            addCardClickHandler(view, game.getPlayerHand().get(index++).toString());
            playerPane.getChildren().add(view);
        }

        index = 0;
        for (ImageView view : game.getDealerCardImages()) {
            String tooltipText;
            if (!game.isGameOver() && index == 0) {
                tooltipText = "Hidden";
            } else {
                tooltipText = game.getDealerHand().get(index) != null ? game.getDealerHand().get(index).toString() : "Hidden";
            }
            addCardClickHandler(view, tooltipText);//adds tooltip to cardPane
            housePane.getChildren().add(view);//adds card image to housePane
            index++;

            playerScore.setText("Player: " + game.getPlayerSum());//updates playerScore when card dealt
            dealerScore.setText("Dealer: " + game.getDealerSum());//updates dealScore when card dealt
        }
    }

    /**
     * allows you to see value of card when clicking on it
     * @param view
     * @param value
     */
    private void addCardClickHandler(ImageView view, String value) {
        Tooltip tip = new Tooltip(value);
        Tooltip.install(view, tip);//associates tooltip with image

        view.setOnMouseClicked(e -> {
            if (tip.isShowing()) {
                tip.hide();//hides tip when clicked
            } else {
                tip.show(view, e.getScreenX(), e.getScreenY());//shows tip when clicked
            }
        });
    }

    /**
     * Method to end the round, calculate the winner/loser, shows score in alert
     * and shows error message if pressing hit/stand without starting a new game
     */
    private void endRound() {
        int playerSum = game.getPlayerSum();//player total point
        int dealerSum = game.getDealerSum();//dealer total point
        String result;

        if (playerSum > 21) {
            result = "You Busted! Dealer Wins.";
        } else if (dealerSum > 21) {
            result = "Dealer Busted! You Win!";
        } else if (playerSum > dealerSum) {
            result = "You Win!";
        } else if (dealerSum > playerSum) {
            result = "Dealer Wins!";
        } else {
            result = "Push! It's a Tie.";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Round Result");
        alert.setHeaderText(result);
        alert.setContentText("Your total: " + playerSum + "\nDealer's total: " + dealerSum);
        alert.showAndWait();
        game = null; //Error pops up if trying to press hit/stand after game ends
    }
}
