package tdc.edu.vn.qlsv.GiaoDien;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;
import tdc.edu.vn.qlsv.Adapter.CustomAdapterTB;
import tdc.edu.vn.qlsv.Database.DataLoaiThietBi;
import tdc.edu.vn.qlsv.Database.DataThietBi;
import tdc.edu.vn.qlsv.Model.LoaiThietBi;
import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.R;


public class ActivityThietBi extends AppCompatActivity {

    Spinner sp_xuatXu, sp_maLoai;

    ArrayList<String> dataXuatXu;
    ArrayList<String> dataMaLoai;
    ArrayList<ThietBi> dataThietBi;

    RecyclerView recyclerViewThietBi;
    EditText editTenTB, editMaTB, editIDThietBi;
    Button bt_them, bt_xoa, bt_sua, bt_clear;
    //tao database de lay gia tri spinner maLoai
    DataLoaiThietBi databaseLTB;
    //luu tru database va lay tat ca
    DataThietBi databaseThietBi;
    CircleImageView imageViewURL;
    CustomAdapterTB adapterTB;
    int index = -1;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thietbi);
        ActionBar();
        setControl();
        setEvent();
    }

    private void ActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        ((TextView) actionBar.getCustomView().findViewById(R.id.actionBarTitle)).setText("Thiết Bị");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setEvent() {
        //spiner xuatxu
        khoiTao();
        final ArrayAdapter adapterXuatXu = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataXuatXu);
        sp_xuatXu.setAdapter(adapterXuatXu);
        //spinner loaiThietbi
        databaseLTB = new DataLoaiThietBi(this);
        final ArrayAdapter adapterLoaiTB = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataMaLoai);
        sp_maLoai.setAdapter(adapterLoaiTB);
        //adapter listview
        databaseThietBi = new DataThietBi(this);
        dataThietBi = databaseThietBi.getAllThietBi();
        adapterTB = new CustomAdapterTB(R.layout.list_custom_thietbi, dataThietBi);
        recyclerViewThietBi.setAdapter(adapterTB);

        //button listener
        bt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThietBi thietBi = getThemThietBi();
                databaseThietBi.themThietbi(thietBi);
                dataThietBi.add(thietBi);
                Collections.sort(dataThietBi, new Comparator<ThietBi>() {
                    @Override
                    public int compare(ThietBi thietBi, ThietBi t1) {
                        return t1.getId() - thietBi.getId();
                    }
                });
                adapterTB.notifyDataSetChanged();
            }
        });
        bt_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != -1) {
                    databaseThietBi.xoaThietBi(getSuaThietBi());
                    dataThietBi.remove(index);
                    adapterTB.notifyItemRemoved(index);
                    index = -1;
                } else
                    Toast.makeText(ActivityThietBi.this, "Bạn chưa chọn bất cứ thứ gì", Toast.LENGTH_SHORT).show();
            }
        });
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTenTB.setText("");
                sp_maLoai.setSelection(0);
                sp_xuatXu.setSelection(0);
                index = -1;
                imageViewURL.setImageResource(R.drawable.choosepicture);
            }
        });
        bt_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != -1) {
                    ThietBi thietBi = getSuaThietBi();
                    databaseThietBi.suaThietBi(thietBi);
                    dataThietBi.set(index, thietBi);
                    adapterTB.notifyItemChanged(index);
                } else
                    Toast.makeText(ActivityThietBi.this, "Bạn chưa chọn bất cứ thứ gì", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        PickImageFromGallery();
                    }
                } else
                    PickImageFromGallery();
            }
        });

        adapterTB.setListener(new CustomAdapterTB.Listener() {
            @Override
            public void onClick(int position) {
                index = position;
                editIDThietBi.setText(String.valueOf(dataThietBi.get(position).getId()));
                editMaTB.setText(dataThietBi.get(position).getMaTB());
                editTenTB.setText(dataThietBi.get(position).getTenTB());
                int positionXuatXu = adapterXuatXu.getPosition(dataThietBi.get(position).getXuatXuTB());
                int positionLoaiTB = adapterLoaiTB.getPosition(dataThietBi.get(position).getMaLoaiTB());
                sp_xuatXu.setSelection(positionXuatXu);
                if (positionLoaiTB == -1) {
                    Toast.makeText
                            (ActivityThietBi.this,
                                    "Ban khong co du lieu ma loai",
                                    Toast.LENGTH_SHORT).show();
                } else {
                    sp_maLoai.setSelection(positionLoaiTB);
                }
                if (dataThietBi.get(position).getImageTB() != null) {
                    Bitmap bitmapToImage = BitmapFactory.decodeByteArray
                            (dataThietBi.get(position).getImageTB(), 0, dataThietBi.get(position).getImageTB().length);
                    imageViewURL.setImageBitmap(bitmapToImage);
                } else
                    imageViewURL.setImageResource(R.drawable.choosepicture);

            }
        });
    }

    private void khoiTao() {
        dataXuatXu.add("Việt Nam");
        dataXuatXu.add("Mỹ");
        dataXuatXu.add("Hàn Quốc");
        dataXuatXu.add("Thái Lan");
        dataXuatXu.add("Nhật");
        databaseLTB = new DataLoaiThietBi(this);
        for (LoaiThietBi loaiTB : databaseLTB.getAllLoaiTB())
            dataMaLoai.add(loaiTB.getMaLoai());
    }


    private void setControl() {
        sp_xuatXu = findViewById(R.id.sp_xuatXu);

        dataXuatXu = new ArrayList<>();
        dataMaLoai = new ArrayList<>();
        dataThietBi = new ArrayList<>();
        editMaTB = findViewById(R.id.edit_maTB);
        editIDThietBi = findViewById(R.id.edit_idTB);
        editTenTB = findViewById(R.id.edit_tenTB);
        sp_maLoai = findViewById(R.id.sp_maLoai);

        bt_them = findViewById(R.id.bt_add);
        bt_xoa = findViewById(R.id.bt_remove);
        bt_sua = findViewById(R.id.bt_update);
        bt_clear = findViewById(R.id.bt_clear);
        imageViewURL = findViewById(R.id.imageThietBi);
        recyclerViewThietBi = findViewById(R.id.recyclerViewThietBi);
        recyclerViewThietBi.setLayoutManager(new LinearLayoutManager(this));
    }

    private ThietBi getThemThietBi() {
        int id = databaseThietBi.MaxId() + 1;
        String tenTB = editTenTB.getText().toString();
        String maLoai = sp_maLoai.getSelectedItem().toString();
        String xuatXu = sp_xuatXu.getSelectedItem().toString();
        String maTB = databaseThietBi.createNewType(maLoai, databaseThietBi.MaxType(maLoai) + 1);
        byte[] bytes = getByteBitmap();
        return new ThietBi(id, maTB, tenTB, xuatXu, maLoai, bytes);
    }

    private byte[] getByteBitmap() {
        try {
            Bitmap bitmap = ((BitmapDrawable) imageViewURL.getDrawable()).getBitmap();
            Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            resizeBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            return bos.toByteArray();
        } catch (Exception e) {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    private int checkPositionSearch(String tenThietBi){
        for (int i=0;i<dataThietBi.size();i++){
            if(dataThietBi.get(i).getTenTB()==tenThietBi)
                return i;
        }
        return -1;
    }
    private ThietBi getSuaThietBi() {
        int id = Integer.parseInt(editIDThietBi.getText().toString());
        String tenTB = editTenTB.getText().toString();
        String maLoai = sp_maLoai.getSelectedItem().toString();
        String xuatXu = sp_xuatXu.getSelectedItem().toString();
        String maTB = "";
        if (!dataThietBi.get(index).getMaLoaiTB().equals(sp_maLoai.getSelectedItem().toString()))
            maTB = databaseThietBi.createNewType(maLoai, databaseThietBi.MaxType(maLoai) + 1);
        else
            maTB = editMaTB.getText().toString();
        byte[] bytes = getByteBitmap();
        return new ThietBi(id, maTB, tenTB, xuatXu, maLoai, bytes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ActivityThietBi.this, MainActivity.class));
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
                adapterTB.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void PickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    PickImageFromGallery();
                else
                    Toast.makeText(this, "Permisstion deny", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageViewURL.setImageURI(data.getData());
        }
    }
}