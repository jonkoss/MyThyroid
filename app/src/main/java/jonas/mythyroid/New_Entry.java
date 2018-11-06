package jonas.mythyroid;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

public class New_Entry extends AppCompatActivity implements
        View.OnClickListener{

    EditText tsh, ft4_upper_value, ft4, ft4_lower_value, ft3_upper_value, ft3, ft3_lower_value, constitution, comment;
    Button btnAdd, buttonDate;
    TextView Date;
    private int mYear, mMonth, mDay;
    String ft3finalresultstring, ft4finalresultstring,ft4resultroundedString, ft3resultroundedString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__entry);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ft4resultroundedString=New_Entry.this.getString(R.string.No_Value);
        ft3resultroundedString=New_Entry.this.getString(R.string.No_Value);

        Date = (TextView) findViewById(R.id.DateTxtView);
        tsh = (EditText) findViewById(R.id.tshEdit);
        ft4 = (EditText) findViewById(R.id.ft4);
        ft4_upper_value = (EditText) findViewById(R.id.ft4_upper_value);
        ft4_lower_value = (EditText) findViewById(R.id.ft4_lower_value);
        ft3 = (EditText) findViewById(R.id.ft3);
        ft3_upper_value = (EditText) findViewById(R.id.ft3_upper_value);
        ft3_lower_value = (EditText) findViewById(R.id.ft3_lower_value);
        constitution = (EditText) findViewById(R.id.constitution);
        comment = (EditText) findViewById(R.id.comment);
        btnAdd = (Button) findViewById(R.id.addBtn);
        buttonDate = (Button) findViewById(R.id.ChangeDateBtn);

        buttonDate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == buttonDate) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if (monthOfYear < 9)
                            {StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("0");
                                monthOfYear+=1;
                                stringBuilder.append(monthOfYear);
                                String monthOfYearString = stringBuilder.toString();
                                Date.setText(year + "/" + (monthOfYearString) + "/" + dayOfMonth);}
                            else
                                Date.setText(year + "/" + (monthOfYear+1) + "/" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    if (v == btnAdd)   {
        if (Date.getText().toString().equals(New_Entry.this.getString(R.string.Date)))
        {
            Toast.makeText(New_Entry.this, New_Entry.this.getString(R.string.Please_change_date),
                    Toast.LENGTH_LONG).show();
        }
        else {
            try {

                String strtsh = tsh.getText().toString();
                if(TextUtils.isEmpty(strtsh)) {
                    tsh.setError(New_Entry.this.getString(R.string.Must_contain_entry));
                    return;}

                String strft4 = ft4.getText().toString();
                if(TextUtils.isEmpty(strft4)) {
                    ft4.setText(New_Entry.this.getString(R.string.No_Value));}

                String strft4uppervalue = ft4_upper_value.getText().toString();
                if(TextUtils.isEmpty(strft4uppervalue)) {
                    ft4_upper_value.setText(New_Entry.this.getString(R.string.No_Value));}

                String strft4lowervalue = ft4_lower_value.getText().toString();
                if(TextUtils.isEmpty(strft4lowervalue)) {
                    ft4_lower_value.setText(New_Entry.this.getString(R.string.No_Value));}

                String strft3 = ft3.getText().toString();
                if(TextUtils.isEmpty(strft3)) {
                    ft3.setText(New_Entry.this.getString(R.string.No_Value));}

                String strft3uppervalue = ft3_upper_value.getText().toString();
                if(TextUtils.isEmpty(strft3uppervalue)) {
                    ft3_upper_value.setText(New_Entry.this.getString(R.string.No_Value));}

                String strft3lowervalue = ft3_lower_value.getText().toString();
                if(TextUtils.isEmpty(strft3lowervalue)) {
                    ft3_lower_value.setText(New_Entry.this.getString(R.string.No_Value));}

                String strConstitution =  constitution.getText().toString();
                if(TextUtils.isEmpty(strConstitution)) {
                    constitution.setText(New_Entry.this.getString(R.string.No_Value));}

                String strComment = comment.getText().toString();
                if(TextUtils.isEmpty(strComment)) {
                    comment.setText(New_Entry.this.getString(R.string.No_Value));}

                String strft42 = ft4.getText().toString();
                String strft4uppervalue2 = ft4_upper_value.getText().toString();
                String strft4lowervalue2 = ft4_lower_value.getText().toString();
                String strft32 = ft3.getText().toString();
                String strft3uppervalue2 = ft3_upper_value.getText().toString();
                String strft3lowervalue2 = ft3_lower_value.getText().toString();
                String strConstitution2 =  constitution.getText().toString();
                String strComment2 = comment.getText().toString();

                if(strft42.matches(New_Entry.this.getString(R.string.No_Value))|| strft4uppervalue2.matches(New_Entry.this.getString(R.string.No_Value))||strft4lowervalue2.matches(New_Entry.this.getString(R.string.No_Value)))
                {
                    ft4finalresultstring = New_Entry.this.getString(R.string.No_Value);

                    double ft3Calc = Double.parseDouble(ft3.getText().toString());
                    double ft3UpperValueCalc = Double.parseDouble(ft3_upper_value.getText().toString());
                    double ft3LowerValueCalc = Double.parseDouble(ft3_lower_value.getText().toString());
                    double ft3result = ((ft3Calc - ft3LowerValueCalc) / (ft3UpperValueCalc - ft3LowerValueCalc)) * 100;
                    double ft3resultrounded = round(ft3result, 2);
                    ft3resultroundedString = String.valueOf(ft3resultrounded);

                    String ft3resultString = String.valueOf(ft3resultrounded);
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("FT3: ");
                    stringBuilder2.append(ft3resultString);
                    stringBuilder2.append("%");
                    ft3finalresultstring = stringBuilder2.toString();

                    newIntent();

                }
                else if(strft32.matches(New_Entry.this.getString(R.string.No_Value))||strft3lowervalue2.matches(New_Entry.this.getString(R.string.No_Value))||strft3uppervalue2.matches(New_Entry.this.getString(R.string.No_Value)))
                {
                    ft3finalresultstring = New_Entry.this.getString(R.string.No_Value);

                    double ft4Calc = Double.parseDouble(ft4.getText().toString());
                    double ft4UpperValueCalc = Double.parseDouble(ft4_upper_value.getText().toString());
                    double ft4LowerValueCalc = Double.parseDouble(ft4_lower_value.getText().toString());
                    double ft4result = ((ft4Calc - ft4LowerValueCalc) / (ft4UpperValueCalc - ft4LowerValueCalc)) * 100;
                    double ft4resultrounded = round(ft4result, 2);
                    ft4resultroundedString = String.valueOf(ft4resultrounded);

                    String ft4resultString = String.valueOf(ft4resultrounded);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("FT4: ");
                    stringBuilder.append(ft4resultString);
                    stringBuilder.append("%");
                    ft4finalresultstring = stringBuilder.toString();
                    Toast.makeText(New_Entry.this, "Here2", Toast.LENGTH_SHORT).show();

                    newIntent();
                }
                else if(strft32.matches(New_Entry.this.getString(R.string.No_Value))&&strft3lowervalue2.matches(New_Entry.this.getString(R.string.No_Value))&&strft3uppervalue2.matches(New_Entry.this.getString(R.string.No_Value))&&strft42.matches(New_Entry.this.getString(R.string.No_Value))&& strft4uppervalue2.matches(New_Entry.this.getString(R.string.No_Value))&&strft4lowervalue2.matches(New_Entry.this.getString(R.string.No_Value)))
                {
                    ft3finalresultstring = New_Entry.this.getString(R.string.No_Value);
                    ft4finalresultstring = New_Entry.this.getString(R.string.No_Value);

                    newIntent();
                }
                else {
                    double ft4Calc = Double.parseDouble(ft4.getText().toString());
                    double ft4UpperValueCalc = Double.parseDouble(ft4_upper_value.getText().toString());
                    double ft4LowerValueCalc = Double.parseDouble(ft4_lower_value.getText().toString());
                    double ft4result = ((ft4Calc - ft4LowerValueCalc) / (ft4UpperValueCalc - ft4LowerValueCalc)) * 100;
                    double ft4resultrounded = round(ft4result, 2);
                    ft4resultroundedString = String.valueOf(ft4resultrounded);

                    String ft4resultString = String.valueOf(ft4resultrounded);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("FT4: ");
                    stringBuilder.append(ft4resultString);
                    stringBuilder.append("%");
                    ft4finalresultstring = stringBuilder.toString();

                    double ft3Calc = Double.parseDouble(ft3.getText().toString());
                    double ft3UpperValueCalc = Double.parseDouble(ft3_upper_value.getText().toString());
                    double ft3LowerValueCalc = Double.parseDouble(ft3_lower_value.getText().toString());
                    double ft3result = ((ft3Calc - ft3LowerValueCalc) / (ft3UpperValueCalc - ft3LowerValueCalc)) * 100;
                    double ft3resultrounded = round(ft3result, 2);
                    ft3resultroundedString = String.valueOf(ft3resultrounded);

                    String ft3resultString = String.valueOf(ft3resultrounded);
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("FT3: ");
                    stringBuilder2.append(ft3resultString);
                    stringBuilder2.append("%");
                    ft3finalresultstring = stringBuilder2.toString();

                    newIntent();
                }
            } catch (Exception e) {
            }
        }
    }
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public void newIntent()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("DATE", Date.getText().toString());
        intent.putExtra("TSH", tsh.getText().toString());
        intent.putExtra("FT4", ft4.getText().toString());
        intent.putExtra("FT4_UPPER_VALUE", ft4_upper_value.getText().toString());
        intent.putExtra("FT4_LOWER_VALUE", ft4_lower_value.getText().toString());
        intent.putExtra("FT3", ft3.getText().toString());
        intent.putExtra("FT3_UPPER_VALUE", ft3_upper_value.getText().toString());
        intent.putExtra("FT3_LOWER_VALUE", ft3_lower_value.getText().toString());
        intent.putExtra("CONSTITUTION", constitution.getText().toString());
        intent.putExtra("COMMENT", comment.getText().toString());
        intent.putExtra("FT4PERCENTAGE", ft4finalresultstring);
        intent.putExtra("FT3PERCENTAGE", ft3finalresultstring);
        intent.putExtra("FT4PERCENTAGEWITHOUTSTRING", ft4resultroundedString);
        intent.putExtra("FT3PERCENTAGEWITHOUTSTRING", ft3resultroundedString);
        intent.putExtra("ErrorShowPlot", "Alles OK");
        startActivity(intent);
    }
}



