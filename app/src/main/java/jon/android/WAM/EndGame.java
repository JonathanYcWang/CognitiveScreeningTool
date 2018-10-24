package jon.android.WAM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jon.android.R;

public class EndGame extends AppCompatActivity {
    int gameType;
    int score;
    Intent intent;
    long meanRt;

    double frequencyValue;
    long gameTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView imageView1 = (ImageView)findViewById(R.id.imageScore1);
        ImageView imageView2 = (ImageView)findViewById(R.id.imageScore2);
        ImageView imageView3 = (ImageView)findViewById(R.id.imageScore3);

        TextView finalScore = (TextView)findViewById(R.id.Score);


        intent = getIntent();
        score = intent.getIntExtra("Score", 0);
        meanRt = intent.getLongExtra("Mean Rt", 0);

        if (meanRt <= 800 && meanRt > 0 ) {
            imageView1.setImageResource(R.drawable.star_orange);
            imageView2.setImageResource(R.drawable.star_orange);
            imageView3.setImageResource(R.drawable.star_orange);
        } else if (meanRt <= 1200 && meanRt > 0) {
            imageView1.setImageResource(R.drawable.star_orange);
            imageView2.setImageResource(R.drawable.star_orange);
            imageView3.setImageResource(R.drawable.star_grey);
        } else if (meanRt <= 1800 && meanRt > 0){
            imageView1.setImageResource(R.drawable.star_orange);
            imageView2.setImageResource(R.drawable.star_grey);
            imageView3.setImageResource(R.drawable.star_grey);
        }
        finalScore.setText("Final Score: "  + Integer.toString(score));
    }
    public void saveNote(View view){
        DatabaseReference recordLevel = FirebaseDatabase.getInstance().getReference().child("Hits/" + intent.getStringExtra("leveId"));
        recordLevel.child("Notes").setValue(((EditText)findViewById(R.id.notes)).getText().toString());

        Toast.makeText(getApplicationContext(), "Note Saved",Toast.LENGTH_LONG ).show();
    }



    public void replay(View view){
        Intent startGame = new Intent(this, Game.class);

        gameType = intent.getIntExtra("GameType", 0);
        frequencyValue = intent.getDoubleExtra("frequencyValue", 1.0);
        gameTime = intent.getLongExtra("gameTime", -1);
        startGame.putExtra("GameType",gameType);
        startGame.putExtra("pId",intent.getStringExtra("pId"));
        startGame.putExtra("charNum", intent.getIntExtra("charNum", 0));
        startGame.putExtra("numOfPos", intent.getIntExtra("numOfPos", 0));
        startGame.putExtra("characterExistTime", intent.getDoubleExtra("characterExistTime", 3000));
        startGame.putExtra("giveFeedBack", intent.getBooleanExtra("giveFeedBack", false));

//        startGame.putExtra("CheckStepCount",intent.getBooleanExtra("CheckStepCount",false));
         startGame.putExtra("gridSize",intent.getIntExtra("gridSize", 0));

        // Inhibition
        if (gameType == 0){
            startGame.putExtra("allDisappear",intent.getBooleanExtra("allDisappear", false));
            startGame.putExtra("gameTime", intent.getLongExtra("gameTime", 0));
            startGame.putExtra("moleNum",intent.getIntExtra("moleNum", 0));
            startGame.putExtra("butterflyNum", intent.getIntExtra("butterflyNum", 0));
            startGame.putExtra("mHatnum", intent.getIntExtra("mHatnum", 0));
            startGame.putExtra("racNum", intent.getIntExtra("racNum", 0));

            // Trial-based games only
            if(frequencyValue != 1.0) {
                startGame.putExtra("frequencyValue", frequencyValue);
                startGame.putExtra("seqLength", intent.getIntExtra("sequenceNum", 0));
                startGame.putExtra("charNum", intent.getIntExtra("charNum", 0));
            }

        }
        else if (gameType == 1){
            startGame.putExtra("gameTime", intent.getLongExtra("gameTime", 0));
            startGame.putExtra("moleNum",intent.getIntExtra("moleNum", 0));
            startGame.putExtra("butterflyNum", intent.getIntExtra("butterflyNum", 0));
        }
        else if (gameType == 2){
            startGame.putExtra("sequenceNum",intent.getIntExtra("sequenceNum", 0));
            startGame.putExtra("nValue",intent.getIntExtra("nValue", 1));
            startGame.putExtra("updatingChar",intent.getBooleanExtra("updatingChar",true));

        }
        startActivity(startGame);
        finish();
    }

    public void gameMenu(View view){
        onBackPressed();
        finish();
    }

}
