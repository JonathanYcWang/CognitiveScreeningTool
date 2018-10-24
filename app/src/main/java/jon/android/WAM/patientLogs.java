package jon.android.WAM;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import database.DatabaseAdapter;
import jon.android.R;

public class patientLogs extends AppCompatActivity {
    String pId;
    int GameType;
    LinearLayout plog;

    private DatabaseAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_logs);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Go Back");
        actionBar.setDisplayUseLogoEnabled(false);

        Intent intent = getIntent();
        pId = intent.getStringExtra("pId");
        GameType = intent.getIntExtra("GameType", -1);
        plog = (LinearLayout) findViewById(R.id.LogInfo);

        final DatabaseReference Patient = FirebaseDatabase.getInstance().getReference().child("Hits").child(pId);

        dbHelper = new DatabaseAdapter(this);
        dbHelper.open();

        final Query level = Patient.orderByChild("EF Style").equalTo(GameType);

        level.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot election : dataSnapshot.getChildren()) {
                        TextView textView = new TextView(getApplicationContext());

                        String startTime = election.child("Appear Time").getValue(String.class);
                        String s = "Start Time: " + startTime + "\n";

                        long gameTime = election.child("Game Time Length (ms)").getValue(Long.class);
                        s += "Game Time: ";
                        int secsLeft = (int) gameTime / 1000;
                        if (secsLeft < 60) {
                            s += (secsLeft + " secs \n");
                        } else if (secsLeft == 60) {
                            s += "1:00 min \n";
                        } else if (secsLeft == 120) {
                            s += "2:00 min \n";
                        } else {
                            s += secsLeft / 60 + ":" + secsLeft % 60 + " min \n";
                        }
                        if (GameType == 2) {
                            int nBackValue = election.child("N-Back").getValue(Integer.class);
                            String nBackType = election.child("N-back Type").getValue(String.class);
                            s += "N Back: " + Integer.toString(nBackValue) + "\n";
                            s += "N Back Type: " + nBackType + "\n";
                            ;
                        } else {
                            int numMole = election.child("Num of moles on screen").getValue(Integer.class);
                            int numButterfly = election.child("Num of butterflies on screen").getValue(Integer.class);
                            int numMoleHat = election.child("Num of with hats on screen").getValue(Integer.class);
                            int numRaccoon = election.child("Num of raccoons on screen").getValue(Integer.class);
                            s += "Number of Moles: " + Integer.toString(numMole) + "\n";
                            s += "Number of ButterFlies: " + Integer.toString(numButterfly) + "\n";
                            s += "Number of Moles With Hats: " + Integer.toString(numMoleHat) + "\n";
                            s += "Number of Raccons: " + Integer.toString(numRaccoon) + "\n";
                            if (GameType == 0) {
                                boolean waves = election.child("Waves").getValue(Boolean.class);
                                if (waves) {
                                    s += "Waves: On";
                                } else {
                                    s += "Waves: Off";
                                }
                            }
                        }

                        textView.setText(s + "\n\n");
                        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        plog.addView(textView);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        displayListViewMetrics();
    }


    private void displayListViewMetrics() {
        Cursor cursor = dbHelper.fetchGameSummary(pId);

        // the columns in the database to be bound
        String[] from = new String[]{
                DatabaseAdapter.COL_GAME_SUMMARY_LEVEL_END_TIME,
                DatabaseAdapter.COL_GAME_SUMMARY_SCORE,
                DatabaseAdapter.COL_GAME_SUMMARY_MEDIAN_RESPONSE_TIME,
                DatabaseAdapter.COL_GAME_SUMMARY_RESPONSE_TIME_STANDARD_DEVIATION,
                DatabaseAdapter.COL_GAME_SUMMARY_DIFFICULTY,
                DatabaseAdapter.COL_GAME_SUMMARY_FREQUENCY,
                DatabaseAdapter.COL_GAME_SUMMARY_SEQUENCE_LENGTH
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.level_end_time,
                R.id.score,
                R.id.median_response_time,
                R.id.response_time_standard_deviation,
                R.id.difficulty,
                R.id.frequency,
                R.id.sequence_length};

        // create the adapter using the cursor pointing to the desired data
        // as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.patient_logs_row,
                cursor,
                from,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.resultsListView);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
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
