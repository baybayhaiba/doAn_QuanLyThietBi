package tdc.edu.vn.qlsv.GiaoDien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.R;

public class ActivityAnalysis extends AppCompatActivity {
    BarChart barChart;
    ArrayList<String> labels;
    ArrayList<BarEntry> entries;
    DataCTSD dataCTSD;
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
        BarDataSet bardataset = new BarDataSet(entries, "Số lượng");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Số lượng chi tiết sử dụng");  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);
    }
    void khoiTao(){
        dataCTSD=new DataCTSD(this);
        HashMap<String,Integer> getInform=dataCTSD.getInfomation();

        int i=0;
        for(HashMap.Entry<String,Integer> data:getInform.entrySet()){
            labels.add(data.getKey());
            entries.add(new BarEntry(data.getValue()+0f,i++));
        }



    }
}