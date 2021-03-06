package tdc.edu.vn.qlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.Model.PhongHoc;
import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.TableDatabase.Table_ChiTietSD;
import tdc.edu.vn.qlsv.TableDatabase.Table_LoaiThietBi;
import tdc.edu.vn.qlsv.TableDatabase.Table_PhongHoc;
import tdc.edu.vn.qlsv.TableDatabase.Table_ThietBi;

public class DataCTSD {
    DatabaseHandler handler;

    public DataCTSD(Context context) {
        this.handler = new DatabaseHandler(context);
    }

    public void themCTSD(ChiTietSuDung ChiTietSuDung) {
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Table_ChiTietSD.KEY_NGAYSD, ChiTietSuDung.getNgaySuDung());
        values.put(Table_ChiTietSD.KEY_MAPHONG, ChiTietSuDung.getMaPhong());
        values.put(Table_ChiTietSD.KEY_SOLUONG, ChiTietSuDung.getSoLuong());
        values.put(Table_ChiTietSD.KEY_MATB, ChiTietSuDung.getMaTB());

        db.insert(Table_ChiTietSD.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<ChiTietSuDung> getAllCTSD() {
        ArrayList<ChiTietSuDung> ChiTietSuDung = new ArrayList<>();

        SQLiteDatabase db = handler.getReadableDatabase();
        String sql = "SELECT * from " + Table_ChiTietSD.TABLE_NAME + " ORDER by " + Table_ChiTietSD.KEY_ID + " DESC";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String maPhong = cursor.getString(1);
                String MaThietBi = cursor.getString(2);
                String NgaySuDung = cursor.getString(3);
                int soluong = cursor.getInt(4);
                ChiTietSuDung.add(new ChiTietSuDung(id, maPhong, MaThietBi, NgaySuDung, soluong));

            } while (cursor.moveToNext());
        }
        return ChiTietSuDung;
    }

    public ChiTietSuDung getCTSDByID(int id) {

        SQLiteDatabase db = handler.getReadableDatabase();
        String sql = "SELECT * from chiTietSD where chiTietSD._id='"+id+"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id1 = cursor.getInt(0);
                String maPhong = cursor.getString(1);
                String MaThietBi = cursor.getString(2);
                String NgaySuDung = cursor.getString(3);
                int soluong = cursor.getInt(4);
                return new ChiTietSuDung(id1, maPhong, MaThietBi, NgaySuDung, soluong);

            } while (cursor.moveToNext());
        }
        return null;
    }
    //dem so san pham theo ma loai
    public int getCountByType(String maPhong) {
        String sql = "SELECT COUNT(*) " +
                " from " + Table_ChiTietSD.TABLE_NAME
                + " WHERE " + Table_ChiTietSD.KEY_MAPHONG + " = '" + maPhong + "' ";
        //SELECT COUNT(*)  from thietBi WHERE thietBi.maLoai="CS"
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
            return cursor.getInt(0);
        return 0;
    }

    public int getCount() {
        String sql = "SELECT * from " + Table_ChiTietSD.TABLE_NAME;
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor.getCount();
    }

    public void xoaCTSD(ChiTietSuDung chiTietSuDung) {
        SQLiteDatabase db = handler.getWritableDatabase();
        db.delete(Table_ChiTietSD.TABLE_NAME, Table_ChiTietSD.KEY_ID + " =?",
                new String[]{String.valueOf(chiTietSuDung.getId())});
    }

    public int suaCTSD(ChiTietSuDung chiTietSuDung) {
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Table_ChiTietSD.KEY_NGAYSD, chiTietSuDung.getNgaySuDung());
        values.put(Table_ChiTietSD.KEY_MAPHONG, chiTietSuDung.getMaPhong());
        values.put(Table_ChiTietSD.KEY_SOLUONG, chiTietSuDung.getSoLuong());
        values.put(Table_ChiTietSD.KEY_MATB, chiTietSuDung.getMaTB());

        return db.update(Table_ChiTietSD.TABLE_NAME,
                values, Table_ChiTietSD.KEY_ID + " =?", new String[]{String.valueOf(chiTietSuDung.getId())});
    }

    public int getCountCTSD() {
        String sql = "SELECT * from " + Table_ChiTietSD.TABLE_NAME;
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor.getCount();
    }

    public int MaxId() {
        String sql = "SELECT " + Table_ChiTietSD.TABLE_NAME + "." + Table_ChiTietSD.KEY_ID
                + ",MAX(" + Table_ChiTietSD.KEY_ID + ") from " + Table_ChiTietSD.TABLE_NAME;
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            if (!cursor.isNull(0))
                return cursor.getInt(0);
        }
        return 0;
    }

    public byte[] getImageThietBi(String maThietBi) {
        String sql = "SELECT anhThietBi from thietBi,chitietSD where \n" +
                "chiTietSD.maThietBi=thietBi.maThietBi and chiTietSD.maThietBi='" + maThietBi + "'";
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            if (!cursor.isNull(0))
                return cursor.getBlob(0);
        }
        return null;
    }

    //lay thong tin nam su dung
    public ArrayList<ChiTietSuDung> getInformationYear() {
        ArrayList<ChiTietSuDung> listInform = new ArrayList<>();

        String sql = "SELECT substr(chiTietSD.ngaySuDung,7,10),SUM(soLuong)\n" +
                "From chiTietSD GROUP by substr(ngaySuDung,7,10) \n" +
                "ORDER BY substr(ngaySuDung,7,10) asc";
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String ngaySuDung = cursor.getString(0);
                int soLuong = cursor.getInt(1);
                listInform.add(new ChiTietSuDung(ngaySuDung, soLuong));
            } while (cursor.moveToNext());

        }
        return listInform;
    }

    public ArrayList<ChiTietSuDung> getInformationDetail(String year) {
        ArrayList<ChiTietSuDung> listInform = new ArrayList<>();
//        String sql = "SELECT chiTietSD.maPhong,chiTietSD.ngaySuDung,chiTietSD.soLuong\n" +
//                "from chiTietSD,PhongHoc WHERE substr(ngaySuDung,7,10) = '"+year+"' \n" +
//                "AND PhongHoc.maPhong=chiTietSD.maPhong ORDER by substr(ngaySuDung,4,2) ASC";

        String sql="SELECT chiTietSD.maPhong,chiTietSD.ngaySuDung,chiTietSD.soLuong,thietBi.tenThietBi\n" +
                "from chiTietSD,PhongHoc,thietBi WHERE substr(ngaySuDung,7,10) = '"+year+"' \n" +
                "AND PhongHoc.maPhong=chiTietSD.maPhong AND thietBi.maThietBi=chiTietSD.maThietBi\n" +
                "ORDER by substr(ngaySuDung,4,2) ASC";
        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String maPhong=cursor.getString(0);
                String ngaySuDung = cursor.getString(1);
                int soLuong = cursor.getInt(2);
                String tenThietbi=cursor.getString(3);
                listInform.add(new ChiTietSuDung(maPhong,ngaySuDung, soLuong,tenThietbi));
            } while (cursor.moveToNext());

        }
        return listInform;
    }

}
