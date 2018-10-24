package jon.android.WAM;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import database.DatabaseAdapter;
import jon.android.R;

public class Game extends AppCompatActivity implements OnTouchListener, SensorEventListener {


    private Level level;
    private Character[] characterList;
    public GridView gridview;
    private int charNum;
    private int moleNum;
    private int buttflyNum;
    private int moleHatNum;
    private int raccoonNum;
    private int numOfPos;
    private long gameTime;
    private int gameType;
    private int nValue;
    private boolean allDisappear;
    private int seqLength;
    private boolean updatingChar;
    private int gridSize;
    private double characterExistTime;

    // touch metrics
    private String touch_time;
    private int positionTouched;
    private int touchX_down;
    private int touchY_down;
    private int touchX_up;
    private int touchY_up;
    private float pressure;
    private float size;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
    private String levelStart;
    private String levelEnd;

    private boolean giveFeedBack;

    private boolean wasCompleted = true;

    private double [] allTotalCharAppearences = new double [4];
    private double [] allTotalErrors = new double [4];

//    boolean checkStepCount;

    TextView stepCounterView;

    private String pId;

    DatabaseReference mRootRef;
    DatabaseReference recordNewLevel;


    SensorManager sensorManager;
    Sensor countSensor;


    int mStep = 0;

    // Trial based inhibition
    double frequencyValue;
    GridView gameGrid;

    private DatabaseAdapter dbHelper;
    private String db_user_id;
    private double db_difficulty;
    private double db_error_rate_moles;
    private double db_frequency;
    private double db_game_time_length;
    private int db_grid_size;
    private boolean db_level_completed;
    private String db_level_end_time;
    private boolean db_level_over;
    private double db_median_response_time;
    private double db_response_time_standard_deviation;
    private double db_non_perseveration_error_count;
    private double db_overall_error_rate;
    private int db_score;
    private double db_total_number_errors;
    private double db_total_number_trials;
    private double db_total_perseveration_error_count;
    private double db_total_perseveration_trials;
    private int db_total_num_moles;
    private int db_total_num_butterflies;
    private int db_total_num_moles_hats;
    private int db_total_num_raccoons;
    private int db_sequence_length;
    private double db_perseveration_error_rate;
    private double db_non_perseveration_error_rate;
    private double db_error_rate_butterflies;
    private double db_error_rate_moles_hats;
    private double db_error_rate_raccoons;
    private String db_level_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();


        giveFeedBack = intent.getBooleanExtra("giveFeedBack",false);
//        checkStepCount = intent.getBooleanExtra("CheckStepCount",false);
        pId = intent.getStringExtra("pId");
        db_user_id = pId;
        mRootRef = FirebaseDatabase.getInstance().getReference().child("Hits");

        recordNewLevel = mRootRef.push();

        dbHelper = new DatabaseAdapter(this);
        dbHelper.open();

        characterExistTime = intent.getDoubleExtra("characterExistTime", 3000);
        nValue = intent.getIntExtra("nValue", 0);

        gameGrid = (GridView) findViewById(R.id.gridview);

        /*
        gameType - describes type of executive function game
        0 = inhibition
        1 = shifting
        2 = updating
         */
        gameType = intent.getIntExtra("GameType", -1);
        if(gameType == 2) {
            charNum = 1;
            moleNum = 1;
            seqLength = intent.getIntExtra("sequenceNum", 0);
            updatingChar = intent.getBooleanExtra("updatingChar", true);
        }
        else{
            charNum = intent.getIntExtra("charNum", 0);
            moleNum = intent.getIntExtra("moleNum", 0);
        }

        gameTime = intent.getLongExtra("gameTime", (long) characterExistTime * seqLength);
        buttflyNum = intent.getIntExtra("butterflyNum", 0);
        moleHatNum = intent.getIntExtra("mHatnum", 0);
        raccoonNum = intent.getIntExtra("racNum", 0);
        numOfPos = intent.getIntExtra("numOfPos", 0);
        frequencyValue = intent.getDoubleExtra("frequencyValue", 1.0);
        gridSize = intent.getIntExtra("gridSize", 0);

