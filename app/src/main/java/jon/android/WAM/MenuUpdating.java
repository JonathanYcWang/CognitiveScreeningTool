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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jon.android.R;


public class MenuUpdating extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int n = 1;  //the n back value
    Spinner spinner;
    Spinner difficultySpinner;
    TextView nValue;
    TextView sequenceNnum;
    int sequenceNum = 45;
    int totalCharNum;
    int posNum = 15;
    int characterExistTime;
    final static int nValueBack = 6;
    final static int sequenceMax = 100;
    boolean matchingChar;
    boolean GiveFeedBack;
    boolean checkStepCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_updating);

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


        spinner = (Spinner) findViewById(R.id.pickMatchspinner);

        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Characters");
        categories.add("Letters");

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
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuUpdating.this);
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

        nValue = (TextView) findViewById(R.id.Nnum);
        sequenceNnum = (TextView) findViewById(R.id.sequenceNum);


    }

    public void sequencePlusB(View view){
        if (sequenceNum  < sequenceMax){
            sequenceNum++;
            sequenceNnum.setText(Integer.toString(sequenceNum));
        }
    }

    public void sequenceMinusB(View view){
        if (sequenceNum  > 1 ){
            sequenceNum--;
            sequenceNnum.setText(Integer.toString(sequenceNum));
        }
    }






    public void PlusB(View view){
        if (n  < nValueBack){
            n++;
            nValue.setText(Integer.toString(n));
        }
    }

    public void MinusB(View view){
        if (n  > 1 ){
            n--;
            nValue.setText(Integer.toString(n));
        }
    }

    public void StartButton(View view) {
        Intent startGame = new Intent(this, Game.class);
        Intent intent = getIntent();
        startGame.putExtra("giveFeedBack", GiveFeedBack);
        startGame.putExtra("sequenceNum",sequenceNum);
        startGame.putExtra("characterExistTime", characterExistTime);
        startGame.putExtra("pId",intent.getStringExtra("pId"));
        startGame.putExtra("nValue", n);
        startGame.putExtra("GameType",intent.getIntExtra("GameType", -1));
        startGame.putExtra("charNum",totalCharNum );
        startGame.putExtra("numOfPos", posNum);
        startGame.putExtra("updatingChar", matchingChar);

        startActivity(startGame);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (item.equals("Characters")){
            matchingChar = true;
        }
        else if(item.equals("Letters")){
            matchingChar = false;
        }
        if(item.equals("Easy")){
            characterExistTime = 3000;
        }
        else if(item.equals("Intermediate")){
            characterExistTime = 1500;
        }
        else if(item.equals("Hard")){
            characterExistTime = 750;
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
    public void onNothingSelected(AdapterView<?> parent) {
        characterExistTime = 3000;
        matchingChar = true;
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
