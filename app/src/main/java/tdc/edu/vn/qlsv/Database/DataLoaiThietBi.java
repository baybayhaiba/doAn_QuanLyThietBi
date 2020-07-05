package tdc.edu.vn.qlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//import com.example.quanlythietbi.Database.DatabaseHandler;
//import com.example.quanlythietbi.TableSQL.Table_LoaiThietBi;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Model.LoaiThietBi;
import tdc.edu.vn.qlsv.TableDatabase.Table_LoaiThietBi;


public class DataLoaiThietBi {
    DatabaseHandler handler;

    public DataLoaiThietBi(Context context) {
        this.handler = new DatabaseHandler(context);
    }

    public void themLoaiTB(LoaiThietBi loaiThietBi)
    {
        SQLiteDatabase db=handler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Table_LoaiThietBi
                .KEY_MALOAI,loaiThietBi.getMaLoai());
        values.put(Table_LoaiThietBi.KEY_TENLOAI,loaiThietBi.getTenLoai());
        db.insert(Table_LoaiThietBi.TABLE_NAME,null,values);
        db.close();
    }
    public ArrayList<LoaiThietBi> getAllLoaiTB(){
        ArrayList<LoaiThietBi> LoaiTB=new ArrayList<>();
        SQLiteDatabase db=handler.getReadableDatabase();
        String sql="SELECT * from " + Table_LoaiThietBi.TABLE_NAME;
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null && cursor.moveToFirst()) {
            do {
                String maLoai = cursor.getString(1);
                String tenLoai = cursor.getString(2);
                LoaiThietBi loaiThietBi = new LoaiThietBi(maLoai, tenLoai);
                LoaiTB.add(loaiThietBi);
            } while (cursor.moveToNext());
        }
        return LoaiTB;
    }
    public void XoaLoaiTB(LoaiThietBi loaiTB){
        SQLiteDatabase db=handler.getWritableDatabase();
        db.delete(Table_LoaiThietBi.TABLE_NAME,Table_LoaiThietBi.KEY_MALOAI+"=?",new String[]{loaiTB.getMaLoai()});
    }
    public int CapNhatLoaiTB(LoaiThietBi loaiThietBi){
        SQLiteDatabase db=handler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Table_LoaiThietBi.KEY_MALOAI,loaiThietBi.getMaLoai());
        values.put(Table_LoaiThietBi.KEY_TENLOAI,loaiThietBi.getTenLoai());
        return db.update(Table_LoaiThietBi.TABLE_NAME,values,Table_LoaiThietBi.KEY_MALOAI+"=?",new String[]{loaiThietBi.getMaLoai()});
    }
}
