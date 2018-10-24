package jon.android.WAM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jon.android.R;

public class PlayerStats extends AppCompatActivity {


    String pId;
    int GameType;
    List<GraphValues> graphValuesList = new ArrayList<GraphValues>();
    List<Entry> LvlMedianRtGraph = new ArrayList<Entry>();
    List<Entry> LvlSdRtGraph = new ArrayList<Entry>();
    List<Entry> timeMedianRtGraph = new ArrayList<Entry>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        Intent intent = getIntent();
        pId = intent.getStringExtra("pId");
        GameType = intent.getIntExtra("GameType", -1);

        //get the data:
        final DatabaseReference Patient = FirebaseDatabase.getInstance().getReference().child("Hits").child(pId);

        final Query level = Patient.orderByChild("EF Style").equalTo(GameType);

        level.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    for (DataSnapshot election : dataSnapshot.getChildren()) {

                        float medianRT = election.child("Median Response Time").getValue(Float.class);
                        float sdRT = election.child("Response Time Standard Deviation").getValue(Float.class);
                        long gameTime = election.child("Game Time Length (ms)").getValue(Long.class);
                        GraphValues graphValues = new GraphValues(gameTime, sdRT,medianRT,GameType);
                        if(GameType == 2){
                            int nBackValue = election.child("N-Back").getValue(Integer.class);
                            String nBackType = election.child("N-back Type").getValue(String.class);
                            graphValues.setNback(nBackValue,nBackType);
                        }else{
                            int numMole = election.child("Num of moles on screen").getValue(Integer.class);
                            int numButterfly = election.child("Num of butterflies on screen").getValue(Integer.class);
                            int numMoleHat = election.child("Num of with hats on screen").getValue(Integer.class);
                            int numRaccoon = election.child("Num of raccoons on screen").getValue(Integer.class);
                            graphValues.setInhibition(numMole,numButterfly,numMoleHat,numRaccoon);
                            if(GameType == 0){
                                boolean waves = election.child("Waves").getValue(Boolean.class);
                                graphValues.setWaves(waves);
                            }
                        }
                        graphValuesList.add(graphValues);
                    }
                    if(graphValuesList.size() > 0){
                        ScatterChart LvlMedianRt = (ScatterChart) findViewById(R.id.LvlMedianRt);


                        ScatterChart LvlSdRt = (ScatterChart) findViewById(R.id.LvlSdRt);
                        ScatterChart timeMedianRt = (ScatterChart) findViewById(R.id.timeMedianRt);


                        for (GraphValues data : graphValuesList) {
                            LvlMedianRtGraph.add(new Entry( data.getMedianRT(), data.getCondition()));
                        }
                        for (GraphValues data : graphValuesList) {
                            // turn your data into Entry objects
                            LvlSdRtGraph.add(new Entry( data.getSdRT(),data.getCondition()));
                        }
                        for (GraphValues data : graphValuesList) {
                            // turn your data into Entry objects
                            timeMedianRtGraph.add(new Entry(data.getGameTime()/1000, data.getMedianRT()));
                        }


                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int height = displayMetrics .heightPixels;
                        int width = displayMetrics.widthPixels;

                        ScatterDataSet dataSet = new ScatterDataSet(LvlMedianRtGraph, "Level and Median Rt");
                        ScatterData data = new ScatterData(dataSet);
                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        LvlMedianRt.setData(data);
                        LvlMedianRt.setLayoutParams(new LinearLayout.LayoutParams(width,height-height/4));


                        ScatterDataSet dataSet2 = new ScatterDataSet(LvlSdRtGraph, "Level and Standard Deviation Rt");
                        ScatterData data2 = new ScatterData(dataSet2);
                        dataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
                        LvlSdRt.setData(data2);
                        LvlSdRt.setLayoutParams(new LinearLayout.LayoutParams(width,height-height/4));
                        LvlSdRt.invalidate();


                        ScatterDataSet dataSet3 = new ScatterDataSet(timeMedianRtGraph, "Game Time and Median Rt");
                        ScatterData data3 = new ScatterData(dataSet3);
                        dataSet3.setColors(ColorTemplate.COLORFUL_COLORS);
                        timeMedianRt.setData(data3);
                        timeMedianRt.setLayoutParams(new LinearLayout.LayoutParams(width,height-height/4));
                        timeMedianRt.invalidate();



                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }
}
