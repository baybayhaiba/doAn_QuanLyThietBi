package tdc.edu.vn.qlsv.GiaoDien;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.R;

public class ActivityAnalysis extends AppCompatActivity{
    BarChart barChart;
    ArrayList<String> labels;
    ArrayList<BarEntry> entries;
    DataCTSD dataCTSD;
    ArrayList<ChiTietSuDung> getInform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        ActionBar();
        setControl();
        setEvent();
    }
    private void ActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        ((TextView) actionBar.getCustomView().findViewById(R.id.actionBarTitle)).setText("Thống kê chi tiết");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    void setControl(){
        barChart= (BarChart) findViewById(R.id.barchart);
        labels= new ArrayList<String>();
        entries = new ArrayList<>();
    }
    void setEvent(){
        khoiTao();
        BarDataSet bardataset = new BarDataSet(entries, "Số lượng");

        BarData data = new BarData(bardataset);
        barChart.setData(data); // set the data and list of lables into chart
        //set onclick
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int getYear=Integer.parseInt(getInform.get((int) e.getX()).getNgaySuDung());
                Intent intent=new Intent(ActivityAnalysis.this,ActivityAnalysisDetail.class);
                intent.putExtra(ActivityAnalysisDetail.getData,getYear);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {
            }
        });
        //set label
        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        //set color
        barChart.getDescription().setText("Chi tiết sử dụng");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        bardataset.setValueTextSize(16f);
        barChart.animateY(1000);
    }


    void khoiTao(){
        dataCTSD=new DataCTSD(this);
        getInform=dataCTSD.getInformationYear();

        int i=0;
        for(ChiTietSuDung chiTietSuDung:getInform){
            labels.add(chiTietSuDung.getNgaySuDung());
            entries.add(new BarEntry(i++,chiTietSuDung.getSoLuong()));
        }
    }

}