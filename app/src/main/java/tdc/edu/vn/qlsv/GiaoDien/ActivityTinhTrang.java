package tdc.edu.vn.qlsv.GiaoDien;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Adapter.CustomAdapterTinhTrang;
import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Database.DataTinhTrang;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.Model.TinhTrangThietBi;
import tdc.edu.vn.qlsv.R;

public class ActivityTinhTrang extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private RecyclerView recyclerView;
    private Spinner sp_tinhTrang;
    private Button bt_Popup;
    private FloatingActionButton fab;
    private CustomAdapterTinhTrang adapter;
    private DataTinhTrang dataTinhTrang;
    private ArrayList<TinhTrangThietBi> listTinhTrang;
    private TextView title_tinhTrang;
    private int idCTSD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinh_trang);
        ActionBar();
        setControl();
        setEvent();
    }
    private void ActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        ((TextView) actionBar.getCustomView().findViewById(R.id.actionBarTitle)).setText("Tình trạng thiết bị");
        ((TextView) actionBar.getCustomView().findViewById(R.id.actionBarTitle)).setTextSize(20f);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    private void setControl(){
        fab=findViewById(R.id.fab1);
        recyclerView=findViewById(R.id.recycleViewTinhTrang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataTinhTrang=new DataTinhTrang(this);
        title_tinhTrang=findViewById(R.id.title_tinhTrangThietBi);
    }
    private void setEvent(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });
        String getDate=getIntent().getExtras().get("date").toString();
        String getMaTB=getIntent().getExtras().get("maTB").toString();
        String getMaPhong=getIntent().getExtras().get("maPhong").toString();
        idCTSD=Integer.parseInt(getIntent().getExtras().get("id").toString());
        Toast.makeText(this, ""+idCTSD, Toast.LENGTH_SHORT).show();
        listTinhTrang=dataTinhTrang.getTinhTrangTheoNgay(getDate,getMaTB,getMaPhong);
        title_tinhTrang.setText("Mã phòng học:"+listTinhTrang.get(0).getMaPhong()
        +"\nMã thiết bị:"+listTinhTrang.get(0).getMaThietBi()+"\nNgày sử dụng:"+listTinhTrang.get(0).getNgaySuDung());
        adapter=new CustomAdapterTinhTrang(this,listTinhTrang);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DataCTSD dataCTSD=new DataCTSD(this);
                ChiTietSuDung chiTietSuDung=dataCTSD.getCTSDByID(idCTSD);
                chiTietSuDung.setSoLuong(listTinhTrang.size());
                dataCTSD.suaCTSD(chiTietSuDung);
                startActivity(new Intent(ActivityTinhTrang.this,ActivityChiTietSD.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void createPopupDialog() {
        builder=new AlertDialog.Builder(this);
        final View view=getLayoutInflater().inflate(R.layout.popup_tinhtrang,null);

        bt_Popup=view.findViewById(R.id.bt_TinhTrang);
        bt_Popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityTinhTrang.this, "1234", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        dialog=builder.create();
        dialog.show();
    }
}