        // update grid size setting for inhibition trial-based games
        if (frequencyValue != 1.0 && gridSize != 5) {
            numOfPos = gridSize * gridSize;
            gameGrid.setNumColumns(gridSize);
        } else {
            // time based games
            gridSize = 5;
            numOfPos = 15;
        }

        // trial based inhibition game
        if (gameType == 0 && frequencyValue != 1.0) {
            // restricts number of characters on the board at once
            charNum = 1;

           seqLength = intent.getIntExtra("sequenceNum", 10);
            if (seqLength == 0) {
                seqLength = 10;
            }
            // game becomes trial based - 400 ms is the time after and 125 ms is an extra buffer
            gameTime = (long) (characterExistTime + 525)*seqLength;

        }

        // allDisappear - required only in inhibition "waves" level
        allDisappear = intent.getBooleanExtra("allDisappear", false);

        gridview = (GridView) findViewById(R.id.gridview);
        level = new Level(this,numOfPos);


        characterList = createCharacters(charNum,level,gridview, moleNum,buttflyNum,moleHatNum,raccoonNum);
        stepCounterView = (TextView) findViewById(R.id.stepCounter);

        stepCounterView.setVisibility(View.GONE);
//        if(!checkStepCount){
//            stepCounterView.setVisibility(View.GONE);
//        }else{
//            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//
//            countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
//
//            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
//        }


        gridview.setAdapter(level);

