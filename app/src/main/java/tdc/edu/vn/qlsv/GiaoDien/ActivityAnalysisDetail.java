package tdc.edu.vn.qlsv.GiaoDien;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.R;

public class ActivityAnalysisDetail extends AppCompatActivity {
    PieChart pieChart;
    ArrayList<PieEntry> data;
    PieData pieData;
    PieDataSet pieDataSet;
    DataCTSD dataCTSD;
    ArrayList<ChiTietSuDung> dataInformation;
    public static final String getData="data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_detail);
        ActionBar();
        setControl();
        setEvent();
    }
    private void ActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        ((TextView) actionBar.getCustomView().findViewById(R.id.actionBarTitle)).setText("Thống kê "+
                String.valueOf(getIntent().getExtras().get(getData)));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    private void setEvent(){
        KhoiTao();

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        pieDataSet=new PieDataSet(data,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieData=new PieData(pieDataSet);


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ActivityAnalysisDetail.this);
                builder.setTitle("Sử dụng thiết bị lớp "+dataInformation.get((int) h.getX()).getMaPhong());
                builder.setMessage("Số lượng thiết bị:"+dataInformation.get((int) h.getX()).getSoLuong()+
                "\nNgày bắt đầu sử dụng:"+dataInformation.get((int) h.getX()).getNgaySuDung()+
                "\nTên thiết bị:"+dataInformation.get((int) h.getX()).getMaTB());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        pieChart.setData(pieData);
        pieData.setValueTextSize(18f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateX(1500);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    private void KhoiTao(){
        dataCTSD=new DataCTSD(ActivityAnalysisDetail.this);
        dataInformation=dataCTSD.getInformationDetail(String.valueOf(getIntent().getExtras().get(getData)));
        for(int i=0;i<dataInformation.size();i++){
            int soLuong=dataInformation.get(i).getSoLuong();
            String maPhong=dataInformation.get(i).getMaPhong();
            data.add(new PieEntry(soLuong,maPhong));
        }
    }
    private void setControl(){
        pieChart=findViewById(R.id.pieChart);
        data=new ArrayList<>();

    }
}