package tdc.edu.vn.qlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Model.LoaiThietBi;
import tdc.edu.vn.qlsv.Model.PhongHoc;
import tdc.edu.vn.qlsv.TableDatabase.Table_LoaiThietBi;
import tdc.edu.vn.qlsv.TableDatabase.Table_PhongHoc;
import tdc.edu.vn.qlsv.TableDatabase.Table_ThietBi;

public class DataPhongHoc {
    DatabaseHandler handler;
    public  DataPhongHoc(Context context){this.handler = new DatabaseHandler(context);}
    public void themPhongHoc(PhongHoc Phonghoc)
        {
            SQLiteDatabase db = handler.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(Table_PhongHoc.KEY_MAPHONG,Phonghoc.getMaPhong());
            values.put(Table_PhongHoc.KEY_LOAIPHONG,Phonghoc.getLoaiPhong());
            values.put(Table_PhongHoc.KEY_TANG,Phonghoc.getTang());
            values.put(Table_PhongHoc.KEY_IMAGE,Phonghoc.getImagePhongHoc());
            db.insert(Table_PhongHoc.TABLE_NAME,null,values);
            db.close();
    }
   public ArrayList<PhongHoc>getAllPhongHoc()
   {
       ArrayList<PhongHoc>LoaiPhong=new ArrayList<>();
       SQLiteDatabase db=handler.getReadableDatabase();
       String sql="SELECT * from " + Table_PhongHoc.TABLE_NAME+" ORDER by "+Table_ThietBi.KEY_ID+" DESC";
       Cursor cursor=db.rawQuery(sql,null);
       if(cursor!=null && cursor.moveToFirst()) {
           do {
               int id=cursor.getInt(0);
               String maphong = cursor.getString(1);
               String loaiphong = cursor.getString(2);
               int tang=cursor.getInt(3);
               byte[] imagePhongHoc=cursor.getBlob(4);
               PhongHoc PhongHoc = new PhongHoc(id,maphong , loaiphong,tang,imagePhongHoc);
               LoaiPhong.add(PhongHoc);
           } while (cursor.moveToNext());
       }
       return LoaiPhong ;
   }
   public byte[] getImagePhongHoc(String maPhong){
       SQLiteDatabase db=handler.getReadableDatabase();
       String sql="SELECT "+Table_PhongHoc.KEY_IMAGE+" from " + Table_PhongHoc.TABLE_NAME +" " +
               "where "+Table_PhongHoc.KEY_MAPHONG+" = '"+maPhong+"'";
       Cursor cursor=db.rawQuery(sql,null);
       if(cursor!=null && cursor.moveToFirst()){
           if(!cursor.isNull(0)){
               return cursor.getBlob(0);
           }
       }
       return null;
   }
   public void XoaMaPhong(PhongHoc MaPhong)
   {
       SQLiteDatabase db = handler.getWritableDatabase();
       db.delete(Table_PhongHoc.TABLE_NAME,Table_PhongHoc.KEY_ID+"=?",
               new String[]{String.valueOf(MaPhong.getId())});
   }
    public int CapNhatPhongHoc(PhongHoc PhongHoc){
        SQLiteDatabase db=handler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Table_PhongHoc.KEY_MAPHONG,PhongHoc.getMaPhong());
        values.put(Table_PhongHoc.KEY_LOAIPHONG,PhongHoc.getLoaiPhong());
        values.put(Table_PhongHoc.KEY_TANG,PhongHoc.getTang());
        values.put(Table_PhongHoc.KEY_IMAGE,PhongHoc.getImagePhongHoc());
        return db.update(Table_PhongHoc.TABLE_NAME,values,Table_PhongHoc.KEY_ID+"=?",
                new String[]{String.valueOf(PhongHoc.getId())});
    }
    public int getCountPhongHoc(){
        String sql="SELECT * from "+ Table_PhongHoc.TABLE_NAME;
        SQLiteDatabase db=handler.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        return cursor.getCount();
    }
    public int MaxId(){
        String sql="SELECT "+Table_PhongHoc.TABLE_NAME+"."+Table_PhongHoc.KEY_ID
                +",MAX("+Table_PhongHoc.KEY_ID+") from "+Table_PhongHoc.TABLE_NAME;
        SQLiteDatabase db=handler.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            if(!cursor.isNull(0))
                return cursor.getInt(0);
        }
        return 0;
    }
}
