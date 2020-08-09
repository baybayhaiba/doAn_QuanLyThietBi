package tdc.edu.vn.qlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Model.TinhTrangThietBi;
import tdc.edu.vn.qlsv.TableDatabase.Table_ChiTietSD;
import tdc.edu.vn.qlsv.TableDatabase.Table_TinhTrang;

public class DataTinhTrang {
    DatabaseHandler databaseHandler;

    public DataTinhTrang(Context context){
        databaseHandler=new DatabaseHandler(context);
    }
    public void themTinhTrangThietBi(TinhTrangThietBi tinhTrang){
        SQLiteDatabase db=databaseHandler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Table_TinhTrang.KEY_MAPHONG,tinhTrang.getMaPhong());
        values.put(Table_TinhTrang.KEY_MATB,tinhTrang.getMaThietBi());
        values.put(Table_TinhTrang.KEY_NGAYSUDUNG,tinhTrang.getNgaySuDung());
        values.put(Table_TinhTrang.KEY_TINHTRANG,tinhTrang.getTinhTrang());
        db.insert(Table_TinhTrang.TABLE_NAME,null,values);
        db.close();
    }
    public ArrayList<TinhTrangThietBi> getAllTinhTrang(){
        ArrayList<TinhTrangThietBi> listTinhTrang=new ArrayList<>();

        SQLiteDatabase db=databaseHandler.getReadableDatabase();
        String sql="SELECT * from "+Table_TinhTrang.TABLE_NAME;
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null && cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                String maPhong=cursor.getString(1);
                String maTB=cursor.getString(2);
                String tinhTrangThietBi=cursor.getString(4);
                String ngaySuDung=cursor.getString(3);
                listTinhTrang.add(new TinhTrangThietBi(id,maPhong,maTB,ngaySuDung,tinhTrangThietBi));
            }while (cursor.moveToNext());
        }
        return listTinhTrang;
    }
    public ArrayList<TinhTrangThietBi> getTinhTrangTheoNgay(String date,String maTB,String maPhong){
        ArrayList<TinhTrangThietBi> listTinhTrang=new ArrayList<>();

        SQLiteDatabase db=databaseHandler.getReadableDatabase();
        String sql1="SELECT * from tinhTrang,chiTietSD \n" +
                "where tinhTrang.maPhong=chiTietSD.maPhong and tinhTrang.maThietBi=chiTietSD.maThietBi\n" +
                "and chiTietSD.ngaySuDung='"+date+"' and chiTietSD.maThietBi='"+maTB+"'";
        String sql="SELECT * from "+Table_TinhTrang.TABLE_NAME+","+ Table_ChiTietSD.TABLE_NAME
                +" where "+Table_TinhTrang.TABLE_NAME+"."+Table_TinhTrang.KEY_MAPHONG+"="+Table_ChiTietSD.TABLE_NAME+"."+Table_ChiTietSD.KEY_MAPHONG
                +" and "+Table_TinhTrang.TABLE_NAME+"."+Table_TinhTrang.KEY_NGAYSUDUNG+"="+Table_ChiTietSD.TABLE_NAME+"."+Table_ChiTietSD.KEY_NGAYSD
                +" and "+Table_TinhTrang.TABLE_NAME+"."+Table_TinhTrang.KEY_MATB+"="+Table_ChiTietSD.TABLE_NAME+"."+Table_ChiTietSD.KEY_MATB
                +" and "+Table_TinhTrang.TABLE_NAME+"."+Table_TinhTrang.KEY_MAPHONG+"='"+maPhong+"'"
                +" and "+Table_TinhTrang.TABLE_NAME+"."+Table_TinhTrang.KEY_MATB+"='"+maTB+"'"
                +" and "+Table_TinhTrang.TABLE_NAME+"."+Table_TinhTrang.KEY_NGAYSUDUNG+"='"+date+"'";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null && cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                String maPhong1=cursor.getString(1);
                String maTB1=cursor.getString(2);
                String tinhTrangThietBi=cursor.getString(4);
                String ngaySuDung=cursor.getString(3);
                listTinhTrang.add(new TinhTrangThietBi(id,maPhong1,maTB1,ngaySuDung,tinhTrangThietBi));
            }while (cursor.moveToNext());
        }
        return listTinhTrang;
    }
    public void DeleteTinhTrang(int id){
        SQLiteDatabase db=databaseHandler.getWritableDatabase();
        db.delete(Table_TinhTrang.TABLE_NAME,Table_TinhTrang.KEY_ID+"=?",
               new String[]{String.valueOf(id)});
    }
    public int UpdateTinhTrang(TinhTrangThietBi tinhTrang){
        SQLiteDatabase db=databaseHandler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Table_TinhTrang.KEY_MAPHONG,tinhTrang.getMaPhong());
        values.put(Table_TinhTrang.KEY_MATB,tinhTrang.getMaThietBi());
        values.put(Table_TinhTrang.KEY_NGAYSUDUNG,tinhTrang.getNgaySuDung());
        values.put(Table_TinhTrang.KEY_TINHTRANG,tinhTrang.getTinhTrang());
        return db.update(Table_TinhTrang.TABLE_NAME,values,Table_TinhTrang.KEY_ID+"=?",
                new String[]{String.valueOf(tinhTrang.getId())});

    }
}
