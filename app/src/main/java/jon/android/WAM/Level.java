package jon.android.WAM;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import jon.android.R;

public class Level extends BaseAdapter{
    private int characterPos; //number of possible positions for a character
    private CountDownTimer gameTimer; //time left in this trial
    private int gameScore; // score of player for this trial
    private int[] characterPositions; //will contain the previous position of each instance of Character
    private Context mContext;

    private long hitTime;

    //These int's are the ids of the png file in the APK, so i might have to change it to all be bitmap drawables
    private BitmapDrawable[] mThumbIds;


    public Level(Context c, int charPos) {
        characterPos = charPos;
        mThumbIds = new BitmapDrawable[characterPos];
        for(int i = 0 ;  i < characterPos ; i ++){

            mThumbIds[i] = ((BitmapDrawable)c.getResources().getDrawable(R.drawable.hole));

        }
        mContext = c;
        //need double the numb of characters since need to store not just current position but also previous position
        characterPositions = new int[characterPos*2];

    }

    public int [] getCharacterPositions(){
       return characterPositions;
    }

    public void setCharacterPositions(int position, int character){
        characterPositions[character] = position;

    }
    public void changeGameScore(int amount){
        gameScore += amount;
    }

    public int getGameScore(){
        return gameScore;

    }

    public CountDownTimer getLevelTime(){
        return gameTimer;

    }

    public void setLevelTime(CountDownTimer cdt){
        gameTimer = cdt;

    }

    public void setTime(long gameTime){
        hitTime = gameTime;
    }
    public long getTime(){
        return hitTime;
    }



    public Context giveContext(){
        return mContext;
    }
    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setItem(Integer index, BitmapDrawable item) {
        mThumbIds[index] = item;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(175, 175));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(15, 15, 15, 15);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageDrawable(mThumbIds[position]);
        return imageView;
    }
    public int getCharacterPos(){
        return characterPos;
    }

}