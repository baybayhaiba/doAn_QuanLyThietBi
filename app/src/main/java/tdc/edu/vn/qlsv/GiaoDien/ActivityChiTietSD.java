package tdc.edu.vn.qlsv.GiaoDien;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import tdc.edu.vn.qlsv.Adapter.CustomAdapterCTSD;
import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Database.DataPhongHoc;
import tdc.edu.vn.qlsv.Database.DataThietBi;
import tdc.edu.vn.qlsv.Database.DataTinhTrang;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.Model.TinhTrangThietBi;
import tdc.edu.vn.qlsv.R;


public class ActivityChiTietSD extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Spinner sp_maPhong, sp_MaThietBi;
    ArrayList<String> dataMaPhong;
    ArrayList<String> dataMaThietBi;
    ArrayList<ChiTietSuDung> listCTSD;
    //luu tru database va lay tat ca
    CustomAdapterCTSD adapter;
    TextView idCTSD;
    EditText edit_ngaySuDung, edit_soLuong;
    RecyclerView recyclerViewCTSD;
    int index = -1;
    Button bt_add, bt_remove, bt_update, bt_clear;
    ImageButton bt_calendar;
    DataCTSD dataCTSD;
    DataTinhTrang dataTinhTrang;
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

        adapterMaPhong = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataMaPhong);
        adapterMaTB = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataMaThietBi);
        sp_MaThietBi.setAdapter(adapterMaTB);
        sp_maPhong.setAdapter(adapterMaPhong);
        dataCTSD = new DataCTSD(this);
        listCTSD = dataCTSD.getAllCTSD();
        adapter = new CustomAdapterCTSD(ActivityChiTietSD.this,
                R.layout.list_custom_ctsd, listCTSD);
        recyclerViewCTSD.setAdapter(adapter);


        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid()) {
                    ChiTietSuDung ctsd = ThemCTSD();
                    dataCTSD.themCTSD(ctsd);
                    listCTSD.add(ctsd);
                    themTinhTrang(ctsd.getSoLuong());
                    Collections.sort(listCTSD, new Comparator<ChiTietSuDung>() {
                        @Override
                        public int compare(ChiTietSuDung chiTietSuDung, ChiTietSuDung t1) {
                            return t1.getId() - chiTietSuDung.getId();
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }
        });
        bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != -1) {
                    ChiTietSuDung ctsd = getCTSD();
                    dataCTSD.xoaCTSD(ctsd);
                    listCTSD.remove(index);
                    adapter.notifyDataSetChanged();
                    index = -1;
                } else
                    Toast.makeText(ActivityChiTietSD.this, "Bạn chưa chọn bất cứ thứ gì", Toast.LENGTH_SHORT).show();
            }

        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    if (index != -1 || isValid()) {
                        ChiTietSuDung ctsd = getCTSD();
                        suaTinhTrang(index, ctsd);
                        dataCTSD.suaCTSD(ctsd);
                        listCTSD.set(index, ctsd);
                        adapter.notifyDataSetChanged();
                    } else
                        Toast.makeText(ActivityChiTietSD.this, "Bạn chưa chọn bất cứ thứ gì", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_maPhong.setSelection(0);
                sp_MaThietBi.setSelection(0);
                edit_ngaySuDung.setText("");
                edit_soLuong.setText("");
            }
        });
        bt_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        adapter.setListener(new CustomAdapterCTSD.ListenerCTSD() {
            @Override
            public void onClick(int position) {
                index = position;
                idCTSD.setText(String.valueOf(listCTSD.get(position).getId()));
                edit_ngaySuDung.setText(listCTSD.get(position).getNgaySuDung());
                edit_soLuong.setText(String.valueOf(listCTSD.get(position).getSoLuong()));
                int positionMaPhong = adapterMaPhong.getPosition(listCTSD.get(position).getMaPhong());
                int positionMaTB = adapterMaTB.getPosition(listCTSD.get(position).getMaTB());
                if (positionMaPhong != -1) {
                    sp_maPhong.setSelection(positionMaPhong);
                }
                if (positionMaTB != -1) {
                    sp_MaThietBi.setSelection(positionMaTB);
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ActivityChiTietSD.this, ActivityChiTietSD.this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
        String textDate = String.format("%02d", (dayofMonth)) + "/" + String.format("%02d", (month)) + "/" + year;
        edit_ngaySuDung.setText(textDate);
    }

    private void setControl() {
        sp_maPhong = findViewById(R.id.sp_maPhong);
        sp_MaThietBi = findViewById(R.id.sp_MaThietBi);

        bt_add = findViewById(R.id.bt_add);
        bt_remove = findViewById(R.id.bt_remove);
        bt_update = findViewById(R.id.bt_update);
        bt_clear = findViewById(R.id.bt_clear);
        idCTSD = findViewById(R.id.idCTSD);
        bt_calendar = findViewById(R.id.bt_Calendar);
        edit_ngaySuDung = findViewById(R.id.edit_ngaySuDung);
        edit_soLuong = findViewById(R.id.edit_soLuong);
        recyclerViewCTSD = findViewById(R.id.listCTSD);

        recyclerViewCTSD.setLayoutManager(new LinearLayoutManager(this));
    }

    private ChiTietSuDung ThemCTSD() {
        int id = dataCTSD.MaxId() + 1;
        String ngaySuDung = edit_ngaySuDung.getText().toString();
        String MaThietBi = sp_MaThietBi.getSelectedItem().toString();
        String maPhong = sp_maPhong.getSelectedItem().toString();
        int MaLoai = Integer.parseInt(edit_soLuong.getText().toString());
        ChiTietSuDung listdevice = new ChiTietSuDung(id, maPhong, MaThietBi, ngaySuDung, MaLoai);
        return listdevice;
    }

    private ChiTietSuDung getCTSD() {
        int id = Integer.parseInt(idCTSD.getText().toString());
        String ngaySuDung = edit_ngaySuDung.getText().toString();
        String MaThietBi = sp_MaThietBi.getSelectedItem().toString();
        String maPhong = sp_maPhong.getSelectedItem().toString();
        int SoLuong = Integer.parseInt(edit_soLuong.getText().toString());
        ChiTietSuDung listdevice = new ChiTietSuDung(id, maPhong, MaThietBi, ngaySuDung, SoLuong);
        return listdevice;
    }

    private void themTinhTrang(int soLuong) {
        dataTinhTrang = new DataTinhTrang(this);
        int i;
        String maTB, maPhongHoc, tinhTrang, ngaySuDung;
        for (i = 0; i < soLuong; i++) {
            maTB = sp_MaThietBi.getSelectedItem().toString();
            maPhongHoc = sp_maPhong.getSelectedItem().toString();
            tinhTrang = "Tốt";
            ngaySuDung = edit_ngaySuDung.getText().toString();
            TinhTrangThietBi modelTinhTrang = new TinhTrangThietBi(0, maPhongHoc, maTB, ngaySuDung, tinhTrang);
            dataTinhTrang.themTinhTrangThietBi(modelTinhTrang);
        }
    }

    private void suaTinhTrang(int position, ChiTietSuDung chiTietSuDung) {
        ArrayList<TinhTrangThietBi> tinhTrangThietBi;
        ArrayList<String> getDataChange = new ArrayList<>();
        String oldmaTB = listCTSD.get(position).getMaTB();
        String oldmaPhong = listCTSD.get(position).getMaPhong();
        String oldNgaySuDung = listCTSD.get(position).getNgaySuDung();
        int oldSoLuong = listCTSD.get(position).getSoLuong();
        dataTinhTrang = new DataTinhTrang(this);
        tinhTrangThietBi = dataTinhTrang.getTinhTrangTheoCTSD(oldNgaySuDung, oldmaTB, oldmaPhong);

        for (TinhTrangThietBi tinhTrang : tinhTrangThietBi) {
            if (!chiTietSuDung.getMaTB().equals(tinhTrang.getMaThietBi())) {
                tinhTrang.setMaThietBi(chiTietSuDung.getMaTB());
                dataTinhTrang.UpdateTinhTrang(tinhTrang);
            }
            if (!chiTietSuDung.getMaPhong().equals(tinhTrang.getMaPhong())) {
                tinhTrang.setMaPhong(chiTietSuDung.getMaPhong());
                dataTinhTrang.UpdateTinhTrang(tinhTrang);
            }
            if (!chiTietSuDung.getNgaySuDung().equals(tinhTrang.getNgaySuDung())) {
                tinhTrang.setNgaySuDung(chiTietSuDung.getNgaySuDung());
                dataTinhTrang.UpdateTinhTrang(tinhTrang);
            }
        }
        if (chiTietSuDung.getSoLuong() != tinhTrangThietBi.size() && chiTietSuDung.getSoLuong()>=0) {
            if (chiTietSuDung.getSoLuong() > tinhTrangThietBi.size()) {
                for(int i=tinhTrangThietBi.size();i<chiTietSuDung.getSoLuong();i++){
                    themTinhTrang(1);
                }
            }
            else{
                for(int i=tinhTrangThietBi.size();i>chiTietSuDung.getSoLuong();i--){
                    dataTinhTrang.DeleteTinhTrang(tinhTrangThietBi.get(i-1).getId());
                }
            }
        }
    }
    private boolean isValid(){
        boolean valid=true;
        if(edit_ngaySuDung.getText().toString().equals("") ){
            edit_ngaySuDung.setError("Làm ơn không bỏ trống");
            edit_ngaySuDung.requestFocus();
            valid=false;
        }
        if( edit_soLuong.getText().toString().equals("")){
            edit_soLuong.setError("Làm ơn không bỏ trống");
            edit_soLuong.requestFocus();
            valid=false;
        }

        //text
        if(!(edit_soLuong.getText().toString().equals("")) &&Integer.parseInt(edit_soLuong.getText().toString())<0){
            edit_soLuong.setError("Làm ơn nhập lớn hơn hoặc bằng 0");
            edit_soLuong.requestFocus();
            valid=false;
        }
        return valid;
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
