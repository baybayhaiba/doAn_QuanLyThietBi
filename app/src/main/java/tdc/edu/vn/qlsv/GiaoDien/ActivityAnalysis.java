package tdc.edu.vn.qlsv.GiaoDien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.R;

public class ActivityAnalysis extends AppCompatActivity {
    BarChart barChart;
    ArrayList<String> labels;
    ArrayList<BarEntry> entries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        setControl();
        setEvent();
    }
    void setControl(){
        barChart= (BarChart) findViewById(R.id.barchart);
        labels= new ArrayList<String>();
        entries = new ArrayList<>();
    }
    void setEvent(){
        khoiTao();
        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Expenditure for the year 2018");  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);
    }
    void khoiTao(){
        labels.add("Dec");
        labels.add("Nov");
        labels.add("Oct");
        labels.add("Sep");
        labels.add("Aug");
        labels.add("Jul");
        labels.add("Jun");
        labels.add("May");
        labels.add("Apr");
        labels.add("Mar");
        labels.add("Feb");
        labels.add("Jan");
        labels.add("Jan");
        labels.add("Jan");
        labels.add("Jan");
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(5f, 2));
        entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));
        entries.add(new BarEntry(8f, 6));
        entries.add(new BarEntry(2f, 7));
        entries.add(new BarEntry(5f, 8));
        entries.add(new BarEntry(20f, 9));
        entries.add(new BarEntry(15f, 10));
        entries.add(new BarEntry(19f, 11));
        entries.add(new BarEntry(19f, 12));
        entries.add(new BarEntry(19f, 13));
        entries.add(new BarEntry(19f, 14));
    }
}