package tdc.edu.vn.qlsv.GiaoDien;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.quanlythietbi.LoaiThietBi.dataLoaiThietBi;
//import com.example.quanlythietbi.LoaiThietBi.LoaiThietBi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tdc.edu.vn.qlsv.Adapter.CustomAdapterMaTB;
import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Database.DataLoaiThietBi;
import tdc.edu.vn.qlsv.Database.DataThietBi;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.Model.LoaiThietBi;
import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.R;

public class ActivityLoaiThietBi extends AppCompatActivity {

    Button bt_them, bt_xoa, bt_sua, bt_clear;
    EditText edit_maLoai, edit_tenLoai;
    ListView lv_maThietBi;
    TextView tvID;
    CustomAdapterMaTB adapter;
    ArrayList<LoaiThietBi> loaiThietBi;
    int index = -1;
    DataLoaiThietBi dataChiTiet;
    DataThietBi dataThietBi;
    DataCTSD dataCTSD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_thietbi);
        ActionBar();
        setControl();
        setEvent();
    }

    private void ActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        ((TextView) actionBar.getCustomView().findViewById(R.id.actionBarTitle)).setText("Loại Thiết Bị");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setEvent() {
        dataChiTiet = new DataLoaiThietBi(ActivityLoaiThietBi.this);
        loaiThietBi = dataChiTiet.getAllLoaiTB();
        adapter = new CustomAdapterMaTB(this, loaiThietBi);
        lv_maThietBi.setAdapter(adapter);
        bt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiThietBi ltb = ThemLoaiTB();
                dataChiTiet.themLoaiTB(ltb);
                loaiThietBi.add(ltb);
                Collections.sort(loaiThietBi, new Comparator<LoaiThietBi>() {

                    @Override
                    public int compare(LoaiThietBi loaiThietBi, LoaiThietBi t1) {
                        return t1.getId()-loaiThietBi.getId();
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
        bt_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index != -1) {
                    LoaiThietBi ltb = getLoaiTB();
                    dataChiTiet.XoaLoaiTB(getLoaiTB());
                    loaiThietBi.remove(index);
                    index = -1;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        bt_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index != -1) {
                    LoaiThietBi ltb = getLoaiTB();
                    updateLTB(index, ltb.getMaLoai());
                    dataChiTiet.CapNhatLoaiTB(getLoaiTB());
                    loaiThietBi.set(index, ltb);
                    index = -1;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaTatCaNhap();
            }
        });


        lv_maThietBi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvID.setText("" + loaiThietBi.get(position).getId());
                edit_maLoai.setText(loaiThietBi.get(position).getMaLoai());
                edit_tenLoai.setText(loaiThietBi.get(position).getTenLoai());
                index = position;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ActivityLoaiThietBi.this, MainActivity.class));
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

    private int updateLTB(int position, String newMaLoai) {
        try {
            dataThietBi = new DataThietBi(this);
            ArrayList<ThietBi> listTB = dataThietBi.getAllThietBi();
            Collections.sort(listTB, new Comparator<ThietBi>() {
                @Override
                public int compare(ThietBi thietBi, ThietBi t1) {
                    return thietBi.getId() - t1.getId();
                }
            });
            for (int i = 0; i < listTB.size(); i++) {
                String maloai = loaiThietBi.get(position).getMaLoai();
                if (!maloai.equals(newMaLoai)) {
                    if (listTB.get(i).getMaLoaiTB().equals(maloai)) {
                        listTB.get(i).setMaLoaiTB(newMaLoai);
                        String maTB = listTB.get(i).getMaTB();
                        listTB.get(i).setMaTB(dataThietBi.createNewType
                                (newMaLoai, dataThietBi.MaxType(newMaLoai) + 1));
                        dataThietBi.suaThietBi(listTB.get(i));
                        updateCTSD(maTB, listTB.get(i).getMaTB());
                    }
                }
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    private int updateCTSD(String oldMaTB, String newMaTB) {
        try {
            dataCTSD = new DataCTSD(this);
            ArrayList<ChiTietSuDung> listCTSD = dataCTSD.getAllCTSD();
            for (int i = 0; i < listCTSD.size(); i++) {
                if (listCTSD.get(i).getMaTB().equals(oldMaTB)) {
                    listCTSD.get(i).setMaTB(newMaTB);
                    return dataCTSD.suaCTSD(listCTSD.get(i));
                }
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    private LoaiThietBi ThemLoaiTB() {
        int id = dataChiTiet.MaxId() + 1;
        String maLoai = edit_maLoai.getText().toString();
        String tenLoai = edit_tenLoai.getText().toString();
        return new LoaiThietBi(id, maLoai, tenLoai);
    }

    private LoaiThietBi getLoaiTB() {
        int id = Integer.parseInt(tvID.getText().toString());
        String maLoai = edit_maLoai.getText().toString();
        String tenLoai = edit_tenLoai.getText().toString();
        return new LoaiThietBi(id, maLoai, tenLoai);
    }

    private void xoaTatCaNhap() {
        edit_maLoai.setText("");
        edit_tenLoai.setText("");
        index = -1;
    }

    private void setControl() {
        bt_them = findViewById(R.id.bt_add);
        bt_xoa = findViewById(R.id.bt_remove);
        bt_sua = findViewById(R.id.bt_update);
        bt_clear = findViewById(R.id.bt_clear);
        loaiThietBi = new ArrayList<>();
        edit_maLoai = findViewById(R.id.edit_maLoai);
        edit_tenLoai = findViewById(R.id.edit_tenLoai);
        tvID = findViewById(R.id.idLTB);
        lv_maThietBi = findViewById(R.id.list_loaiTB);
    }


}