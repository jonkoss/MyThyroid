package jonas.mythyroid;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ShowPlot extends AppCompatActivity {
    GraphView graph;
    LineGraphSeries<DataPoint> seriesFt3;
    LineGraphSeries<DataPoint> seriesFt4;
    CheckBox checkboxFt3, checkboxFt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_plot);
        graph = (GraphView) findViewById(R.id.chart);
        DbCon dbCon = new DbCon(this);

        try {
            String[] arrayft3 = dbCon.getFt3();
            DataPoint[] dpFt3 = new DataPoint[dbCon.rowCounter];

            for (int i = 0; i < dbCon.rowCounter; i++) {
                dpFt3[i] = new DataPoint(i, (int) Math.round(Double.parseDouble(arrayft3[i])));
            }
            seriesFt3 = new LineGraphSeries<>(dpFt3);
            seriesFt3.setDrawDataPoints(true);
            seriesFt3.setTitle("FT3 in %");
            seriesFt3.setColor(Color.RED);
            seriesFt3.setDataPointsRadius(10);
            seriesFt3.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Toast.makeText(ShowPlot.this, "FT3: " + dataPoint, Toast.LENGTH_SHORT).show();
                }
            });
            graph.addSeries(seriesFt3);

            String[] arrayft4 = dbCon.getFt4();
            DataPoint[] dpFt4 = new DataPoint[dbCon.rowCounter];
            for (int i = 0; i < dbCon.rowCounter; i++) {
                dpFt4[i] = new DataPoint(i, (int) Math.round(Double.parseDouble(arrayft4[i])));
            }
            seriesFt4 = new LineGraphSeries<>(dpFt4);
            seriesFt4.setDrawDataPoints(true);
            seriesFt4.setTitle("FT4 in %");
            graph.getLegendRenderer().setVisible(true);
            graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            seriesFt4.setColor(Color.BLUE);
            seriesFt4.setDataPointsRadius(10);
            seriesFt4.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Toast.makeText(ShowPlot.this, "FT4: " + dataPoint, Toast.LENGTH_SHORT).show();
                }
            });
            graph.addSeries(seriesFt4);
        } catch (Exception e) {
            Toast.makeText(ShowPlot.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

                checkboxFt3 = (CheckBox) findViewById(R.id.checkBoxFt3);
        checkboxFt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) graph.addSeries(seriesFt3);
                else graph.removeSeries(seriesFt3);
            }
        });

        checkboxFt4 = (CheckBox) findViewById(R.id.checkBoxFt4);
        checkboxFt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) graph.addSeries(seriesFt4);
                else graph.removeSeries(seriesFt4);
            }
        });
    }
}
