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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import tdc.edu.vn.qlsv.Adapter.CustomAdapterPhongHoc;
import tdc.edu.vn.qlsv.Database.DataPhongHoc;
import tdc.edu.vn.qlsv.Model.PhongHoc;
import tdc.edu.vn.qlsv.R;
import tdc.edu.vn.qlsv.TableDatabase.Table_PhongHoc;


public class ActivityPhongHoc extends AppCompatActivity {

Button bt_add,bt_remove,bt_update,bt_clear;
EditText edit_maPhong,edit_loaiPhong,edit_tang;
RecyclerView listRoom;
CustomAdapterPhongHoc adapterPhongHoc;
DataPhongHoc dataPhongHoc;
int index=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_hoc);
        ActionBar();
        setControl();
        setEvent();
    }
    private void ActionBar(){
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        ((TextView)actionBar.getCustomView().findViewById(R.id.actionBarTitle)).setText("Phòng Học");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public void setEvent(){
        dataPhongHoc=new DataPhongHoc(ActivityPhongHoc.this);

        adapterPhongHoc=new CustomAdapterPhongHoc(R.layout.list_custom_phonghoc,
                dataPhongHoc.getAllPhongHoc());
        listRoom.setAdapter(adapterPhongHoc);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPhongHoc.themPhongHoc(getLoaiPhongHoc());

            }
        });
        bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPhongHoc.XoaMaPhong(getLoaiPhongHoc());

            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPhongHoc.CapNhatPhongHoc(getLoaiPhongHoc());

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
                edit_maPhong.setText(dataPhongHoc.getAllPhongHoc().get(position).getMaPhong());
                edit_loaiPhong.setText(dataPhongHoc.getAllPhongHoc().get(position).getLoaiPhong());
                edit_tang.setText(String.valueOf(dataPhongHoc.getAllPhongHoc().get(position).getTang()));
                index=position;
            }
        });

    }

    private PhongHoc getLoaiPhongHoc()
    {
        String maPhong = edit_maPhong.getText().toString();
        String LoaiPhong = edit_loaiPhong.getText().toString();
        int tang=Integer.parseInt(edit_tang.getText().toString());
        return new PhongHoc(maPhong,LoaiPhong,tang);
    }
    private void xoaTatCaNhap(){
        edit_maPhong.setText("");
        edit_loaiPhong.setText("");
        edit_tang.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(ActivityPhongHoc.this,MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
    private void setControl() {
        bt_add=findViewById(R.id.bt_add);
        bt_remove=findViewById(R.id.bt_remove);
        bt_update=findViewById(R.id.bt_update);
        bt_clear=findViewById(R.id.bt_clear);

        edit_maPhong=findViewById(R.id.edit_maPhong);
        edit_loaiPhong=findViewById(R.id.edit_loaiPhong);
        edit_tang=findViewById(R.id.edit_tang);
        listRoom=findViewById(R.id.listRoom);
        listRoom.setLayoutManager(new LinearLayoutManager(this));
    }



}