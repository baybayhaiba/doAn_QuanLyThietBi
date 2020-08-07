package tdc.edu.vn.qlsv.GiaoDien;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.R;

public class ActivityAnalysis extends AppCompatActivity {
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

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Số lượng chi tiết sử dụng");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        bardataset.setValueTextSize(16f);
        barChart.animateY(2500);
    }
    void khoiTao(){
        dataCTSD=new DataCTSD(this);
        getInform=dataCTSD.getInfomation();

        int i=0;
        for(ChiTietSuDung chiTietSuDung:getInform){
            labels.add(chiTietSuDung.getNgaySuDung());
            entries.add(new BarEntry(chiTietSuDung.getSoLuong(),i++));
        }

    }

}