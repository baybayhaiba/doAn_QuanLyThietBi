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
        values.put(Table_ThietBi.KEY_ID,thietBi.getId());
        values.put(Table_ThietBi.KEY_MATB,thietBi.getMaTB());
        values.put(Table_ThietBi.KEY_TENTB,thietBi.getTenTB());
        values.put(Table_ThietBi.KEY_XUATXU,thietBi.getXuatXuTB());
        values.put(Table_ThietBi.KEY_MALOAI,thietBi.getMaLoaiTB());

        db.insert(Table_ThietBi.TABLE_NAME,null,values);
    }
    public ArrayList<ThietBi> getAllThietBi(){
        SQLiteDatabase db=handler.getReadableDatabase();
        ArrayList<ThietBi> listThietBi=new ArrayList<>();
        String sql="SELECT * from "+Table_ThietBi.TABLE_NAME + " ORDER by "+Table_ThietBi.KEY_ID+" DESC";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                String maThietBi=cursor.getString(1);
                String tenThietBi=cursor.getString(2);
                String xuatXu=cursor.getString(3);
                String maLoai=cursor.getString(4);
                listThietBi.add(new ThietBi(id,maThietBi,tenThietBi,xuatXu,maLoai));
            }while (cursor.moveToNext());
        }
        return listThietBi;
    }
    //dem so san pham theo ma loai
    public int getCountByType(String maLoai){
        String sql="SELECT COUNT(*) " +
                " from "+Table_ThietBi.TABLE_NAME
                +" WHERE "+ Table_ThietBi.KEY_MALOAI +" = '"+maLoai+"' ";
        //SELECT COUNT(*)  from thietBi WHERE thietBi.maLoai="CS"
        SQLiteDatabase db=handler.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst())
            return cursor.getInt(0);
        return 0;
    }
    public int MaxId(){
        String sql="SELECT "+Table_ThietBi.TABLE_NAME+"."+Table_ThietBi.KEY_ID
                +",MAX("+Table_ThietBi.KEY_ID+") from "+Table_ThietBi.TABLE_NAME;
        SQLiteDatabase db=handler.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            if(!cursor.isNull(0))
                return cursor.getInt(0);
        }
        return 0;
    }
    public int MaxType(String maLoai){
        String createNewValue="";
        String sql= "SELECT " +Table_ThietBi.TABLE_NAME+"."+Table_ThietBi.KEY_MATB +
                ",MAX(" + Table_ThietBi.KEY_MATB +") as "
                +Table_ThietBi.KEY_MALOAI + " from " +Table_ThietBi.TABLE_NAME+
                " WHERE " + Table_ThietBi.TABLE_NAME+"."+Table_ThietBi.KEY_MALOAI +"=" + "'"+maLoai+"'";

        SQLiteDatabase db=handler.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()) {
            if(!cursor.isNull(0))
            createNewValue = cursor.getString(0);
            else
                return 0;
        }

        int i=0;
        while (createNewValue.charAt(i) < '0' || createNewValue.charAt(i) >'9')
            i++;


        int number=0;

        for(int j=i;j<createNewValue.length();j++){
            number+=Integer.parseInt(String.valueOf(createNewValue.charAt(j)));
        }
        return number;
    }
    //lay gia tri lon nhat roi gan gia tri moi
    public String createNewType(String maLoai,int maxMaLoai){
        StringBuilder resultType= new StringBuilder();
        resultType.append(maLoai);
        resultType.append(String.format("%02d",(maxMaLoai)));
        return  resultType.toString();
    }
    //dem tong san pham
    public int getCount(){
        String sql="SELECT * from "+Table_ThietBi.TABLE_NAME;
        SQLiteDatabase db=handler.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
       return cursor.getCount();
    }
    public void xoaThietBi(ThietBi thietBi){
        SQLiteDatabase db=handler.getWritableDatabase();
        db.delete(Table_ThietBi.TABLE_NAME,Table_ThietBi.KEY_ID+" =?",
                new String[]{String.valueOf(thietBi.getId())});
    }
    public int suaThietBi(ThietBi thietBi){
        SQLiteDatabase db=handler.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Table_ThietBi.KEY_MATB,thietBi.getMaTB());
        values.put(Table_ThietBi.KEY_TENTB,thietBi.getTenTB());
        values.put(Table_ThietBi.KEY_XUATXU,thietBi.getXuatXuTB());
        values.put(Table_ThietBi.KEY_MALOAI,thietBi.getMaLoaiTB());

        return db.update(Table_ThietBi.TABLE_NAME,values,Table_ThietBi.KEY_ID+" =?",new
                String[]{String.valueOf(thietBi.getId())});
    }

}
