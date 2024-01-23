package com.rk.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView  textViewLast, textViewRight,textViewHint;
    private Button buttonConfirm;
    private EditText editTextGuess;

    boolean twoDigits, threeDigits, fourDigits;

    Random r = new Random();
    int random;
    int remainingRight = 10;

    ArrayList<Integer> guessesList = new ArrayList<Integer>();
    int userAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewHint = findViewById(R.id.textViewHint);
        textViewRight = findViewById(R.id.textViewRight);
        textViewLast = findViewById(R.id.textViewLast);

        buttonConfirm = findViewById(R.id.buttonConfirm);

        editTextGuess = findViewById(R.id.editTextGuess);

        twoDigits = getIntent().getBooleanExtra("two",false);
        threeDigits = getIntent().getBooleanExtra("three",false);
        fourDigits = getIntent().getBooleanExtra("four",false);

        if (twoDigits)
        {
            random = r.nextInt(90)+10;
        }
        if (threeDigits)
        {
            random = r.nextInt(900)+100;
        }

        if (fourDigits)
        {
            random = r.nextInt(9000)+1000;
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = editTextGuess.getText().toString();

                if(guess.equals(""))
                {
                    Toast.makeText(GameActivity.this,"Please enter a guess !",Toast.LENGTH_LONG).show();
                }
                else
                {
                    textViewLast.setVisibility(View.VISIBLE);
                    textViewHint.setVisibility(View.VISIBLE);
                    textViewRight.setVisibility(View.VISIBLE);

                    userAttempts++;
                    remainingRight--;

                    int userGuess = Integer.parseInt(guess);

                    guessesList.add(userGuess);
                    textViewLast.setText("Your Last guess is : " + guess);
                    textViewRight.setText("Your remaining right : " + remainingRight);

                    if (random == userGuess)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Guess the Number");
                        builder.setCancelable(false);
                        builder.setMessage("Congrats! My guess was " + random + "\n\n You guessed my number in "
                                + userAttempts + " attempts. \n\n  Your Guesses : "
                        + guessesList + "\n\n Would you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent  = new Intent(GameActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });

                        builder.create().show();

                    }

                    if (random < userGuess)
                    {
                        textViewHint.setText("Decrease your guess");
                        editTextGuess.setText("");

                    }

                    if (random > userGuess)
                    {
                        textViewHint.setText("Increase your guess");
                        editTextGuess.setText("");

                    }

                    if (remainingRight == 0)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Guess the Number");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry your right to guess is over"
                                + " \n\n My guess was " + random
                                +  " \n\n  Your Guesses : " + guessesList
                                + " \n\n Would you like to play again?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent  = new Intent(GameActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });

                        builder.create().show();

                    }
                    editTextGuess.setText("");
                }
            }
        });


    }
}