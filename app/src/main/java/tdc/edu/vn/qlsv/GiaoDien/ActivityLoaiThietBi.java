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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

//import com.example.quanlythietbi.LoaiThietBi.dataLoaiThietBi;
//import com.example.quanlythietbi.LoaiThietBi.LoaiThietBi;

import tdc.edu.vn.qlsv.Database.DataLoaiThietBi;
import tdc.edu.vn.qlsv.Model.LoaiThietBi;
import tdc.edu.vn.qlsv.R;

public class ActivityLoaiThietBi extends AppCompatActivity {

    Button bt_them,bt_xoa,bt_sua,bt_clear;
    EditText edit_maLoai,edit_tenLoai;
    ListView lv_maThietBi;
    ArrayAdapter<LoaiThietBi> adapter;
    int index=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_thietbi);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setControl();
        setEvent();
    }


    private void setEvent() {
        final DataLoaiThietBi dataChiTiet=new DataLoaiThietBi(ActivityLoaiThietBi.this);

        adapter=new ArrayAdapter<LoaiThietBi>(ActivityLoaiThietBi.this,android.R.layout.simple_list_item_1,
                dataChiTiet.getAllLoaiTB());
        lv_maThietBi.setAdapter(adapter);
        bt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataChiTiet.themLoaiTB(getLoai());
                adapter.clear();
                adapter.addAll(dataChiTiet.getAllLoaiTB());
                adapter.notifyDataSetChanged();
            }
        });
        bt_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataChiTiet.XoaLoaiTB(getLoaiTB());
                adapter.clear();
                adapter.addAll(dataChiTiet.getAllLoaiTB());
                adapter.notifyDataSetChanged();
            }
        });
        bt_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataChiTiet.CapNhatLoaiTB(getLoaiTB());
                adapter.clear();
                adapter.addAll(dataChiTiet.getAllLoaiTB());
                adapter.notifyDataSetChanged();
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
                edit_maLoai.setText(dataChiTiet.getAllLoaiTB().get(position).getMaLoai());
                edit_tenLoai.setText(dataChiTiet.getAllLoaiTB().get(position).getTenLoai());
                index=position;
            }
        });
    }

    private LoaiThietBi getLoaiTB(){
        String maLoai=edit_maLoai.getText().toString();
        String tenLoai=edit_tenLoai.getText().toString();
        return new LoaiThietBi(maLoai,tenLoai);
    }
    private void xoaTatCaNhap(){
        edit_maLoai.setText("");
        edit_tenLoai.setText("");
    }

    private LoaiThietBi getLoai(){
        String maLoai=edit_maLoai.getText().toString();
        String tenLoai=edit_tenLoai.getText().toString();
        return new LoaiThietBi(maLoai,tenLoai);
    }

    private void setControl() {
        bt_them=findViewById(R.id.bt_add);
        bt_xoa=findViewById(R.id.bt_remove);
        bt_sua=findViewById(R.id.bt_update);
        bt_clear=findViewById(R.id.bt_clear);

        edit_maLoai=findViewById(R.id.edit_maLoai);
        edit_tenLoai=findViewById(R.id.edit_tenLoai);

        lv_maThietBi=findViewById(R.id.list_loaiTB);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(ActivityLoaiThietBi.this,MainActivity.class));
        return super.onOptionsItemSelected(item);
    }



}