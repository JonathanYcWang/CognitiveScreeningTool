package jon.android.WAM;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.GridView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import database.DatabaseAdapter;
import jon.android.R;

import static java.lang.Math.abs;

public class Character {

    private Handler handler;

    private boolean isTarget; //true if character is a targert, false if a distractor
    private int gameType; //which game type is this character playing in

    private int[] possibleChar = {
            R.drawable.basicmole, R.drawable.basicmolecorrect, R.drawable.basicmolewrong,
            R.drawable.butterfly, R.drawable.butterflycorrect, R.drawable.butterflyincorrect,
            R.drawable.molehat, R.drawable.molehatcorrect, R.drawable.molehatincorrect,
            R.drawable.raccoon, R.drawable.raccooncorrect, R.drawable.raccoonincorrect};//all possible icons for characters

    private int sequenceLen;
    private int nTrialNum;

    final private double nBackRatio = 14.0 / 45.0;

    private int[] nBackSequence;
    private int sequencePos;
    private int nTrialCounter;
    private boolean seqOver;
    private ArrayList<Integer> notNbackPositions; //keeps track of all position that can be replaced for nback trial if there are not enough
    private boolean updatingChar;

    private int randomChar; //the random character index for updating/working memory task
    private int nValue; //how far back to track

    private final int shiftingNum = 3; //this the number of correct trials a patterns stay for the shifting task before it changes
    private int shiftingCount = 3; //the number of correct trials before a new pattern
    private int condition; //the condition which decides if character is target or not
    private int previousCondition;

    private int characterType; //which character is it (i.e. mole, butterfly, mole with hat, etc.)
    private int characterId; //the index in the list of characters in Game where this specific character is
    private int characterIcon; //which image will this character have
    private int characterHitIcon; //which image will this character have when it's hit it's a target
    private int characterWrongHit; //which image will this character have when it's hit when it's not a target
    private int hitScore; //the score user gets when Character is hit

    private int[] totalAppearences = {0, 0, 0, 0};
    private int[] errorsCounter = {0, 0, 0, 0};
    private boolean wasHit; //true if character was hit

    private int[] perseverationMeasure = {0, 0}; //index 0 is num of persv errors, index 1 is num of persv trials
    boolean isPersev;

    private boolean trialOver;
    private boolean giveFeedBack;

    private CountDownTimer timeStaying; //time the character stay on the screen

    private boolean noTimeLeft = false;
    private int CharPosition; //position in the grid view where the character appears
    private int previousPosition; //previous position in the grid view where the character appears

    private String[] letters = {"A", "E", "I", "O", "U",
            "J", "W", "T", "K", "C",
            "B", "D", "F", "G", "H",
            "L", "M", "N", "P", "Q",
            "R", "S", "U", "V", "X",
            "Y", "Z"};

    private int touchX_down; //x coordinate of down touch
    private int touchY_down; //y coordinate of down touch
    private int touchX_up;  //x coordinate of up touch
    private int touchY_up; //y coordinate of up touch
    private float hitPressure; //preasure of user hit on device
    private float hitSize; //circumference of contact on screen

    private String timeAppearedDate; //the actual time character appeared on screen
    private long timeAppeared; //data object of when character appeared

    private String timeDisappearedDate; //the actual time character disapeared from screen

    private int totalHits = 0;
    private long totalResponseTime = 0;
    private List<Long> allResponseTime = new ArrayList<>();
    private String timeHitDate; //time character was hit
    private long timeHit;

    private float deltaAccuracy;

    private int moleNum;
    private int buttflyNum;
    private int moleHatNum;
    private int raccoonNum;
    private String gameStart;
    private double gameTime;
    private boolean Waves;

    private String s; //shifitng task value on character
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");

    private Level level; //the level where the character exists in
    private GridView gridView; //the gridview where the character exists

    DatabaseReference recordNewLevel = FirebaseDatabase.getInstance().getReference().child("Hits");
    DatabaseReference recordNewCharacter;
    String Level_id;
    String pId;

