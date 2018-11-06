package jonas.mythyroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class Disclaimer extends AppCompatActivity {

    public static final String PREFS_NAME = "user_conditions";
    public static SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);

        settings = getSharedPreferences(PREFS_NAME, 0);
        boolean accepted = settings.getBoolean("accepted", false);

        ScrollView mScrollView = (ScrollView) findViewById(R.id.scrollview_disclaimer);

        mScrollView.fullScroll(View.FOCUS_DOWN);

        if( accepted ){

            startActivity(new Intent(this, MainActivity.class));

        }else{

            Button denybutton = (Button) findViewById(R.id.denyButton);
            denybutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
            Button acceptbutton = (Button) findViewById(R.id.acceptButton);
            acceptbutton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("accepted", true);
                    editor.commit();
                    startActivity(new Intent(Disclaimer.this, MainActivity.class));
                }
            });
        }

    }
}
