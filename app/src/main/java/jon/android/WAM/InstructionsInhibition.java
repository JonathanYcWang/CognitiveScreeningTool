package jon.android.WAM;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import jon.android.R;

public class InstructionsInhibition extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    private Button next, prev;
    private int gameType;
    //Keep track of which page of instructions we're on
    private int[] instructions;
    private int currImage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_instructions);
        next = (Button) findViewById(R.id.next);
        prev = (Button) findViewById(R.id.previous);
        prev.setText("Back To Menu");
        prev.setVisibility(View.VISIBLE);


        (findViewById(R.id.backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        gameType = getIntent().getIntExtra("GameType", -1);

        if(gameType == 0){
            instructions = new int[]{R.drawable.instr1, R.drawable.instr2, R.drawable.instr3};
        }else if(gameType == 1){
            instructions = new int[]{R.drawable.instructions_shifting1, R.drawable.instructions_shifting2};
        }else if(gameType == 2){
            instructions = new int[]{R.drawable.instructions_updating1, R.drawable.instructions_updating2, R.drawable.instructions_updating3};
        }

        imageSwitcher = (ImageSwitcher) findViewById(R.id.instructions_panel);


        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView currView = new ImageView(getApplicationContext());
                currView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                currView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT,
                        ImageSwitcher.LayoutParams.MATCH_PARENT));
                return currView;
            }
        });

        imageSwitcher.setImageResource(instructions[currImage]);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currImage < instructions.length - 1) {
                    currImage++;
                    imageSwitcher.setImageResource(instructions[currImage]);
                    if(currImage == instructions.length - 1){
                        next.setText("Back To Menu");

                    }else{
                        next.setVisibility(View.VISIBLE);
                    }
                    prev.setVisibility(View.VISIBLE);
                    prev.setText("Previous");
                }else {
                    onBackPressed();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currImage > 0) {
                    currImage--;
                    imageSwitcher.setImageResource(instructions[currImage]);
                    if(currImage == 0){
                        prev.setText("Back To Menu");
                    }else{
                        prev.setVisibility (View.VISIBLE);

                    }
                    next.setVisibility(View.VISIBLE);
                    next.setText("Next");
                }else {
                    onBackPressed();
                }
            }
        });




    }
    public void onBackPressed() {
        finish();
    }

}
