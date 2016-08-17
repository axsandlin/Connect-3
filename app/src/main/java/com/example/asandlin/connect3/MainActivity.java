package com.example.asandlin.connect3;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {


        // 0 = trump, 1 = bernie

        int activePlayer = 0;

        boolean gameIsActive = true;

        MediaPlayer mplayer;

        AudioManager audioManager;

        // 2 means unplayed

        // game state

        int[] gameState =  {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int [][] winningPositions = {{0,1,2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};


        public void dropIn(View view) {

            ImageView counter = (ImageView) view;

            int tappedCounter = Integer.parseInt(counter.getTag().toString());

            if (gameState[tappedCounter] == 2 && gameIsActive) {

                gameState[tappedCounter] = activePlayer;

                counter.setTranslationY(-1000f);

                if (activePlayer == 0) {

                    counter.setImageResource(R.drawable.trump);

                    activePlayer = 1;

                } else {

                    counter.setImageResource(R.drawable.bernie);

                    activePlayer = 0;
                }

                counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

                for (int[] winningPosition : winningPositions) {

                    if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                            gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                            gameState[winningPosition[0]] != 2) {

                        //Someone has  won!

                        gameIsActive = false;

                        String winner = "Bernie";

                        mplayer = MediaPlayer.create(this, R.raw.tinkle);

                        if (gameState[winningPosition[0]] == 0) {

                            winner = "Trump";

                            mplayer = MediaPlayer.create(this, R.raw.machinegun);

                        }

                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                        winnerMessage.setText(winner + " has won!");

                        mplayer.start();

                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

                        layout.setVisibility(View.VISIBLE);



                    }else {

                        boolean gameIsOver = true;

                        for (int counterState : gameState) {

                            if (counterState == 2) gameIsOver = false;

                        }

                        if (gameIsOver) {

                            TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                            winnerMessage.setText("It's a draw!");

                            mplayer = MediaPlayer.create(this, R.raw.crickets);

                            mplayer.start();

                            LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

                            layout.setVisibility(View.VISIBLE);





                        }
                    }
                }


            }
        }

    public void playAgain(View view) {

        gameIsActive = true;

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++) {

            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }



    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
