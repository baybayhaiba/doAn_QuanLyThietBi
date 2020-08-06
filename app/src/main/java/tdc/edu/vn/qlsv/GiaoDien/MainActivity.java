package tdc.edu.vn.qlsv.GiaoDien;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Adapter.CustomAdapterMain;
import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Database.DataLoaiThietBi;
import tdc.edu.vn.qlsv.Database.DataPhongHoc;
import tdc.edu.vn.qlsv.Database.DataThietBi;
import tdc.edu.vn.qlsv.Model.Main;
import tdc.edu.vn.qlsv.R;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> data;
    ArrayList<Main> dataUI;
    RecyclerView list;
    CustomAdapterMain adapter;
    @Override
    //onc
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {
        khoiTaoDuLieu();
        adapter=new CustomAdapterMain(R.layout.list_custom_main,dataUI);

        list.setAdapter(adapter);
        adapter.setListener(new CustomAdapterMain.ListenerMain() {
            @Override
            public void onClick(int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this,ActivityLoaiThietBi.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,ActivityThietBi.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,ActivityChiTietSD.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,ActivityPhongHoc.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, ActivityData.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this,ActivityAnalysis.class));
                }
            }
        });
    }

    private void setControl() {
        list=findViewById(R.id.list_hienThiDS);
        list.setLayoutManager(new LinearLayoutManager(this));
        data=new ArrayList<>();
        dataUI=new ArrayList<>();
    }

    private void khoiTaoDuLieu(){
        data.add("Thêm Loại Thiết Bị");
        data.add("Thêm Thiết Bị");
        data.add("Thêm Chi Tiết Sử Dụng");
        data.add("Thêm Phòng Học");
        int countMaThietBi=new DataLoaiThietBi(this).getCountLTB();
        int countThietBi=new DataThietBi(this).getCount();
        int countPhongHoc=new DataPhongHoc(this).getCountPhongHoc();
        int countCTSD=new DataCTSD(this).getCountCTSD();
        dataUI.add(new Main(R.drawable.mathietbi,"Mã Thiết Bị",countMaThietBi,Color.parseColor("#b794f6")));
        dataUI.add(new Main(R.drawable.thietbi,"Thiết Bị",countThietBi, Color.parseColor("#c6f68d")));
        dataUI.add(new Main(R.drawable.chitietsudung,"Chi Tiết Sử Dụng",countCTSD, Color.parseColor("#90ee02")));
        dataUI.add(new Main(R.drawable.phonghoc,"Phòng Học",countPhongHoc, Color.parseColor("#ffc77d")));
        dataUI.add(new Main(R.drawable.digital,"Dữ liệu có sẵn (online)",4, Color.parseColor("#AA00FF")));
        dataUI.add(new Main(R.drawable.growth,"Thống kê số lượng",0,Color.parseColor("#5C6BC0")));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnExit:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có muốn đăng xuất không ?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       startActivity(new Intent(MainActivity.this,DangNhap.class));
                    }
                });
                builder.create().show();
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
