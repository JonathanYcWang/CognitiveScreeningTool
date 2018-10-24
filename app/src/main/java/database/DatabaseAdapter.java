package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Adapts the database to deal with the front end.
 */

public class DatabaseAdapter {

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

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    /**
     * The adapter constructor.
     *
     * @param context
     */
    public DatabaseAdapter(Context context) {
        this.context = context;
    }


    /**
     * Creates the database helper and gets the database.
     *
     * @return
     * @throws SQLException
     */

    public DatabaseAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }


    /**
     * Closes the database.
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Creates the user.
     *
     * @param user_id The username.
     * @param session The session.
     * @return
     */
    public long createUser(String user_id, Integer session) {
        ContentValues initialValues = createUserTableContentValues(user_id, session);
        return database.insert(TABLE_USERS, null, initialValues);
    }

    public long createGameSummary(String user_id, Double difficulty,
                                  Double error_rate_moles, Double frequency,
                                  Double game_time_length, Integer grid_size,
                                  Boolean level_completed, String level_end_time,
                                  Boolean level_over, Double median_response_time,
                                  Double response_time_standard_deviation,
                                  Double non_perseveration_error_count,
                                  Double overall_error_rate, Integer score,
                                  Double total_number_errors, Double total_number_trials,
                                  Double total_perseveration_error_count, Double total_perseveration_trials,
                                  Integer total_num_moles, Integer total_num_butterflies,
                                  Integer total_num_moles_hats, Integer total_num_raccoons,
                                  Integer sequence_length,
                                  Double perseveration_error_rate, Double non_perseveration_error_rate,
                                  Double error_rate_butterflies, Double error_rate_moles_hats,
                                  Double error_rate_raccoons, String level_id
                                  ) {
        ContentValues initialValues = createGameSummaryTableContentValues(user_id, difficulty, error_rate_moles, frequency,
                game_time_length, grid_size, level_completed, level_end_time, level_over, median_response_time, response_time_standard_deviation,
                non_perseveration_error_count, overall_error_rate, score, total_number_errors, total_number_trials, total_perseveration_error_count, total_perseveration_trials,
                total_num_moles, total_num_butterflies, total_num_moles_hats, total_num_raccoons, sequence_length,
                perseveration_error_rate, non_perseveration_error_rate,
                error_rate_butterflies, error_rate_moles_hats,
                error_rate_raccoons, level_id);
        return database.insert(TABLE_GAME_SUMMARY, null, initialValues);
    }

    public long createGameHits(String user_id, String appear_time, Integer character_type,
                               Double delta_distance, Double response_time, String disappear_time, Integer ef_style,
                               Boolean error, Double hit_game_time, Boolean is_target,
                               String level_id, Boolean level_over, Integer num_moles_on_screen,
                               Integer num_butterflies_on_screen, Integer num_moles_hats_on_screen,
                               Integer num_raccoons_on_screen,
                               Integer num_columns, Integer num_rows, Boolean perseveration_trial,
                               Boolean perseveration_error,
                               Double signal_detection, Integer spawn_position, Double touch_down_x,
                               Double touch_down_y, Double touch_up_x, Double touch_up_y,
                               Double touch_size, Double touch_pressure, Boolean was_hit,
                               Boolean waves,
                               String character_string, Integer n_back, String n_back_type,
                               String hit_time, String level_start_time
                               ) {
        ContentValues initialValues = createGameHitsTableContentValues(user_id, appear_time, character_type,
                delta_distance, response_time, disappear_time, ef_style, error,
                hit_game_time, is_target, level_id, level_over, num_moles_on_screen, num_butterflies_on_screen,
                num_moles_hats_on_screen, num_raccoons_on_screen, num_columns, num_rows, perseveration_trial,
                perseveration_error,
                signal_detection, spawn_position, touch_down_x, touch_down_y, touch_up_x, touch_up_y,
                touch_size, touch_pressure, was_hit, waves,
                character_string, n_back, n_back_type, hit_time, level_start_time);
        return database.insert(TABLE_GAME_HITS, null, initialValues);
    }


    /**
     * Removes a user's details given an id.
     *
     * @param rowId Column id.
     * @return
     */
    public boolean deleteUser(long rowId) {
        return database.delete(TABLE_USERS, COL_USER_KEY + "=" + rowId, null) > 0;
    }

    public boolean deleteGamesummary(long rowId) {
        return database.delete(TABLE_GAME_SUMMARY, COL_GAME_SUMMARY_KEY + "=" + rowId, null) > 0;
    }

    public boolean deleteGameHit(long rowId) {
        return database.delete(TABLE_GAME_HITS, COL_GAME_HITS_KEY + "=" + rowId, null) > 0;
    }


    /**
     * Update table
     */
    public boolean updateUserTable(long rowId, String user_id, Integer session) {
        ContentValues updateValues = createUserTableContentValues(user_id, session);
        return database.update(TABLE_USERS, updateValues, COL_USER_KEY + "=" + rowId, null) > 0;
    }

    public boolean updateGameSummaryTable(long rowId, String user_id, Double difficulty,
                                          Double error_rate_moles, Double frequency,
                                          Double game_time_length, Integer grid_size,
                                          Boolean level_completed, String level_end_time,
                                          Boolean level_over, Double median_response_time,
                                          Double response_time_standard_deviation,
                                          Double non_perseveration_error_count,
                                          Double overall_error_rate, Integer score,
                                          Double total_number_errors, Double total_number_trials,
                                          Double total_perseveration_error_count, Double total_perseveration_trials,
                                          Integer total_num_moles, Integer total_num_butterflies,
                                          Integer total_num_moles_hats, Integer total_num_raccoons,
                                          Integer sequence_length,
                                          Double perseveration_error_rate, Double non_perseveration_error_rate,
                                          Double error_rate_butterflies, Double error_rate_moles_hats,
                                          Double error_rate_raccoons, String level_id) {
        ContentValues updateValues = createGameSummaryTableContentValues(
                user_id, difficulty, error_rate_moles, frequency,
                game_time_length, grid_size, level_completed, level_end_time, level_over, median_response_time, response_time_standard_deviation,
                non_perseveration_error_count, overall_error_rate, score, total_number_errors, total_number_trials, total_perseveration_error_count, total_perseveration_trials,
                total_num_moles, total_num_butterflies, total_num_moles_hats, total_num_raccoons, sequence_length,
                perseveration_error_rate, non_perseveration_error_rate,
                error_rate_butterflies, error_rate_moles_hats,
                error_rate_raccoons, level_id
        );
        return database.update(TABLE_GAME_SUMMARY, updateValues, COL_GAME_SUMMARY_KEY + "=" + rowId, null) > 0;
    }


    public boolean updateGameHitsTable(long rowId, String user_id, String appear_time, Integer character_type,
                                       Double delta_distance, Double response_time, String disappear_time, Integer ef_style,
                                       Boolean error, Double hit_game_time, Boolean is_target,
                                       String level_id, Boolean level_over, Integer num_moles_on_screen,
                                       Integer num_butterflies_on_screen, Integer num_moles_hats_on_screen,
                                       Integer num_raccoons_on_screen,
                                       Integer num_columns, Integer num_rows, Boolean perseveration_trial,
                                       Boolean perseveration_error,
                                       Double signal_detection, Integer spawn_position, Double touch_down_x,
                                       Double touch_down_y, Double touch_up_x, Double touch_up_y,
                                       Double touch_size, Double touch_pressure, Boolean was_hit,
                                       Boolean waves,
                                       String character_string, Integer n_back, String n_back_type,
                                       String hit_time, String level_start_time) {
        ContentValues updateValues = createGameHitsTableContentValues(
                user_id, appear_time, character_type, delta_distance, response_time, disappear_time, ef_style, error,
                hit_game_time,
                is_target, level_id, level_over, num_moles_on_screen, num_butterflies_on_screen,
                num_moles_hats_on_screen, num_raccoons_on_screen, num_columns, num_rows, perseveration_trial,
                perseveration_error,
                signal_detection, spawn_position, touch_down_x, touch_down_y, touch_up_x, touch_up_y,
                touch_size, touch_pressure, was_hit, waves,
                character_string, n_back, n_back_type,
                hit_time, level_start_time
        );
        return database.update(TABLE_GAME_HITS, updateValues, COL_GAME_HITS_KEY + "=" + rowId, null) > 0;
    }


    /**
     * Retrieves the details of all the users stored in the login table.
     *
     * @return
     */
    public Cursor fetchAllUsers() {
        return database.query(TABLE_USERS, new String[] { COL_USER_KEY, COL_USER_ID,
                COL_USER_SESSION },
                null, null, null, null, null);
    }

    public Cursor fetchAllGameSummaries() {
        return database.query(TABLE_GAME_SUMMARY, new String[] { COL_GAME_SUMMARY_KEY, COL_GAME_SUMMARY_USER_ID,
                COL_GAME_SUMMARY_DIFFICULTY, COL_GAME_SUMMARY_ERROR_RATE_MOLES,
                COL_GAME_SUMMARY_FREQUENCY, COL_GAME_SUMMARY_GAME_TIME_LENGTH,
                COL_GAME_SUMMARY_GRID_SIZE, COL_GAME_SUMMARY_LEVEL_COMPLETED,
                COL_GAME_SUMMARY_LEVEL_END_TIME, COL_GAME_SUMMARY_LEVEL_OVER,
                COL_GAME_SUMMARY_MEDIAN_RESPONSE_TIME, COL_GAME_SUMMARY_RESPONSE_TIME_STANDARD_DEVIATION,
                COL_GAME_SUMMARY_NON_PERSERVERATION_ERROR_COUNT, COL_GAME_SUMMARY_OVERALL_ERROR_RATE,
                COL_GAME_SUMMARY_SCORE, COL_GAME_SUMMARY_TOTAL_NUMBER_ERRORS, COL_GAME_SUMMARY_TOTAL_NUMBER_TRIALS,
                COL_GAME_SUMMARY_TOTAL_PERSERVERATION_ERROR_COUNT, COL_GAME_SUMMARY_TOTAL_PERSERVERATION_TRIALS,
                COL_GAME_SUMMARY_TOTAL_NUM_MOLES, COL_GAME_SUMMARY_TOTAL_NUM_BUTTERFLIES,
                COL_GAME_SUMMARY_TOTAL_NUM_MOLES_HATS, COL_GAME_SUMMARY_TOTAL_NUM_RACCOONS,
                COL_GAME_SUMMARY_SEQUENCE_LENGTH,
                        COL_GAME_SUMMARY_PERSEVERATION_ERROR_RATE, COL_GAME_SUMMARY_NON_PERSEVERATION_ERROR_RATE,
                        COL_GAME_SUMMARY_ERROR_RATE_BUTTERFLIES, COL_GAME_SUMMARY_ERROR_RATE_MOLES_HATS, COL_GAME_SUMMARY_ERROR_RATE_RACCOONS,
                        COL_GAME_SUMMARY_LEVEL_ID},
                null, null, null, null, null);
    }

    public Cursor fetchAllGameHits() {
        return database.query(TABLE_GAME_HITS, new String[] { COL_GAME_HITS_KEY, COL_GAME_HITS_USER_ID, COL_GAME_HITS_APPEAR_TIME,
                COL_GAME_HITS_CHARACTER_TYPE, COL_GAME_HITS_DELTA_DISTANCE, COL_GAME_HITS_RESPONSE_TIME,
                COL_GAME_HITS_DISAPPEAR_TIME, COL_GAME_HITS_EF_STYLE, COL_GAME_HITS_ERROR,
                COL_GAME_HITS_HIT_GAME_TIME, COL_GAME_HITS_IS_TARGET, COL_GAME_HITS_LEVEL_ID, COL_GAME_HITS_LEVEL_OVER, COL_GAME_HITS_NUM_MOLES_ON_SCREEN,
                COL_GAME_HITS_NUM_BUTTERFLIES_ON_SCREEN, COL_GAME_HITS_NUM_MOLES_HATS_ON_SCREEN,
                COL_GAME_HITS_NUM_RACCOONS_ON_SCREEN, COL_GAME_HITS_NUM_COLUMNS, COL_GAME_HITS_NUM_ROWS,
                COL_GAME_HITS_PERSEVERATION_TRIAL, COL_GAME_HITS_PERSEVERATION_ERROR, COL_GAME_HITS_SIGNAL_DETECTION, COL_GAME_HITS_SPAWN_POSITION,
                COL_GAME_HITS_TOUCH_DOWN_X, COL_GAME_HITS_TOUCH_DOWN_Y, COL_GAME_HITS_TOUCH_UP_X, COL_GAME_HITS_TOUCH_UP_Y,
                COL_GAME_HITS_TOUCH_SIZE, COL_GAME_HITS_TOUCH_PRESSURE, COL_GAME_HITS_WAS_HIT, COL_GAME_HITS_WAVES,
                COL_GAME_HITS_CHARACTER_TYPE, COL_GAME_HITS_N_BACK, COL_GAME_HITS_N_BACK_TYPE,
                COL_GAME_HITS_HIT_TIME, COL_GAME_HITS_LEVEL_START_TIME},
                null, null, null, null, null);
    }

    /**
     * Retrieves the details of a specific user, given a user_id
     *
     * @return
     */
    public Cursor fetchUser(String user_id) {
        Cursor myCursor = database.query(TABLE_USERS,
                new String[] { COL_USER_KEY, COL_USER_ID },
                COL_USER_ID + "='" + user_id + "'", null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }


    public Cursor fetchGameSummary(String user_id) {
        Cursor myCursor = database.query(TABLE_GAME_SUMMARY,
                new String[] { COL_GAME_SUMMARY_KEY, COL_GAME_SUMMARY_USER_ID,
                        COL_GAME_SUMMARY_DIFFICULTY, COL_GAME_SUMMARY_ERROR_RATE_MOLES,
                        COL_GAME_SUMMARY_FREQUENCY, COL_GAME_SUMMARY_GAME_TIME_LENGTH,
                        COL_GAME_SUMMARY_GRID_SIZE, COL_GAME_SUMMARY_LEVEL_COMPLETED,
                        COL_GAME_SUMMARY_LEVEL_END_TIME, COL_GAME_SUMMARY_LEVEL_OVER,
                        COL_GAME_SUMMARY_MEDIAN_RESPONSE_TIME, COL_GAME_SUMMARY_RESPONSE_TIME_STANDARD_DEVIATION,
                        COL_GAME_SUMMARY_NON_PERSERVERATION_ERROR_COUNT, COL_GAME_SUMMARY_OVERALL_ERROR_RATE,
                        COL_GAME_SUMMARY_SCORE, COL_GAME_SUMMARY_TOTAL_NUMBER_ERRORS, COL_GAME_SUMMARY_TOTAL_NUMBER_TRIALS,
                        COL_GAME_SUMMARY_TOTAL_PERSERVERATION_ERROR_COUNT, COL_GAME_SUMMARY_TOTAL_PERSERVERATION_TRIALS,
                        COL_GAME_SUMMARY_TOTAL_NUM_MOLES, COL_GAME_SUMMARY_TOTAL_NUM_BUTTERFLIES,
                        COL_GAME_SUMMARY_TOTAL_NUM_MOLES_HATS, COL_GAME_SUMMARY_TOTAL_NUM_RACCOONS,
                        COL_GAME_SUMMARY_SEQUENCE_LENGTH,
                COL_GAME_SUMMARY_PERSEVERATION_ERROR_RATE, COL_GAME_SUMMARY_NON_PERSEVERATION_ERROR_RATE,
                COL_GAME_SUMMARY_ERROR_RATE_BUTTERFLIES, COL_GAME_SUMMARY_ERROR_RATE_MOLES_HATS, COL_GAME_SUMMARY_ERROR_RATE_RACCOONS,
                        COL_GAME_SUMMARY_LEVEL_ID},
                COL_GAME_SUMMARY_USER_ID + "='" + user_id + "'", null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    public Cursor fetchGameHits(String user_id) {
        Cursor myCursor = database.query(TABLE_GAME_HITS,
                new String[] { COL_GAME_HITS_KEY, COL_GAME_HITS_USER_ID, COL_GAME_HITS_APPEAR_TIME,
                        COL_GAME_HITS_CHARACTER_TYPE, COL_GAME_HITS_DELTA_DISTANCE, COL_GAME_HITS_RESPONSE_TIME,
                        COL_GAME_HITS_DISAPPEAR_TIME, COL_GAME_HITS_EF_STYLE, COL_GAME_HITS_ERROR,
                        COL_GAME_HITS_HIT_GAME_TIME, COL_GAME_HITS_IS_TARGET, COL_GAME_HITS_LEVEL_ID, COL_GAME_HITS_LEVEL_OVER,
                        COL_GAME_HITS_NUM_MOLES_ON_SCREEN,
                        COL_GAME_HITS_NUM_BUTTERFLIES_ON_SCREEN, COL_GAME_HITS_NUM_MOLES_HATS_ON_SCREEN,
                        COL_GAME_HITS_NUM_RACCOONS_ON_SCREEN, COL_GAME_HITS_NUM_COLUMNS, COL_GAME_HITS_NUM_ROWS,
                        COL_GAME_HITS_PERSEVERATION_TRIAL, COL_GAME_HITS_PERSEVERATION_ERROR,
                        COL_GAME_HITS_SIGNAL_DETECTION, COL_GAME_HITS_SPAWN_POSITION,
                        COL_GAME_HITS_TOUCH_DOWN_X, COL_GAME_HITS_TOUCH_DOWN_Y, COL_GAME_HITS_TOUCH_UP_X, COL_GAME_HITS_TOUCH_UP_Y,
                        COL_GAME_HITS_TOUCH_SIZE, COL_GAME_HITS_TOUCH_PRESSURE, COL_GAME_HITS_WAS_HIT, COL_GAME_HITS_WAVES,
                        COL_GAME_HITS_CHARACTER_TYPE, COL_GAME_HITS_N_BACK, COL_GAME_HITS_N_BACK_TYPE,
                        COL_GAME_HITS_HIT_TIME, COL_GAME_HITS_LEVEL_START_TIME},
                COL_GAME_HITS_USER_ID + "='" + user_id + "'", null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }


    /**
     * Stores the username.
     *
     * Check table for existing user, only add user if they don't exist.
     *
     * */
    public long insertUserDuplicate(String user_id, Integer session) {
        ContentValues initialValues = createUserTableContentValues(user_id, session);
        return database.insertWithOnConflict(TABLE_USERS, null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
    }


    /**
     * Stores the username and password upon creation of new login details.
     *
     * @param user_id The user id.
     * @param session The session.
     * @return The entered values.
     */
    private ContentValues createUserTableContentValues(String user_id, Integer session) {
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, user_id);
        values.put(COL_USER_SESSION, session);
        return values;
    }

    private ContentValues createGameSummaryTableContentValues(String user_id, Double difficulty,
                                                              Double error_rate_moles, Double frequency,
                                                              Double game_time_length, Integer grid_size,
                                                              Boolean level_completed, String level_end_time,
                                                              Boolean level_over, Double median_response_time,
                                                              Double response_time_standard_deviation,
                                                              Double non_perseveration_error_count,
                                                              Double overall_error_rate, Integer score,
                                                              Double total_number_errors, Double total_number_trials,
                                                              Double total_perseveration_error_count, Double total_perseveration_trials,
                                                              Integer total_num_moles, Integer total_num_butterflies,
                                                              Integer total_num_moles_hats, Integer total_num_raccoons,
                                                              Integer sequence_length,
                                                              Double perseveration_error_rate, Double non_perseveration_error_rate,
                                                              Double error_rate_butterflies, Double error_rate_moles_hats,
                                                              Double error_rate_raccoons, String level_id) {
        ContentValues values = new ContentValues();
        values.put(COL_GAME_SUMMARY_USER_ID, user_id);
        values.put(COL_GAME_SUMMARY_DIFFICULTY, difficulty);
        values.put(COL_GAME_SUMMARY_ERROR_RATE_MOLES, error_rate_moles);
        values.put(COL_GAME_SUMMARY_FREQUENCY, frequency);
        values.put(COL_GAME_SUMMARY_GAME_TIME_LENGTH, game_time_length);
        values.put(COL_GAME_SUMMARY_GRID_SIZE, grid_size);
        values.put(COL_GAME_SUMMARY_LEVEL_COMPLETED, level_completed);
        values.put(COL_GAME_SUMMARY_LEVEL_END_TIME, level_end_time);
        values.put(COL_GAME_SUMMARY_LEVEL_OVER, level_over);
        values.put(COL_GAME_SUMMARY_MEDIAN_RESPONSE_TIME, median_response_time);
        values.put(COL_GAME_SUMMARY_RESPONSE_TIME_STANDARD_DEVIATION, response_time_standard_deviation);
        values.put(COL_GAME_SUMMARY_NON_PERSERVERATION_ERROR_COUNT, non_perseveration_error_count);
        values.put(COL_GAME_SUMMARY_OVERALL_ERROR_RATE, overall_error_rate);
        values.put(COL_GAME_SUMMARY_SCORE, score);
        values.put(COL_GAME_SUMMARY_TOTAL_NUMBER_ERRORS, total_number_errors);
        values.put(COL_GAME_SUMMARY_TOTAL_NUMBER_TRIALS, total_number_trials);
        values.put(COL_GAME_SUMMARY_TOTAL_PERSERVERATION_ERROR_COUNT, total_perseveration_error_count);
        values.put(COL_GAME_SUMMARY_TOTAL_PERSERVERATION_TRIALS, total_perseveration_trials);
        values.put(COL_GAME_SUMMARY_TOTAL_NUM_MOLES, total_num_moles);
        values.put(COL_GAME_SUMMARY_TOTAL_NUM_BUTTERFLIES, total_num_butterflies);
        values.put(COL_GAME_SUMMARY_TOTAL_NUM_MOLES_HATS, total_num_moles_hats);
        values.put(COL_GAME_SUMMARY_TOTAL_NUM_RACCOONS, total_num_raccoons);
        values.put(COL_GAME_SUMMARY_SEQUENCE_LENGTH, sequence_length);
        values.put(COL_GAME_SUMMARY_PERSEVERATION_ERROR_RATE, perseveration_error_rate);
        values.put(COL_GAME_SUMMARY_NON_PERSEVERATION_ERROR_RATE, non_perseveration_error_rate);
        values.put(COL_GAME_SUMMARY_ERROR_RATE_BUTTERFLIES, error_rate_butterflies);
        values.put(COL_GAME_SUMMARY_ERROR_RATE_MOLES_HATS, error_rate_moles_hats);
        values.put(COL_GAME_SUMMARY_ERROR_RATE_RACCOONS, error_rate_raccoons);
        values.put(COL_GAME_SUMMARY_LEVEL_ID, level_id);

        return values;
    }


    private ContentValues createGameHitsTableContentValues(String user_id, String appear_time, Integer character_type,
                                                           Double delta_distance, Double response_time, String disappear_time, Integer ef_style,
                                                           Boolean error, Double hit_game_time, Boolean is_target,
                                                           String level_id, Boolean level_over, Integer num_moles_on_screen,
                                                           Integer num_butterflies_on_screen, Integer num_moles_hats_on_screen,
                                                           Integer num_raccoons_on_screen,
                                                           Integer num_columns, Integer num_rows, Boolean perseveration_trial,
                                                           Boolean perseveration_error,
                                                           Double signal_detection, Integer spawn_position, Double touch_down_x,
                                                           Double touch_down_y, Double touch_up_x, Double touch_up_y,
                                                           Double touch_size, Double touch_pressure, Boolean was_hit,
                                                           Boolean waves,
                                                           String character_string, Integer n_back, String n_back_type,
                                                           String hit_time, String level_start_time
    ) {
        ContentValues values = new ContentValues();
        values.put(COL_GAME_HITS_USER_ID, user_id);
        values.put(COL_GAME_HITS_APPEAR_TIME, appear_time);
        values.put(COL_GAME_HITS_CHARACTER_TYPE, character_type);
        values.put(COL_GAME_HITS_DELTA_DISTANCE, delta_distance);
        values.put(COL_GAME_HITS_RESPONSE_TIME, response_time);
        values.put(COL_GAME_HITS_DISAPPEAR_TIME, disappear_time);
        values.put(COL_GAME_HITS_EF_STYLE, ef_style);
        values.put(COL_GAME_HITS_ERROR, error);
        values.put(COL_GAME_HITS_HIT_GAME_TIME, hit_game_time);
        values.put(COL_GAME_HITS_IS_TARGET, is_target);
        values.put(COL_GAME_HITS_LEVEL_ID, level_id);
        values.put(COL_GAME_HITS_LEVEL_OVER, level_over);
        values.put(COL_GAME_HITS_NUM_MOLES_ON_SCREEN, num_moles_on_screen);
        values.put(COL_GAME_HITS_NUM_BUTTERFLIES_ON_SCREEN, num_butterflies_on_screen);
        values.put(COL_GAME_HITS_NUM_MOLES_HATS_ON_SCREEN, num_moles_hats_on_screen);
        values.put(COL_GAME_HITS_NUM_RACCOONS_ON_SCREEN, num_raccoons_on_screen);
        values.put(COL_GAME_HITS_NUM_COLUMNS, num_columns);
        values.put(COL_GAME_HITS_NUM_ROWS, num_rows);
        values.put(COL_GAME_HITS_PERSEVERATION_TRIAL, perseveration_trial);
        values.put(COL_GAME_HITS_PERSEVERATION_ERROR, perseveration_error);
        values.put(COL_GAME_HITS_SIGNAL_DETECTION, signal_detection);
        values.put(COL_GAME_HITS_SPAWN_POSITION, spawn_position);
        values.put(COL_GAME_HITS_TOUCH_DOWN_X, touch_down_x);
        values.put(COL_GAME_HITS_TOUCH_DOWN_Y, touch_down_y);
        values.put(COL_GAME_HITS_TOUCH_UP_X, touch_up_x);
        values.put(COL_GAME_HITS_TOUCH_UP_Y, touch_up_y);
        values.put(COL_GAME_HITS_TOUCH_SIZE, touch_size);
        values.put(COL_GAME_HITS_TOUCH_PRESSURE, touch_pressure);
        values.put(COL_GAME_HITS_WAS_HIT, was_hit);
        values.put(COL_GAME_HITS_WAVES, waves);
        values.put(COL_GAME_HITS_CHARACTER_STRING, character_string);
        values.put(COL_GAME_HITS_N_BACK, n_back);
        values.put(COL_GAME_HITS_N_BACK_TYPE, n_back_type);
        values.put(COL_GAME_HITS_HIT_TIME, hit_time);
        values.put(COL_GAME_HITS_LEVEL_START_TIME, level_start_time);
        return values;
    }


}
