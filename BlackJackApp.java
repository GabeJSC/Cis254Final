package com.example.finalcis;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class BlackJackApp {

    ArrayList<Card> deck; //Deck
    Random random = new Random();//Shuffle deck

    Card hiddenCard; //Dealer
    ArrayList<Card> dealerHand;
    int dealerSum;

    ArrayList<Card> playerHand; //Player
    int playerSum;

    private boolean gameOver = false;


    /**
     * gets player hand value
     * @return player hand value
     */
    public int getPlayerSum() {
        return playerSum;
    }

    /**
     * gets dealer hand value
     * @return dealer hand value
     */
    public int getDealerSum() {
        return dealerSum;
    }

    /**
     * shows playing card with a value and suit
     */
    public class Card{
        String value;
        String type;

        Card(String value, String type){
            this.value = value;
            this.type = type;
        }

        /**
         * string representation of card
         * @return card value and type
         */
        public String toString(){
            return value + "-" + type;
        }

        /**
         * gets value of card
         * @return point value
         */
        public int getValue() {
            if ("AJQK".contains(value)) { //A-J-Q-K
                if (value.equals("A")) {
                    return 11;
                } else {
                    return 10;
                }
            } else {
                return Integer.parseInt(value); //2-10
            }
        }
        /**
         * Checks to see if value of card is A
         * @return true if A
         */
        public boolean isAce() {
            return value == "A";
        }

        /**
         * card image path
         * @return path to card image
         */
        public String getImagePath(){
            return "/cards/" + value + "-" + type.charAt(0) + ".png";
        }

    }

    /**
     * checks the value of hand and adjust for any aced
     * @param hand
     * @return Value of hand
     */
    private int handValue(ArrayList<Card> hand){ //track value of hand
        int sum = 0;
        int aceCount = 0;
        for (Card card : hand) {
            sum += card.getValue();
            if (card.isAce()) aceCount++;
        }
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }
        return sum;
    }

    /**
     * initializes and starts a new round
     */
    public void startGame(){
        gameOver = false;
        buildDeck();
        shuffleDeck();

        // Dealer setup
        dealerHand = new ArrayList<>();
        hiddenCard = deck.remove(deck.size() - 1);
        Card visibleCard = deck.remove(deck.size() - 1);
        dealerHand.add(visibleCard);

        // Player setup
        playerHand = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Card card = deck.remove(deck.size() - 1);
            playerHand.add(card);
        }

        // Recalculate hand values
        playerSum = handValue(playerHand);
        dealerSum = handValue(dealerHand);

        System.out.println("START GAME");
        System.out.println("Player Hand: " + playerHand);
        System.out.println("Dealer Hand (1 visible): " + dealerHand);
    }


    /**
     * creates deck of cards
     */
    public void buildDeck(){
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types= {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++) {
            for(int j = 0; j < values.length; j++){
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }
    }

    /**
     * Shuffles deck to randomize cards
     */
    public void shuffleDeck(){
        for(int i = 0; i < deck.size(); i++){
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }
    }

    /**
     * deals card to player, adjusts value of hand, and ends game if bust
     */
    public void playerHit() {
        Card card = deck.remove(deck.size() - 1);
        playerHand.add(card);
        playerSum = handValue(playerHand);

        System.out.println("Player hits: " + card);
        System.out.println("Player hand: " + playerHand);
        System.out.println("Player sum: " + playerSum);

        if (playerSum > 21) {
            gameOver = true;
            System.out.println("Player Busts! Dealer wins...");
        }
    }

    /**
     * shows hidden card and plays dealer hand
     */
    public void dealerPlay(){
        dealerHand.add(hiddenCard); // Shows Hidden Card
        dealerSum = handValue(dealerHand);

        System.out.println("Dealer shows hidden card: " + hiddenCard);
        System.out.println("Dealer Hand: " + dealerHand);
        System.out.println("Dealer sum: " + dealerSum);

        while (dealerSum <17) {
            Card card = deck.remove(deck.size() - 1);
            dealerHand.add(card);
            dealerSum = handValue(dealerHand);

            System.out.println("dealer Hits: " + card);
            System.out.println("dealer sum : " + dealerSum);
        }
        checkWinner();
    }

    /**
     * Compares dealer and players hand to see who wins
     */
    private void checkWinner(){
        gameOver = true;
        if (playerSum > 21) {
            System.out.println("Player Busts! Dealer wins...");
        }
        else if (dealerSum > 21) {
            System.out.println("Dealer Busts! Player wins");
        }
        else if (playerSum > dealerSum) {
            System.out.println("Player wins");
        }
        else if (dealerSum > playerSum) {
            System.out.println("Dealer wins");
        }
    }

    /**
     * shows card image for player hand
     * @return list of ImageView objects
     */
    public ArrayList<ImageView> getPlayerCardImages() {
        ArrayList<ImageView> views = new ArrayList<>();
        for (Card card : playerHand) {
            views.add(createCardImageView(card));
        }
        return views;
    }

    /**
     * shows cards for dealer and hides first card if game is not over
     * @return ImageView objects
     */
    public ArrayList<ImageView> getDealerCardImages() {// Render dealer cards as images
        ArrayList<ImageView> views = new ArrayList<>();

        if (!gameOver) { // Show card back for hidden card if game is not over
            views.add(createCardBackImageView());
        } else {
            views.add(createCardImageView(hiddenCard)); // Reveal hidden card
        }
        for (Card card : dealerHand) {//show all other dealer cards
            views.add(createCardImageView(card));
        }
        return views;
    }

    /**
     * creates image of the front of the cards
     * @param card
     * @return image of front of card
     */
    private ImageView createCardImageView(Card card) {//loads card images from resource path
        try {
            String path = card.getImagePath(); // Example: "/cards/A-S.png"
            Image image = new Image(getClass().getResourceAsStream(path));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(120);
            return imageView;
        } catch (Exception e) {
            return new ImageView(); // Return blank image view
        }
    }

    /**
     * created image of back of cards for dealers hidden card
     * @return image of back of hidden card
     */
    private ImageView createCardBackImageView() {
        try {
            Image image = new Image(getClass().getResourceAsStream("/cards/back.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(120);
            return imageView;
        } catch (Exception e) {
            return new ImageView(); // fallback if image fails to load
        }
    }

    /**
     * returns if game is over
     * @return true if game over, false if not
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * returns list of cards in players hand
     * @return list of card objects
     */
    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    /**
     * returns dealers hand and hidden card if game is over
     * @return list of card objects
     */
    public ArrayList<Card> getDealerHand() {
        ArrayList<Card> fullHand = new ArrayList<>();
        if (gameOver) {
            fullHand.add(hiddenCard);
            fullHand.addAll(dealerHand);
        } else {
            fullHand.add(null); // placeholder for hidden card
            fullHand.addAll(dealerHand);
        }
        return fullHand;
    }
}

