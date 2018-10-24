package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class creates the relation with the SQLite Database Helper
 * through which queries can be SQL called.
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "users_table";
    public static final String TABLE_GAME_SUMMARY = "game_summary_table";
    public static final String TABLE_GAME_HITS = "game_hits_table";

    // Column names
    public static final String COL_USER_KEY = "_id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USER_SESSION = "session";

    public static final String COL_GAME_SUMMARY_KEY = "_id";
    public static final String COL_GAME_SUMMARY_USER_ID = "user_id";
    public static final String COL_GAME_SUMMARY_DIFFICULTY = "difficulty";
    public static final String COL_GAME_SUMMARY_ERROR_RATE_MOLES = "error_rate_moles";
    public static final String COL_GAME_SUMMARY_FREQUENCY = "frequency";
    public static final String COL_GAME_SUMMARY_GAME_TIME_LENGTH = "game_time_length";
    public static final String COL_GAME_SUMMARY_GRID_SIZE = "grid_size";
    public static final String COL_GAME_SUMMARY_LEVEL_COMPLETED = "level_completed";
    public static final String COL_GAME_SUMMARY_LEVEL_END_TIME = "level_end_time";
    public static final String COL_GAME_SUMMARY_LEVEL_OVER = "level_over";
    public static final String COL_GAME_SUMMARY_MEDIAN_RESPONSE_TIME = "median_response_time";
    public static final String COL_GAME_SUMMARY_RESPONSE_TIME_STANDARD_DEVIATION = "response_time_standard_deviation";
    public static final String COL_GAME_SUMMARY_NON_PERSERVERATION_ERROR_COUNT = "non_perseveration_error_count";
    public static final String COL_GAME_SUMMARY_OVERALL_ERROR_RATE = "overall_error_rate";
    public static final String COL_GAME_SUMMARY_SCORE = "score";
    public static final String COL_GAME_SUMMARY_TOTAL_NUMBER_ERRORS = "total_number_errors";
    public static final String COL_GAME_SUMMARY_TOTAL_NUMBER_TRIALS = "total_number_trials";
    public static final String COL_GAME_SUMMARY_TOTAL_PERSERVERATION_ERROR_COUNT = "total_perseveration_error_count";
    public static final String COL_GAME_SUMMARY_TOTAL_PERSERVERATION_TRIALS = "total_perseveration_trials";
    public static final String COL_GAME_SUMMARY_TOTAL_NUM_MOLES = "total_num_moles";
    public static final String COL_GAME_SUMMARY_TOTAL_NUM_BUTTERFLIES = "total_num_butterflies";
    public static final String COL_GAME_SUMMARY_TOTAL_NUM_MOLES_HATS = "total_num_moles_hats";
    public static final String COL_GAME_SUMMARY_TOTAL_NUM_RACCOONS = "total_num_raccoons";
    public static final String COL_GAME_SUMMARY_SEQUENCE_LENGTH = "sequence_length";
    public static final String COL_GAME_SUMMARY_PERSEVERATION_ERROR_RATE = "preseveration_error_rate";
    public static final String COL_GAME_SUMMARY_NON_PERSEVERATION_ERROR_RATE = "non_preseveration_error_rate";
    public static final String COL_GAME_SUMMARY_ERROR_RATE_BUTTERFLIES = "error_rate_butterflies";
    public static final String COL_GAME_SUMMARY_ERROR_RATE_MOLES_HATS = "error_rate_moles_hats";
    public static final String COL_GAME_SUMMARY_ERROR_RATE_RACCOONS = "error_rate_raccoons";
    public static final String COL_GAME_SUMMARY_LEVEL_ID = "level_id";

    public static final String COL_GAME_HITS_KEY = "_id";
    public static final String COL_GAME_HITS_USER_ID = "user_id";
    public static final String COL_GAME_HITS_APPEAR_TIME = "appear_time";
    public static final String COL_GAME_HITS_CHARACTER_TYPE = "character_type";
    public static final String COL_GAME_HITS_DELTA_DISTANCE = "delta_distance";
    public static final String COL_GAME_HITS_RESPONSE_TIME = "response_time";
    public static final String COL_GAME_HITS_DISAPPEAR_TIME = "disappear_time";
    public static final String COL_GAME_HITS_EF_STYLE = "ef_style";
    public static final String COL_GAME_HITS_ERROR = "error";
    public static final String COL_GAME_HITS_HIT_GAME_TIME = "hit_game_time";
    public static final String COL_GAME_HITS_IS_TARGET = "is_target";
    public static final String COL_GAME_HITS_LEVEL_ID = "level_id";
    public static final String COL_GAME_HITS_LEVEL_OVER = "level_over";
    public static final String COL_GAME_HITS_NUM_MOLES_ON_SCREEN = "num_moles_on_screen";
    public static final String COL_GAME_HITS_NUM_BUTTERFLIES_ON_SCREEN = "num_butterflies_on_screen";
    public static final String COL_GAME_HITS_NUM_MOLES_HATS_ON_SCREEN = "num_moles_hats_on_screen";
    public static final String COL_GAME_HITS_NUM_RACCOONS_ON_SCREEN = "num_raccoons_on_screen";
    public static final String COL_GAME_HITS_NUM_COLUMNS = "num_columns";
    public static final String COL_GAME_HITS_NUM_ROWS = "num_rows";
    public static final String COL_GAME_HITS_PERSEVERATION_TRIAL = "perseveration_trial";
    public static final String COL_GAME_HITS_PERSEVERATION_ERROR = "perseveration_error";
    public static final String COL_GAME_HITS_SIGNAL_DETECTION = "signal_detection";
    public static final String COL_GAME_HITS_SPAWN_POSITION = "spawn_position";
    public static final String COL_GAME_HITS_TOUCH_DOWN_X = "touch_down_x";
    public static final String COL_GAME_HITS_TOUCH_DOWN_Y = "touch_down_y";
    public static final String COL_GAME_HITS_TOUCH_UP_X = "touch_up_x";
    public static final String COL_GAME_HITS_TOUCH_UP_Y = "touch_up_y";
    public static final String COL_GAME_HITS_TOUCH_SIZE = "touch_size";
    public static final String COL_GAME_HITS_TOUCH_PRESSURE = "touch_pressure";
    public static final String COL_GAME_HITS_WAS_HIT = "was_hit";
    public static final String COL_GAME_HITS_WAVES = "waves";
    public static final String COL_GAME_HITS_CHARACTER_STRING = "character_string";
    public static final String COL_GAME_HITS_N_BACK = "n_back";
    public static final String COL_GAME_HITS_N_BACK_TYPE = "n_back_type";
    public static final String COL_GAME_HITS_HIT_TIME = "hit_time";
    public static final String COL_GAME_HITS_LEVEL_START_TIME = "level_start_time";

    private static DatabaseHelper mInstance = null;

    public static DatabaseHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Database Helper constructor.
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database tables.
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
                + COL_USER_KEY + " INTEGER PRIMARY KEY autoincrement,"
                + COL_USER_ID + " TEXT UNIQUE not null,"
                + COL_USER_SESSION + " INTEGER not null"
                + ")";

        String CREATE_TABLE_GAME_SUMMARY = "CREATE TABLE " + TABLE_GAME_SUMMARY + "("
                + COL_GAME_SUMMARY_KEY + " INTEGER PRIMARY KEY autoincrement not null,"
                + COL_GAME_SUMMARY_USER_ID + " TEXT not null,"
                + COL_GAME_SUMMARY_DIFFICULTY + " DOUBLE,"
                + COL_GAME_SUMMARY_ERROR_RATE_MOLES + " DOUBLE,"
                + COL_GAME_SUMMARY_FREQUENCY + " DOUBLE,"
                + COL_GAME_SUMMARY_GAME_TIME_LENGTH + " DOUBLE,"
                + COL_GAME_SUMMARY_GRID_SIZE + " INTEGER,"
                + COL_GAME_SUMMARY_LEVEL_COMPLETED + " BOOLEAN,"
                + COL_GAME_SUMMARY_LEVEL_END_TIME + " TEXT,"
                + COL_GAME_SUMMARY_LEVEL_OVER + " BOOLEAN,"
                + COL_GAME_SUMMARY_MEDIAN_RESPONSE_TIME + " DOUBLE,"
                + COL_GAME_SUMMARY_RESPONSE_TIME_STANDARD_DEVIATION + " DOUBLE,"
                + COL_GAME_SUMMARY_NON_PERSERVERATION_ERROR_COUNT + " DOUBLE,"
                + COL_GAME_SUMMARY_OVERALL_ERROR_RATE + " FLOAT,"
                + COL_GAME_SUMMARY_SCORE + " INTEGER,"
                + COL_GAME_SUMMARY_TOTAL_NUMBER_ERRORS + " DOUBLE,"
                + COL_GAME_SUMMARY_TOTAL_NUMBER_TRIALS + " DOUBLE,"
                + COL_GAME_SUMMARY_TOTAL_PERSERVERATION_ERROR_COUNT + " DOUBLE,"
                + COL_GAME_SUMMARY_TOTAL_PERSERVERATION_TRIALS + " DOUBLE,"
                + COL_GAME_SUMMARY_TOTAL_NUM_MOLES + " INTEGER,"
                + COL_GAME_SUMMARY_TOTAL_NUM_BUTTERFLIES + " INTEGER,"
                + COL_GAME_SUMMARY_TOTAL_NUM_MOLES_HATS + " INTEGER,"
                + COL_GAME_SUMMARY_TOTAL_NUM_RACCOONS + " INTEGER,"
                + COL_GAME_SUMMARY_SEQUENCE_LENGTH + " INTEGER,"
                + COL_GAME_SUMMARY_PERSEVERATION_ERROR_RATE + " DOUBLE,"
                + COL_GAME_SUMMARY_NON_PERSEVERATION_ERROR_RATE + " DOUBLE,"
                + COL_GAME_SUMMARY_ERROR_RATE_BUTTERFLIES + " DOUBLE,"
                + COL_GAME_SUMMARY_ERROR_RATE_MOLES_HATS + " DOUBLE,"
                + COL_GAME_SUMMARY_ERROR_RATE_RACCOONS + " DOUBLE,"
                + COL_GAME_SUMMARY_LEVEL_ID  + " TEXT"
                + ")";

        String CREATE_TABLE_GAME_HITS = "CREATE TABLE " + TABLE_GAME_HITS + "("
                + COL_GAME_HITS_KEY + " INTEGER PRIMARY KEY autoincrement not null,"
                + COL_GAME_HITS_USER_ID + " TEXT not null,"
                + COL_GAME_HITS_APPEAR_TIME + " TEXT,"
                + COL_GAME_HITS_CHARACTER_TYPE + " INTEGER,"
                + COL_GAME_HITS_RESPONSE_TIME + " DOUBLE,"
                + COL_GAME_HITS_DELTA_DISTANCE + " DOUBLE,"
                + COL_GAME_HITS_DISAPPEAR_TIME + " TEXT ,"
                + COL_GAME_HITS_EF_STYLE + " INTEGER,"
                + COL_GAME_HITS_ERROR + " BOOLEAN,"
                + COL_GAME_HITS_HIT_GAME_TIME + " DOUBLE,"
                + COL_GAME_HITS_IS_TARGET + " BOOLEAN,"
                + COL_GAME_HITS_LEVEL_ID + " TEXT,"
                + COL_GAME_HITS_LEVEL_OVER + " BOOLEAN,"
                + COL_GAME_HITS_NUM_MOLES_ON_SCREEN + " INTEGER,"
                + COL_GAME_HITS_NUM_BUTTERFLIES_ON_SCREEN + " INTEGER,"
                + COL_GAME_HITS_NUM_MOLES_HATS_ON_SCREEN + " INTEGER,"
                + COL_GAME_HITS_NUM_RACCOONS_ON_SCREEN + " INTEGER,"
                + COL_GAME_HITS_NUM_COLUMNS + " INTEGER,"
                + COL_GAME_HITS_NUM_ROWS + " INTEGER,"
                + COL_GAME_HITS_PERSEVERATION_TRIAL + " BOOLEAN,"
                + COL_GAME_HITS_PERSEVERATION_ERROR + " BOOLEAN,"
                + COL_GAME_HITS_SIGNAL_DETECTION + " DOUBLE,"
                + COL_GAME_HITS_SPAWN_POSITION + " INTEGER,"
                + COL_GAME_HITS_TOUCH_DOWN_X + " DOUBLE,"
                + COL_GAME_HITS_TOUCH_DOWN_Y + " DOUBLE,"
                + COL_GAME_HITS_TOUCH_UP_X + " DOUBLE,"
                + COL_GAME_HITS_TOUCH_UP_Y + " DOUBLE,"
                + COL_GAME_HITS_TOUCH_SIZE + " DOUBLE,"
                + COL_GAME_HITS_TOUCH_PRESSURE + " DOUBLE,"
                + COL_GAME_HITS_WAS_HIT + " BOOLEAN,"
                + COL_GAME_HITS_WAVES + " BOOLEAN,"
                + COL_GAME_HITS_CHARACTER_STRING + " TEXT,"
                + COL_GAME_HITS_N_BACK + " INTEGER,"
                + COL_GAME_HITS_N_BACK_TYPE + " TEXT, "
                + COL_GAME_HITS_HIT_TIME + " TEXT,"
                + COL_GAME_HITS_LEVEL_START_TIME + " INTEGER"
                + ")";

        database.execSQL(CREATE_TABLE_USERS);

        database.execSQL(CREATE_TABLE_GAME_SUMMARY);

        database.execSQL(CREATE_TABLE_GAME_HITS);
    }

    /**
     * Handles the table version and the drop of a table.
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading databse from version" + oldVersion + "to "
                        + newVersion + ", which will destroy all old data");

        database.execSQL("DROP TABLE IF EXISTS TABLE_USERS");
        database.execSQL("DROP TABLE IF EXISTS TABLE_GAME_SUMMARY");
        database.execSQL("DROP TABLE IF EXISTS TABLE_GAME_HITS");

        onCreate(database);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
