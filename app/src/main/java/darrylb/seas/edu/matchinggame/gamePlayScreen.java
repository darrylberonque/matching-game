package darrylb.seas.edu.matchinggame;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import java.util.HashSet;
import java.util.Random;
import java.util.Arrays;


public class gamePlayScreen extends AppCompatActivity {
    GridView cards;
    TextView moves;
    TextView matches;
    TextView difficulty;

    String difficultyChosen;
    int numMoves = 0;
    int matchesRemaining;

    int numCards;
    int clickCounter = 0;

    ImageView[] lastTwoClicked = new ImageView[2];
    int[] lastTwoClickedPositions = new int[2];
    private int[] imageArray = {R.drawable.two_of_clubs, R.drawable.two_of_diamonds, R.drawable.two_of_hearts, R.drawable.two_of_spades,
            R.drawable.three_of_clubs, R.drawable.three_of_diamonds, R.drawable.three_of_hearts, R.drawable.three_of_spades,
            R.drawable.four_of_clubs, R.drawable.four_of_diamonds, R.drawable.four_of_hearts, R.drawable.four_of_spades,
            R.drawable.five_of_clubs, R.drawable.five_of_diamonds, R.drawable.five_of_hearts, R.drawable.five_of_spades,
            R.drawable.six_of_clubs, R.drawable.six_of_diamonds, R.drawable.six_of_hearts, R.drawable.six_of_spades,
            R.drawable.seven_of_clubs, R.drawable.seven_of_diamonds, R.drawable.seven_of_hearts, R.drawable.seven_of_spades,
            R.drawable.eight_of_clubs, R.drawable.eight_of_diamonds, R.drawable.eight_of_hearts, R.drawable.eight_of_spades,
            R.drawable.nine_of_clubs, R.drawable.nine_of_diamonds, R.drawable.nine_of_hearts, R.drawable.nine_of_spades,
            R.drawable.ten_of_clubs, R.drawable.ten_of_diamonds, R.drawable.ten_of_hearts, R.drawable.ten_of_spades,
            R.drawable.jack_of_clubs, R.drawable.jack_of_diamonds, R.drawable.jack_of_hearts, R.drawable.jack_of_spades,
            R.drawable.queen_of_clubs, R.drawable.queen_of_diamonds, R.drawable.queen_of_hearts, R.drawable.queen_of_spades,
            R.drawable.king_of_clubs, R.drawable.king_of_diamonds, R.drawable.king_of_hearts, R.drawable.king_of_spades,
            R.drawable.ace_of_clubs, R.drawable.ace_of_diamonds, R.drawable.ace_of_hearts, R.drawable.ace_of_spades, R.drawable.joker
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play_screen);

        // Receive chosen difficulty from previous activity and initialize game status variables
        Intent fromWelcomeScreen = getIntent();
        difficultyChosen = fromWelcomeScreen.getStringExtra("difficultyChosen");

        cards = findViewById(R.id.cardsGrid);
        moves = findViewById(R.id.movesText);
        matches = findViewById(R.id.remainingText);
        difficulty = findViewById(R.id.difficultyText);

        switch(difficultyChosen) {
            case "Easy":
                matchesRemaining = 3;
                numCards = 3;
                cards.setNumColumns(3);
                break;
            case "Medium":
                matchesRemaining = 6;
                numCards = 6;
                cards.setNumColumns(4);
                break;
            case "Hard":
                matchesRemaining = 12;
                numCards = 12;
                cards.setNumColumns(6);
                break;
        }

        moves.setText("Moves: " + Integer.toString(numMoves));
        difficulty.setText("Difficulty: " + difficultyChosen);
        matches.setText("Matches Remaining: \n" +  Integer.toString(matchesRemaining));

        // Randomly select the unique cards from the "deck" of images
        int[] uniqueCards = new int[numCards];
        Random r = new Random();
        HashSet<Integer> cardSet = new HashSet<>();
        int iter = 0;
        while(iter < uniqueCards.length) {
            int nextRandom = r.nextInt(51);
            if(!cardSet.contains(nextRandom)) {
                cardSet.add(nextRandom);
                uniqueCards[iter] = nextRandom;
                iter++;
            }
        }

