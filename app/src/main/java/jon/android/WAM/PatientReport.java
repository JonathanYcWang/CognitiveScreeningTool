package jon.android.WAM;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.DecimalFormat;

import database.DatabaseHelper;
import jon.android.R;
import mail.SendEmailAsyncTask;

public class PatientReport extends AppCompatActivity {
    private DatabaseHelper dbhelper;
    private SQLiteDatabase db;
    private DecimalFormat decimal;

    Intent patientInfo;
    String pId;

    TextView text_value_spark_score;
    TextView text_label_spark_score;
    private String spark_score = "";

    TextView text_value_total_games_played;
    TextView text_value_mean_response_time;
    TextView text_label_total_games_played;
    TextView text_label_mean_response_time;

    TextView text_value_total_hits;
    TextView text_value_total_go_targets_hit;
    TextView text_value_total_no_go_targets_hit;
    TextView text_label_total_go_targets_hit;
    TextView text_label_total_no_go_targets_hit;
    TextView text_label_total_hits;

    TextView text_value_total_proportion_go_targets_hit;
    TextView text_value_total_proportion_no_go_targets_hit;
    TextView text_label_total_proportion_go_targets_hit;
    TextView text_label_total_proportion_no_go_targets_hit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_report);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Go Back");
        actionBar.setDisplayUseLogoEnabled(false);

        dbhelper = new DatabaseHelper(getApplicationContext());
        db = dbhelper.getReadableDatabase();

        patientInfo = getIntent();
        pId = patientInfo.getStringExtra("pId");

        decimal = new DecimalFormat("#.##");

        /*
        Game play overview stats - row 0
         */

        text_value_spark_score = (TextView) findViewById(R.id.text_value_spark_score);
        text_label_spark_score = (TextView) findViewById(R.id.text_label_spark_score);

        try {
            text_value_spark_score.setText(String.valueOf(decimal.format(stdev())));
            spark_score = String.valueOf(decimal.format(stdev()));
        } finally {
        }

        text_label_spark_score.setText("SPARK Score");

        /*
        Game play overview stats - row 1
         */
        text_value_total_games_played = (TextView) findViewById(R.id.text_value_total_games_played);
        text_value_mean_response_time = (TextView) findViewById(R.id.text_value_average_response_time);
        text_label_total_games_played = (TextView) findViewById(R.id.text_label_total_games_played);
        text_label_mean_response_time = (TextView) findViewById(R.id.text_label_average_response_time);

        text_value_total_games_played.setText("" + decimal.format(total_games_played()));
        text_label_total_games_played.setText("Total Number of Games Played");

        text_value_mean_response_time.setText(String.valueOf(decimal.format(avg_rt())));
        text_label_mean_response_time.setText("Average Response Time (s)");

        /*
        Game play overview stats - row 2
         */
        text_value_total_hits = (TextView) findViewById(R.id.text_value_total_hits);
        text_value_total_go_targets_hit = (TextView) findViewById(R.id.text_value_total_go_targets_hit);
        text_value_total_no_go_targets_hit = (TextView) findViewById(R.id.text_value_total_no_go_targets_hit);
        text_label_total_hits = (TextView) findViewById(R.id.text_label_total_hits);
        text_label_total_go_targets_hit = (TextView) findViewById(R.id.text_label_total_go_targets_hit);
        text_label_total_no_go_targets_hit = (TextView) findViewById(R.id.text_label_total_no_go_targets_hit);

        text_label_total_hits.setText("Total Hits");
        text_label_total_go_targets_hit.setText("Total Targets Hit");
        text_label_total_no_go_targets_hit.setText("Total Non-Targets Hit");

        text_value_total_hits.setText(decimal.format(total_hits()));
        text_value_total_go_targets_hit.setText(decimal.format(total_go_targets()));
        text_value_total_no_go_targets_hit.setText(decimal.format(total_no_go_targets()));


         /*
        Game play overview stats - row 3
         */
        text_value_total_proportion_go_targets_hit = (TextView) findViewById(R.id.text_value_total_proportion_go_targets_hits);
        text_value_total_proportion_no_go_targets_hit = (TextView) findViewById(R.id.text_value_total_proportion_no_go_targets_hits);
        text_label_total_proportion_go_targets_hit = (TextView) findViewById(R.id.text_label_total_proportion_go_targets_hits);
        text_label_total_proportion_no_go_targets_hit = (TextView) findViewById(R.id.text_label_total_proportion_no_go_targets_hits);

        text_label_total_proportion_go_targets_hit.setText("Total Proportion of Targets Hit");
        text_label_total_proportion_no_go_targets_hit.setText("Total Proportion of Non-Targets Hit");

        text_value_total_proportion_go_targets_hit.setText(decimal.format((double) total_go_targets() / (double) total_hits()));
        text_value_total_proportion_no_go_targets_hit.setText(decimal.format((double) total_no_go_targets() / (double) total_hits()));

        sendEmail();
    }



    private double stdev() {

        double stdev = 0.0;
        double mean = 0.0;
        double n = 0.0;
        double sum_squares = 0.0;

        Cursor curTotal = db.rawQuery("SELECT COUNT(response_time) FROM game_hits_table " +
                "WHERE user_id = ?", new String[]{pId});

        if (curTotal.moveToNext()) {
            if (curTotal.getString(0) != null) {
                n = Double.parseDouble(curTotal.getString(0));
            }
        }

        Cursor curAvg = db.rawQuery("SELECT AVG(response_time) FROM (SELECT response_time FROM game_hits_table " +
                "WHERE user_id = ?)", new String[]{pId});

        Cursor curAvgValue = db.rawQuery("SELECT response_time FROM game_hits_table " +
                "WHERE user_id = ?", new String[]{pId});

        if (curAvg.moveToNext()) {
            if (curAvg.getString(0) != null) {
                mean = Double.parseDouble(curAvg.getString(0)) / 1000;
            }
        }
        while (curAvgValue.moveToNext()) {
            double diff = Double.parseDouble(curAvgValue.getString(0)) / 1000 - mean;
            sum_squares += Math.pow(diff, 2);
        }

        curAvg.close();
        curTotal.close();
        curAvgValue.close();
        stdev = Math.sqrt(sum_squares / n);

        text_value_spark_score.setBackgroundResource(R.drawable.circle_spark);
        GradientDrawable gd = (GradientDrawable) text_value_spark_score.getBackground().getCurrent();

        if (stdev < 0.61) {
            gd.setColor(Color.parseColor("#C0FF8C"));
            gd.setStroke(2, Color.parseColor("#C0FF8C"));
        } else if (stdev < 0.71) {
            gd.setColor(Color.parseColor("#FFF78C"));
            gd.setStroke(2, Color.parseColor("#FFF78C"));
        } else {
            gd.setColor(Color.parseColor("#FF8C9D"));
            gd.setStroke(2, Color.parseColor("#FF8C9D"));
        }
        return stdev;
    }


    private int total_games_played() {
        int total_games = 0;

        Cursor curTotal = db.rawQuery("SELECT COUNT(level_id) FROM game_summary_table " +
                "WHERE user_id = ?", new String[]{pId});

        if (curTotal.moveToNext()) {
            if (curTotal.getString(0) != null) {
                total_games = Integer.parseInt(curTotal.getString(0));
            }
        }
        return total_games;
    }


    private float avg_rt() {
        float avg_rt = 0;

        Cursor curAvg = db.rawQuery("SELECT AVG(response_time) FROM game_hits_table " +
                "WHERE response_time > 0 AND user_id = ?", new String[]{pId});

        if (curAvg.moveToNext()) {
            if (curAvg.getString(0) != null) {
                avg_rt = Float.parseFloat(curAvg.getString(0)) / 1000;
            }
        }

        return avg_rt;
    }


    private int total_go_targets() {
        int total_go_targets = 0;

        Cursor curAvg = db.rawQuery("SELECT SUM(total_num_moles) + " +
                "SUM(total_num_raccoons) FROM game_summary_table " +
                "WHERE user_id = ?", new String[]{pId});

        if (curAvg.moveToNext()) {
            if (curAvg.getString(0) != null) {
                total_go_targets = Integer.parseInt(curAvg.getString(0));
            }
        }

        return total_go_targets;
    }


    private int total_no_go_targets() {
        int total_no_go_targets = 0;

        Cursor curAvg = db.rawQuery("SELECT SUM(total_num_butterflies) + " +
                "SUM(total_num_moles_hats) FROM game_summary_table " +
                "WHERE user_id = ?", new String[]{pId});

        if (curAvg.moveToNext()) {
            if (curAvg.getString(0) != null) {
                total_no_go_targets = Integer.parseInt(curAvg.getString(0));
            }
        }

        return total_no_go_targets;
    }


    private int total_hits() {
        int total_hits = 0;

        total_hits = total_go_targets() + total_no_go_targets();
        return total_hits;
    }


    private void sendEmail() {
        /* Send email without user interaction
        Attach spark score
        */
        String email_subject =  pId + "," + spark_score;
        String email_contents = email_subject;

        SendEmailAsyncTask email = new SendEmailAsyncTask(email_subject, email_contents);
        email.execute();
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