        //create game level timer
        level.setLevelTime(new CountDownTimer((long) gameTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                TextView timeSign = (TextView)findViewById(R.id.timeElapsed);
                int secsLeft = (int) millisUntilFinished/1000;
                level.setTime(millisUntilFinished);
                if (secsLeft < 60){
                    timeSign.setText("Time remaining: " + secsLeft + " secs");
                }
                else{
                    timeSign.setText("Time remaining: " + secsLeft/60 + ":" + secsLeft%60 + " min");
                }

                if(millisUntilFinished<characterExistTime+1  || (millisUntilFinished<characterExistTime+1 && gameType == 0 && frequencyValue == 1.0)) {
                    for(int i = 0; i < charNum; i++){
                        characterList[i].setNoTimeLeft();
                    }
                }

            }
            @Override
            public void onFinish() {
                levelEnd = sdf.format(new Date());
                gameFinish();
                finish();
            }

        });
        
        level.getLevelTime().start();
        levelStart = sdf.format(new Date());



            gridview.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                pressure = event.getPressure();
                size = event.getSize();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
                touch_time = sdf.format(new Date());

                positionTouched = gridview.pointToPosition((int) event.getX(), (int) event.getY());

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    touchX_down = (int)event.getX();
                    touchY_down = (int)event.getY();
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    touchX_up = (int)event.getX();
                    touchY_up = (int)event.getY();
                }
                return false;
            }

        });




        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(final AdapterView<?> parent, final View v,
                                    final int position, long id) {
                for(int i = 0; i < charNum; i++){
                    if (characterList[i].getCharacterPosition() == position && !characterList[i].getrialOver()) {

                        ((Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);
                        level.changeGameScore(characterList[i].getHitScore());
                        TextView scoreView = (TextView)findViewById(R.id.Score);
                        scoreView.setText("Score: "+ level.getGameScore());
                        characterList[i].Hit();
                        if(allDisappear){
                            for(int j = 0; j < charNum; j++){
                                characterList[j].getTimeStaying().onFinish();
                            }
                        }
                        else{
                            characterList[i].getTimeStaying().onFinish();
                            if((gameType == 2 || (gameType == 0 && frequencyValue != 1.0)) && characterList[i].getSeqOver()){
                                level.getLevelTime().onFinish();
                                level.getLevelTime().cancel();
                            }
                        }
                        ImageView imageView = (ImageView) v;
                        final int[] x = new int[2];
                        imageView.getLocationOnScreen(x);
                        Drawable d = getResources().getDrawable(R.drawable.basicmolecorrect);
                        characterList[i].setTouch(touchX_down,touchY_down,touchX_up,touchY_up,pressure,size,
                                (int)Math.sqrt(Math.pow(((x[0] + (d.getIntrinsicHeight() / 2)) - touchX_down), 2) + Math.pow(((x[1] + (d.getIntrinsicWidth() / 2)) - touchY_down), 2)));
                        break;
                    }
                }
            }
        });

    }



    public void stopGame(View view){
        wasCompleted = false;
        level.getLevelTime().onFinish();
        level.getLevelTime().cancel();
    }

    private void gameFinish(){
        double totalTrials = 0;
        double totalNumberErrors = 0;

        long totalResponseTime = 0;
        long totalHits = 0;
        List <Long> allRTs = new ArrayList<>();
        double [] perseverationMeasure = new double[2];
        for (int i = 0 ; i < charNum; i ++){
            for(int j = 0; j < 4; j++){
                allTotalCharAppearences[j] += characterList[i].getTotalAppearences()[j];
                totalTrials += characterList[i].getTotalAppearences()[j];


                allTotalErrors[j] += characterList[i].getErrorsCounter()[j];
                totalNumberErrors += characterList[i].getErrorsCounter()[j];
            }
            totalResponseTime+=characterList[i].getTotalResponseTime()[0];
            totalHits+=characterList[i].getTotalResponseTime()[1];

            allRTs.addAll(characterList[i].getAllResponseTimes());
            if(gameType == 1){
                perseverationMeasure[0] += characterList[i].getperseverationMeasure()[0];
                perseverationMeasure[1] += characterList[i].getperseverationMeasure()[1];
            }

        }



        double val = 0;
        for(int t = 0; t < allRTs.size(); t ++){
           val += Math.pow (allRTs.get(t)-(totalResponseTime/totalHits),2);
        }
        if(totalHits < 2){
            recordNewLevel.child("Response Time Standard Deviation").setValue(0);
            db_response_time_standard_deviation = 0;
        }else{
            recordNewLevel.child("Response Time Standard Deviation").setValue(Math.sqrt(val/(totalHits-1)));
            db_response_time_standard_deviation = Math.sqrt(val/(totalHits-1));
        }


        Collections.sort(allRTs,null);
        int rtMedian = (int)Math.ceil(allRTs.size()/2);
        if (allRTs.size() == 0){
            recordNewLevel.child("Median Response Time").setValue(0);
            db_median_response_time = 0;
        }
        else if(allRTs.size()%2 == 1){
            recordNewLevel.child("Median Response Time").setValue(allRTs.get(rtMedian));
            db_median_response_time = allRTs.get(rtMedian);
        }
        else if(allRTs.size()%2 == 0){
            recordNewLevel.child("Median Response Time").setValue((allRTs.get(rtMedian)+ allRTs.get(rtMedian-1))/2);
            db_median_response_time = (allRTs.get(rtMedian)+ allRTs.get(rtMedian-1))/2;
        }

        if(perseverationMeasure[1] > 0){
            recordNewLevel.child("Perseveration Error Rate").setValue(perseverationMeasure[0]/perseverationMeasure[1]);
            recordNewLevel.child("Non Perseveration Error Rate").setValue((totalNumberErrors - perseverationMeasure[0]) /(totalTrials-perseverationMeasure[1]));
            db_perseveration_error_rate = perseverationMeasure[0]/perseverationMeasure[1];
            db_non_perseveration_error_rate = totalNumberErrors - perseverationMeasure[0] / totalTrials-perseverationMeasure[1];
        }
        recordNewLevel.child("Total Perseveration Trials").setValue(perseverationMeasure[1]);
        recordNewLevel.child("Non Perseveration Error Count").setValue(totalNumberErrors - perseverationMeasure[0]);
        recordNewLevel.child("Total Perseveration Error Count").setValue(perseverationMeasure[0]);
        db_total_perseveration_trials = perseverationMeasure[1];
        db_non_perseveration_error_count = totalNumberErrors - perseverationMeasure[0];
        db_total_perseveration_error_count = perseverationMeasure[0];

//        if(checkStepCount){
//            recordNewLevel.child("Total Step Count").setValue(mStep);
//        }

        recordNewLevel.child("Level End Time").setValue(levelEnd);
        recordNewLevel.child("Level Completed").setValue(wasCompleted);
        recordNewLevel.child("Score").setValue(level.getGameScore());
        db_level_end_time = levelEnd;
        db_level_completed = wasCompleted;
        db_score = level.getGameScore();

        if(totalHits>0){
            recordNewLevel.child("Mean Response Time").setValue(totalResponseTime/totalHits);
        }
        recordNewLevel.child("Total num of moles").setValue(allTotalCharAppearences[0]);
        recordNewLevel.child("Total num of butterflies").setValue( allTotalCharAppearences[1]);
        recordNewLevel.child("Total num of moles with hats").setValue(allTotalCharAppearences[2]);
        recordNewLevel.child("Total num of raccoons").setValue(allTotalCharAppearences[3]);
        db_total_num_moles = (int) allTotalCharAppearences[0];
        db_total_num_butterflies = (int) allTotalCharAppearences[1];
        db_total_num_moles_hats = (int) allTotalCharAppearences[2];
        db_total_num_raccoons = (int) allTotalCharAppearences[3];

        if(allTotalCharAppearences[0] == 0){
            recordNewLevel.child("Error Rate Moles").setValue(null);
        }
        else {
            recordNewLevel.child("Error Rate Moles").setValue(allTotalErrors[0] / allTotalCharAppearences[0]);
            db_error_rate_moles = allTotalErrors[0] / allTotalCharAppearences[0];
        }
        if(allTotalCharAppearences[1] == 0){
            recordNewLevel.child("Error Rate Butterflies").setValue(null);
        }else{
            recordNewLevel.child("Error Rate Butterflies").setValue(allTotalErrors[1]/allTotalCharAppearences[1]);
            db_error_rate_butterflies = allTotalErrors[1]/allTotalCharAppearences[1];
        }
        if(allTotalCharAppearences[2] == 0){
            recordNewLevel.child("Error Rate Moles with hats").setValue(null);
        }else{
            recordNewLevel.child("Error Rate Moles with hats").setValue(allTotalErrors[2]/allTotalCharAppearences[2]);
            db_error_rate_moles_hats = allTotalErrors[2]/allTotalCharAppearences[2];
        }
        if(allTotalCharAppearences[3] == 0){
            recordNewLevel.child("Error Rate Raccoons").setValue(null);
        }else {
            recordNewLevel.child("Error Rate Raccoons").setValue(allTotalErrors[3] / allTotalCharAppearences[3]);
            db_error_rate_raccoons = allTotalErrors[3] / allTotalCharAppearences[3];
        }
        recordNewLevel.child("Total Number of Trials").setValue(totalTrials);  //the total number of times any character appeared
        recordNewLevel.child("Total Number of Errors").setValue(totalNumberErrors);
        recordNewLevel.child("Overall Error Rate").setValue((totalNumberErrors/totalTrials));
        recordNewLevel.child("Level Over").setValue(true);
        db_total_number_trials = totalTrials;
        db_total_number_errors = totalNumberErrors;
        db_overall_error_rate = (float) (totalNumberErrors/totalTrials);
        db_level_over = true;

        recordNewLevel.child("Game Time Length (ms)").setValue(gameTime);
        recordNewLevel.child("Difficulty (ms)").setValue(characterExistTime);
        db_game_time_length = gameTime;
        db_difficulty = (float) characterExistTime;


        if (gameType == 2){
            recordNewLevel.child("Sequence Length").setValue(seqLength);
            db_sequence_length = seqLength;
        }

        if (frequencyValue != 1.0) {
            recordNewLevel.child("Frequency").setValue(frequencyValue);
            db_frequency = frequencyValue;
        } else {
            recordNewLevel.child("Frequency").setValue(null);
        }


        if (gridSize != 0) {
            recordNewLevel.child("Grid Size").setValue(gridSize);
            db_grid_size = gridSize;
        } else {
            recordNewLevel.child("Grid Size").setValue(null);

        }
        db_level_id = recordNewLevel.getKey();
        dbHelper.createGameSummary(db_user_id, db_difficulty, db_error_rate_moles, db_frequency,
                db_game_time_length, db_grid_size, db_level_completed, db_level_end_time, db_level_over, db_median_response_time, db_response_time_standard_deviation,
                db_non_perseveration_error_count, db_overall_error_rate, db_score, db_total_number_errors, db_total_number_trials, db_total_perseveration_error_count, db_total_perseveration_trials,
                db_total_num_moles, db_total_num_butterflies, db_total_num_moles_hats, db_total_num_raccoons, db_sequence_length,
                db_perseveration_error_rate, db_non_perseveration_error_rate,
                db_error_rate_butterflies, db_error_rate_moles_hats, db_error_rate_raccoons, db_level_id);

        for(int t = 0; t < charNum; t++){
           characterList[t].getTimeStaying().cancel();
        }
//        if(checkStepCount){
//            sensorManager.unregisterListener(this);
//        }


        Intent endGame = new Intent(this, EndGame.class);


        if(totalHits == 0){
            endGame.putExtra("Mean Rt", 0);
        }else{
            endGame.putExtra("Mean Rt", totalResponseTime/totalHits);
        }
        endGame.putExtra("sequenceNum",seqLength);
//        endGame.putExtra("CheckStepCount",checkStepCount);
        endGame.putExtra("leveId",recordNewLevel.getKey());
        endGame.putExtra("pId",pId);
        endGame.putExtra("allDisappear", allDisappear);
        endGame.putExtra("nValue",nValue);
        endGame.putExtra("GameType", gameType);
        endGame.putExtra("charNum",charNum );
        endGame.putExtra("numOfPos", 15);
        endGame.putExtra("moleNum", moleNum);
        endGame.putExtra("butterflyNum", buttflyNum);
        endGame.putExtra("mHatnum", moleHatNum);
        endGame.putExtra("racNum", raccoonNum);
        endGame.putExtra("gameTime", gameTime);
        endGame.putExtra("Score",level.getGameScore());
        endGame.putExtra("updatingChar", updatingChar);
        endGame.putExtra("characterExistTime", characterExistTime);
        endGame.putExtra("frequencyValue", frequencyValue);
        endGame.putExtra("gridSize", gridSize);
        endGame.putExtra("giveFeedBack", giveFeedBack);


        startActivity(endGame);

    }

    public void onBackPressed() {

    }




    //create instances of all Character objects that the user requests
    private Character[] createCharacters(int charNum, Level lvl, GridView gridv, int moleNum, int butterflyNum, int mHatnum, int racNum){
        Character[] charList = new Character[charNum*2];

        for (int i = 0; i < charNum; i++){
            if (moleNum != 0) {
                charList[i] = new Character(true, 0, 1, characterExistTime, lvl, gridv, i * 2,
                        gameType,nValue,seqLength,updatingChar,pId,recordNewLevel,
                        levelStart,gameTime,allDisappear,moleNum,buttflyNum,moleHatNum,raccoonNum,
                        frequencyValue, gridSize,giveFeedBack);
                moleNum--;
            }else if (butterflyNum != 0) {
                charList[i] = new Character(false, 1, -1, characterExistTime, lvl, gridv, i * 2,
                        gameType,nValue,seqLength,updatingChar,pId,recordNewLevel,
                        levelStart,gameTime,allDisappear,moleNum,buttflyNum,moleHatNum,raccoonNum,
                        frequencyValue, gridSize,giveFeedBack);
                butterflyNum--;
            }else if (mHatnum != 0) {
                charList[i] = new Character(false, 2, -1, characterExistTime, lvl, gridv, i * 2,
                        gameType,nValue,seqLength,updatingChar,pId,recordNewLevel,
                        levelStart,gameTime,allDisappear,moleNum,buttflyNum,moleHatNum,raccoonNum,
                        frequencyValue, gridSize,giveFeedBack);
                mHatnum--;
            }else if (racNum != 0) {
                charList[i] = new Character(true, 3, 2, characterExistTime, lvl, gridv, i * 2,
                        gameType,nValue,seqLength,updatingChar,pId,recordNewLevel,
                        levelStart,gameTime,allDisappear,moleNum,buttflyNum,moleHatNum,raccoonNum,
                        frequencyValue, gridSize,giveFeedBack);
                racNum--;
            }

            lvl.setCharacterPositions(charList[i].getCharacterPosition(), i*2);
        }
        return charList;

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            mStep++;
        }
        stepCounterView.setText("Step Count: " + Integer.toString(mStep));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void onFinish() {
        dbHelper.close();
    }
}

