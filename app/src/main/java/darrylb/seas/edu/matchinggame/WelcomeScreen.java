package darrylb.seas.edu.matchinggame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;

import static darrylb.seas.edu.matchinggame.R.id.difficultyDropDown;

public class WelcomeScreen extends AppCompatActivity {
    Spinner difficulty;
    String difficultyChosen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        // Initialize spinner
        difficulty = findViewById(difficultyDropDown);
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(WelcomeScreen.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.difficulty));
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(difficultyAdapter);

        // Acquire selected difficulty in spinner and setting up listener
        difficultyChosen = difficultyAdapter.getItem(0);

        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                difficultyChosen = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Setup the intent to the game screen
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toGameScreen = new Intent(WelcomeScreen.this, gamePlayScreen.class);
                toGameScreen.putExtra("difficultyChosen", difficultyChosen);
                startActivity(toGameScreen);

            }

        });



    }


}