    private int[] inhibitionSequence;
    private List<Integer> goPosition = new ArrayList<>();
    private List<Integer> nogoPosition = new ArrayList<>();
    private int goCount = 0;
    private int goTarget;
    private int noGoTarget;
    private double frequencyValue;
    private int gridSize;
    private int gridSizeFinal;

    private DatabaseAdapter dbHelper;
    private String db_user_id;
    private String db_appear_time;
    private int db_character_type;
    private double db_delta_distance;
    private double db_response_time;
    private String db_disappear_time;
    private int db_ef_style;
    private boolean db_error;
    private double db_hit_game_time;
    private boolean db_is_target;
    private String db_level_id;
    private boolean db_level_over;
    private int db_num_moles_on_screen;
    private int db_num_butterflies_on_screen;
    private int db_num_moles_hats_on_screen;
    private int db_num_raccoons_on_screen;
    private int db_num_columns;
    private int db_num_rows;
    private boolean db_perseveration_trial;
    private boolean db_perseveration_error;
    private double db_signal_detection;
    private int db_spawn_position;
    private double db_touch_down_x;
    private double db_touch_down_y;
    private double db_touch_up_x;
    private double db_touch_up_y;
    private double db_touch_size;
    private double db_touch_pressure;
    private boolean db_was_hit;
    private boolean db_waves;
    private String db_character_string;
    private int db_n_back;
    private String  db_n_back_type;
    private String db_hit_time;
    private String db_level_start_time;

