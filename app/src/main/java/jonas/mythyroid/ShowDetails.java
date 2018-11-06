package jonas.mythyroid;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

public class ShowDetails extends Activity implements
        View.OnClickListener{

    EditText tshEditDetail, ft4Detail, ft4_upper_valueDetail, ft4_lower_valueDetail, ft3Detail, ft3_upper_valueDetail, ft3_lower_valueDetail, constitutionDetail, commentDetail;
    TextView DateTxtViewDetail, tvFT4PercentageProgressBar, tvFT3PercentageProgressBar;
    Button btnDelete, btnUpdate, ChangeDateBtnDetail;
    DbCon dbCon = new DbCon(this);
    int ft4percentagewithoutstringint,ft3percentagewithoutstringint;
    SeekBar seekBarFt4, seekBarFt3;
    private int mYear, mMonth, mDay;
    String id,date, tsh,ft4, ft4_upper, ft4_lower, ft3, ft3_upper, ft3_lower, constitution, comment, TSHwithString, ft4finalresultstring, ft3finalresultstring, ft4PercentageString, ft3PercentageString, Ft4PercentageStringwithoutPercent, Ft3PercentageStringwithoutPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        seekBarFt4 = (SeekBar) findViewById(R.id.seekbarFt4);
        seekBarFt3 = (SeekBar) findViewById(R.id.seekbarFt3) ;
        DateTxtViewDetail = (TextView) findViewById(R.id.DateTxtViewDetail);
        tvFT4PercentageProgressBar = (TextView) findViewById(R.id.tvFT4PercentageProgressBar);
        tvFT3PercentageProgressBar = (TextView) findViewById(R.id.tvFT3PercentageProgressBar);
        tshEditDetail = (EditText) findViewById(R.id.tshEditDetail);
        ft4Detail = (EditText) findViewById(R.id.ft4Detail);
        ft4_upper_valueDetail = (EditText) findViewById(R.id.ft4_upper_valueDetail);
        ft4_lower_valueDetail = (EditText) findViewById(R.id.ft4_lower_valueDetail);
        ft3Detail = (EditText) findViewById(R.id.ft3Detail);
        ft3_upper_valueDetail = (EditText) findViewById(R.id.ft3_upper_valueDetail);
        ft3_lower_valueDetail = (EditText) findViewById(R.id.ft3_lower_valueDetail);
        constitutionDetail = (EditText) findViewById(R.id.constitutionDetail);
        commentDetail = (EditText) findViewById(R.id.commentDetail);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        ChangeDateBtnDetail = (Button) findViewById(R.id.ChangeDateBtnDetail);

        Intent intent = getIntent();
        date = intent.getStringExtra("sendDate");
        tsh = intent.getStringExtra("sendTsh");
        id = intent.getStringExtra("sendId");
        ft4 = intent.getStringExtra("sendFt4");
        ft4_upper = intent.getStringExtra("sendFt4UpperValue");
        ft4_lower = intent.getStringExtra("sendFt4LowerValue");
        ft3 = intent.getStringExtra("sendFt3");
        ft3_upper = intent.getStringExtra("sendFt3UpperValue");
        ft3_lower = intent.getStringExtra("sendFt3LowerValue");
        constitution = intent.getStringExtra("sendConstitution");
        comment = intent.getStringExtra("sendComment");
        ft4PercentageString = intent.getStringExtra("sendFt4Percentage");
        ft3PercentageString = intent.getStringExtra("sendFt3Percentage");
        Ft4PercentageStringwithoutPercent = intent.getStringExtra("sendFt4Percentagewithoutstring");
        Ft3PercentageStringwithoutPercent = intent.getStringExtra("sendFt3Percentagewithoutstring");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TSH: ");
        stringBuilder.append(tsh);
        TSHwithString = stringBuilder.toString();

        if(!ft4PercentageString.matches(ShowDetails.this.getString(R.string.No_Value))&& !ft3PercentageString.matches(ShowDetails.this.getString(R.string.No_Value))){
        double valueft4 = Double.parseDouble(Ft4PercentageStringwithoutPercent);
        ft4percentagewithoutstringint = (int)valueft4;
        double valueft3 = Double.parseDouble(Ft3PercentageStringwithoutPercent);
        ft3percentagewithoutstringint = (int)valueft3;
        seekBarFt4.setProgress(ft4percentagewithoutstringint);
        seekBarFt3.setProgress(ft3percentagewithoutstringint);
        seekBarFt4.setEnabled(false);
        seekBarFt3.setEnabled(false);}

        else if(ft4PercentageString.matches(ShowDetails.this.getString(R.string.No_Value))){
            double valueft3 = Double.parseDouble(Ft3PercentageStringwithoutPercent);
            ft3percentagewithoutstringint = (int)valueft3;
            seekBarFt3.setProgress(ft3percentagewithoutstringint);
            seekBarFt3.setEnabled(false);
            seekBarFt4.setVisibility(View.GONE);
            seekBarFt4.setEnabled(false);
        }

        else if(ft3PercentageString.matches(ShowDetails.this.getString(R.string.No_Value))){
            double valueft4 = Double.parseDouble(Ft4PercentageStringwithoutPercent);
            ft4percentagewithoutstringint = (int)valueft4;
            seekBarFt4.setProgress(ft4percentagewithoutstringint);
            seekBarFt4.setEnabled(false);
            seekBarFt3.setVisibility(View.GONE);
            seekBarFt3.setEnabled(false);
        }

        else if(ft3PercentageString.matches(ShowDetails.this.getString(R.string.No_Value))&&ft4PercentageString.matches(ShowDetails.this.getString(R.string.No_Value))){
            seekBarFt4.setVisibility(View.GONE);
            seekBarFt4.setEnabled(false);
            seekBarFt3.setVisibility(View.GONE);
            seekBarFt3.setEnabled(false);
        }

        DateTxtViewDetail.setText(date);
        tshEditDetail.setText(tsh);
        ft4Detail.setText(ft4);
        ft4_upper_valueDetail.setText(ft4_upper);
        ft4_lower_valueDetail.setText(ft4_lower);
        ft3Detail.setText(ft3);
        ft3_upper_valueDetail.setText(ft3_upper);
        ft3_lower_valueDetail.setText(ft3_lower);
        constitutionDetail.setText(constitution);
        commentDetail.setText(comment);
        tvFT4PercentageProgressBar.setText(ft4PercentageString);
        tvFT3PercentageProgressBar.setText(ft3PercentageString);

        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        ChangeDateBtnDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDelete) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(ShowDetails.this.getString(R.string.really_delete) )
                    .setNegativeButton(ShowDetails.this.getString(R.string.No), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialoginterface, int i) {
                    dialoginterface.cancel();
                    returnHome();}})
                    .setPositiveButton(ShowDetails.this.getString(R.string.Yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            dbCon.delete(Long.parseLong(id));
                            returnHome();
                        }
                    }).show();
        }
        if (v == btnUpdate) {
            try {
                String strtsh = tshEditDetail.getText().toString();
                if (TextUtils.isEmpty(strtsh)) {
                    tshEditDetail.setError(ShowDetails.this.getString(R.string.Must_contain_entry));
                    return;
                }
                String strft4 = ft4Detail.getText().toString();
                if(TextUtils.isEmpty(strft4)) {
                    ft4Detail.setText(ShowDetails.this.getString(R.string.No_Value));}

                String strft4uppervalue = ft4_upper_valueDetail.getText().toString();
                if(TextUtils.isEmpty(strft4uppervalue)) {
                    ft4_upper_valueDetail.setText(ShowDetails.this.getString(R.string.No_Value));}

                String strft4lowervalue = ft4_lower_valueDetail.getText().toString();
                if(TextUtils.isEmpty(strft4lowervalue)) {
                    ft4_lower_valueDetail.setText(ShowDetails.this.getString(R.string.No_Value));}

                String strft3 = ft3Detail.getText().toString();
                if(TextUtils.isEmpty(strft3)) {
                    ft3Detail.setText(ShowDetails.this.getString(R.string.No_Value));}

                String strft3uppervalue = ft3_upper_valueDetail.getText().toString();
                if(TextUtils.isEmpty(strft3uppervalue)) {
                    ft3_upper_valueDetail.setText(ShowDetails.this.getString(R.string.No_Value));}

                String strft3lowervalue = ft3_lower_valueDetail.getText().toString();
                if(TextUtils.isEmpty(strft3lowervalue)) {
                    ft3_lower_valueDetail.setText(ShowDetails.this.getString(R.string.No_Value));}

                String strConstitution =  constitutionDetail.getText().toString();
                if(TextUtils.isEmpty(strConstitution)) {
                    constitutionDetail.setText(ShowDetails.this.getString(R.string.No_Value));}

                String strComment = commentDetail.getText().toString();
                if(TextUtils.isEmpty(strComment)) {
                    commentDetail.setText(ShowDetails.this.getString(R.string.No_Value));}

                String strft42 = ft4Detail.getText().toString();
                String strft4uppervalue2 = ft4_upper_valueDetail.getText().toString();
                String strft4lowervalue2 = ft4_lower_valueDetail.getText().toString();
                String strft32 = ft3Detail.getText().toString();
                String strft3uppervalue2 = ft3_upper_valueDetail.getText().toString();
                String strft3lowervalue2 = ft3_lower_valueDetail.getText().toString();
                String strConstitution2 =  constitutionDetail.getText().toString();
                String strComment2 = commentDetail.getText().toString();

                if(strft42.matches(ShowDetails.this.getString(R.string.No_Value))|| strft4uppervalue2.matches(ShowDetails.this.getString(R.string.No_Value))||strft4lowervalue2.matches(ShowDetails.this.getString(R.string.No_Value)))
                {
                    ft4finalresultstring = ShowDetails.this.getString(R.string.No_Value);

                    double ft3Calc = Double.parseDouble(ft3Detail.getText().toString());
                    double ft3UpperValueCalc = Double.parseDouble(ft3_upper_valueDetail.getText().toString());
                    double ft3LowerValueCalc = Double.parseDouble(ft3_lower_valueDetail.getText().toString());
                    double ft3result = ((ft3Calc - ft3LowerValueCalc) / (ft3UpperValueCalc - ft3LowerValueCalc)) * 100;
                    double ft3resultrounded = round(ft3result, 2);
                    String ft3resultroundedString = String.valueOf(ft3resultrounded);

                    String ft3resultString = String.valueOf(ft3resultrounded);
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("FT3: ");
                    stringBuilder2.append(ft3resultString);
                    stringBuilder2.append("%");
                    String ft3finalresultstring = stringBuilder2.toString();

                    dbCon.update(Long.parseLong(id), DateTxtViewDetail.getText().toString(), TSHwithString, ft4Detail.getText().toString(), ft4_upper_valueDetail.getText().toString(), ft4_lower_valueDetail.getText().toString(), ft3Detail.getText().toString(), ft3_upper_valueDetail.getText().toString(), ft3_lower_valueDetail.getText().toString(), constitutionDetail.getText().toString(), commentDetail.getText().toString(), tshEditDetail.getText().toString(), ft4finalresultstring, ft3finalresultstring, ft4finalresultstring, ft3resultroundedString);
                    returnHome();
                }
                else if(strft32.matches(ShowDetails.this.getString(R.string.No_Value))||strft3lowervalue2.matches(ShowDetails.this.getString(R.string.No_Value))||strft3uppervalue2.matches(ShowDetails.this.getString(R.string.No_Value)))
                {
                    ft3finalresultstring = ShowDetails.this.getString(R.string.No_Value);

                    double ft4Calc = Double.parseDouble(ft4Detail.getText().toString());
                    double ft4UpperValueCalc = Double.parseDouble(ft4_upper_valueDetail.getText().toString());
                    double ft4LowerValueCalc = Double.parseDouble(ft4_lower_valueDetail.getText().toString());
                    double ft4result = ((ft4Calc - ft4LowerValueCalc) / (ft4UpperValueCalc - ft4LowerValueCalc)) * 100;
                    double ft4resultrounded = round(ft4result, 2);
                    String ft4resultroundedString = String.valueOf(ft4resultrounded);

                    String ft4resultString = String.valueOf(ft4resultrounded);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("FT4: ");
                    stringBuilder.append(ft4resultString);
                    stringBuilder.append("%");
                    String ft4finalresultstring = stringBuilder.toString();

                    dbCon.update(Long.parseLong(id), DateTxtViewDetail.getText().toString(), TSHwithString, ft4Detail.getText().toString(), ft4_upper_valueDetail.getText().toString(), ft4_lower_valueDetail.getText().toString(), ft3Detail.getText().toString(), ft3_upper_valueDetail.getText().toString(), ft3_lower_valueDetail.getText().toString(), constitutionDetail.getText().toString(), commentDetail.getText().toString(), tshEditDetail.getText().toString(), ft4finalresultstring, ft3finalresultstring, ft4resultroundedString, ft3finalresultstring);
                    returnHome();

                }
                else if(strft32.matches(ShowDetails.this.getString(R.string.No_Value))&&strft3lowervalue2.matches(ShowDetails.this.getString(R.string.No_Value))&&strft3uppervalue2.matches(ShowDetails.this.getString(R.string.No_Value))&&strft42.matches(ShowDetails.this.getString(R.string.No_Value))&& strft4uppervalue2.matches(ShowDetails.this.getString(R.string.No_Value))&&strft4lowervalue2.matches(ShowDetails.this.getString(R.string.No_Value)))
                {
                    ft3finalresultstring = ShowDetails.this.getString(R.string.No_Value);
                    ft4finalresultstring = ShowDetails.this.getString(R.string.No_Value);

                    dbCon.update(Long.parseLong(id), DateTxtViewDetail.getText().toString(), TSHwithString, ft4Detail.getText().toString(), ft4_upper_valueDetail.getText().toString(), ft4_lower_valueDetail.getText().toString(), ft3Detail.getText().toString(), ft3_upper_valueDetail.getText().toString(), ft3_lower_valueDetail.getText().toString(), constitutionDetail.getText().toString(), commentDetail.getText().toString(), tshEditDetail.getText().toString(), ft4finalresultstring, ft3finalresultstring, ft4finalresultstring, ft3finalresultstring);
                    returnHome();
                }
                else {
                    double ft4Calc = Double.parseDouble(ft4Detail.getText().toString());
                    double ft4UpperValueCalc = Double.parseDouble(ft4_upper_valueDetail.getText().toString());
                    double ft4LowerValueCalc = Double.parseDouble(ft4_lower_valueDetail.getText().toString());
                    double ft4result = ((ft4Calc - ft4LowerValueCalc) / (ft4UpperValueCalc - ft4LowerValueCalc)) * 100;
                    double ft4resultrounded = round(ft4result, 2);
                    String ft4resultroundedString = String.valueOf(ft4resultrounded);

                    String ft4resultString = String.valueOf(ft4resultrounded);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("FT4: ");
                    stringBuilder.append(ft4resultString);
                    stringBuilder.append("%");
                    String ft4finalresultstring = stringBuilder.toString();

                    double ft3Calc = Double.parseDouble(ft3Detail.getText().toString());
                    double ft3UpperValueCalc = Double.parseDouble(ft3_upper_valueDetail.getText().toString());
                    double ft3LowerValueCalc = Double.parseDouble(ft3_lower_valueDetail.getText().toString());
                    double ft3result = ((ft3Calc - ft3LowerValueCalc) / (ft3UpperValueCalc - ft3LowerValueCalc)) * 100;
                    double ft3resultrounded = round(ft3result, 2);
                    String ft3resultroundedString = String.valueOf(ft3resultrounded);

                    String ft3resultString = String.valueOf(ft3resultrounded);
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("FT3: ");
                    stringBuilder2.append(ft3resultString);
                    stringBuilder2.append("%");
                    String ft3finalresultstring = stringBuilder2.toString();

                    dbCon.update(Long.parseLong(id), DateTxtViewDetail.getText().toString(), TSHwithString, ft4Detail.getText().toString(), ft4_upper_valueDetail.getText().toString(), ft4_lower_valueDetail.getText().toString(), ft3Detail.getText().toString(), ft3_upper_valueDetail.getText().toString(), ft3_lower_valueDetail.getText().toString(), constitutionDetail.getText().toString(), commentDetail.getText().toString(), tshEditDetail.getText().toString(), ft4finalresultstring, ft3finalresultstring, ft4resultroundedString, ft3resultroundedString);
                    returnHome();
                }

            } catch (Exception e) {
            }
        }
        if (v == ChangeDateBtnDetail) {
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
                                DateTxtViewDetail.setText(year + "/" + (monthOfYearString) + "/" + dayOfMonth);}
                            else
                                DateTxtViewDetail.setText(year + "/" + (monthOfYear+1) + "/" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
    public void returnHome(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
