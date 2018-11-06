package jonas.mythyroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class InfoActivity extends AppCompatActivity{
    Button excelexport,back;
    DbCon dbCon = new DbCon(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        back = (Button) findViewById(R.id.back_button);
        excelexport = (Button) findViewById(R.id.excel_export_button);
    }

    public void excel_export_button(View v)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(InfoActivity.this);
        dialog.setTitle(InfoActivity.this.getString(R.string.export_excel))
                .setNegativeButton((InfoActivity.this.getString(R.string.No)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();}})
                .setPositiveButton((InfoActivity.this.getString(R.string.Yes)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        Dexter.withActivity(InfoActivity.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                                        try {
                                            dbCon.exportToCsv();
                                        } catch (Exception e) {
                                            Toast.makeText(InfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {Toast.makeText(InfoActivity.this, "Permission required", Toast.LENGTH_SHORT).show();}
                                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {token.continuePermissionRequest();}
                                }).check();
                    }
                }).show();
    }

    public void back_button(View v)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void excel_import_button(View v){
        Intent intent=new Intent(this,ShowPlot.class);
        startActivity(intent);
               //dbCon.importCsv();
    }
}
