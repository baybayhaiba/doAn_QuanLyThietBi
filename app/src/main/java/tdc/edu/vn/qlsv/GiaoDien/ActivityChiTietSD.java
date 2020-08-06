package tdc.edu.vn.qlsv.GiaoDien;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Adapter.CustomAdapterCTSD;
import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Database.DataPhongHoc;
import tdc.edu.vn.qlsv.Database.DataThietBi;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.R;


public class ActivityChiTietSD extends AppCompatActivity {
    Spinner sp_maPhong, sp_MaThietBi;
    ArrayList<String> dataMaPhong;
    ArrayList<String> dataMaThietBi;
    ArrayList<ChiTietSuDung> chiTietSuDung;
    //luu tru database va lay tat ca
    CustomAdapterCTSD adapter;
    TextView idCTSD;
    EditText edit_ngaySuDung, edit_soLuong;
    RecyclerView listCTSD;
    int index = -1;
    Button bt_add, bt_remove, bt_update, bt_clear;
    DataCTSD dataCTSD;
    ArrayAdapter<String> adapterMaPhong;
    ArrayAdapter<String> adapterMaTB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sd);
        ActionBar();
        setControl();
        setEvent();
    }

    private void ActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        ((TextView) actionBar.getCustomView().findViewById(R.id.actionBarTitle)).setText("Chi tiết sử dụng");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setEvent() {

        //lay du lieu
        DataThietBi dbThietBi = new DataThietBi(this);
        final DataPhongHoc dbPhongHoc = new DataPhongHoc(this);
        int i = 0, j = 0;
        //get lay string chuoi
        dataMaThietBi = new ArrayList<>();
        dataMaPhong = new ArrayList<>();

        while (i < dbThietBi.getAllThietBi().size()) {
            dataMaThietBi.add(dbThietBi.getAllThietBi().get(i).getMaTB());
            i++;
        }
        while (j < dbPhongHoc.getAllPhongHoc().size()) {
            dataMaPhong.add(dbPhongHoc.getAllPhongHoc().get(j).getMaPhong());
            j++;
        }

        adapterMaPhong=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataMaPhong);
        adapterMaTB=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataMaThietBi);
        sp_MaThietBi.setAdapter(adapterMaTB);
        sp_maPhong.setAdapter(adapterMaPhong);
        dataCTSD = new DataCTSD(this);
        chiTietSuDung=dataCTSD.getAllCTSD();
        adapter = new CustomAdapterCTSD(ActivityChiTietSD.this,
                R.layout.list_custom_ctsd, chiTietSuDung);
        listCTSD.setAdapter(adapter);


        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChiTietSuDung ctsd=ThemCTSD();
                dataCTSD.themCTSD(ctsd);
                chiTietSuDung.add(ctsd);
                adapter.notifyDataSetChanged();
                Toast.makeText(ActivityChiTietSD.this, "" + getCTSD().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != -1) {
                    ChiTietSuDung ctsd=getCTSD();
                    dataCTSD.xoaCTSD(ctsd);
                    chiTietSuDung.remove(index);
                    adapter.notifyDataSetChanged();
                    index=-1;
                } else
                    Toast.makeText(ActivityChiTietSD.this, "Bạn chưa chọn bất cứ thứ gì", Toast.LENGTH_SHORT).show();
            }

        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    if (index != -1) {
                        ChiTietSuDung ctsd=getCTSD();
                        dataCTSD.suaCTSD(ctsd);
                        chiTietSuDung.set(index,ctsd);
                        adapter.notifyDataSetChanged();
                    } else
                        Toast.makeText(ActivityChiTietSD.this, "Bạn chưa chọn bất cứ thứ gì", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter.setListener(new CustomAdapterCTSD.ListenerCTSD() {
            @Override
            public void onClick(int position) {
                index=position;
                idCTSD.setText(String.valueOf(chiTietSuDung.get(position).getId()));
                edit_ngaySuDung.setText(chiTietSuDung.get(position).getNgaySuDung());
                edit_soLuong.setText(String.valueOf(chiTietSuDung.get(position).getSoLuong()));
                int positionMaPhong = adapterMaPhong.getPosition(chiTietSuDung.get(position).getMaPhong());
                int positionMaTB = adapterMaTB.getPosition(chiTietSuDung.get(position).getMaTB());
                if(positionMaPhong!=-1){
                    sp_maPhong.setSelection(positionMaPhong);
                }
                if(positionMaTB !=-1){
                    sp_MaThietBi.setSelection(positionMaTB);
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
        idCTSD=findViewById(R.id.idCTSD);
        edit_ngaySuDung = findViewById(R.id.edit_ngaySuDung);
        edit_soLuong = findViewById(R.id.edit_soLuong);
        listCTSD = findViewById(R.id.listCTSD);
        listCTSD.setLayoutManager(new LinearLayoutManager(this));
    }

    private ChiTietSuDung ThemCTSD() {
        int id=dataCTSD.MaxId()+1;
        String ngaySuDung = edit_ngaySuDung.getText().toString();
        String MaThietBi = sp_MaThietBi.getSelectedItem().toString();
        String maPhong = sp_maPhong.getSelectedItem().toString();
        int MaLoai = Integer.parseInt(edit_soLuong.getText().toString());
        ChiTietSuDung listdevice = new ChiTietSuDung(id,maPhong, MaThietBi, ngaySuDung, MaLoai);
        return listdevice;
    }
    private ChiTietSuDung getCTSD() {
        int id=Integer.parseInt(idCTSD.getText().toString());
        String ngaySuDung = edit_ngaySuDung.getText().toString();
        String MaThietBi = sp_MaThietBi.getSelectedItem().toString();
        String maPhong = sp_maPhong.getSelectedItem().toString();
        int SoLuong = Integer.parseInt(edit_soLuong.getText().toString());
        ChiTietSuDung listdevice = new ChiTietSuDung(id,maPhong, MaThietBi, ngaySuDung, SoLuong);
        return listdevice;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ActivityChiTietSD.this, MainActivity.class));
                break;
            case R.id.mnExit:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.mnSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }
}
