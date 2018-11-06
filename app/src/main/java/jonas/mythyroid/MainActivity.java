package jonas.mythyroid;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class MainActivity extends AppCompatActivity {

    ListView lst;
    DbCon dbcon;
    DbCon.DbHelper dbHelper;
    public static final String PREFS_NAME2 = "FirstOpen";
    public static SharedPreferences settings2;

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firstOpen();

        try {
            dbcon = new DbCon(this);  // open & create Database
                dbcon.open();
            }
            catch (Exception e) {}

            Intent intent = getIntent(); //getIntents from New_Entry Class
            Bundle bd = intent.getExtras();
            if (bd != null) {
                String errorShowPlot = (String) bd.get("ErrorShowPlot");
                if (errorShowPlot.matches("errorshowplot"))
                    noValueError();
                else {
                    String getDATE = (String) bd.get("DATE");
                    String getTSH = (String) bd.get("TSH");
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("TSH: ");
                    stringBuilder.append(getTSH);
                    String TSHwithString = stringBuilder.toString();
                    String getFT4 = (String) bd.get("FT4");
                    String getFT4_UPPER_VALUE = (String) bd.get("FT4_UPPER_VALUE");
                    String getFT4_LOWER_VALUE = (String) bd.get("FT4_LOWER_VALUE");
                    String getFT3 = (String) bd.get("FT3");
                    String getFT3_UPPER_VALUE = (String) bd.get("FT3_UPPER_VALUE");
                    String getFT3_LOWER_VALUE = (String) bd.get("FT3_LOWER_VALUE");
                    String getCONSTITUION = (String) bd.get("CONSTITUTION");
                    String getCOMMENT = (String) bd.get("COMMENT");
                    String getFt4Percentage = (String) bd.get("FT4PERCENTAGE");
                    String getFt3Percentage = (String) bd.get("FT3PERCENTAGE");
                    String getFt4Percentagewithoutstring = (String) bd.get("FT4PERCENTAGEWITHOUTSTRING");
                    String getFt3Percentagewithoutstring = (String) bd.get("FT3PERCENTAGEWITHOUTSTRING");
                    dbcon.insert(getDATE, TSHwithString, getFT4, getFT4_UPPER_VALUE, getFT4_LOWER_VALUE, getFT3, getFT3_UPPER_VALUE, getFT3_LOWER_VALUE, getCONSTITUION, getCOMMENT, getTSH, getFt4Percentage, getFt3Percentage, getFt4Percentagewithoutstring, getFt3Percentagewithoutstring);
                }
            }

            load();

            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Cursor c = dbcon.selected(id);
                    Intent EditEntry = new Intent(MainActivity.this, ShowDetails.class);
                    String sendFt3Percentagewithoutstring = c.getString(15);
                    String sendFt4Percentagewithoutstring = c.getString(14);
                    String sendFt3Percentage = c.getString(13);
                    String sendFt4Percentage = c.getString(12);
                    String sendTsh = c.getString(11);
                    String sendComment = c.getString(10);
                    String sendConstitution = c.getString(9);
                    String sendFt3LowerValue = c.getString(8);
                    String sendFt3UpperValue = c.getString(7);
                    String sendFt3 = c.getString(6);
                    String sendFt4LowerValue = c.getString(5);
                    String sendFt4UpperValue = c.getString(4);
                    String sendFt4 = c.getString(3);
                    String sendDate = c.getString(1);
                    String sendId = c.getString(0);
                    EditEntry.putExtra("sendFt3Percentagewithoutstring", sendFt3Percentagewithoutstring);
                    EditEntry.putExtra("sendFt4Percentagewithoutstring", sendFt4Percentagewithoutstring);
                    EditEntry.putExtra("sendFt3Percentage", sendFt3Percentage);
                    EditEntry.putExtra("sendFt4Percentage", sendFt4Percentage);
                    EditEntry.putExtra("sendTsh", sendTsh);
                    EditEntry.putExtra("sendDate", sendDate);
                    EditEntry.putExtra("sendId", sendId);
                    EditEntry.putExtra("sendFt4", sendFt4);
                    EditEntry.putExtra("sendFt4UpperValue", sendFt4UpperValue);
                    EditEntry.putExtra("sendFt4LowerValue", sendFt4LowerValue);
                    EditEntry.putExtra("sendFt3", sendFt3);
                    EditEntry.putExtra("sendFt3UpperValue", sendFt3UpperValue);
                    EditEntry.putExtra("sendFt3LowerValue", sendFt3LowerValue);
                    EditEntry.putExtra("sendConstitution", sendConstitution);
                    EditEntry.putExtra("sendComment", sendComment);
                    startActivity(EditEntry);
                }
            });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), New_Entry.class));
                }
            });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InfoActivity.class));

            }
        });

    }
    public void load() {
        Cursor cursor = null;
        try {
            cursor = dbcon.readAll();
        } catch (Exception e) {}

        String[] from = new String[]{dbHelper.FT4Percentage, dbHelper.FT3Percentage,dbHelper.DATE, dbHelper.TSH, dbHelper.COMMENT, dbHelper.CONSTITUTION};
        int[] to = new int[]{R.id.tvFT4Percentage, R.id.tvFT3Percentage,R.id.tvDATE, R.id.tvTSH, R.id.tvComment, R.id.tvConstitution};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.showlist, cursor, from, to);
        adapter.notifyDataSetChanged();
        lst = (ListView) findViewById(R.id.lst);
        lst.setAdapter(adapter);
    }

    public void firstOpen(){
        settings2 = getSharedPreferences(PREFS_NAME2, 0);
        boolean accepted = settings2.getBoolean("accepted", false);

        if( accepted ){

        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle((MainActivity.this.getString(R.string.Welcome)));
            alertDialog.setMessage(MainActivity.this.getString(R.string.To_add_new));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            settings2 = getSharedPreferences(PREFS_NAME2, 0);
                            SharedPreferences.Editor editor = settings2.edit();
                            editor.putBoolean("accepted", true);
                            editor.commit();
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
                }
    }

    public void noValueError(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(MainActivity.this.getString(R.string.Error_no_value));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }



}