        // Randomly select the positions of the cards in the grid view
        // and populate an image array of the chosen cards/positions
        int[] cardPositions = new int[2*numCards];
        for(int i = 0; i < cardPositions.length; i++) {
            cardPositions[i] = i;
        }

        HashSet<Integer> positionSet = new HashSet<>();
        int posIter = 0;
        while(posIter < cardPositions.length) {
            int nextRandom = r.nextInt(2*numCards);
            if(!positionSet.contains(nextRandom)) {
                positionSet.add(nextRandom);
                cardPositions[posIter] = nextRandom;
                posIter++;
            }
        }

        final int[] chosenImages = new int[2*uniqueCards.length];
        for(int i = 0; i < chosenImages.length; i++) {
            if(i >= numCards){
                chosenImages[cardPositions[i]] = imageArray[uniqueCards[i-numCards]];
            } else {
                chosenImages[cardPositions[i]] = imageArray[uniqueCards[i]];
            }
        }

        // Setup the adapter and grid view item click listener
        CardsAdapter cardAdapter = new CardsAdapter(this, 2*numCards);
        cards.setAdapter(cardAdapter);

        cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ImageView image = (ImageView) view;
                image.setImageResource(chosenImages[i]);
                image.setScaleType(ScaleType.CENTER_INSIDE);

                // Handles game display depending on the number of clicks and
                // the images clicked
                if(clickCounter == 0 && image.isEnabled()) {
                    image.setEnabled(false);
                    lastTwoClicked[clickCounter] = image;
                    lastTwoClickedPositions[clickCounter] = i;
                    clickCounter++;
                } else if(clickCounter == 1 && image.isEnabled()) {
                    if(lastTwoClickedPositions[0] != i) {
                        lastTwoClicked[clickCounter] = image;
                        lastTwoClickedPositions[clickCounter] = i;

                        numMoves++;
                        moves.setText("Moves: " + Integer.toString(numMoves));

                        final ImageView imageOne = lastTwoClicked[0];
                        final ImageView imageTwo = lastTwoClicked[1];

                        if(chosenImages[lastTwoClickedPositions[0]] != chosenImages[lastTwoClickedPositions[1]]) {
                            imageOne.setEnabled(true);
                            imageOne.setBackgroundColor(Color.RED);
                            imageTwo.setBackgroundColor(Color.RED);

                            new CountDownTimer(500, 1000) {

                                public void onTick(long millisUntilFinished) {

                                }

                                public void onFinish() {
                                    imageOne.setBackgroundColor(Color.TRANSPARENT);
                                    imageTwo.setBackgroundColor(Color.TRANSPARENT);
                                    imageOne.setImageResource(R.drawable.back_of_card_two);
                                    imageTwo.setImageResource(R.drawable.back_of_card_two);
                                }
                            }.start();
                        } else {
                            imageOne.setEnabled(false);
                            imageTwo.setEnabled(false);

                            imageOne.setBackgroundColor(Color.GREEN);
                            imageTwo.setBackgroundColor(Color.GREEN);

                            matchesRemaining--;
                            matches.setText("Matches Remaining: " + Integer.toString(matchesRemaining));

                            new CountDownTimer(500, 1000) {

                                public void onTick(long millisUntilFinished) {

                                }

                                public void onFinish() {
                                    imageOne.setBackgroundColor(Color.TRANSPARENT);
                                    imageTwo.setBackgroundColor(Color.TRANSPARENT);
                                }
                            }.start();

                            // Sets up intent back to the main screen after the game is won
                            if(matchesRemaining == 0) {
                                Toast.makeText(getApplicationContext(),"You Win! Congratulations!", Toast.LENGTH_SHORT).show();
                                new CountDownTimer(2000, 1000) {
                                    @Override
                                    public void onTick(long l) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        Intent toWelcomeScreen = new Intent(gamePlayScreen.this, WelcomeScreen.class);
                                        startActivity(toWelcomeScreen);
                                    }
                                }.start();
                            }
                        }
                    }

                    // Reset click counter
                    clickCounter = 0;
                }
            }
        });
    }


}
