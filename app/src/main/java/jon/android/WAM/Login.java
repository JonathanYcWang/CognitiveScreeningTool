package jon.android.WAM;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import database.DatabaseAdapter;
import jon.android.R;


public class Login extends AppCompatActivity {

    DatabaseReference mRootRef;
    String pid_input;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    private DatabaseAdapter dbHelper;

    FirebaseDatabase fb = FirebaseDatabase.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        fb.setPersistenceEnabled(true);
        mRootRef = fb.getReference().child("Users");

        dbHelper = new DatabaseAdapter(this);
        dbHelper.open();
    }


    public void Login(View view) {

        pid_input = ((EditText) findViewById(R.id.PatientID)).getText().toString();

        dbHelper.insertUserDuplicate(pid_input, 0);

        Intent startGame = new Intent(getApplicationContext(), GameType.class);
        startGame.putExtra("pId", pid_input);
        startActivity(startGame);

//        if(pid_input.length() != 0) {
//            if(pid_input.matches("^[^.#$[ ]]+$")) {
//                view.setEnabled(false);
//                builder = new AlertDialog.Builder(Login.this);
//                builder.setTitle("Logging in").setView(R.layout.logging);
//                builder.setCancelable(false);
//                dialog = builder.create();
//                dialog.show();
//
//
//                final DatabaseReference Patients = mRootRef.child(pid_input);
//                Patients.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                         dialog.dismiss();
//                        if (dataSnapshot.getValue() != null) {
//
//
//                            Patient p = dataSnapshot.getValue(Patient.class);
//                            DatabaseReference Patients = mRootRef.child(pid_input);
//                            Patients.child("Session").setValue(p.getSession() + 1);
//
//
//
//                            Intent startGame = new Intent(getApplicationContext(), GameType.class);
//                            Toast.makeText(getApplicationContext(), "Log in successful!", Toast.LENGTH_SHORT).show();
//                            startGame.putExtra("pId", pid_input);
//                            startActivity(startGame);
//                            (findViewById(R.id.Login)).setEnabled(true);
//
//                        } else {
//                            AlertDialog.Builder register = new AlertDialog.Builder(Login.this);
//                            register.setPositiveButton("Register", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    Register(findViewById( R.id.Register));
//                                }
//                            });
//                            register.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int id) {
//                                }
//                            });
//                            register.setTitle("Unregistered patient ID").setView(R.layout.dialog).create().show();
//                            (findViewById(R.id.Login)).setEnabled(true);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }else {
//                new AlertDialog.Builder(Login.this).setTitle("Patient ID cannot contain . # $ [ ] or space").setView(R.layout.dialog).create().show();
//                view.setEnabled(true);
//            }
//        }else{
//            new AlertDialog.Builder(Login.this).setTitle("Please enter patient ID to log in").setView(R.layout.dialog).create().show();
//            view.setEnabled(true);
//
//        }
//

    }

    public void Register(View view) {
        pid_input = ((EditText) findViewById(R.id.PatientID)).getText().toString();
        if (pid_input.length() != 0) {
            if (pid_input.matches("^[^.#$[ ]]+$")) {
                view.setEnabled(false);

                builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Registering").setView(R.layout.logging);
                builder.setCancelable(false);
                dialog = builder.create();
                dialog.show();

                final DatabaseReference Patients = mRootRef.child(pid_input);

                Patients.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dialog.dismiss();
                        if (dataSnapshot.getValue() != null) {
                            new AlertDialog.Builder(Login.this).setTitle(Login.this.getString(R.string.already_resgisterd)).setView(R.layout.dialog).create().show();
                        } else {
                            Patients.child("pId").setValue(pid_input);
                            Patients.child("Session").setValue(0);

                            dbHelper.insertUserDuplicate(pid_input, 0);

                            AlertDialog.Builder login = new AlertDialog.Builder(Login.this);

                            login.setPositiveButton(Login.this.getString(R.string.login), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Login(findViewById(R.id.Login));
                                }
                            });
                            login.setNegativeButton(Login.this.getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            login.setTitle(Login.this.getString(R.string.registered)).setView(R.layout.dialog).create().show();


                        }
                        (findViewById(R.id.Register)).setEnabled(true);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {

                view.setEnabled(true);
                new AlertDialog.Builder(Login.this).setTitle(Login.this.getString(R.string.register_error)).setView(R.layout.dialog).create().show();
            }
        } else {
            view.setEnabled(true);
            new AlertDialog.Builder(Login.this).setTitle(Login.this.getString(R.string.register_instructions2)).setView(R.layout.dialog).create().show();
            // "Please enter patient ID you wish to register"

        }

    }

    void onFinish() {
//        dbHelper.close();
    }
}
