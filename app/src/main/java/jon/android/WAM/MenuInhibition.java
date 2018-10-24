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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jon.android.R;


public class MenuInhibition extends AppCompatActivity implements OnItemSelectedListener{
    int moleNum;
    TextView moleNumber;
    int buttflyNum;
    TextView butterflyNumber;
    int moleHatNum;
    TextView moleHatNumber;
    int raccoonNum;
    TextView raccoonNumber;
    int totalCharNum;
    Spinner spinner;
    Spinner difficultySpinner;
    Spinner frequencySpinner;
    ArrayAdapter<String> frequencyAdapter;
    Spinner trialLengthSpinner;
    long gameTime;
    int posNum = 15;
    boolean allDisappear;
    final static int maxTotalCharNum = 6;
    boolean GiveFeedBack;
    double characterExistTime;
    double frequencyValue;
    int sequenceNum = 0;
    int gridSize = 5;

    LinearLayout PickMoleLayout;
    LinearLayout PickButterflyLayout;
    LinearLayout PickMoleHatLayout;
    LinearLayout PickRaccoonLayout;

    RadioGroup gameTypeGroup;
    LinearLayout gameTimePicker;
    LinearLayout gameTrialPicker;
    Space spacerAfterGameType;

    LinearLayout goTargetGroupPicker;
    LinearLayout nogoTargetGroupPicker;
    RadioGroup goTargetGroup;
    RadioGroup nogoTargetGroup;

    LinearLayout TrialLengthPicker;
    LinearLayout GridSizeLayout;

    RadioButton TimeRadioButton;
    RadioButton TrialRadioButton;

