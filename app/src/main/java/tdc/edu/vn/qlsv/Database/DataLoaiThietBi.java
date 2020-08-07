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
import tdc.edu.vn.qlsv.TableDatabase.Table_ThietBi;


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
        String sql="SELECT * from " + Table_LoaiThietBi.TABLE_NAME+" ORDER by "+Table_LoaiThietBi.KEY_ID+" DESC";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null && cursor.moveToFirst()) {
            do {
                int id=cursor.getInt(0);
                String maLoai = cursor.getString(1);
                String tenLoai = cursor.getString(2);
                LoaiThietBi loaiThietBi = new LoaiThietBi(id,maLoai, tenLoai);
                LoaiTB.add(loaiThietBi);
            } while (cursor.moveToNext());
        }
        return LoaiTB;
    }
    public void XoaLoaiTB(LoaiThietBi loaiTB){
        SQLiteDatabase db=handler.getWritableDatabase();
        db.delete(Table_LoaiThietBi.TABLE_NAME,Table_LoaiThietBi.KEY_ID+"=?",
                new String[]{String.valueOf(loaiTB.getId())});
    }
    public int CapNhatLoaiTB(LoaiThietBi loaiThietBi){
        SQLiteDatabase db=handler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Table_LoaiThietBi.KEY_MALOAI,loaiThietBi.getMaLoai());
        values.put(Table_LoaiThietBi.KEY_TENLOAI,loaiThietBi.getTenLoai());
        return db.update(Table_LoaiThietBi.TABLE_NAME,values,Table_LoaiThietBi.KEY_ID+"=?",
                new String[]{String.valueOf(loaiThietBi.getId())});
    }
    public int getCountLTB(){
        String sql="SELECT * from "+Table_LoaiThietBi.TABLE_NAME;
        SQLiteDatabase db=handler.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        return cursor.getCount();
    }
    public int MaxId(){
        String sql="SELECT "+Table_LoaiThietBi.TABLE_NAME+"."+Table_LoaiThietBi.KEY_ID
                +",MAX("+Table_LoaiThietBi.KEY_ID+") from "+Table_LoaiThietBi.TABLE_NAME;
        SQLiteDatabase db=handler.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            if(!cursor.isNull(0))
                return cursor.getInt(0);
        }
        return 0;
    }
}
