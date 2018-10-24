package jon.android.WAM;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.channels.FileChannel;

import database.DatabaseAdapter;
import database.DatabaseHelper;
import jon.android.R;


public class GameType extends AppCompatActivity {

    Intent patientInfo;
    private DatabaseAdapter dbHelper;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_type);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        patientInfo = getIntent();

        dbHelper = new DatabaseAdapter(this);
        dbHelper.open();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    //GameTypes:
    //0 - inhibition
    //1 - shifting
    //2 - updating

    //goes to pick the type of characters
    public void inhibitionGame(View view) {
        Intent goMenu = new Intent(this, MenuInhibition.class);
        goMenu.putExtra("GameType",0);
        goMenu.putExtra("pId",patientInfo.getStringExtra("pId"));
        startActivity(goMenu);
    }

    //goes right into the game
    public void shiftingGame(View view) {
        Intent startGame = new Intent(this, MenuShifting.class);
        startGame.putExtra("GameType",1);
        startGame.putExtra("pId",patientInfo.getStringExtra("pId"));
        startActivity(startGame);
    }

    public void updatingGame(View view) {
        Intent startGame = new Intent(this, MenuUpdating.class);
        startGame.putExtra("GameType",2);
        startGame.putExtra("pId",patientInfo.getStringExtra("pId"));
        startActivity(startGame);

    }

    public void understandingDelirium(View view) {
        Intent startGame = new Intent(this, UnderstandingDeliriumPamphlet.class);
        startActivity(startGame);
    }

    public void report(View view) {
        Intent startReport = new Intent(this, PatientReport.class);
        startReport.putExtra("pId",patientInfo.getStringExtra("pId"));
        startActivity(startReport);
    }

    public void logOut(View view){
        exportDB();
        exportCSVGameSummary();
        exportCSVGameHits();
        onBackPressed();
    }



    public void onBackPressed() {
        finish();
    }




    private void exportDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + this.getPackageName() + "//databases//" + "database";
                String backupDBPath = "/database";

                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                // Toast.makeText(getBaseContext(), backupDB.toString(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void exportCSVGameSummary() {
        DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());

        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        String currentPatient = patientInfo.getStringExtra("pId");


        File file = new File(exportDir, "database_game_summary" + "-" + currentPatient + ".csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM game_summary_table", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to export
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2), curCSV.getString(3), curCSV.getString(4), curCSV.getString(5),
                        curCSV.getString(6), curCSV.getString(7), curCSV.getString(8), curCSV.getString(9), curCSV.getString(10),
                        curCSV.getString(11), curCSV.getString(12), curCSV.getString(13), curCSV.getString(14), curCSV.getString(15), curCSV.getString(16), curCSV.getString(17),
                        curCSV.getString(18),curCSV.getString(19), curCSV.getString(20), curCSV.getString(21), curCSV.getString(22), curCSV.getString(23),
                        curCSV.getString(24), curCSV.getString(25), curCSV.getString(26), curCSV.getString(27), curCSV.getString(28)
                        };

                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();

            // Toast.makeText(getBaseContext(), file.toString(), Toast.LENGTH_LONG).show();
            // Toast.makeText(getApplicationContext(), "Success, export to CSV is complete.", Toast.LENGTH_SHORT).show();

        } catch (Exception sqlEx) {
            Log.e("GameType", sqlEx.getMessage(), sqlEx);
            // Toast.makeText(getApplicationContext(), "Sorry, export to CSV has failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void exportCSVGameHits() {
        DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());

        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        String currentPatient = patientInfo.getStringExtra("pId");


        File file = new File(exportDir, "database_game_hits" + "-" + currentPatient + ".csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM game_hits_table", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to export
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2), curCSV.getString(3), curCSV.getString(4), curCSV.getString(5),
                        curCSV.getString(6), curCSV.getString(7), curCSV.getString(8), curCSV.getString(9), curCSV.getString(10),
                        curCSV.getString(11), curCSV.getString(12), curCSV.getString(13), curCSV.getString(14), curCSV.getString(15), curCSV.getString(16), curCSV.getString(17),
                        curCSV.getString(18),curCSV.getString(19), curCSV.getString(20), curCSV.getString(21), curCSV.getString(22), curCSV.getString(23),
                        curCSV.getString(24), curCSV.getString(25), curCSV.getString(26), curCSV.getString(27), curCSV.getString(28),
                        curCSV.getString(29), curCSV.getString(30), curCSV.getString(31), curCSV.getString(32), curCSV.getString(33),
                        curCSV.getString(34), curCSV.getString(35)};

                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();

            // Toast.makeText(getBaseContext(), file.toString(), Toast.LENGTH_LONG).show();
            // Toast.makeText(getApplicationContext(), "Success, export to CSV is complete.", Toast.LENGTH_SHORT).show();

        } catch (Exception sqlEx) {
            Log.e("GameType", sqlEx.getMessage(), sqlEx);
            // Toast.makeText(getApplicationContext(), "Sorry, export to CSV has failed.", Toast.LENGTH_SHORT).show();
        }
    }
}
