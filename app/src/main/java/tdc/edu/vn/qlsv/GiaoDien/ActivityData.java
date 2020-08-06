package tdc.edu.vn.qlsv.GiaoDien;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Database.DataLoaiThietBi;
import tdc.edu.vn.qlsv.Database.DataPhongHoc;
import tdc.edu.vn.qlsv.Database.DataThietBi;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.Model.LoaiThietBi;
import tdc.edu.vn.qlsv.Model.PhongHoc;
import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.Network.WebRequest;
import tdc.edu.vn.qlsv.R;
import tdc.edu.vn.qlsv.TableDatabase.Table_ChiTietSD;
import tdc.edu.vn.qlsv.TableDatabase.Table_LoaiThietBi;
import tdc.edu.vn.qlsv.TableDatabase.Table_PhongHoc;
import tdc.edu.vn.qlsv.TableDatabase.Table_ThietBi;

public class ActivityData extends AppCompatActivity{

    // JSON Node names
    Button bt_maTB,bt_TB,bt_CTSD,bt_PhongHoc;
    GetJson getJson;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_json);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setControl();
        setEvent();
    }
    private void setControl(){
        if(!checkInternetConnection()){
            AlertDialog.Builder dialog=new AlertDialog.Builder(ActivityData.this);
            dialog.setTitle("Thông Báo");
            dialog.setMessage("Bạn cần bật mạng để sử dụng tính năng này");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBackPressed();
                }
            });
            dialog.create().show();
        }
        bt_maTB=findViewById(R.id.jsonMaTB);
        bt_TB=findViewById(R.id.jsonTB);
        bt_CTSD=findViewById(R.id.jsonCTSD);
        bt_PhongHoc=findViewById(R.id.jsonPhongHoc);
        listView=findViewById(R.id.lvJson);

    }
    private  void setEvent(){
        bt_maTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJson= (GetJson) new
                        GetJson().execute("https://raw.githubusercontent.com/baybayhaiba/testJson/master/index1.html");

            }
        });
        //set listener
        bt_TB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getJson= (GetJson) new
                GetJson().execute("https://raw.githubusercontent.com/baybayhaiba/testJson/master/index.html");
            }
        });

        bt_PhongHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJson= (GetJson) new
                        GetJson().execute("https://raw.githubusercontent.com/baybayhaiba/testJson/master/index2.html");
            }
        });
        bt_CTSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJson= (GetJson) new
                        GetJson().execute("https://raw.githubusercontent.com/baybayhaiba/testJson/master/index4.html");
            }
        });
    }

    private boolean checkInternetConnection() {
        // Get Connectivity Manager
        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Details about the currently active default data network
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
           // Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
          //  Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
          //  Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }

    private class GetJson extends AsyncTask<String, Void, String> {

        ProgressDialog pDialog;
        ArrayList<HashMap<String, String>> getDataJson;
        ImageView imgData=findViewById(R.id.dataImg);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ActivityData.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            // Creating service handler class instance
            WebRequest webreq = new WebRequest();

            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(arg0[0], WebRequest.GET);
            if(checkType(jsonStr).equals(Table_LoaiThietBi.TABLE_NAME))
                getDataJson= ParseJSONLoaiThietBi(jsonStr);
            else if(checkType(jsonStr).equals(Table_ThietBi.TABLE_NAME))
                getDataJson=ParseJSONThietBi(jsonStr);
            else if(checkType(jsonStr).equals(Table_PhongHoc.TABLE_NAME))
                getDataJson = ParseJSONPhongHoc(jsonStr);
            else
                getDataJson = ParseJSONCTSD(jsonStr);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if(checkType(result).equals(Table_LoaiThietBi.TABLE_NAME))
            {
                ArrayList<LoaiThietBi> loaiTB=new ArrayList<>();
                DataLoaiThietBi databaseLoaiTB=new DataLoaiThietBi(ActivityData.this);
                for(int i=0;i<getDataJson.size();i++){
                    HashMap<String,String> hashMap=getDataJson.get(i);
                    int id=Integer.parseInt(hashMap.get(Table_LoaiThietBi.KEY_ID));
                    String maLoai=hashMap.get(Table_LoaiThietBi.KEY_MALOAI);
                    String tenLoai=hashMap.get(Table_LoaiThietBi.KEY_TENLOAI);
                    loaiTB.add(new LoaiThietBi(id,maLoai,tenLoai));
                    databaseLoaiTB.themLoaiTB(loaiTB.get(i));
                }
                thongBao("Loại Thiết Bị").create().show();
                listView.setAdapter(new ArrayAdapter<LoaiThietBi>(ActivityData.this,
                        android.R.layout.simple_list_item_1,loaiTB));
            }
            else if(checkType(result).equals(Table_ThietBi.TABLE_NAME)){
                ArrayList<ThietBi> thietBi=new ArrayList<>();
                DataThietBi databaseThietBi=new DataThietBi(ActivityData.this);
                    for (int i = 0; i < getDataJson.size(); i++) {
                        HashMap<String, String> hashMap = getDataJson.get(i);
                        int id = databaseThietBi.MaxId()+1;
                        String tenThietBi = hashMap.get(Table_ThietBi.KEY_TENTB);
                        String xuatXu = hashMap.get(Table_ThietBi.KEY_XUATXU);
                        String maLoai = hashMap.get(Table_ThietBi.KEY_MALOAI);
                        String maTB= databaseThietBi.createNewType(maLoai, databaseThietBi.MaxType(maLoai)+1);
                        String url=hashMap.get(Table_ThietBi.KEY_IMAGE);
                        Picasso.get().load(url).into(imgData);
                        byte[] img=getByteBitmap(imgData);
                        thietBi.add(new ThietBi(id, maTB, tenThietBi, xuatXu, maLoai,img));
                        databaseThietBi.themThietbi(thietBi.get(i));
                    }
                    thongBao("Thiết Bị").create().show();
                    listView.setAdapter(new ArrayAdapter<ThietBi>(ActivityData.this,
                            android.R.layout.simple_list_item_1, thietBi));
            }

            else if(checkType(result).equals(Table_PhongHoc.TABLE_NAME)){
                ArrayList<PhongHoc> phongHoc=new ArrayList<>();
                DataPhongHoc databasePhongHoc=new DataPhongHoc(ActivityData.this);
                for(int i=0;i<getDataJson.size();i++){
                    HashMap<String,String> hashMap=getDataJson.get(i);
                    int id=Integer.parseInt(hashMap.get(Table_PhongHoc.KEY_ID));
                    String maPhong=hashMap.get(Table_PhongHoc.KEY_MAPHONG);
                    String tenPhong=hashMap.get(Table_PhongHoc.KEY_LOAIPHONG);
                    int tangPhongHoc=Integer.parseInt(hashMap.get(Table_PhongHoc.KEY_TANG));
                    phongHoc.add(new PhongHoc(id,maPhong,tenPhong,tangPhongHoc));
                    databasePhongHoc.themPhongHoc(phongHoc.get(i));
                }
                thongBao("Phòng Học").create().show();
                listView.setAdapter(new ArrayAdapter<PhongHoc>(ActivityData.this,
                        android.R.layout.simple_list_item_1,phongHoc));
            }
            else{
                ArrayList<ChiTietSuDung> CTSD=new ArrayList<>();
                DataCTSD databaseCTSD=new DataCTSD(ActivityData.this);
                for(int i=0;i<getDataJson.size();i++){
                    HashMap<String,String> hashMap=getDataJson.get(i);
                    int id=Integer.parseInt(hashMap.get(Table_ChiTietSD.KEY_ID));
                    String maPhong=hashMap.get(Table_ChiTietSD.KEY_MAPHONG);
                    String maTB=hashMap.get(Table_ChiTietSD.KEY_MATB);
                    String ngaySD=hashMap.get(Table_ChiTietSD.KEY_NGAYSD);
                    int soLuong=Integer.parseInt(hashMap.get(Table_ChiTietSD.KEY_SOLUONG));
                    CTSD.add(new ChiTietSuDung(id,maPhong,maTB,ngaySD,soLuong));
                    databaseCTSD.themCTSD(CTSD.get(i));
                }
                thongBao("Chi Tiết Sử Dụng").create().show();
                listView.setAdapter(new ArrayAdapter<ChiTietSuDung>(ActivityData.this,
                        android.R.layout.simple_list_item_1,CTSD));
            }
        }
    }
    private byte[] getByteBitmap(ImageView imageView){
        try {
            Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
            Bitmap resizeBitmap=Bitmap.createScaledBitmap(bitmap,120,120,false);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            resizeBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            return bos.toByteArray();
        }catch (Exception e){
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    private String checkType(String type){
        String textType="";
        int countColon=0;

        for(int i=0;i<type.length() && countColon !=3;i++){
            if(type.charAt(i) >='a' && type.charAt(i) <='z')
                textType+=type.charAt(i);
            else if(type.charAt(i) >='A' && type.charAt(i) <='Z')
                textType+=type.charAt(i);
            else
                countColon++;
        }
        return textType;
    }
    private ArrayList<HashMap<String, String>> ParseJSONThietBi(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<HashMap<String, String>> listThietBi = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray listJSThietBi = jsonObj.getJSONArray(Table_ThietBi.TABLE_NAME);

                // looping through All Students
                for (int i = 0; i < listJSThietBi.length(); i++) {
                    JSONObject c = listJSThietBi.getJSONObject(i);

                    String id = c.getString(Table_ThietBi.KEY_ID);
                    String tenTB = c.getString(Table_ThietBi.KEY_TENTB);
                    String maTB = c.getString(Table_ThietBi.KEY_MATB);
                    String xuatXu = c.getString(Table_ThietBi.KEY_XUATXU);
                    String maLoai = c.getString(Table_ThietBi.KEY_MALOAI);
                    String url=c.getString(Table_ThietBi.KEY_IMAGE);

                    // tmp hashmap for single thietBi
                    HashMap<String, String> thietBi = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    thietBi.put(Table_ThietBi.KEY_ID, id);
                    thietBi.put(Table_ThietBi.KEY_TENTB, tenTB);
                    thietBi.put(Table_ThietBi.KEY_MATB,maTB);
                    thietBi.put(Table_ThietBi.KEY_XUATXU, xuatXu);
                    thietBi.put(Table_ThietBi.KEY_MALOAI, maLoai);
                    thietBi.put(Table_ThietBi.KEY_IMAGE,url);
                    // adding thietBi to students list
                    listThietBi.add(thietBi);
                }
                return listThietBi;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }
    private ArrayList<HashMap<String, String>> ParseJSONLoaiThietBi(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<HashMap<String, String>> listLoaiThietBi = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray listMaThietBi = jsonObj.getJSONArray(Table_LoaiThietBi.TABLE_NAME);

                // looping through All Students
                for (int i = 0; i < listMaThietBi.length(); i++) {
                    JSONObject c = listMaThietBi.getJSONObject(i);

                    String id = c.getString(Table_LoaiThietBi.KEY_ID);
                    String tenTB = c.getString(Table_LoaiThietBi.KEY_MALOAI);
                    String maTB = c.getString(Table_LoaiThietBi.KEY_TENLOAI);


                    // tmp hashmap for single thietBi
                    HashMap<String, String> LoaiThietBi = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    LoaiThietBi.put(Table_LoaiThietBi.KEY_ID, id);
                    LoaiThietBi.put(Table_LoaiThietBi.KEY_MALOAI, tenTB);
                    LoaiThietBi.put(Table_LoaiThietBi.KEY_TENLOAI,maTB);


                    // adding thietBi to students list
                    listLoaiThietBi.add(LoaiThietBi);
                }
                return listLoaiThietBi;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }
    private ArrayList<HashMap<String, String>> ParseJSONPhongHoc(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<HashMap<String, String>> listPhongHoc = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray listJSPhongHoc = jsonObj.getJSONArray(Table_PhongHoc.TABLE_NAME);

                // looping through All Students
                for (int i = 0; i < listJSPhongHoc.length(); i++) {
                    JSONObject c = listJSPhongHoc.getJSONObject(i);

                    String id = c.getString(Table_PhongHoc.KEY_ID);
                    String maPhong = c.getString(Table_PhongHoc.KEY_MAPHONG);
                    String tenPhong = c.getString(Table_PhongHoc.KEY_LOAIPHONG);
                    String tangPhongHoc=c.getString(Table_PhongHoc.KEY_TANG);


                    // tmp hashmap for single thietBi
                    HashMap<String, String> PhongHoc = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    PhongHoc.put(Table_PhongHoc.KEY_ID, id);
                    PhongHoc.put(Table_PhongHoc.KEY_MAPHONG, maPhong);
                    PhongHoc.put(Table_PhongHoc.KEY_LOAIPHONG,tenPhong);
                    PhongHoc.put(Table_PhongHoc.KEY_TANG,tangPhongHoc);

                    // adding thietBi to students list
                    listPhongHoc.add(PhongHoc);
                }
                return listPhongHoc;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }
    private ArrayList<HashMap<String, String>> ParseJSONCTSD(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<HashMap<String, String>> listCTSD = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray listJSCTSD = jsonObj.getJSONArray(Table_ChiTietSD.TABLE_NAME);

                // looping through All Students
                for (int i = 0; i < listJSCTSD.length(); i++) {
                    JSONObject c = listJSCTSD.getJSONObject(i);

                    String id = c.getString(Table_ChiTietSD.KEY_ID);
                    String maPhong = c.getString(Table_ChiTietSD.KEY_MAPHONG);
                    String maThietBi = c.getString(Table_ChiTietSD.KEY_MATB);
                    String ngaySuDung = c.getString(Table_ChiTietSD.KEY_NGAYSD);
                    String soLuong = c.getString(Table_ChiTietSD.KEY_SOLUONG);


                    // tmp hashmap for single thietBi
                    HashMap<String, String> chiTietSD = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    chiTietSD.put(Table_ChiTietSD.KEY_ID, id);
                    chiTietSD.put(Table_ChiTietSD.KEY_MAPHONG, maPhong);
                    chiTietSD.put(Table_ChiTietSD.KEY_MATB,maThietBi);
                    chiTietSD.put(Table_ChiTietSD.KEY_NGAYSD, ngaySuDung);
                    chiTietSD.put(Table_ChiTietSD.KEY_SOLUONG, soLuong);

                    // adding thietBi to students list
                    listCTSD.add(chiTietSD);
                }
                return listCTSD;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }
    private AlertDialog.Builder thongBao(String type){
        AlertDialog.Builder dialog=new AlertDialog.Builder(ActivityData.this);
        dialog.setTitle("Thông Báo");
        dialog.setMessage("Bạn đã thêm dữ liệu " +type+" thành công");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return dialog;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(ActivityData.this,MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
}