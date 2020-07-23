package tdc.edu.vn.qlsv.GiaoDien;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Database.DataLoaiThietBi;
import tdc.edu.vn.qlsv.Database.DataPhongHoc;
import tdc.edu.vn.qlsv.Database.DataThietBi;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.R;


public class ActivityChiTietSD extends AppCompatActivity {
    Spinner sp_maPhong, sp_MaThietBi;
    ArrayList<String> dataMaPhong;
    ArrayList<String> dataMaThietBi;
    DataLoaiThietBi databaseLTB;
    //luu tru database va lay tat ca
    ArrayAdapter<ChiTietSuDung> adapter;

    EditText edit_ngaySuDung, edit_soLuong;
    ListView listCTSD;
    int index = -1;
    Button bt_add, bt_remove, bt_update, bt_clear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sd);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setControl();
        setEvent();
    }

    private void setEvent() {

        //lay du lieu
        DataThietBi dbThietBi = new DataThietBi(this);
        final DataPhongHoc dbPhongHoc = new DataPhongHoc(this);
        int i = 0, j = 0;
        //get lay string chuoi
        dataMaThietBi = new ArrayList<>();
        dataMaPhong = new ArrayList<>();
        while (i < dbThietBi.getAllThietBi().size() && j < dbPhongHoc.getAllPhongHoc().size()) {
            while (i < dbThietBi.getAllThietBi().size()) {
                dataMaThietBi.add(dbThietBi.getAllThietBi().get(i).getMaTB());
                i++;
            }
            while (j < dbPhongHoc.getAllPhongHoc().size()) {
                dataMaPhong.add(dbPhongHoc.getAllPhongHoc().get(j).getMaPhong());
                j++;
            }
        }

        sp_MaThietBi.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataMaThietBi));
        sp_maPhong.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataMaPhong));
        final DataCTSD dataCTSD=new DataCTSD(this);
        adapter = new ArrayAdapter<ChiTietSuDung>(this, android.R.layout.simple_list_item_1, dataCTSD.getAllCTSD());
        listCTSD.setAdapter(adapter);



        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataCTSD.themCTSD(getCTSD());
                adapter.clear();
                adapter.addAll(dataCTSD.getAllCTSD());
                adapter.notifyDataSetChanged();
                Toast.makeText(ActivityChiTietSD.this, ""+getCTSD().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != -1) {
                    dataCTSD.xoaCTSD(getCTSD());
                    adapter.clear();
                    adapter.addAll(dataCTSD.getAllCTSD());
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(ActivityChiTietSD.this, "Bạn chưa chọn bất cứ thứ gì", Toast.LENGTH_SHORT).show();
            }

        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    if (index != -1) {
                        dataCTSD.suaCTSD(getCTSD());
                        adapter.clear();
                        adapter.addAll(dataCTSD.getAllCTSD());
                        adapter.notifyDataSetChanged();
                    } else
                        Toast.makeText(ActivityChiTietSD.this, "Bạn chưa chọn bất cứ thứ gì", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setControl() {
        sp_maPhong = findViewById(R.id.sp_maPhong);
        sp_MaThietBi = findViewById(R.id.sp_MaThietBi);

        bt_add = findViewById(R.id.bt_add);
        bt_remove = findViewById(R.id.bt_remove);
        bt_update = findViewById(R.id.bt_update);
        bt_clear = findViewById(R.id.bt_clear);

        edit_ngaySuDung = findViewById(R.id.edit_ngaySuDung);
        edit_soLuong = findViewById(R.id.edit_soLuong);
        listCTSD = findViewById(R.id.listCTSD);
    }

    private ChiTietSuDung getCTSD() {
        String ngaySuDung = edit_ngaySuDung.getText().toString();
        String MaThietBi = sp_MaThietBi.getSelectedItem().toString();
        String maPhong = sp_maPhong.getSelectedItem().toString();
        int MaLoai = Integer.parseInt(edit_soLuong.getText().toString());
        ChiTietSuDung listdevice = new ChiTietSuDung(maPhong,MaThietBi,ngaySuDung,MaLoai);
        return listdevice;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(ActivityChiTietSD.this,MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