    public Character(boolean isTar, int charType, int scr, double characterExistTime,
                     Level lvl, GridView gv, int charId, int gameT, int nVal, int seqLen,
                     boolean updatingC, String patient, DatabaseReference recordNewLvl,
                     String levelStart, double gametime, boolean allDisappear,
                     int moleN, int buttflyN, int moleHatN, int raccoonN,
                     double frequencyValue, int gridSize, boolean gFB) {
        isTarget = isTar;
        characterType = charType;
        gameType = gameT;
        handler = new Handler();
        updatingChar = updatingC;
        characterId = charId;
        pId = patient;
        moleNum = moleN;
        buttflyNum = buttflyN;
        moleHatNum = moleHatN;
        raccoonNum = raccoonN;
        gameStart = levelStart;
        gameTime = gametime;
        Waves = allDisappear;
        giveFeedBack = gFB;
        frequencyValue = frequencyValue;
        gridSizeFinal = gridSize;

        Level_id = recordNewLvl.getKey();
        recordNewCharacter = recordNewLevel.push();

        dbHelper = new DatabaseAdapter(GameType.getContext());
        dbHelper.open();

        //character types:
        //0 is mole
        //1 is butterfly
        //2 is mole with hats
        //3 is raccoon
        if (characterType == 0) {
            characterIcon = possibleChar[0];
            characterHitIcon = possibleChar[1];
            characterWrongHit = possibleChar[2];
        } else if (characterType == 1) {
            characterIcon = possibleChar[3];
            characterHitIcon = possibleChar[4];
            characterWrongHit = possibleChar[5];

        } else if (characterType == 2) {
            characterIcon = possibleChar[6];
            characterHitIcon = possibleChar[7];
            characterWrongHit = possibleChar[8];
        } else if (characterType == 3) {
            characterIcon = possibleChar[9];
            characterHitIcon = possibleChar[10];
            characterWrongHit = possibleChar[11];
        }

        totalAppearences[characterType] += 1;

        hitScore = scr;
        wasHit = false;
        trialOver = false;
        timeAppeared = System.currentTimeMillis();
        timeAppearedDate = sdf.format(new Date());

        level = lvl;
        gridView = gv;

        while (generatePosition(lvl, CharPosition = (int) (Math.random() * (level.getCharacterPos()))));
        previousPosition = CharPosition;
        timeStaying = initTimer((long) characterExistTime, 1000);
        timeStaying.start();

        /*
        gameType - describes type of executive function game
        0 = inhibition
        1 = shifting
        2 = updating
         */

        if (gameType == 0) {
            if (frequencyValue == 1.0) {
                level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(characterIcon)));
            } else {
                // Trial based games only

                if (seqLen == 0) {
                    seqLen = 10;
                }
                sequenceLen = seqLen;
                inhibitionSequence = new int[sequenceLen];
                goPosition = new ArrayList<>(); // store position of go target
                nogoPosition = new ArrayList<>(); // store position of no go target
                sequencePos = 0;
                seqOver = false;

                // check for go and no go target for inhibition trial-based game
                // by default there must be a go target and a no-go target
                if (moleNum != 0) {
                    goTarget = 0 * 3;
                } else {
                    goTarget = 3 * 3;
                }

                if (buttflyNum != 0) {
                    noGoTarget = 1 * 3;
                } else {
                    noGoTarget = 2 * 3;
                }


            /*
            generate random inhibition sequence
            0 = go,
            1 = no go
             */
                Random r = new Random();
                for (int i = 0; i < sequenceLen; i++) {
                    if (r.nextInt(2) == 0) {
                        // [0...1] [min = 0, max = 1]
                        inhibitionSequence[i] = goTarget;

                        goPosition.add(0);
                        nogoPosition.add(null);
                        goCount += 1;
                    } else {
                        inhibitionSequence[i] = noGoTarget;

                        goPosition.add(null);
                        nogoPosition.add(1);
                    }
                }

            /*
            Check if frequency is met
            Otherwise, iterate through goPosition sequence and replace by noGo
            */
                while (goCount != (int) Math.round(sequenceLen * frequencyValue)) {
                    for (int i = 0; i < sequenceLen; i++) {
                        if (goCount <= (int) Math.round(sequenceLen * frequencyValue)) {
                            // not enough go targets
                            if (goPosition.get(i) == null) {
                                goPosition.set(i, 0);
                                nogoPosition.set(i, null);
                                inhibitionSequence[i] = goTarget; // 1 gets replaced by 0
                                goCount += 1;
                                break;
                            }
                        } else {
                            // too many go targets
                            goPosition.set(i, null);
                            nogoPosition.set(i, 1);
                            inhibitionSequence[i] = noGoTarget; // 0 gets replaced by 1
                            goCount -= 1;
                            break;
                        }

                    }
                }

                // System.out.println(Arrays.toString(inhibitionSequence));
                nextInInhibitionSequence(inhibitionSequence[sequencePos]);
            }
        } else if (gameType == 1) {
            getAlphaNum();
            condition = (int) (Math.random() * 4);
            previousCondition = condition;
            setIsTarget();
            level.setItem(CharPosition, writeOnDrawable(characterIcon, s));
        } else if (gameType == 2) {
            nValue = nVal;
            sequenceLen = seqLen;
            nTrialNum = (int) (seqLen * nBackRatio);
            nBackSequence = new int[sequenceLen];
            nTrialCounter = nTrialNum;
            sequencePos = 0;
            notNbackPositions = new ArrayList<>();
            seqOver = false;


            for (int i = 0; i < sequenceLen; i++) {
                //generate original sequence

                if (updatingChar) {
                    randomChar = getRandomCharacter();
                } else {
                    //put the index of the alphabet letter in instead of the character index
                    randomChar = getAlpha();
                }

                if (i < nVal) {
                    //if it's the first n values cannot have an n back check
                    nBackSequence[i] = randomChar;
                } else {
                    if (nBackSequence[i - nVal] == randomChar) {
                        //this is an n back trial
                        if (nTrialCounter == 0) {
                            //generate a new character that is not an n back trial
                            if (updatingChar) {
                                while ((nBackSequence[i] = getRandomCharacter()) == randomChar) ;
                            } else {
                                while ((nBackSequence[i] = getAlpha()) == randomChar) ;
                            }
                        } else {
                            nTrialCounter--;
                            nBackSequence[i] = randomChar;
                        }
                    } else {
                        nBackSequence[i] = randomChar;
                        notNbackPositions.add(i);
                    }
                }
            }


            for (int i = 0; i < nTrialCounter; i++) {
                //this is the position where there is not an n back trial
                int notNbackindex = (int) (Math.random() * notNbackPositions.size());
                nBackSequence[notNbackPositions.get(notNbackindex)] = nBackSequence[notNbackPositions.get(notNbackindex) - nVal];
                notNbackPositions.remove(notNbackindex);
            }

            nextInSequence(nBackSequence[sequencePos]);
        }
    }

    private CountDownTimer initTimer(final long startTime, long interval) {
        return new CountDownTimer(startTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                trialOver = true;
                timeDisappearedDate = sdf.format(new Date());
                recordNewCharacter = recordNewLevel.push();
                recordNewCharacter.child("Error").setValue(false);
                if (isPersev) {
                    recordNewCharacter.child("Perseveration Trial").setValue(true);
                    db_perseveration_trial = true;
                } else {
                    recordNewCharacter.child("Perseveration Trial").setValue(false);
                    db_perseveration_trial = false;
                }
                recordNewCharacter.child("Character Type").setValue(characterType);
                recordNewCharacter.child("Spawn Position").setValue(CharPosition);
                recordNewCharacter.child("Appear Time").setValue(timeAppearedDate);
                recordNewCharacter.child("Hit Time").setValue(timeHitDate);
                recordNewCharacter.child("Disappear Time").setValue(timeDisappearedDate);
                recordNewCharacter.child("Is Target").setValue(IsTarget());
                recordNewCharacter.child("Was Hit ").setValue(wasHit);
                recordNewCharacter.child("Signal Detection ").setValue(signalDetection());
                recordNewCharacter.child("Touch Down X").setValue(touchX_down);
                recordNewCharacter.child("Touch Down Y").setValue(touchY_down);
                recordNewCharacter.child("Touch Up X").setValue(touchX_up);
                recordNewCharacter.child("Touch Up Y").setValue(touchY_up);
                recordNewCharacter.child("Touch Pressure").setValue(hitPressure);
                recordNewCharacter.child("Touch Size").setValue(hitSize);
                recordNewCharacter.child("Delta Distance").setValue(deltaAccuracy);
                recordNewCharacter.child("Level Id").setValue(Level_id);
                recordNewCharacter.child("EF Style").setValue(gameType);
                recordNewCharacter.child("Num of butterflies on screen").setValue(buttflyNum);
                recordNewCharacter.child("Num of moles on screen").setValue(moleNum);
                recordNewCharacter.child("Num of raccoons on screen").setValue(raccoonNum);
                recordNewCharacter.child("Num of moles with hats on screen").setValue(moleHatNum);
                recordNewCharacter.child("pId").setValue(pId);
                db_character_type = characterType;
                db_spawn_position = CharPosition;
                db_appear_time = timeAppearedDate;
                db_hit_time = timeHitDate;
                db_disappear_time = timeDisappearedDate;
                db_is_target = IsTarget();
                db_was_hit = wasHit;
                db_signal_detection = signalDetection();
                db_touch_down_x = touchX_down;
                db_touch_down_y = touchY_down;
                db_touch_up_x = touchX_up;
                db_touch_up_y = touchY_up;
                db_touch_pressure = hitPressure;
                db_touch_size = hitSize;
                db_delta_distance = deltaAccuracy;
                db_level_id = Level_id;
                db_ef_style = gameType;
                db_num_moles_on_screen = moleNum;
                db_num_butterflies_on_screen = buttflyNum;
                db_num_moles_hats_on_screen = moleHatNum;
                db_num_raccoons_on_screen = raccoonNum;
                db_user_id = pId;

                if (gridSizeFinal != 5) {
                    recordNewCharacter.child("NumColumns").setValue(gridSizeFinal);
                    recordNewCharacter.child("NumRows").setValue(gridSizeFinal);
                    db_num_columns = gridSizeFinal;
                    db_num_rows = gridSizeFinal;
                } else {
                    recordNewCharacter.child("NumColumns").setValue(3);
                    recordNewCharacter.child("NumRows").setValue(5);
                    db_num_columns = 3;
                    db_num_rows = 5;
                }

                recordNewCharacter.child("Waves").setValue(Waves);
                recordNewCharacter.child("Level Start Time").setValue(gameStart);
                recordNewCharacter.child("Level Over").setValue(false);
                recordNewCharacter.child("Hit Game Time (ms)").setValue(level.getTime());
                db_waves = Waves;
                db_level_start_time = gameStart;
                db_level_over = false;
                db_hit_game_time = level.getTime();

                if (gameType > 0) {
                    recordNewCharacter.child("Character String").setValue(s);
                    db_character_string = s;

                    if (gameType == 2) {
                        recordNewCharacter.child("N-Back").setValue(nValue);
                        db_n_back = nValue;

                        if (updatingChar) {
                            recordNewCharacter.child("N-back Type").setValue("Characters");
                            db_n_back_type = "Characters";
                        } else {
                            recordNewCharacter.child("N-back Type").setValue("Letters");
                            db_n_back_type = "Letters";
                        }
                    }
                }


                if (wasHit) {
                    recordNewCharacter.child("Response Time (ms)").setValue(getDeltaTime());
                    db_response_time = getDeltaTime();
                    totalHits++;
                    if (isTarget) {
                        if (gameType == 1) {
                            shiftingCount--;
                        }
                        level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(characterHitIcon)));
                    } else {
                        level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(characterWrongHit)));
                        errorsCounter[characterType] += 1;
                        recordNewCharacter.child("Error").setValue(true);

                        if (gameType == 1) {
                            if (isPersev) {
                                recordNewCharacter.child("Perseveration Error").setValue(true);
                                db_perseveration_error = true;

                                perseverationMeasure[0]++;
                            } else {
                                recordNewCharacter.child("Perseveration Error").setValue(false);
                                db_perseveration_error = false;
                            }

                        }
                    }

                } else {
                    if (isTarget) {
                        errorsCounter[characterType] += 1;
                        recordNewCharacter.child("Error").setValue(true);
                        db_error = true;

                        if (giveFeedBack) {
                            level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(characterWrongHit)));
                        } else {
                            level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(R.drawable.hole)));
                        }

                    } else {
                        db_error = false;
                        if (giveFeedBack) {
                            level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(characterHitIcon)));
                        } else {
                            level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(R.drawable.hole)));
                        }
                    }
                }

                dbHelper.createGameHits(db_user_id, db_appear_time, db_character_type,
                        db_delta_distance, db_response_time, db_disappear_time, db_ef_style, db_error,
                        db_hit_game_time, db_is_target, db_level_id, db_level_over, db_num_moles_on_screen, db_num_butterflies_on_screen,
                        db_num_moles_hats_on_screen, db_num_raccoons_on_screen, db_num_columns, db_num_rows, db_perseveration_trial,
                        db_perseveration_error,
                        db_signal_detection, db_spawn_position, db_touch_down_x, db_touch_down_y, db_touch_up_x, db_touch_up_y,
                        db_touch_size, db_touch_pressure, db_was_hit, db_waves,
                        db_character_string, db_n_back, db_n_back_type, db_hit_time, db_level_start_time);

                level.notifyDataSetChanged();
                gridView.invalidateViews();
                if (gameType == 2 && sequencePos == sequenceLen) {
                    seqOver = true;
                } else if (gameType == 0 && sequencePos == sequenceLen && frequencyValue != 1.0 && sequenceLen != 0) {
                    // this is for trial-based versions of the inhibition game
                    // end the game once all trials are completed in the case there is time remaining
                    seqOver = true;
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            newCharacter(level, gridView);
                            handler.removeCallbacksAndMessages(null);
                        }
                    }, 200);
                }
            }
        };
    }


    private int getRandomCharacter() {
        return 3 * (int) (Math.random() * 4);
    }


    private BitmapDrawable writeOnDrawable(int drawableId, String text) {

        Bitmap bm = BitmapFactory.decodeResource(level.giveContext().getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.WHITE);
        paint.setTextSize(325);

        Canvas canvas = new Canvas(bm);
        if (!updatingChar && gameType == 2) {

            canvas.drawText(text, canvas.getWidth() / 2 - canvas.getWidth() / 3, canvas.getHeight() - canvas.getHeight() / 4, paint);
        } else {

            canvas.drawText(text, canvas.getWidth() / 8, canvas.getHeight() - canvas.getHeight() / 4, paint);
        }

        return new BitmapDrawable(level.giveContext().getResources(), bm);
    }


    //generates a new character
    private void newCharacter(Level level, GridView gridView) {
        timeAppearedDate = sdf.format(new Date());
        timeAppeared = System.currentTimeMillis();
        level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(R.drawable.hole)));
        level.notifyDataSetChanged();
        gridView.invalidateViews();

        if (!noTimeLeft) {
            //generates a new position of the character
            while (generatePosition(level, CharPosition = (int) (Math.random() * (level.getCharacterPos()))))
                ;

            level.setCharacterPositions(CharPosition, characterId);
            level.setCharacterPositions(previousPosition, characterId + 1);
            previousPosition = CharPosition;

            if (gameType == 0) {

                // Trial based games only
                if (frequencyValue != 1.0 && sequenceLen != 0) {
                    nextInInhibitionSequence(inhibitionSequence[sequencePos]);
                } else {
                    // regular game
                    level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(characterIcon)));
                }
            }
            //only if its the shifing task do the characters get new is target values
            else if (gameType == 1) {
                getAlphaNum();
                setIsTarget();
                checkPerseveration();
                level.setItem(CharPosition, writeOnDrawable(characterIcon, s));

            } else if (gameType == 2) {
                nextInSequence(nBackSequence[sequencePos]);

            }

            totalAppearences[characterType] += 1;
            timeStaying.start();
            trialOver = false;
            wasHit = false;

        } else {

            timeStaying.cancel();
        }


    }


    private boolean generatePosition(Level level, int potentialPos) {
        //if generated number is in the level character position array then pick a new number
        for (int i = 0; i < level.getCharacterPositions().length; i++) {
            if (level.getCharacterPositions()[i] == potentialPos || potentialPos == previousPosition) {
                //its not a valid position continue the while loop until there is a valid one
                return true;
            }
        }
        //its a valid position so break out of while loop
        return false;
    }


    //for updating
    private void nextInSequence(int index) {

        if (updatingChar) {
            level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(possibleChar[index])));
            characterHitIcon = possibleChar[index + 1];
            characterWrongHit = possibleChar[index + 2];
        } else {
            s = letters[index];
            level.setItem(CharPosition, writeOnDrawable(characterIcon, letters[index]));
        }
        if (sequencePos < nValue) {
            isTarget = false;
            hitScore = -abs(hitScore);
        } else {
            if (nBackSequence[sequencePos] == nBackSequence[sequencePos - nValue]) {
                hitScore = abs(hitScore);
                isTarget = true;
            } else {
                isTarget = false;
                hitScore = -abs(hitScore);
            }
        }
        sequencePos++;
    }


    //for inhibition - trial based
    private void nextInInhibitionSequence(int index) {
        /*
        Index describes the type of character

        0 = mole
        1 = butterfly
        2 = mole with hat
        3 = raccoon
        */
        level.setItem(CharPosition, ((BitmapDrawable) level.giveContext().getResources().getDrawable(possibleChar[index])));
        characterHitIcon = possibleChar[index + 1];
        characterWrongHit = possibleChar[index + 2];

        if (index == 0 * 3 || index == 3 * 3) {
            isTarget = true;
            hitScore = abs(hitScore);
        } else {
            isTarget = false;
            hitScore = -abs(hitScore);
        }

        sequencePos++;
    }


    public boolean getSeqOver() {
        return seqOver;
    }

    private int getAlpha() {
        return (int) (Math.random() * 26);
    }


    public void setNoTimeLeft() {
        noTimeLeft = true;
    }


    //for shifting
    private void getAlphaNum() {
        s = letters[(int) (Math.random() * 10)] + Integer.toString((int) (Math.random() * 10));

    }

    private void setIsTarget() {
        //Match:
        //0 = odd
        //1 = even
        //2 = vowel
        //3 = consonant

        if (shiftingCount == 0) {
            previousCondition = condition;
            while ((condition = (int) (Math.random() * 4)) == previousCondition) ;
            shiftingCount = shiftingNum;

        }
        isTarget = false;
        if (condition > 1) {
            if ("AEIOU".indexOf(s.charAt(0)) < 0) {
                //not a vowel
                if (condition == 3) {
                    isTarget = true;
                }

            } else {
                if (condition == 2) {
                    isTarget = true;
                }
            }
        } else {
            if (s.charAt(1) % 2 == 1) {
                //odd
                if (condition == 0) {
                    isTarget = true;
                }
            } else {
                if (condition == 1) {
                    isTarget = true;
                }
            }

        }
        if (isTarget) {
            hitScore = abs(hitScore);
        } else {
            hitScore = -abs(hitScore);
        }
    }


    private void checkPerseveration() {
        isPersev = false;
        if (previousCondition > 1) {
            if ("AEIOU".indexOf(s.charAt(0)) < 0) {
                //not a vowel
                if (previousCondition == 3) {
                    isPersev = true;
                }

            } else {
                if (previousCondition == 2) {
                    isPersev = true;
                }
            }
        } else {
            if (s.charAt(1) % 2 == 1) {
                //odd
                if (previousCondition == 0) {
                    isPersev = true;
                }
            } else {
                if (previousCondition == 1) {
                    isPersev = true;
                }
            }

        }
        if (isPersev) {
            perseverationMeasure[1]++;
        }

    }

    public int[] getperseverationMeasure() {
        return perseverationMeasure;
    }


    public int getCharacterPosition() {
        return CharPosition;
    }

    public int getHitScore() {
        return hitScore;
    }

    public CountDownTimer getTimeStaying() {
        return timeStaying;
    }

    public void Hit() {
        timeHit = System.currentTimeMillis();
        ;
        timeHitDate = sdf.format(new Date());
        totalResponseTime += (timeHit - timeAppeared);
        allResponseTime.add(timeHit - timeAppeared);
        wasHit = true;

    }

    public boolean getrialOver() {
        return trialOver;
    }


    public long[] getTotalResponseTime() {
        long[] rtStats = new long[2];
        rtStats[0] = totalResponseTime;
        rtStats[1] = totalHits;
        return rtStats;
    }

    public List<Long> getAllResponseTimes() {
        return allResponseTime;
    }


    public void setTouch(int X_down, int Y_down, int X_up, int Y_up, float pressure, float size, float accuracy) {
        touchX_down = X_down;
        touchY_down = Y_down;
        touchX_up = X_up;
        touchY_up = Y_up;
        hitPressure = pressure;
        hitSize = size;
        deltaAccuracy = accuracy;


    }


    public boolean IsTarget() {
        return isTarget;
    }

    private long getDeltaTime() {

        return timeHit - timeAppeared;

    }

    public int[] getTotalAppearences() {
        return totalAppearences;
    }

    public int[] getErrorsCounter() {
        return errorsCounter;
    }

    // return 0 if hit, signal and response present
    // return 1 if miss, signal present and response absent
    // return 2 if false alarm, signal absent and respsonse present
    // return 3 if correct rejection, signal and response absent
    public int signalDetection() {
        if (wasHit) {
            if (isTarget) {
                return 0;
            }
            return 2;
        } else {
            if (isTarget) {
                return 1;
            }
            return 3;
        }
    }

}

