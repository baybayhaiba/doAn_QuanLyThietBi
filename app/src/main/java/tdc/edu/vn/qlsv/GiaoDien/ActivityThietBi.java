package tdc.edu.vn.qlsv.GiaoDien;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

//import com.example.quanlythietbi.Class.ThietBi;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

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
    EditText editTenTB,editMaTB;

    Button bt_them,bt_xoa,bt_sua,bt_clear,bt_finish;
    ImageButton img_signature;
    //tao database de lay gia tri spinner maLoai
    DataLoaiThietBi databaseLTB;
    //luu tru database va lay tat ca
    DataThietBi databaseThietBi;
    CustomAdapterTB adapterTB;
    int index=-1;
    CustomSign customSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thietbi);
        setControl();
        setEvent();
        ActionBar actionBar=getSupportActionBar();
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

        img_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });

        //button listener
        bt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThietBi thietBi=getThietBi();
                if(isValid()) {
                    databaseThietBi.themThietbi(getThietBi());
                    String dinhDangMaLoai = thietBi.getMaLoaiTB() + String.format("%02d",
                            databaseThietBi.getCountByType(thietBi.getMaLoaiTB()));
                    thietBi.setMaTB(dinhDangMaLoai);
                    dataThietBi.add(thietBi);
                    adapterTB.notifyDataSetChanged();
                }
            }
        });
        bt_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index!=-1) {
                    databaseThietBi.xoaThietBi(dataThietBi.get(index));
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

            }
        });
        bt_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index!=-1) {
                    ThietBi thietBi=getThietBi();
                    databaseThietBi.suaThietBi(getThietBi());
                    dataThietBi.set(index,thietBi);
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
                editMaTB.setText(databaseThietBi.getAllThietBi().get(index).getMaTB());
                editTenTB.setText(databaseThietBi.getAllThietBi().get(index).getTenTB());
                int positionXuatXu=adapterXuatXu.getPosition(databaseThietBi.getAllThietBi().get(index).getXuatXuTB());
                int positionLoaiTB=adapterLoaiTB.getPosition(databaseThietBi.getAllThietBi().get(index).getMaLoaiTB());
                sp_xuatXu.setSelection(positionXuatXu);
                sp_maLoai.setSelection(positionLoaiTB);
                Toast.makeText(ActivityThietBi.this, ""+dataThietBi.get(index).getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //kiem tra xem du lieu dau vao co hop ly khong
    private boolean isValid(){
        if(this.editTenTB.getText().toString().equals("")) {
            editTenTB.setError("Bạn nên nhập đầy đủ tên thiết bị");
            return false;
        }
        return true;
    }
    private Bitmap getSignature(){
        customSign.buildDrawingCache();
        Bitmap signature=customSign.getDrawingCache();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        signature.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray=stream.toByteArray();
        return signature;
    }
    public void createPopupDialog(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(ActivityThietBi.this);
        View view=getLayoutInflater().inflate(R.layout.signature,null);
        customSign=view.findViewById(R.id.customSign);
        bt_finish=view.findViewById(R.id.bt_finish);
        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customSign.buildDrawingCache();
                Bitmap signature=customSign.getDrawingCache();
               img_signature.setImageBitmap(signature);
            }
        });
        builder.setView(view);
        final AlertDialog show= builder.show();



    }
    private void khoiTao() {
        dataXuatXu.add("Việt Nam");
        dataXuatXu.add("Mỹ");
        dataXuatXu.add("Hàn");

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
        editTenTB=findViewById(R.id.edit_tenTB);
        sp_maLoai=findViewById(R.id.sp_maLoai);


        bt_them=findViewById(R.id.bt_add);
        bt_xoa=findViewById(R.id.bt_remove);
        bt_sua=findViewById(R.id.bt_update);
        bt_clear=findViewById(R.id.bt_clear);

        img_signature=findViewById(R.id.bt_signature);

        recyclerViewThietBi=findViewById(R.id.recyclerViewThietBi);
        recyclerViewThietBi.setLayoutManager(new LinearLayoutManager(this));

    }
    private ThietBi getThietBi(){
        String tenTB=editTenTB.getText().toString();
        String maLoai=sp_maLoai.getSelectedItem().toString();
        String xuatXu=sp_xuatXu.getSelectedItem().toString();
        String maTB=editMaTB.getText().toString();
        ThietBi device=new ThietBi(0,maTB,tenTB,xuatXu,maLoai);
        return device;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }



}