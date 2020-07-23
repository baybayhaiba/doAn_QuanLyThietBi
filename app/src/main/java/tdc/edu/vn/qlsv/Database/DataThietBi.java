package tdc.edu.vn.qlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.TableDatabase.Table_ThietBi;


public class DataThietBi {
    DatabaseHandler handler;

    public DataThietBi(Context context) {
        this.handler = new DatabaseHandler(context);
    }


    public void themThietbi(ThietBi thietBi)
    {
        SQLiteDatabase db=handler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Table_ThietBi.KEY_MATB,thietBi.getMaLoaiTB()+String.format("%02d",getCountByType(thietBi.getMaLoaiTB())+1));
        values.put(Table_ThietBi.KEY_TENTB,thietBi.getTenTB());
        values.put(Table_ThietBi.KEY_XUATXU,thietBi.getXuatXuTB());
        values.put(Table_ThietBi.KEY_MALOAI,thietBi.getMaLoaiTB());

        db.insert(Table_ThietBi.TABLE_NAME,null,values);
    }
    public ArrayList<ThietBi> getAllThietBi(){
        SQLiteDatabase db=handler.getReadableDatabase();
        ArrayList<ThietBi> listThietBi=new ArrayList<>();
        String sql="SELECT * from "+Table_ThietBi.TABLE_NAME;
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int idThietBi=cursor.getInt(0);
                String maThietBi=cursor.getString(1);
                String tenThietBi=cursor.getString(2);
                String xuatXu=cursor.getString(3);
                String maLoai=cursor.getString(4);
                listThietBi.add(new ThietBi(idThietBi,maThietBi,tenThietBi,xuatXu,maLoai));
            }while (cursor.moveToNext());
        }
        return listThietBi;
    }
    //dem so san pham theo ma loai
    public int getCountByType(String maLoai){
        String sql="SELECT COUNT(*) " +
                " from "+Table_ThietBi.TABLE_NAME
                +" WHERE "+ Table_ThietBi.KEY_ID +" = '"+maLoai+"' ";
        //SELECT COUNT(*)  from thietBi WHERE thietBi.maLoai="CS"
        SQLiteDatabase db=handler.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst())
            return cursor.getInt(0);
        return 0;
    }
    //dem tong san pham
    public int getCountTB(){
        String sql="SELECT * from "+Table_ThietBi.TABLE_NAME;
        SQLiteDatabase db=handler.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
       return cursor.getCount();
    }
    public void xoaThietBi(ThietBi thietBi){
        SQLiteDatabase db=handler.getWritableDatabase();
        db.delete(Table_ThietBi.TABLE_NAME,Table_ThietBi.KEY_MATB+"=?",new String[]{String.valueOf(thietBi.getMaTB())});
    }
    public int suaThietBi(ThietBi thietBi){
        SQLiteDatabase db=handler.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Table_ThietBi.KEY_TENTB,thietBi.getTenTB());
        values.put(Table_ThietBi.KEY_XUATXU,thietBi.getXuatXuTB());
        values.put(Table_ThietBi.KEY_MALOAI,thietBi.getMaLoaiTB());

        return db.update(Table_ThietBi.TABLE_NAME,values,Table_ThietBi.KEY_MATB+"=?",new String[]{String.valueOf(thietBi.getMaTB())});
    }

}