    LinearLayout scrollBar;
    LinearLayout WavesPedometer;
    Button clearTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inhibition);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Go Back");
        actionBar.setDisplayUseLogoEnabled(false);

        moleNumber = (TextView) findViewById(R.id.numMole);
        butterflyNumber = (TextView) findViewById(R.id.numButterfly);
        moleHatNumber = (TextView) findViewById(R.id.moleHatNum);
        raccoonNumber = (TextView) findViewById(R.id.numRaccon);

        PickMoleLayout = (LinearLayout) findViewById(R.id.PickMoleLayout);
        PickButterflyLayout = (LinearLayout) findViewById(R.id.PickButterflyLayout);
        PickMoleHatLayout = (LinearLayout) findViewById(R.id.PickMoleHatLayout);
        PickRaccoonLayout = (LinearLayout) findViewById(R.id.PickRaccoonLayout);

        gameTimePicker = (LinearLayout) findViewById(R.id.GameTimeLayout);
        gameTrialPicker = (LinearLayout) findViewById(R.id.GameTrialLayout);
        gameTypeGroup = (RadioGroup) findViewById(R.id.radioGroup);

        spacerAfterGameType = (Space) findViewById(R.id.spacerAfterGameType);

        goTargetGroupPicker = (LinearLayout) findViewById(R.id.goTargetGroupPicker);
        nogoTargetGroupPicker = (LinearLayout) findViewById(R.id.nogoTargetGroupPicker);
        goTargetGroup = (RadioGroup) findViewById(R.id.goTargetGroup);
        nogoTargetGroup = (RadioGroup) findViewById(R.id.nogoTargetGroup);

        TrialLengthPicker = (LinearLayout) findViewById(R.id.TriaLLengthLayout);
        GridSizeLayout = (LinearLayout) findViewById(R.id.GridSizeLayout);

        scrollBar = (LinearLayout) findViewById(R.id.scrollBar);
        clearTime = (Button)  findViewById(R.id.clearTime);
        WavesPedometer = (LinearLayout)  findViewById(R.id.WavesPedometer);


        (findViewById(R.id.backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        (findViewById(R.id.GiveFeedBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiveFeedBack = ((CheckBox) v).isChecked();

            }
        });


        (findViewById(R.id.checkBox)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allDisappear = ((CheckBox) v).isChecked();


                if(allDisappear && moleNum + raccoonNum > 1){
                    moleNum = 0;
                    raccoonNum = 0;
                    moleNumber.setText(Integer.toString(moleNum));
                    raccoonNumber.setText(Integer.toString(raccoonNum));
                    changeTotalCharCount();
                    Toast.makeText(getApplicationContext(), "Can only have 1 mole or 1 raccoon with Waves on",Toast.LENGTH_SHORT ).show();

                }

            }
        });



        difficultySpinner = (Spinner) findViewById(R.id.difficulty);

        difficultySpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> difficulty = new ArrayList<String>();
        difficulty.add("0.5 sec");
        difficulty.add("0.6 sec");
        difficulty.add("0.7 sec");
        difficulty.add("0.8 sec");
        difficulty.add("0.9 sec");
        difficulty.add("1 sec");


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



        frequencySpinner = (Spinner) findViewById(R.id.frequencySpinner);

        frequencySpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> frequency = new ArrayList<String>();
        frequency.add("90% 'go' targets, 10% 'no-go' targets");
        frequency.add("80% 'go' targets, 20% 'no-go' targets");
        frequency.add("75% 'go' targets, 25% 'no-go' targets");
        frequency.add("70% 'go' targets, 30% 'no-go' targets");
        frequency.add("60% 'go' targets, 40% 'no-go' targets");


        // Creating adapter for spinner
        frequencyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, frequency);

        // Drop down layout style - list view with radio button
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        frequencySpinner.setAdapter(frequencyAdapter);


        trialLengthSpinner = (Spinner) findViewById(R.id.trialLengthSpinner);

        trialLengthSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> trialLength = new ArrayList<String>();
        trialLength.add("10");
        trialLength.add("20");
        trialLength.add("30");
        trialLength.add("40");
        trialLength.add("50");
        trialLength.add("60");


        // Creating adapter for spinner
        ArrayAdapter<String> trialLengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trialLength);

        // Drop down layout style - list view with radio button
        trialLengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        trialLengthSpinner.setAdapter(trialLengthAdapter);


        Button wavesInstructions = (Button) findViewById(R.id.wavesInstructions);
        wavesInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuInhibition.this);
                builder.setMessage("When Waves is checked, there can only be one target character and whenever it is hit, all other character on screen also disappears.")
                        .setTitle("What is Waves?")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                            }
                        });
                builder.create().show();
            }
        });

        Button difficultyInstructions = (Button) findViewById(R.id.difficultyInstructions);
        difficultyInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuInhibition.this);
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

        Button frequencyInstructions = (Button) findViewById(R.id.frequencyInstructions);
        frequencyInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuInhibition.this);
                builder.setMessage("Frequency describes the number of 'go' targets to 'no-go' targets.\n")
                        .setTitle("What is frequency?")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                            }
                        });
                builder.create().show();
            }
        });

    }

    public void changeTotalCharCount(){
        totalCharNum = moleNum + buttflyNum + moleHatNum +raccoonNum;

    }

    public void molePlusB(View view){
        if(allDisappear){
           if (moleNum + raccoonNum < 1){
               moleNum++;
               changeTotalCharCount();
               moleNumber.setText(Integer.toString(moleNum));
           }
           else {
               Toast.makeText(getApplicationContext(), "Can only have 1 total target character with Waves on",Toast.LENGTH_SHORT ).show();
           }
        }
        else if (totalCharNum  < maxTotalCharNum){
            moleNum++;
            changeTotalCharCount();
            moleNumber.setText(Integer.toString(moleNum));


        }
        else{
            Toast.makeText(this, "You can only have 6 total characters at most!",Toast.LENGTH_SHORT ).show();
        }

    }

    public void moleMinusB(View view){
        if (totalCharNum  > 0 && moleNum > 0){
            moleNum--;
            changeTotalCharCount();
            moleNumber.setText(Integer.toString(moleNum));

        }
    }

    public void ButterflyPlusB(View view){
        if (totalCharNum < maxTotalCharNum){
            buttflyNum++;
            changeTotalCharCount();
            butterflyNumber.setText(Integer.toString(buttflyNum));

        }
        else{
            Toast.makeText(this, "You can only have 6 total characters at most!",Toast.LENGTH_SHORT ).show();
        }
    }
    public void ButterflyMinusB(View view){
        if (totalCharNum > 0 && buttflyNum >0){
            buttflyNum--;
            changeTotalCharCount();
            butterflyNumber.setText(Integer.toString(buttflyNum));

        }

    }
    public void MoleHatPlusB(View view){
        if (totalCharNum < maxTotalCharNum){
            moleHatNum++;
            changeTotalCharCount();
            moleHatNumber.setText(Integer.toString(moleHatNum));

        }
        else{
            Toast.makeText(this, "You can only have 6 total characters at most!",Toast.LENGTH_SHORT ).show();
        }
    }
    public void MoleHatMinusB(View view){
        if (totalCharNum > 0 && moleHatNum >0){
            moleHatNum--;
            changeTotalCharCount();
            moleHatNumber.setText(Integer.toString(moleHatNum));

        }
    }
    public void RaccoonPlusB(View view){
        if(allDisappear){
            if (moleNum + raccoonNum < 1){
                raccoonNum++;
                changeTotalCharCount();
                raccoonNumber.setText(Integer.toString(raccoonNum));
            }else{
                Toast.makeText(getApplicationContext(), "Can only have 1 total target character with Waves on",Toast.LENGTH_SHORT ).show();
            }
        }
        else if (totalCharNum < maxTotalCharNum){
            raccoonNum++;
            changeTotalCharCount();
            raccoonNumber.setText(Integer.toString(raccoonNum));

        }
        else{
            Toast.makeText(this, "You can only have 6 total characters at most!",Toast.LENGTH_SHORT ).show();
        }
    }
    public void RaccoonflyMinusB(View view){
        if (totalCharNum > 0 && raccoonNum >0){
            raccoonNum--;
            changeTotalCharCount();
            raccoonNumber.setText(Integer.toString(raccoonNum));
        }

    }

    public void clearTime (View view){
        moleNum = 0;
        buttflyNum = 0;
        moleHatNum = 0;
        raccoonNum = 0;
        changeTotalCharCount();
        moleNumber.setText(Integer.toString(moleNum));
        butterflyNumber.setText(Integer.toString(buttflyNum));
        moleHatNumber.setText(Integer.toString(moleHatNum));
        raccoonNumber.setText(Integer.toString(raccoonNum));


    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.TimeRadioButton:
                if (checked)
                    spacerAfterGameType.setVisibility(View.GONE);
                scrollBar.setVisibility(View.VISIBLE);
                clearTime.setVisibility(View.VISIBLE);
                WavesPedometer.setVisibility(View.VISIBLE);
                gameTimePicker.setVisibility(View.VISIBLE);
                gameTrialPicker.setVisibility(View.GONE);

                TrialLengthPicker.setVisibility(View.GONE);

                PickMoleLayout.setVisibility(View.VISIBLE);
                PickButterflyLayout.setVisibility(View.VISIBLE);
                PickMoleHatLayout.setVisibility(View.VISIBLE);
                PickRaccoonLayout.setVisibility(View.VISIBLE);

                goTargetGroupPicker.setVisibility(View.GONE);
                nogoTargetGroupPicker.setVisibility(View.GONE);

                GridSizeLayout.setVisibility(View.GONE);

                // set defaults
                frequencyValue = 1.0;
                sequenceNum = 0;
                gameTime = 10000;
                break;
            case R.id.TrialRadioButton:
                if (checked)
                    spacerAfterGameType.setVisibility(View.GONE);
                scrollBar.setVisibility(View.VISIBLE);
                clearTime.setVisibility(View.GONE);
                gameTimePicker.setVisibility(View.GONE);
                gameTrialPicker.setVisibility(View.VISIBLE);
                TrialLengthPicker.setVisibility(View.VISIBLE);
                WavesPedometer.setVisibility(View.GONE);
                PickMoleLayout.setVisibility(View.GONE);
                PickButterflyLayout.setVisibility(View.GONE);
                PickMoleHatLayout.setVisibility(View.GONE);
                PickRaccoonLayout.setVisibility(View.GONE);

                goTargetGroupPicker.setVisibility(View.VISIBLE);
                nogoTargetGroupPicker.setVisibility(View.VISIBLE);

                GridSizeLayout.setVisibility(View.VISIBLE);

                // set defaults
                frequencyValue = 0.9;
                gameTime = -1;
                break;
            case R.id.GoMoleButton:
                moleNum = 1;
                raccoonNum = 0;
                break;
            case R.id.GoRaccoonButton:
                moleNum = 0;
                raccoonNum = 1;
                break;
            case R.id.NoGoButterflyButton:
                buttflyNum = 1;
                moleHatNum = 0;
                break;
            case R.id.NoGoMoleHatButton:
                buttflyNum = 0;
                moleHatNum = 1;
                break;
            case R.id.Grid2Button:
                gridSize = 2;
                break;
            case R.id.Grid3Button:
                gridSize = 3;
                break;
            case R.id.Grid5Button:
                gridSize = 5;
                break;
        }
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


        if (item.equals("0.5 sec")){
            characterExistTime = 0.5 * 1000;
        }
        else if (item.equals("0.6 sec")){
            characterExistTime = 0.6 * 1000;
        }
        else if (item.equals("0.7 sec")){
            characterExistTime = 0.7 * 1000;
        }
        else if (item.equals("0.8 sec")){
            characterExistTime = 0.8 * 1000;
        }
        else if (item.equals("0.9 sec")) {
            characterExistTime = 0.9 * 1000;
        }
        else if (item.equals("1 sec")){
            characterExistTime = 1000;
        }


        if (item.equals("90% 'go' targets, 10% 'no-go' targets")){
            frequencyValue = 0.9;
        }
        else if (item.equals("80% 'go' targets, 20% 'no-go' targets")) {
            frequencyValue = 0.8;
        }
        else if (item.equals("75% 'go' targets, 25% 'no-go' targets")) {
            frequencyValue = 0.75;
        }
        else if (item.equals("70% 'go' targets, 30% 'no-go' targets")) {
            frequencyValue = 0.7;
        }
        else if (item.equals("60% 'go' targets, 40% 'no-go' targets")) {
            frequencyValue = 0.6;
        }


        if (item.equals("10")){
            sequenceNum = 10;
        }
        else if (item.equals("20")){
            sequenceNum = 20;
        }
        else if (item.equals("30")){
            sequenceNum = 30;
        }
        else if (item.equals("40")){
            sequenceNum = 40;
        }
        else if (item.equals("50")){
            sequenceNum = 50;
        }
        else if (item.equals("60")){
            sequenceNum = 60;
        }

    }


    // TODO - randomize game level not working on first run
    public void RandomGame(View view){
        totalCharNum = 0;

        TimeRadioButton = (RadioButton) findViewById(R.id.TimeRadioButton);
        TrialRadioButton = (RadioButton) findViewById(R.id.TrialRadioButton);

        RadioButton GoMoleButton = (RadioButton) findViewById(R.id.GoMoleButton);
        RadioButton GoRaccoonButton = (RadioButton) findViewById(R.id.GoRaccoonButton);
        RadioButton NoGoButterflyButton = (RadioButton) findViewById(R.id.NoGoButterflyButton);
        RadioButton NoGoMoleHatButton = (RadioButton) findViewById(R.id.NoGoMoleHatButton);

        RadioButton Grid2Button = (RadioButton) findViewById(R.id.Grid2Button);
        RadioButton Grid3Button = (RadioButton) findViewById(R.id.Grid3Button);
        RadioButton Grid5Button = (RadioButton) findViewById(R.id.Grid5Button);

        if (TimeRadioButton.isChecked() || !TimeRadioButton.isChecked() && !TrialRadioButton.isChecked()) {
            TimeRadioButton.setChecked(true);
            spacerAfterGameType.setVisibility(View.GONE);
            gameTimePicker.setVisibility(View.VISIBLE);
            gameTrialPicker.setVisibility(View.GONE);

            PickMoleLayout.setVisibility(View.VISIBLE);
            PickButterflyLayout.setVisibility(View.VISIBLE);
            PickMoleHatLayout.setVisibility(View.VISIBLE);
            PickRaccoonLayout.setVisibility(View.VISIBLE);

            if (allDisappear) {
                while ((totalCharNum > 6 || totalCharNum == 0 || moleNum + raccoonNum != 1)) {
                    moleNum = (int) (Math.random() * 6);
                    buttflyNum = (int) (Math.random() * 6);
                    moleHatNum = (int) (Math.random() * 6);
                    raccoonNum = (int) (Math.random() * 6);
                    changeTotalCharCount();
                }
            } else {
                while ((totalCharNum > 6 || totalCharNum == 0 || moleNum + raccoonNum < 1)) {
                    moleNum = (int) (Math.random() * 6);
                    buttflyNum = (int) (Math.random() * 6);
                    moleHatNum = (int) (Math.random() * 6);
                    raccoonNum = (int) (Math.random() * 6);
                    changeTotalCharCount();
                }
            }
        } else if (TrialRadioButton.isChecked()) {
            spacerAfterGameType.setVisibility(View.GONE);
            gameTimePicker.setVisibility(View.GONE);
            gameTrialPicker.setVisibility(View.VISIBLE);

            PickMoleLayout.setVisibility(View.GONE);
            PickButterflyLayout.setVisibility(View.GONE);
            PickMoleHatLayout.setVisibility(View.GONE);
            PickRaccoonLayout.setVisibility(View.GONE);

            while (totalCharNum == 0) {
                Random r = new Random();

                int gridSizeRand = r.nextInt(3);
                int frequencyRand = r.nextInt(5);

                if (gridSizeRand == 0) {
                    Grid2Button.setChecked(true);
                    gridSize = 2;
                } else if (gridSizeRand == 0) {
                    Grid3Button.setChecked(true);
                    gridSize = 3;
                } else {
                    Grid5Button.setChecked(true);
                    gridSize = 5;
                }

                // 0 = mole
                if (r.nextInt(2) == 0 ) {
                    GoMoleButton.setChecked(true);
                    moleNum = 1;
                    raccoonNum = 0;
                } else {
                    GoRaccoonButton.setChecked(true);
                    moleNum = 0;
                    raccoonNum = 1;
                }

                // 0 = butterfly
                if (r.nextInt(2) == 0 ) {
                    NoGoButterflyButton.setChecked(true);
                    buttflyNum = 1;
                    moleHatNum = 0;
                } else {
                    NoGoMoleHatButton.setChecked(true);
                    buttflyNum = 1;
                    moleHatNum = 0;
                }

                if (frequencyRand == 0) {
                    int selectionPosition = frequencyAdapter.getPosition("90% 'go' targets, 10% 'no-go' targets");
                    frequencySpinner.setSelection(selectionPosition);
                    frequencyValue = 0.9;
                } else if (frequencyRand == 1) {
                    int selectionPosition = frequencyAdapter.getPosition("80% 'go' targets, 20% 'no-go' targets");
                    frequencySpinner.setSelection(selectionPosition);
                    frequencyValue = 0.8;
                } else if (frequencyRand == 2) {
                    int selectionPosition = frequencyAdapter.getPosition("75% 'go' targets, 25% 'no-go' targets");
                    frequencySpinner.setSelection(selectionPosition);
                    frequencyValue = 0.75;
                } else if (frequencyRand == 3) {
                    int selectionPosition = frequencyAdapter.getPosition("70% 'go' targets, 30% 'no-go' targets");
                    frequencySpinner.setSelection(selectionPosition);
                    frequencyValue = 0.7;
                } else if (frequencyRand == 4) {
                    int selectionPosition = frequencyAdapter.getPosition("60% 'go' targets, 40% 'no-go' targets");
                    frequencySpinner.setSelection(selectionPosition);
                    frequencyValue = 0.6;
                } else {
                    int selectionPosition = frequencyAdapter.getPosition("90% 'go' targets, 10% 'no-go' targets");
                    frequencySpinner.setSelection(selectionPosition);
                    frequencyValue = 0.9;
                }

                changeTotalCharCount();
            }


        }


        moleNumber.setText(Integer.toString(moleNum));
        butterflyNumber.setText(Integer.toString(buttflyNum));
        moleHatNumber.setText(Integer.toString(moleHatNum));
        raccoonNumber.setText(Integer.toString(raccoonNum));

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        characterExistTime = 3000;
        gameTime = 10 * 1000;
        frequencyValue = 1.0;
    }




    public void StartButton(View view) {
        totalCharNum = moleNum + buttflyNum + moleHatNum + raccoonNum;
        if (maxTotalCharNum >= totalCharNum && totalCharNum > 0){
            if(moleNum+raccoonNum>0) {

                Intent startGame = new Intent(this, Game.class);
                Intent intent = getIntent();
                startGame.putExtra("characterExistTime", characterExistTime);
                startGame.putExtra("giveFeedBack", GiveFeedBack);
                startGame.putExtra("pId", intent.getStringExtra("pId"));
                startGame.putExtra("allDisappear", allDisappear);
                startGame.putExtra("GameType", intent.getIntExtra("GameType", -1));
                startGame.putExtra("charNum", totalCharNum);
                startGame.putExtra("numOfPos", posNum);
                startGame.putExtra("moleNum", moleNum);
                startGame.putExtra("butterflyNum", buttflyNum);
                startGame.putExtra("mHatnum", moleHatNum);
                startGame.putExtra("racNum", raccoonNum);
                startGame.putExtra("gameTime", gameTime);
                startGame.putExtra("frequencyValue", frequencyValue);
                startGame.putExtra("sequenceNum",sequenceNum);
                startGame.putExtra("gridSize",gridSize);

                // final sanity check to ensure that trial-based games have no time
                if (frequencyValue != 1.0) {
                    if (sequenceNum == 0) {
                        sequenceNum = 10;
                    }
                    gameTime = -1;
                }
                startActivity(startGame);
            }
            else{
                Toast.makeText(this, "You need 1 mole or 1 raccoon at least!",Toast.LENGTH_SHORT ).show();
            }
        }
        else if(totalCharNum == 0){
            Toast.makeText(this, "You need 1 character at least!",Toast.LENGTH_SHORT ).show();
        }
        else{
            Toast.makeText(this, "You can only have 6 total characters at most!",Toast.LENGTH_SHORT ).show();
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
