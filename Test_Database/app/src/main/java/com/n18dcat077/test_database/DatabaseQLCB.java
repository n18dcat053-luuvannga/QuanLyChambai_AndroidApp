package com.n18dcat077.test_database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.n18dcat077.test_database.GiaoVien.GiaoVien;
import com.n18dcat077.test_database.Hoadon.THONGTINCHAMBAI;
import com.n18dcat077.test_database.MonHoc.MonHoc;
import com.n18dcat077.test_database.PhieuChamBai.PhieuChamBai;
import com.n18dcat077.test_database.TTPCB.PCB;
import com.n18dcat077.test_database.User.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseQLCB extends SQLiteOpenHelper {

    public static String DATABASE_NAME="qlcb.db";
    public static String TABLE_NAME="GIAOVIEN";
    public static String COLUMN_MAGV="MAGV";
    public static String COLUMN_HOTENGV="HOTENGV";
    public static String COLUMN_SODT="SODT";
    public static String COLUMN_HINHANH = "HINHANH" ;
    public static String COLUMN_EMAIL = "EMAIL" ;

    public DatabaseQLCB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("+COLUMN_MAGV+" TEXT PRIMARY KEY, "+COLUMN_HOTENGV+" TEXT,"+COLUMN_SODT+" TEXT)");
        String tableGV = "CREATE TABLE IF NOT EXISTS \"GIAOVIEN\" (\n" +
                "\t\"MAGV\"\tTEXT,\n" +
                "\t\"HOTENGV\"\tTEXT NOT NULL,\n" +
                "\t\"SODT\"\tTEXT NOT NULL,\n" +
                "\t\"HINHANH\"\tBLOB NOT NULL,\n" +
                "\tPRIMARY KEY(\"MAGV\")\n" +
                ")";
        String tableMH  = "CREATE TABLE IF NOT EXISTS \"MONHOC\" (\n" +
                "\t\"MAMH\"\tTEXT,\n" +
                "\t\"TENMH\"\tTEXT NOT NULL,\n" +
                "\t\"CHIPHI\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"MAMH\")\n" +
                ")";
        String tablePCB = "CREATE TABLE IF NOT EXISTS \"PHIEUCHAMBAI\" (\n" +
                "\t\"SOPHIEU\"\tTEXT,\n" +
                "\t\"NGAYGIAO\"\tTEXT NOT NULL,\n" +
                "\t\"MAGV\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"SOPHIEU\"),\n" +
                "\tFOREIGN KEY(\"MAGV\") REFERENCES \"GIAOVIEN\"(\"MAGV\")\n" +
                ")";
        String tableTTCB = "CREATE TABLE IF NOT EXISTS \"THONGTINCHAMBAI\" (\n" +
                "\t\"SOPHIEU\"\tTEXT NOT NULL,\n" +
                "\t\"MAMH\"\tTEXT NOT NULL,\n" +
                "\t\"SOBAI\"\tTEXT NOT NULL,\n" +   
                "\tFOREIGN KEY(\"SOPHIEU\") REFERENCES \"PHIEUCHAMBAI\"(\"SOPHIEU\"),\n" +
                "\tFOREIGN KEY(\"MAMH\") REFERENCES \"MONHOC\"(\"MAMH\"),\n" +
                "\tPRIMARY KEY(\"SOPHIEU\",\"MAMH\")\n" +
                ")  ";
        String tableUser = "CREATE TABLE IF NOT EXISTS \"USER\" (\n" +
                "\t\"USERNAME\"\tTEXT NOT NULL,\n" +
                "\t\"PASSWORD\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"USERNAME\")\n" +
                ")";
        db.execSQL("PRAGMA foreign_keys = ON;");
//        db.execSQL(tableGV);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("+COLUMN_MAGV+" TEXT PRIMARY KEY, "+COLUMN_HOTENGV+" TEXT,"+COLUMN_SODT+" TEXT,"+COLUMN_HINHANH+" BLOB,"+COLUMN_EMAIL+" TEXT)");
        db.execSQL(tableMH);
        db.execSQL(tablePCB);
        db.execSQL(tableTTCB);
        db.execSQL(tableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + "GIAOVIEN");
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    public boolean insertGV(GiaoVien gv){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MAGV,gv.getId());
        contentValues.put(COLUMN_HOTENGV,gv.getHoTen());
        contentValues.put(COLUMN_SODT,gv.getSdt());
        contentValues.put(COLUMN_HINHANH,gv.getHinhanh());
        contentValues.put(COLUMN_EMAIL,gv.getEmail());

        if(db.insert(TABLE_NAME,null,contentValues) < 0){
            return false;
        }
        return true;
    }

    public boolean deleteGV(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete(TABLE_NAME,COLUMN_MAGV +" = ?",new String[]{id}) < 0){
            return false;
        }
        return true;
    }

    public boolean updateGV(GiaoVien gv){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MAGV,gv.getId());
        contentValues.put(COLUMN_HOTENGV,gv.getHoTen());
        contentValues.put(COLUMN_SODT,gv.getSdt());
        contentValues.put(COLUMN_HINHANH,gv.getHinhanh());
        contentValues.put(COLUMN_EMAIL,gv.getEmail());
        if(db.update(TABLE_NAME,contentValues,COLUMN_MAGV +" = ?",new String[]{gv.getId()}) < 0){
            return false;
        }
        return true;
    }

    public ArrayList<GiaoVien> getListGiaoVien(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<GiaoVien> list = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME,null,null,null,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            GiaoVien gv = new GiaoVien();
            gv.setId(c.getString(0));
            gv.setHoTen(c.getString(1));
            gv.setSdt(c.getString(2));
            gv.setHinhanh( c.getBlob(3));
            gv.setEmail(c.getString(4));
            list.add(gv);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public GiaoVien getGiaoVienByMaGV(String magv){
        GiaoVien giaoVien = new GiaoVien();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_MAGV+" = ?",new String[]{magv});
        if(cursor.moveToLast()){
            giaoVien.setId(cursor.getString(0));
            giaoVien.setHoTen(cursor.getString(1));
            giaoVien.setSdt(cursor.getString(2));
            giaoVien.setHinhanh(cursor.getBlob(3));
            giaoVien.setEmail(cursor.getString(4));
            return giaoVien;
        }else{
            Log.e("error not found", "giaovien can't be found or database empty");
            return giaoVien;
        }
    }

    public GiaoVien getGiaoVienByEmail(String email){
        GiaoVien giaoVien = new GiaoVien();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_EMAIL+" = ?",new String[]{email});
        if(cursor.moveToLast()){
            giaoVien.setId(cursor.getString(0));
            giaoVien.setHoTen(cursor.getString(1));
            giaoVien.setSdt(cursor.getString(2));
            giaoVien.setHinhanh(cursor.getBlob(3));
            giaoVien.setEmail(cursor.getString(4));
            return giaoVien;
        }else{
            Log.e("error not found", "giaovien can't be found or database empty");
            return null;
        }
    }

    public ArrayList<PhieuChamBai> getListGiaoVienInPhieuChamBai(String magv){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<PhieuChamBai> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM PHIEUCHAMBAI WHERE MAGV = ?",new String[]{magv});
        c.moveToFirst();
        while(c.isAfterLast() == false){
            PhieuChamBai p = new PhieuChamBai();
            p.setSoPhieu(c.getString(0));
            p.setNgayGiao(c.getString(1));
            p.setMaGV(c.getString(2));
            list.add(p);
            c.moveToNext();
        }
        c.close();
        return list;
    }


    public List<String> getIdGiaoVien(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> id = new ArrayList<>();
        Cursor c = db.query("GIAOVIEN",null,null,null,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            GiaoVien gv = new GiaoVien();
            gv.setId(c.getString(0));
            id.add(gv.getId());
            c.moveToNext();
        }
        c.close();
        return id;

    }

    public ArrayList<PhieuChamBai> getListPCB(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<PhieuChamBai> list = new ArrayList<>();
        Cursor c = db.query("PHIEUCHAMBAI",null,null,null,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            PhieuChamBai pcb = new PhieuChamBai();
            pcb.setSoPhieu(c.getString(0));
            pcb.setNgayGiao(c.getString(1));
            pcb.setMaGV(c.getString(2));
            list.add(pcb);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public List<String> getIDPCB(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> id = new ArrayList<>();
        Cursor c = db.query("PHIEUCHAMBAI",null,null,null,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            PhieuChamBai pcb = new PhieuChamBai();
            pcb.setSoPhieu(c.getString(0));
            id.add("Số phiếu: " + pcb.getSoPhieu());
            c.moveToNext();
        }
        c.close();
        return id;
    }

    public boolean insertPCB(PhieuChamBai pcb){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SOPHIEU",pcb.getSoPhieu());
        contentValues.put("NGAYGIAO",pcb.getNgayGiao());
        contentValues.put("MAGV",pcb.getMaGV());
        if(db.insert("PHIEUCHAMBAI",null,contentValues) < 0){
            return false;
        }
        return true;
    }

//    public boolean deletePCB(String id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        if(db.delete("PHIEUCHAMBAI","SOPHIEU" +" = ?",new String[]{id}) < 0){
//            return false;
//        }
//        return true;
//    }
    public boolean deletePCB(String id){
        String sql ="delete from PHIEUCHAMBAI where SOPHIEU=?";
        SQLiteDatabase database = getWritableDatabase();
        try{
            database.execSQL(sql,new String[]{id});
            database.close();
            return  true;
        }catch (Exception ex){
            database.close();
            return false;
        }
    }

    public ArrayList<PCB> getListidSoPhieuInTTPCB(String sophieu){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<PCB> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM THONGTINCHAMBAI WHERE SOPHIEU = ?",new String[]{sophieu});
        c.moveToFirst();
        while(c.isAfterLast() == false){
            PCB p = new PCB();
            p.setMaPhieu(c.getString(0));
            p.setMaMH(c.getString(1));
            p.setSoBai(c.getString(2));
            list.add(p);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public boolean updatePCB(PhieuChamBai pcb){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SOPHIEU",pcb.getSoPhieu());
        contentValues.put("NGAYGIAO",pcb.getNgayGiao());
        contentValues.put("MAGV",pcb.getMaGV());
        if(db.update("PHIEUCHAMBAI",contentValues,"SOPHIEU" +" = ?",new String[]{pcb.getSoPhieu()}) < 0){
            return false;
        }
        return true;
    }
    public void ThemDL(MonHoc monHoc) {

        String sql = "insert into MONHOC values(?,?,?)";
        SQLiteDatabase database = getWritableDatabase(); //Khi cần cập nhật dữ liệu (CREATE, DELETE, UPDATE, INSERT)
        database.execSQL(sql,new String[]{monHoc.getMaMH(),monHoc.getTenMH(),monHoc.getChiPhi()});
        database.close();
    }
    public ArrayList<MonHoc> DocDL(){
        ArrayList<MonHoc> data= new ArrayList<>();
        String sql ="select *from MONHOC";
        SQLiteDatabase database = getReadableDatabase(); //Khi cần đọc dữ liệu (SELECT)
        Cursor cursor = database.rawQuery(sql,null); //để bắt đầu từ đầu table,
        if(cursor.moveToFirst()){
            do{
                MonHoc monHoc= new MonHoc();
                monHoc.setMaMH(cursor.getString(0));
                monHoc.setTenMH(cursor.getString(1));
                monHoc.setChiPhi(cursor.getString(2));
                data.add(monHoc);
            }while(cursor.moveToNext());// doc sang dong tiep theo
        }
        return data;
    }

    public void SuaDL(MonHoc monHoc){
        String sql="update MONHOC set TENMH=?,CHIPHI=? where MAMH=?";
        SQLiteDatabase database =getWritableDatabase();
        database.execSQL(sql,new String[]{monHoc.getTenMH(),monHoc.getChiPhi(),monHoc.getMaMH()});
        database.close();
    }

    public boolean XoaDL(MonHoc monHoc){
        String sql ="delete from MONHOC where MAMH=?";
        SQLiteDatabase database = getWritableDatabase();
        try{
            database.execSQL(sql,new String[]{monHoc.getMaMH()});
            database.close();
            return  true;
        }catch (Exception ex){
            database.close();
            return false;
        }
    }
    public ArrayList<MonHoc> danhsachID(){
        ArrayList<MonHoc> ds= new ArrayList<>();
        String sql ="select MAMH from MONHOC";
        SQLiteDatabase database = getReadableDatabase(); //Khi cần đọc dữ liệu (SELECT)
        Cursor cursor = database.rawQuery(sql,null); //để bắt đầu từ đầu table,
        if(cursor.moveToFirst()){
            do{
                MonHoc monHoc= new MonHoc();
                monHoc.setMaMH(cursor.getString(0));
                ds.add(monHoc);
            }while(cursor.moveToNext());// doc sang dong tiep theo
        }
        return ds;
    }


    public ArrayList<PhieuChamBai> countGV(){
        ArrayList<PhieuChamBai> ds= new ArrayList<>();
        String sql = "SELECT MAGV, COUNT(MAGV) FROM PHIEUCHAMBAI GROUP BY MAGV";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                PhieuChamBai pcb = new PhieuChamBai();
                pcb.setMaGV(cursor.getString(0));
                pcb.setCount(cursor.getString(1));
                ds.add(pcb);
            }while(cursor.moveToNext());// doc sang dong tiep theo
        }
        return ds;
    }

    @SuppressLint("Range")
    public String getResult(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String name = null;
        try
        {
            Cursor c = null;
            c = db.rawQuery("SELECT HOTENGV FROM GIAOVIEN WHERE MAGV = ?",new String[]{id});
            c.moveToFirst();
            name = c.getString(0);
            c.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return name;
    }
    public List<String> getIdMonHoc(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> id = new ArrayList<>();
        Cursor c = db.query("MONHOC",null,null,null,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            MonHoc mh = new MonHoc();
            mh.setMaMH(c.getString(0));
            id.add(mh.getMaMH());
            c.moveToNext();
        }
        c.close();
        return id;
    }

    public ArrayList<String> getIdMonHocKoTrung(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> id = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT DISTINCT(MAMH) FROM THONGTINCHAMBAI", null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            MonHoc mh = new MonHoc();
            mh.setMaMH(c.getString(0));
            id.add(mh.getMaMH());
            c.moveToNext();
        }
        c.close();
        return id;
    }

    public ArrayList<PCB> getListidMonHocInTTPCB(String mamonhoc){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<PCB> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM THONGTINCHAMBAI WHERE MAMH = ?",new String[]{mamonhoc});
        c.moveToFirst();
        while(c.isAfterLast() == false){
            PCB p = new PCB();
            p.setMaPhieu(c.getString(0));
            p.setMaMH(c.getString(1));
            p.setSoBai(c.getString(2));
            list.add(p);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public List<String> getIdPhieu(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> id = new ArrayList<>();
        Cursor c = db.query("PHIEUCHAMBAI",null,null,null,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            PhieuChamBai pcb = new PhieuChamBai();
            pcb.setSoPhieu(c.getString(0));
            id.add(pcb.getSoPhieu());
            c.moveToNext();
        }
        c.close();
        return id;

    }

    public ArrayList<PCB> getListTTPCB(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<PCB> list = new ArrayList<>();
        Cursor c = db.query("THONGTINCHAMBAI",null,null,null,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            PCB pcb = new PCB();
            pcb.setMaPhieu(c.getString(0));
            pcb.setMaMH(c.getString(1));
            pcb.setSoBai(c.getString(2));
            list.add(pcb);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public ArrayList<PCB> getListTTPCB2(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<PCB> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT DISTINCT(T1.MAMH), (SELECT SUM(T2.SOBAI) FROM THONGTINCHAMBAI as T2 WHERE T1.MAMH = T2.MAMH) as TONGBAI\n" +
                "FROM THONGTINCHAMBAI as T1", null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            PCB pcb = new PCB();
            pcb.setMaMH(c.getString(0));
            pcb.setTongBai(c.getInt(1));
            list.add(pcb);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public boolean insertTTPCB(PCB pcb){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SOPHIEU",pcb.getMaPhieu());
        contentValues.put("MAMH",pcb.getMaMH());
        contentValues.put("SOBAI",pcb.getSoBai());
        if(db.insert("THONGTINCHAMBAI",null,contentValues) < 0){
            return false;
        }
        return true;
    }

    public boolean deleteTTPCB(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete("THONGTINCHAMBAI","SOPHIEU" +" = ?",new String[]{id}) < 0){
            return false;
        }
        return true;
    }

    public boolean updateTTPCB(PCB pcb){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SOPHIEU",pcb.getMaPhieu());
        contentValues.put("MAMH",pcb.getMaMH());
        contentValues.put("SOBAI",pcb.getSoBai());
        if(db.update("THONGTINCHAMBAI",contentValues,"SOPHIEU" +" = ?",new String[]{pcb.getMaPhieu()}) < 0){
            return false;
        }
        return true;
    }
    public ArrayList<THONGTINCHAMBAI> getPCB1GV1(String maGV){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<THONGTINCHAMBAI> list = new ArrayList<>();
        Cursor c = db.rawQuery("select PHIEUCHAMBAI.MAGV,PHIEUCHAMBAI.NGAYGIAO,PHIEUCHAMBAI.SOPHIEU,MONHOC.MAMH,MONHOC.TENMH,MONHOC.CHIPHI,THONGTINCHAMBAI.SOBAI from PHIEUCHAMBAI,MONHOC,THONGTINCHAMBAI WHERE PHIEUCHAMBAI.SOPHIEU = THONGTINCHAMBAI.SOPHIEU AND THONGTINCHAMBAI.MAMH = MONHOC.MAMH AND PHIEUCHAMBAI.MAGV = ?",new String[] {maGV});
        c.moveToFirst();
        while(c.isAfterLast() == false){
            THONGTINCHAMBAI th = new THONGTINCHAMBAI();
            GiaoVien gv = new GiaoVien();
            MonHoc mh = new MonHoc();
            PhieuChamBai p = new PhieuChamBai();
            p.setMaGV(c.getString(0));
            p.setNgayGiao(c.getString(1));
            p.setSoPhieu(c.getString(2));
            mh.setMaMH(c.getString(3));
            mh.setTenMH(c.getString(4));
            mh.setChiPhi(c.getString(5));
            th.setSobai(c.getString(6));

            long total = 0;
            long chiphi = Long.valueOf(mh.getChiPhi());
            long sobai = Long.valueOf(th.getSobai());
            total = chiphi*sobai;
            th.setThanhtien(String.valueOf(total));
            th.setMonHoc(mh);
            th.setPhieuChamBai(p);
            list.add(th);
            c.moveToNext();
        }
        //Toast.makeText(DatabaseQLCB.this,list.get(0).getSoPhieu(),Toast.LENGTH_SHORT).show();
        c.close();
        return list;
    }

    // User
    public boolean insertUser(User us){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME",us.getUsername());
        contentValues.put("PASSWORD",us.getPassword());
        if(db.insert("USER",null,contentValues) < 0){
            return false;
        }
        return true;
    }

    // custom
    public boolean CreateUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = db.insert("user",null,contentValues);
        if(result == -1) {
            return false;}
        else {
            return true;
        }
    }

    public boolean checkAccount(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From USER where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From USER where username = ?", new String[] {username});
        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public User getUserByUsername(String username){
       User user = new User();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE USERNAME = ?",new String[]{username});
        if(cursor.moveToLast()){
            user.setUsername(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            return user;
        }else{
//            Log.e("error not found", "giaovien can't be found or database empty");
            return null;
        }
    }
    public boolean deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete("USER","USERNAME" +" = ?",new String[]{username}) < 0){
            return false;
        }
        return true;
    }

    public boolean updateUser(User us){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME",us.getUsername());
        contentValues.put("PASSWORD",us.getPassword());
        if(db.update("USER",contentValues,"USERNAME" +" = ?",new String[]{us.getUsername()}) < 0){
            return false;
        }
        return true;
    }


    public ArrayList<User> getListUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<User> list = new ArrayList<>();
        Cursor c = db.query("USER",null,null,null,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            User us = new User();
            us.setUsername(c.getString(0));
            us.setPassword(c.getString(1));
            list.add(us);
            c.moveToNext();
        }
        c.close();
        return list;
    }



}
