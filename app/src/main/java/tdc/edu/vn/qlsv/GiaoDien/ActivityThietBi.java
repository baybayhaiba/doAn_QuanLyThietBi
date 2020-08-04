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
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.quanlythietbi.Class.ThietBi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tdc.edu.vn.qlsv.Adapter.CustomAdapterTB;
import tdc.edu.vn.qlsv.Database.DataLoaiThietBi;
import tdc.edu.vn.qlsv.Database.DataThietBi;
import tdc.edu.vn.qlsv.Model.LoaiThietBi;
import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.R;


public class ActivityThietBi extends AppCompatActivity{

    Spinner sp_xuatXu,sp_maLoai;

    ArrayList<String> dataXuatXu;
    ArrayList<String> dataMaLoai;
    ArrayList<ThietBi> dataThietBi;

    RecyclerView recyclerViewThietBi;
    EditText editTenTB,editMaTB,editIDThietBi;

    Button bt_them,bt_xoa,bt_sua,bt_clear;
    //tao database de lay gia tri spinner maLoai
    DataLoaiThietBi databaseLTB;
    //luu tru database va lay tat ca
    DataThietBi databaseThietBi;
    CustomAdapterTB adapterTB;
    int index=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thietbi);
        ActionBar();
        setControl();
        setEvent();
    }
    private void ActionBar(){
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        ((TextView)actionBar.getCustomView().findViewById(R.id.actionBarTitle)).setText("Thiết Bị");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setEvent() {
        //spiner xuatxu
        khoiTao();
        final ArrayAdapter adapterXuatXu=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataXuatXu);
        sp_xuatXu.setAdapter(adapterXuatXu);
        //spinner loaiThietbi
        databaseLTB=new DataLoaiThietBi(this);
        final ArrayAdapter adapterLoaiTB=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataMaLoai);
        sp_maLoai.setAdapter(adapterLoaiTB);
        //adapter listview

        databaseThietBi=new DataThietBi(this);
        dataThietBi=databaseThietBi.getAllThietBi();
        adapterTB=new CustomAdapterTB(R.layout.list_custom_thietbi,dataThietBi);
        recyclerViewThietBi.setAdapter(adapterTB);



        //button listener
        bt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThietBi thietBi= getThemThietBi();
                databaseThietBi.themThietbi(thietBi);
                dataThietBi.add(thietBi);
                Collections.sort(dataThietBi, new Comparator<ThietBi>() {
                    @Override
                    public int compare(ThietBi thietBi, ThietBi t1) {
                       return t1.getId()-thietBi.getId();
                    }
                });
                adapterTB.notifyDataSetChanged();
            }
        });
        bt_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index!=-1) {
                    databaseThietBi.xoaThietBi(getSuaThietBi());
                    dataThietBi.remove(index);
                    adapterTB.notifyItemRemoved(index);
                    index=-1;
                }else
                    Toast.makeText(ActivityThietBi.this, "Bạn chưa chọn bất cứ thứ gì", Toast.LENGTH_SHORT).show();
            }
        });
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTenTB.setText("");
                sp_maLoai.setSelection(0);
                sp_xuatXu.setSelection(0);
                index=-1;
                Toast.makeText(ActivityThietBi.this, ""+databaseThietBi.MaxId(), Toast.LENGTH_SHORT).show();
            }
        });
        bt_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index!=-1) {
                    ThietBi thietBi= getSuaThietBi();
                    databaseThietBi.suaThietBi(thietBi);
                    dataThietBi.set(index, thietBi);
                    adapterTB.notifyItemChanged(index);
                }else
                    Toast.makeText(ActivityThietBi.this, "Bạn chưa chọn bất cứ thứ gì", Toast.LENGTH_SHORT).show();
            }
        });
//
        adapterTB.setListener(new CustomAdapterTB.Listener() {
            @Override
            public void onClick(int position) {
                index=position;
                editIDThietBi.setText(String.valueOf(dataThietBi.get(position).getId()));
                editMaTB.setText(dataThietBi.get(position).getMaTB());
                editTenTB.setText(dataThietBi.get(position).getTenTB());
                int positionXuatXu=adapterXuatXu.getPosition(dataThietBi.get(position).getXuatXuTB());
                int positionLoaiTB=adapterLoaiTB.getPosition(dataThietBi.get(position).getMaLoaiTB());
                sp_xuatXu.setSelection(positionXuatXu);
                sp_maLoai.setSelection(positionLoaiTB);
                Toast.makeText(ActivityThietBi.this, ""+dataThietBi.get(position).getId()+"|"+
                        editMaTB.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void khoiTao() {
        dataXuatXu.add("Việt Nam");
        dataXuatXu.add("Mỹ");
        dataXuatXu.add("Hàn Quốc");
        dataXuatXu.add("Thái Lan");
        dataXuatXu.add("Nhật");
        databaseLTB=new DataLoaiThietBi(this);
        for(LoaiThietBi loaiTB:databaseLTB.getAllLoaiTB())
            dataMaLoai.add(loaiTB.getMaLoai());
    }


    private void setControl() {
        sp_xuatXu=findViewById(R.id.sp_xuatXu);

        dataXuatXu=new ArrayList<>();
        dataMaLoai=new ArrayList<>();
        dataThietBi=new ArrayList<>();
        editMaTB=findViewById(R.id.edit_maTB);
        editIDThietBi=findViewById(R.id.edit_idTB);
        editTenTB=findViewById(R.id.edit_tenTB);
        sp_maLoai=findViewById(R.id.sp_maLoai);

        bt_them=findViewById(R.id.bt_add);
        bt_xoa=findViewById(R.id.bt_remove);
        bt_sua=findViewById(R.id.bt_update);
        bt_clear=findViewById(R.id.bt_clear);

        recyclerViewThietBi=findViewById(R.id.recyclerViewThietBi);
        recyclerViewThietBi.setLayoutManager(new LinearLayoutManager(this));
    }
    private ThietBi getThemThietBi(){
        int id=databaseThietBi.MaxId()+1;
        String tenTB=editTenTB.getText().toString();
        String maLoai=sp_maLoai.getSelectedItem().toString();
        String xuatXu=sp_xuatXu.getSelectedItem().toString();
        String maTB= databaseThietBi.createNewType(maLoai, databaseThietBi.MaxType(maLoai)+1);
        return new ThietBi(id,maTB,tenTB,xuatXu,maLoai);
    }
    private ThietBi getSuaThietBi(){
        int id=Integer.parseInt(editIDThietBi.getText().toString());
        String tenTB=editTenTB.getText().toString();
        String maLoai=sp_maLoai.getSelectedItem().toString();
        String xuatXu=sp_xuatXu.getSelectedItem().toString();
        String maTB="";
        if(!dataThietBi.get(index).getMaLoaiTB().equals(sp_maLoai.getSelectedItem().toString()))
            maTB = databaseThietBi.createNewType(maLoai, databaseThietBi.MaxType(maLoai) + 1);
        else
            maTB = editMaTB.getText().toString();
        return new ThietBi(id,maTB,tenTB,xuatXu,maLoai);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(ActivityThietBi.this,MainActivity.class));
                break;
            case R.id.mnExit:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}