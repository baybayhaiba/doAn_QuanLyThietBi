package tdc.edu.vn.qlsv.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tdc.edu.vn.qlsv.TableDatabase.Table_ChiTietSD;
import tdc.edu.vn.qlsv.TableDatabase.Table_LoaiThietBi;
import tdc.edu.vn.qlsv.TableDatabase.Table_PhongHoc;
import tdc.edu.vn.qlsv.TableDatabase.Table_ThietBi;


//import com.example.quanlythietbi.TableSQL.Table_ChiTietSD;
//import com.example.quanlythietbi.TableSQL.Table_LoaiThietBi;
//import com.example.quanlythietbi.TableSQL.Table_PhongHoc;
//import com.example.quanlythietbi.TableSQL.Table_ThietBi;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Table_LoaiThietBi.DB_NAME,null, Table_LoaiThietBi.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tao bang loai thiet bi
        String sqlLoaiThietBi="CREATE TABLE "+ Table_LoaiThietBi.TABLE_NAME+"("
                + Table_LoaiThietBi.KEY_ID +" INTEGER PRIMARY KEY,"
                + Table_LoaiThietBi.KEY_MALOAI + " TEXT,"
                + Table_LoaiThietBi.KEY_TENLOAI+ " TEXT);";
        //tao bang thiet bi
        String sqlThietBi="CREATE TABLE "+ Table_ThietBi.TABLE_NAME + " ("
                +Table_ThietBi.KEY_ID + " INTEGER PRIMARY KEY,"
                +Table_ThietBi.KEY_MATB + " TEXT,"
                +Table_ThietBi.KEY_TENTB + " TEXT,"
                +Table_ThietBi.KEY_XUATXU + " TEXT,"
                +Table_ThietBi.KEY_MALOAI + " TEXT,"
                + Table_ThietBi.KEY_IMAGE+" BLOB);";
        //tao bang chi tiet su dung
        String sqlChiTietSD="CREATE TABLE "+ Table_ChiTietSD.TABLE_NAME+" ( "
                +Table_ChiTietSD.KEY_ID+" INTEGER PRIMARY KEY ,"
                +Table_ChiTietSD.KEY_MAPHONG+" TEXT,"
                +Table_ChiTietSD.KEY_MATB+" TEXT,"
                +Table_ChiTietSD.KEY_NGAYSD+" TEXT,"
                +Table_ChiTietSD.KEY_SOLUONG+" TEXT);";
        //tao bang phong hoc
        String sqlPhongHoc="CREATE TABLE "+ Table_PhongHoc.TABLE_NAME +"("
                +Table_PhongHoc.KEY_ID + " INTEGER PRIMARY KEY,"
                +Table_PhongHoc.KEY_MAPHONG + " TEXT,"
                +Table_PhongHoc.KEY_LOAIPHONG + " TEXT,"
                +Table_PhongHoc.KEY_TANG + " INTEGER);";

        db.execSQL(sqlLoaiThietBi);
        db.execSQL(sqlThietBi);
        db.execSQL(sqlChiTietSD);
        db.execSQL(sqlPhongHoc);
        //dataAvailable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
