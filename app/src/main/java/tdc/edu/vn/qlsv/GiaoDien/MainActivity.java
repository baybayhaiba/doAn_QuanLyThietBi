package tdc.edu.vn.qlsv.GiaoDien;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.R;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> data;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTaoDuLieu();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this, ActivityLoaiThietBi.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,ActivityThietBi.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, ActivityChiTietSD.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,ActivityPhongHoc.class));
                        break;
                }
            }
        });
    }

    private void setControl() {
        list=findViewById(R.id.list_hienThiDS);
        data=new ArrayList<>();
    }



    private void khoiTaoDuLieu(){
        data.add("Thêm Loại Thiết Bị");
        data.add("Thêm Thiết Bị");
        data.add("Thêm Chi Tiết Sử Dụng");
        data.add("Thêm Phòng Học");
    }




}
