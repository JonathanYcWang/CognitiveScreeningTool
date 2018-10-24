package jon.android.WAM;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Locale;

import jon.android.R;


public class ChooseLanguage extends AppCompatActivity {
    private static String languageVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Spinner spinner = (Spinner) findViewById(R.id.chooseLanguageSpinner);
        spinner.setPrompt("Select language");

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.language_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

            public void onItemSelected(AdapterView arg0, View arg1,
                                       int arg2, long arg3) {
                Configuration config = new Configuration();

                switch (arg2) {
                    case 0:
                        config.locale = Locale.ENGLISH;
                        languageVar = "English";
                        break;
                    case 1:
                        config.locale = Locale.CANADA_FRENCH;
                        languageVar = "French";
                        break;
                    default:
                        config.locale = Locale.ENGLISH;
                        languageVar = "English";
                        break;
                }
                getResources().updateConfiguration(config, null);
            }
        });
    }


    public void onClick(View v) {
        Intent i = new Intent(v.getContext(), Login.class);
        startActivity(i);
    }


}
