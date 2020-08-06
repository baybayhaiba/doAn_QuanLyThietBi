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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Adapter.CustomAdapterPhongHoc;
import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Database.DataPhongHoc;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.Model.PhongHoc;
import tdc.edu.vn.qlsv.R;


public class ActivityPhongHoc extends AppCompatActivity {

    Button bt_add, bt_remove, bt_update, bt_clear;
    EditText edit_maPhong, edit_loaiPhong, edit_tang;
    TextView tvID;
    RecyclerView listRoom;
    CustomAdapterPhongHoc adapterPhongHoc;
    DataPhongHoc dataPhongHoc;
    ArrayList<PhongHoc> listPhongHoc;
    int index = -1;
    DataCTSD dataCTSD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_hoc);
        ActionBar();
        setControl();
        setEvent();
    }

    private void ActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        ((TextView) actionBar.getCustomView().findViewById(R.id.actionBarTitle)).setText("Phòng Học");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void setEvent() {
        dataPhongHoc = new DataPhongHoc(ActivityPhongHoc.this);
        listPhongHoc = dataPhongHoc.getAllPhongHoc();
        adapterPhongHoc = new CustomAdapterPhongHoc(R.layout.list_custom_phonghoc,
                listPhongHoc);
        listRoom.setAdapter(adapterPhongHoc);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhongHoc phongHoc = themPhongHoc();
                dataPhongHoc.themPhongHoc(phongHoc);
                listPhongHoc.add(phongHoc);
                adapterPhongHoc.notifyDataSetChanged();
            }
        });
        bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != -1) {
                    PhongHoc phongHoc = getLoaiPhongHoc();
                    dataPhongHoc.XoaMaPhong(getLoaiPhongHoc());
                    listPhongHoc.remove(index);
                    adapterPhongHoc.notifyDataSetChanged();
                    index = -1;
                }
            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != -1) {
                    PhongHoc phongHoc = getLoaiPhongHoc();
                    dataPhongHoc.CapNhatPhongHoc(getLoaiPhongHoc());
                    updateCTSD(index,phongHoc.getMaPhong());
                    listPhongHoc.set(index, phongHoc);
                    adapterPhongHoc.notifyDataSetChanged();
                    index = -1;
                }
            }
        });
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaTatCaNhap();
            }
        });


        adapterPhongHoc.setListener(new CustomAdapterPhongHoc.ListenerPhongHoc() {
            @Override
            public void onClick(int position) {
                tvID.setText(String.valueOf(listPhongHoc.get(position).getId()));
                edit_maPhong.setText(listPhongHoc.get(position).getMaPhong());
                edit_loaiPhong.setText(listPhongHoc.get(position).getLoaiPhong());
                edit_tang.setText(String.valueOf(listPhongHoc.get(position).getTang()));
                index = position;
            }
        });

    }

    private PhongHoc themPhongHoc() {
        String maPhong = edit_maPhong.getText().toString();
        String loaiPhong = edit_loaiPhong.getText().toString();
        int tang = Integer.parseInt(edit_tang.getText().toString());
        int id = dataPhongHoc.MaxId() + 1;
        return new PhongHoc(id, maPhong, loaiPhong, tang);
    }

    private PhongHoc getLoaiPhongHoc() {
        String maPhong = edit_maPhong.getText().toString();
        String LoaiPhong = edit_loaiPhong.getText().toString();
        int tang = Integer.parseInt(edit_tang.getText().toString());
        int id = Integer.parseInt(tvID.getText().toString());
        return new PhongHoc(id, maPhong, LoaiPhong, tang);
    }

    private void xoaTatCaNhap() {
        edit_maPhong.setText("");
        edit_loaiPhong.setText("");
        edit_tang.setText("");
        index = -1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ActivityPhongHoc.this, MainActivity.class));
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
                adapterPhongHoc.getFilter().filter(s);
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }

    private int updateCTSD(int position,String newMaPhong){
        try {
            dataCTSD = new DataCTSD(this);
            ArrayList<ChiTietSuDung> listCTSD = dataCTSD.getAllCTSD();
            for (int i = 0; i < listCTSD.size(); i++) {
                String maPhong=listPhongHoc.get(position).getMaPhong();
                if(!maPhong.equals(newMaPhong))
                    if(listCTSD.get(i).getMaPhong().equals(maPhong)) {
                        listCTSD.get(i).setMaPhong(newMaPhong);
                        dataCTSD.suaCTSD(listCTSD.get(i));
                    }
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }
    private void setControl() {
        bt_add = findViewById(R.id.bt_add);
        bt_remove = findViewById(R.id.bt_remove);
        bt_update = findViewById(R.id.bt_update);
        bt_clear = findViewById(R.id.bt_clear);

        edit_maPhong = findViewById(R.id.edit_maPhong);
        edit_loaiPhong = findViewById(R.id.edit_loaiPhong);
        edit_tang = findViewById(R.id.edit_tang);
        listRoom = findViewById(R.id.listRoom);
        tvID = findViewById(R.id.idPhongHoc);
        listRoom.setLayoutManager(new LinearLayoutManager(this));
    }


}