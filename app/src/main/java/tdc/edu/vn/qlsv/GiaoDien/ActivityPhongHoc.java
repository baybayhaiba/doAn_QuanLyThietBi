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

import tdc.edu.vn.qlsv.Database.DataPhongHoc;
import tdc.edu.vn.qlsv.Model.PhongHoc;
import tdc.edu.vn.qlsv.R;


public class ActivityPhongHoc extends AppCompatActivity {

Button bt_add,bt_remove,bt_update,bt_clear;
EditText edit_maPhong,edit_loaiPhong,edit_tang;
ListView listRoom;
ArrayAdapter<PhongHoc>adapter;
int index=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_hoc);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setControl();
        setEvent();
    }
    public void setEvent(){
        final DataPhongHoc dataPhongHoc=new DataPhongHoc(ActivityPhongHoc.this);
        adapter=new ArrayAdapter<PhongHoc>(ActivityPhongHoc.this,android.R.layout.simple_list_item_1,dataPhongHoc.getAllPhongHoc());
        listRoom.setAdapter(adapter);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPhongHoc.themPhongHoc(getLoaiPhongHoc());
                adapter.clear();
                adapter.addAll(dataPhongHoc.getAllPhongHoc());
                adapter.notifyDataSetChanged();
            }
        });
        bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPhongHoc.XoaMaPhong(getLoaiPhongHoc());
                adapter.clear();
                adapter.addAll(dataPhongHoc.getAllPhongHoc());
                adapter.notifyDataSetChanged();
            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPhongHoc.CapNhatPhongHoc(getLoaiPhongHoc());
                adapter.clear();
                adapter.addAll(dataPhongHoc.getAllPhongHoc());
                adapter.notifyDataSetChanged();
            }
        });
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaTatCaNhap();
            }
        });
        listRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
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
    }



}