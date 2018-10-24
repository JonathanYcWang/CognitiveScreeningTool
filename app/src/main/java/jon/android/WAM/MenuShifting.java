package jon.android.WAM;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jon.android.R;

public class MenuShifting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int moleNum;
    int buttflyNum;


    int totalCharNum;
    Spinner spinner;
    Spinner difficultySpinner;
    long gameTime;
    int posNum = 15;
    boolean checkStepCount;
    final static int maxTotalCharNum = 6;
    int characterExisitTime;
    boolean GiveFeedBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_shifting);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Go Back");
        actionBar.setDisplayUseLogoEnabled(false);

        (findViewById(R.id.GiveFeedBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiveFeedBack = ((CheckBox) v).isChecked();

            }
        });


        (findViewById(R.id.selectMole)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {

                    moleNum = 1;
                }
                else{
                    moleNum = 0;
                }


            }
        });

        (findViewById(R.id.selectButterfly)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    buttflyNum = 1;
                }
                else{
                    buttflyNum = 0;
                }


            }
        });

        (findViewById(R.id.backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        difficultySpinner = (Spinner) findViewById(R.id.difficulty);

        difficultySpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> difficulty = new ArrayList<String>();
        difficulty.add("Easy");
        difficulty.add("Intermediate");
        difficulty.add("Hard");


        // Creating adapter for spinner
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, difficulty);

        // Drop down layout style - list view with radio button
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        difficultySpinner.setAdapter(difficultyAdapter);

        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("10 sec");
        categories.add("30 sec");
        categories.add("1:00 min");
        categories.add("1:30 min");
        categories.add("2:00 min");
        categories.add("2:30 min");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        Button difficultyInstuctions = (Button) findViewById(R.id.difficultyInstructions);
        difficultyInstuctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuShifting.this);
                builder.setMessage("Difficulty describes the time length that each character/trial appears on the game board.\n")
                        .setTitle("What is difficulty?")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                            }
                        });
                builder.create().show();
            }
        });

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (item.equals("10 sec")){
            gameTime = 10 * 1000;
        }
        else if(item.equals("30 sec")){
            gameTime = 30 * 1000;
        }
        else if (item.equals("1:00 min")){
            gameTime = 60 * 1000;
        }
        else if (item.equals("1:30 min")){
            gameTime = 90 * 1000;
        }
        else if (item.equals("2:00 min")){
            gameTime = 120 * 1000;
        }
        else if (item.equals("2:30 min")){
            gameTime = 150 * 1000;
        }
        if(item.equals("Easy")){
            characterExisitTime = 3000;
        }
        else if(item.equals("Intermediate")){
            characterExisitTime = 1500;
        }
        else if(item.equals("Hard")){
            characterExisitTime = 750;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        characterExisitTime = 3000;
        gameTime = 10 * 1000;
    }
    public void StartButton(View view) {
        if ((totalCharNum = moleNum + buttflyNum) > 0){
            Intent startGame = new Intent(this, Game.class);
            Intent intent = getIntent();

            startGame.putExtra("characterExistTime",characterExisitTime);
            startGame.putExtra("giveFeedBack", GiveFeedBack);
            startGame.putExtra("pId",intent.getStringExtra("pId"));
            startGame.putExtra("GameType",intent.getIntExtra("GameType", -1));
            startGame.putExtra("charNum",totalCharNum );
            startGame.putExtra("numOfPos", posNum);
            startGame.putExtra("moleNum", moleNum);
            startGame.putExtra("butterflyNum", buttflyNum);
            startGame.putExtra("gameTime", gameTime);

            startActivity(startGame);

        }else {
            Toast.makeText(this, "You need 1 character at least!",Toast.LENGTH_LONG ).show();
        }
    }

    public void Instructions(View view){
        Intent intent = getIntent();
        Intent startGame = new Intent(this, InstructionsInhibition.class);
        startGame.putExtra("GameType",intent.getIntExtra("GameType", -1));
        startActivity(startGame);
    }

    public void goGraphs(View view) {
        Intent intent = getIntent();
        Intent startGame = new Intent(this, PlayerStats.class);
        startGame.putExtra("GameType", intent.getIntExtra("GameType", -1));
        startGame.putExtra("pId",intent.getStringExtra("pId"));
        startActivity(startGame);
    }


    public void patientLog(View view) {
        Intent intent = getIntent();
        Intent startGame = new Intent(this, patientLogs.class);
        startGame.putExtra("GameType", intent.getIntExtra("GameType", -1));
        startGame.putExtra("pId",intent.getStringExtra("pId"));
        startActivity(startGame);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return true;
        }
    }
}
