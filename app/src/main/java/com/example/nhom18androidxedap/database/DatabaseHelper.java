package com.example.nhom18androidxedap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.nhom18androidxedap.model.Bicycle;
import com.example.nhom18androidxedap.model.Bill;
import com.example.nhom18androidxedap.model.Notify;
import com.example.nhom18androidxedap.model.TKe;
import com.example.nhom18androidxedap.model.Users;
import com.example.nhom18androidxedap.model.Voucher;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String BICYCLE = "bicycle";
    public static final String USERS = "users";
    public static final String VOUCHER = "voucher";
    public static final String NOTIFY = "noti";
    public static final String BILL = "bill";
    //Bicycle table
    public static final String ID = "ID";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_CONDITION = "condition";
    public static final String COLUMN_NOTE = "note";
    //user table
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AUTHORITY = "authority";
    public static final String FULLNAME = "fullname";
    public static final String GMAIL = "gmail";
    //bill table
    public static final String USER_ID = "usersid";
    public static final String VOUCHER_ID = "voucherid";
    public static final String QUANTITY = "quantity";
    public static final String TOTAL = "total";
    public static final String START_AT = "start_at";
    public static final String END_AT = "end_at";
    public static final String STATUS = "status";
    //voucher table
    public static final String DISCOUNT = "discount";
    public static final String MIN_BILL = "min_bill";
    //bill detail
    public static final String BILL_DETAIL = "bill_detail";
    public static final String BILL_ID = "billid";
    public static final String BICYCLE_ID = "bicycleid";
    //notify table
    public static final String TITLE = "title";
    public static final String DETAIL = "detail";
    public static final String TIME = "time";
//    public static final String USER_NOTI = "user_" + NOTIFY;
    //report table
    public static final String REPORT_PROBLEM = "report_problem";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Bicycle.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableBicycle = "CREATE TABLE " + BICYCLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CODE + " VARCHAR(255) UNIQUE, " +
                COLUMN_CONDITION + " VARCHAR(255), " + COLUMN_NOTE + " VARCHAR(255) )";
        String createTableUser = "CREATE TABLE " + USERS + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " VARCHAR(50), " + PASSWORD + " VARCHAR(50), " +
                AUTHORITY + " INTEGER, " + FULLNAME + " VARCHAR(255), " + GMAIL + " VARCHAR(255))";
        String createTableVoucher = "CREATE TABLE " + VOUCHER + " (" + ID + " integer primary key autoincrement, " + COLUMN_CODE + " VARCHAR(255) UNIQUE, " + DISCOUNT + " integer, " + MIN_BILL + " integer, " +
                START_AT + " DATETIME, " + END_AT + " DATETIME)";
        String createTableBill = "CREATE TABLE " + BILL + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CODE + " VARCHAR(255) UNIQUE, "  + USER_ID + " integer, "
                + VOUCHER_ID + " integer, "  + QUANTITY + " integer, " + TOTAL + " integer, " + START_AT + " DATETIME, " + END_AT + " DATETIME, " + STATUS + " integer, " +
                "FOREIGN KEY (" + USER_ID + ") REFERENCES " + USERS + "(id), FOREIGN KEY (" + VOUCHER + "id) REFERENCES voucher(id)) ";
        String createTableBillDetail = "CREATE TABLE " + BILL_DETAIL + " (" + ID + " integer primary key autoincrement, " + BILL_ID + " integer, " + BICYCLE_ID + " integer, " +
                "foreign key ( " + BILL_ID + " ) references bill(id), foreign key (" + BICYCLE_ID + ") references bicycle(id))";
        String createTableNoti = "CREATE TABLE " + NOTIFY + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " VARCHAR(255), " + DETAIL + " VARCHAR(255), " + TIME + "  DATETIME)";
//        String createTableUserNoti = "CREATE TABLE " + USER_NOTI + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, usersid integer, " + NOTIFY + "id integer, status integer, foreign key (usersid) references users(id), " +
//                "foreign key (" + NOTIFY + "id) references noti(id))";
        String createTableReportProblem = "CREATE TABLE " + REPORT_PROBLEM + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + TIME + " DATETIME, " + USER_ID + " integer, " + BICYCLE_ID + " integer, " +
                "foreign key (" + USER_ID + ") references users( " + ID + " ), foreign key (" + BICYCLE_ID + ") references bicycle( " + ID + " ))";
        db.execSQL(createTableBicycle);
        db.execSQL(createTableUser);
        db.execSQL(createTableVoucher);
        db.execSQL(createTableBill);
        db.execSQL(createTableBillDetail);
        db.execSQL(createTableNoti);
//        db.execSQL(createTableUserNoti);
        db.execSQL(createTableReportProblem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//ngan

    public boolean addBike(@NonNull Bicycle bike){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CODE, bike.getCode());
//        cv.put(COLUMN_TYPE, bike.getType());
//        cv.put(COLUMN_PRICE, bike.getPrice());
        cv.put(COLUMN_NOTE, bike.getNote());
        cv.put(COLUMN_CONDITION, bike.getCondition());
        long insert = db.insert(BICYCLE, null, cv);
        if (insert == -1) return  false;
        return true;
    }

    public ArrayList<Bicycle> getAll(){
        ArrayList<Bicycle> list;
        list = new ArrayList<>();
        String queryDB = "SELECT * FROM "+BICYCLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryDB, null);
        if (cursor.moveToFirst()){
            do {
                int bicycleID = cursor.getInt(0);
                String bicycleCode = cursor.getString(1);
//                String bicycleType = cursor.getString(2);
//                int bicyclePrice = cursor.getInt(3);
                String bicycleCondition = cursor.getString(2);
                String bicycleNote = cursor.getString(3);
                Boolean status = getBillInprocess(bicycleID);
//                Boolean status = false;
                Bicycle bicycle = new Bicycle(bicycleID, bicycleCode, bicycleNote, bicycleCondition, status);
                list.add(bicycle);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Bicycle> getByKey(String key){
        ArrayList<Bicycle> list = new ArrayList<>();
        String queryDB = "SELECT * FROM " + BICYCLE + " WHERE " + COLUMN_CODE + " LIKE '%" + key + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryDB, null);
        if (cursor.moveToFirst()){
            do {
                int bicycleID = cursor.getInt(0);
                String bicycleCode = cursor.getString(1);
                String bicycleNote = cursor.getString(3);
                String bicycleCondition = cursor.getString(2);
//                Boolean status = getBillInprocess(bicycleID);
                Boolean status = getBillInprocess(bicycleID);
                Bicycle bicycle = new Bicycle(bicycleID, bicycleCode, bicycleNote, bicycleCondition, status);
                list.add(bicycle);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int deleteBike(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM bicycle WHERE id = " + id;
        int kq = db.delete(BICYCLE, ID + " = " + id, null);
        return kq;
    }

    public boolean updateBike(@NonNull Bicycle bicycle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CODE, bicycle.getCode());
//        cv.put(COLUMN_TYPE, bicycle.getType());
//        cv.put(COLUMN_PRICE, bicycle.getPrice());
        cv.put(COLUMN_NOTE, bicycle.getNote());
        cv.put(COLUMN_CONDITION, bicycle.getCondition());
        long update = db.update(BICYCLE, cv, ID + " = " + bicycle.getId(), null);

        if (update == -1) return  false;
        return true;
    }

    public boolean getBillInprocess(int bicycleID){
        String query = "SELECT count(bill.id) FROM bill_detail , bill WHERE bill_detail." + BICYCLE_ID + " = " + bicycleID + " and bill." + STATUS + " = 1 and bill.id = bill_" + DETAIL + ".billid";
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0) return true;
        else return false;
    }

    public ArrayList<Bill> getAllBill(){
        ArrayList<Bill> list;
        list = new ArrayList<>();
        String queryDB = "SELECT * FROM "+ BILL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryDB, null);
        if (cursor.moveToFirst()){
            do {
                int billID = cursor.getInt(0);
                String billCode = cursor.getString(1);
                Integer billQuantity =cursor.getInt(4);
                Integer billTotal = cursor.getInt(5);
                String billStartAt = cursor.getString(6);
                String billEndAt = cursor.getString(7);
                boolean billStatus = cursor.getInt(8) == 1;
                int user_id = cursor.getInt(2);
                int voucher_id = cursor.getInt(3);

                Users user = null;
                ArrayList<Bicycle> listBicycle = null;
                Voucher voucher = null;
                if (user_id > 0) user = getUserByID(user_id);
                listBicycle = getListBicycleByBill(billID);
                if (voucher_id > 0) voucher = getVoucherByID(voucher_id);
                Bill bill = new Bill(billID,billCode,user,listBicycle,voucher,billQuantity,billTotal,billStartAt,billEndAt,billStatus);
                list.add(bill);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Bicycle> getListBicycleByBill(int bill_id){
        ArrayList<Bicycle> listBicycle = new ArrayList<>();
        String queryDB = "SELECT * FROM "+ BILL_DETAIL + " where billid = " + bill_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryDB, null);
        if (cursor.moveToFirst()){
            do{
                int bicycle_id = cursor.getInt(2);
                listBicycle.add(getBicycleByID(bicycle_id));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listBicycle;
    }

    public Bicycle getBicycleByID(int id){
        Bicycle bicycle = null;
        String query = "SELECT * FROM " + BICYCLE + " WHERE id = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            int bicycleID = cursor.getInt(0);
            String bicycleCode = cursor.getString(1);
            String bicycleCondition = cursor.getString(2);
            String bicycleNote = cursor.getString(3);
            Boolean status = getBillInprocess(bicycleID);
            bicycle = new Bicycle(bicycleID, bicycleCode, bicycleNote, bicycleCondition, status);
        }
        cursor.close();
        db.close();
        return bicycle;
    }

    public Users getUserByID(int id){
        Users user = null;
        String query = "SELECT * FROM " + USERS + " WHERE id = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            int userID = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String authority = cursor.getString(3);
            String fullname = cursor.getString(4);
            String gmail = cursor.getString(5);
            user = new Users(userID, username, password, authority, fullname, gmail);
        }
        cursor.close();
        db.close();
        return user;
    }

    public Voucher getVoucherByID(int id){
        Voucher voucher = null;
        String query = "SELECT * FROM " + VOUCHER + " WHERE id = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            int voucherID = cursor.getInt(0);
            String code = cursor.getString(1);
            Integer discount = cursor.getInt(2);
            Integer min_bill = cursor.getInt(3);
            String start_at = cursor.getString(4);
            String end_at = cursor.getString(5);
            voucher = new Voucher(voucherID, code, discount, min_bill, start_at, end_at);
        }
        cursor.close();
        db.close();
        return voucher;
    }

    public int getBillByCode(String code){
        String query = "SELECT id FROM bill where code = '" + code + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        cursor.close();
        db.close();
        return id;
    }

    public ArrayList<Bill> getBillByKey(String key){
        ArrayList<Bill> list;
        list = new ArrayList<>();
        String queryDB = "SELECT distinct bill.* FROM "+ BILL +" , "+ BICYCLE + ", bill_" + DETAIL + " WHERE bill.code LIKE '%" + key + "%' or (bill_" + DETAIL + "." + BICYCLE_ID + " = bicycle.id and bicycle.code LIKE '%" + key + "%')" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryDB, null);
        if (cursor.moveToFirst()){
            do {
                int billID = cursor.getInt(0);
                String billCode = cursor.getString(1);
                Integer billQuantity =cursor.getInt(4);
                Integer billTotal = cursor.getInt(5);
                String billStartAt = cursor.getString(6);
                String billEndAt = cursor.getString(7);
                boolean billStatus = cursor.getInt(8) == 1;
                int user_id = cursor.getInt(2);
                int voucher_id = cursor.getInt(3);

                Users user = null;
                ArrayList<Bicycle> listBicycle = null;
                Voucher voucher = null;
                if (user_id > 0) user = getUserByID(user_id);
                listBicycle = getListBicycleByBill(billID);
                if (voucher_id > 0) voucher = getVoucherByID(voucher_id);
                Bill bill = new Bill(billID,billCode,user,listBicycle,voucher,billQuantity,billTotal,billStartAt,billEndAt,billStatus);
                list.add(bill);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public TKe tke(String month){
        TKe tke = null;
        String queryDB = "SELECT sum(total), sum(quantity) FROM "+ BILL + " where start_at >= '" + month + "-01 00:00:00' and start_at <= '" + month + "-31 23:59:59'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryDB, null);
        if (cursor.moveToFirst()){
            do {
                Integer sum_total = cursor.getInt(0);
                Integer sum_quantity =cursor.getInt(1);
                tke = new TKe(month, sum_total, sum_quantity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tke;
    }

    public ArrayList<String> getMonth(){
        ArrayList<String> list = new ArrayList<>() ;
        String query = "SELECT DISTINCT strf" + TIME + "('%Y-%m', start_at) start_at from bill order by start_at desc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                String month = cursor.getString(0);
                list.add(month);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean checkExistBill(int idBicycle){
        boolean b;
        String query = "SELECT count(*) from bill_" + DETAIL + " where " + BICYCLE_ID + " = " + idBicycle;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            int count = cursor.getInt(0);
            if (count > 0) return true;
        }
        return false;
    }
//Viet

    public boolean addUser(Users user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FULLNAME,user.getFullname());
        cv.put(GMAIL,user.getGmail());
        cv.put(USERNAME,user.getUsername());
        cv.put(PASSWORD,user.getPassword());
        cv.put(AUTHORITY,user.getAuthority());

        long insert = db.insert(USERS,null,cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public List<Users> getAllUser(){
        List<Users> listUser = new ArrayList<>();
        String queryString = "SELECT * FROM " + USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                listUser.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listUser;
    }


    public Users getUserById(int id){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "ID = ? ";
        String[] whereArgs = {String.valueOf(id)};

        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,null);

        if(cursor.moveToFirst()){
            int userID = cursor.getInt(0);
            String userName = cursor.getString(1);
            String password = cursor.getString(2);
            String authority = cursor.getString(3);
            String fullName = cursor.getString(4);
            String gmail = cursor.getString(5);


            Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
            cursor.close();
            db.close();
            return newUser;
        }
        cursor.close();
        db.close();
        return null;

    }

    public List<Users> getCustomerByIdASC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " authority like ?";
        String[] whereArgs = {"Customer"};
        String Order = "ID ASC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);

                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getCustomerByIdDESC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "authority like ?";
        String[] whereArgs = {"Customer"};
        String Order = "ID DESC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getAdminByIdASC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " authority like ?";
        String[] whereArgs = {"Admin"};
        String Order = "ID ASC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getAdminByIdDESC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " authority like ?";
        String[] whereArgs = {"Admin"};
        String Order = "ID DESC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getCustomerByFullName(String fullname){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " fullname like ? AND authority like ?";
        String[] whereArgs = {"%"+fullname+"%","Customer"};
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getCustomerByUserName(String username){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " username like ? AND authority like ?";
        String[] whereArgs = {"%"+username+"%","Customer"};
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getCustomerByUserNameASC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "authority like ?";
        String[] whereArgs = {"Customer"};
        String order="username ASC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getCustomerByUserNameDESC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "authority like ?";
        String[] whereArgs = {"Customer"};
        String order="username DESC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getCustomerByFullNameASC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "authority like ?";
        String[] whereArgs = {"Customer"};
        String order="fullname ASC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getCustomerByFullNameDESC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "authority like ?";
        String[] whereArgs = {"Customer"};
        String Order="fullname DESC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);


            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }


    public List<Users> getAdminByFullname(String fullname){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " fullname like ? AND authority like ?";
        String[] whereArgs = {"%"+fullname+"%","Admin"};
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getAdminByUsername(String username){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " username like ? AND authority like ?";
        String[] whereArgs = {"%"+username+"%","Admin"};
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getAdminByUsernameASC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "authority like ?";
        String[] whereArgs = {"Admin"};
        String Order = "username ASC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getAdminByUsernameDESC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "authority like ?";
        String[] whereArgs = {"Admin"};
        String Order = "username DESC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getAdminByFullnameASC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "authority like ?";
        String[] whereArgs = {"Admin"};
        String Order = "fullname ASC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getAdminByFullnameDESC(){
        List<Users> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "authority like ?";
        String[] whereArgs = {"Admin"};
        String Order = "fullname DESC";
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public Users getUserByUserName(String username){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " username like ? ";
        String[] whereArgs = {username};
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            int userID = cursor.getInt(0);
            String userName = cursor.getString(1);
            String password = cursor.getString(2);
            String authority = cursor.getString(3);
            String fullName = cursor.getString(4);
            String gmail = cursor.getString(5);


            Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
            cursor.close();
            db.close();
            return newUser;
        }
        cursor.close();
        db.close();
        return null;
    }

    public List<Users> getAllCustomer(){
        SQLiteDatabase db = getReadableDatabase();
        List<Users> list = new ArrayList<>();
        String whereClause = " authority like ? ";
        String[] whereArgs = {"Customer"};
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Users> getAllAdmin(){
        SQLiteDatabase db = getReadableDatabase();
        List<Users> list = new ArrayList<>();
        String whereClause = " authority like ? ";
        String[] whereArgs = {"Admin"};
        Cursor cursor = db.query(USERS,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String authority = cursor.getString(3);
                String fullName = cursor.getString(4);
                String gmail = cursor.getString(5);


                Users newUser = new Users(userID,userName,password,authority,fullName,gmail);
                list.add(newUser);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean updateUser(Users user){
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(user.getId());
        ContentValues cv = new ContentValues();
        cv.put(FULLNAME,user.getFullname());
        cv.put(GMAIL,user.getGmail());
        cv.put(USERNAME,user.getUsername());
        cv.put(PASSWORD,user.getPassword());
        cv.put(AUTHORITY,user.getAuthority());
        long result = db.update(USERS,cv,"ID=?",new String[]{id});
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteUser(Users user){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(USERS,"ID = ?",new String[]{String.valueOf(user.getId())});
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteAllCustomer(){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = " authority like ? ";
        String[] whereArgs = {"Customer"};
        int result = db.delete(USERS,whereClause,whereArgs);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteAllAdmin(){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = " authority like ? ";
        String[] whereArgs = {"Admin"};
        int result = db.delete(USERS,whereClause,whereArgs);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }


    //----------------------------------------------------------------------------------------------
    //Notify

    public boolean addNotify(Notify notify){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TITLE,notify.getTitle());
        cv.put(DETAIL,notify.getDetail());
        cv.put(TIME, String.valueOf(notify.getTime()));

        long insert = db.insert(NOTIFY,null,cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public List<Notify> getAllNotify(){
        List<Notify> listNotify = new ArrayList<>();
        String queryString = "SELECT * FROM " + NOTIFY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do{
                int notifyID = cursor.getInt(0);
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String time = cursor.getString(3);
                Notify newNotify = new Notify(notifyID,title,details,time);
                listNotify.add(newNotify);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listNotify;
    }

    public List<Notify> getNotifyById(int id){
        List<Notify> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "ID = ? ";
        String[] whereArgs = {String.valueOf(id)};

        Cursor cursor = db.query(NOTIFY,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String time = cursor.getString(3);
                Notify newNotify = new Notify(id,title,details,time);
                list.add(newNotify);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Notify> getNotifyByTitle(String key){
        List<Notify> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " title like ? ";
        String[] whereArgs = {"%"+ key + "%"};

        Cursor cursor = db.query(NOTIFY,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String time = cursor.getString(3);
                Notify newNotify = new Notify(id,title,details,time);
                list.add(newNotify);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Notify> getNotifyByTitleASC(){
        List<Notify> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String Order = "title ASC";
        Cursor cursor = db.query(NOTIFY,null,null,null,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String time = cursor.getString(3);
                Notify newNotify = new Notify(id,title,details,time);
                list.add(newNotify);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Notify> getNotifyByTitleDESC(){
        List<Notify> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String Order = "TITLE DESC";
        Cursor cursor = db.query(NOTIFY,null,null,null,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String time = cursor.getString(3);
                Notify newNotify = new Notify(id,title,details,time);
                list.add(newNotify);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Notify> getNotifyByDate(String key){
        List<Notify> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " time like ? ";
        String[] whereArgs = {"%"+ key + "%"};

        Cursor cursor = db.query(NOTIFY,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String time = cursor.getString(3);
                Notify newNotify = new Notify(id,title,details,time);
                list.add(newNotify);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Notify> getNotifyByDateASC(){
        List<Notify> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String Order = "time ASC";

        Cursor cursor = db.query(NOTIFY,null,null,null,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String time = cursor.getString(3);
                Notify newNotify = new Notify(id,title,details,time);
                list.add(newNotify);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Notify> getNotifyByDateDESC(){
        List<Notify> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String Order = "time DESC";

        Cursor cursor = db.query(NOTIFY,null,null,null,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String time = cursor.getString(3);
                Notify newNotify = new Notify(id,title,details,time);
                list.add(newNotify);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean updateNotify(Notify notify){
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(notify.getId());
        ContentValues cv = new ContentValues();
        cv.put(TITLE,notify.getTitle());
        cv.put(TIME,notify.getTime());
        cv.put(DETAIL,notify.getDetail());
        long result = db.update(NOTIFY,cv,"ID=?",new String[]{id});
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteNotify(Notify notify){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(NOTIFY,"ID = ?",new String[]{String.valueOf(notify.getId())});
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteAllNotify(){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(NOTIFY,null,null);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    //----------------------------------------------------------------------------------------------
    //Voucher
    public boolean addVoucher(Voucher voucher){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CODE,voucher.getCode());
        cv.put(DISCOUNT,voucher.getDiscount());
        cv.put(MIN_BILL,voucher.getMin_bill());
        cv.put(START_AT,String.valueOf(voucher.getStart_at()));
        cv.put(END_AT,String.valueOf(voucher.getEnd_at()));

        long insert = db.insert(VOUCHER,null,cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public List<Voucher> getAllVoucher(){
        List<Voucher> listVoucher = new ArrayList<>();
        String queryString = "SELECT * FROM " + VOUCHER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do{
                int voucherID = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(voucherID,code,discount,minBill,startTime,endTime);
                listVoucher.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listVoucher;
    }

    public List<Voucher> getVoucherById(int id){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "ID = ? ";
        String[] whereArgs = {String.valueOf(id)};

        Cursor cursor = db.query(VOUCHER,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public Voucher confirmCodeVoucher(String key){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " code like ? ";
        String[] whereArgs = {"%"+ key +"%"};

        Cursor cursor = db.query(VOUCHER,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            String code = cursor.getString(1);
            int discount = cursor.getInt(2);
            int minBill = cursor.getInt(3);
            String startTime = cursor.getString(4);
            String endTime = cursor.getString(5);
            Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
            cursor.close();
            db.close();
            return newVoucher;
        }
        cursor.close();
        db.close();
        return null;
    }

    public List<Voucher> getVoucherByCode(String key){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " code like ? ";
        String[] whereArgs = {"%"+ key +"%"};

        Cursor cursor = db.query(VOUCHER,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Voucher> getVoucherByCodeASC(){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String Order = " code ASC";

        Cursor cursor = db.query(VOUCHER,null,null,null,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Voucher> getVoucherByCodeDESC(){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String Order = " code DESC";

        Cursor cursor = db.query(VOUCHER,null,null,null,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Voucher> getVoucherByDiscountASC(){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String Order = " discount ASC";

        Cursor cursor = db.query(VOUCHER,null,null,null,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Voucher> getVoucherByDiscountDESC(){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String Order = " discount DESC";

        Cursor cursor = db.query(VOUCHER,null,null,null,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Voucher> getVoucherByMinBillASC(){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String Order = " min_bill ASC";

        Cursor cursor = db.query(VOUCHER,null,null,null,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Voucher> getVoucherByMinBillDESC(){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String Order = " min_bill DESC";

        Cursor cursor = db.query(VOUCHER,null,null,null,null,null,Order);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Voucher> getVoucherByStartTime(String from, String to){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " start_at BETWEEN ? AND ?";
        String[] whereArgs = {from.trim(),to.trim()};

        Cursor cursor = db.query(VOUCHER,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Voucher> getVoucherByEndTime(String from, String to){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " end_at BETWEEN ? AND ?";
        String[] whereArgs = {from.trim(),to.trim()};

        Cursor cursor = db.query(VOUCHER,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Voucher> getVoucherByDate(String date){
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = " ? BETWEEN start_at AND end_at";
        String[] whereArgs = {date};

        Cursor cursor = db.query(VOUCHER,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int discount = cursor.getInt(2);
                int minBill = cursor.getInt(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);
                Voucher newVoucher = new Voucher(id,code,discount,minBill,startTime,endTime);
                list.add(newVoucher);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean updateVoucher(Voucher voucher){
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(voucher.getId());
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CODE,voucher.getCode());
        cv.put(DISCOUNT,voucher.getDiscount());
        cv.put(MIN_BILL,voucher.getMin_bill());
        cv.put(START_AT,String.valueOf(voucher.getStart_at()));
        cv.put(END_AT,String.valueOf(voucher.getEnd_at()));

        long result = db.update(VOUCHER,cv,"ID=?",new String[]{id});
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteVoucher(Voucher voucher){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(VOUCHER,"ID = ?",new String[]{String.valueOf(voucher.getId())});
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteAllVoucher(){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(VOUCHER,null,null);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }


    // Thanhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh

    public boolean addReport(int idReport ,String time , int idUser ,int idBicycle)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TIME,time);
        cv.put(USER_ID,idUser);
        cv.put(BICYCLE_ID,idBicycle);
        db.insert(REPORT_PROBLEM,null,cv);

        return true;
    }

    public boolean addBillDetail(int idBillDetail,int idBill, int idBicycle)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BILL_ID,idBill);
        cv.put(BICYCLE_ID,idBicycle);
        db.insert(BILL_DETAIL,null,cv);

        return true;
    }

//    public boolean addBicycle(Bicycle bicycle)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_CODE_BICYCLE,bicycle.getCodeBicycle());
//        cv.put(COLUMN_CONDITON,bicycle.getCondition());
//        cv.put(COLUMN_NOTE_BICYCLE,bicycle.getNoteBicycle());
//        cv.put(COLUMN_STATUS_BICYCLE,bicycle.isStatusBicycle());
//        long insert = db.insert(BICYCLE_TABLE, null, cv);
//        return true;
//    }

//    public boolean addVoucher(Voucher voucher)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_CODE_VOUCHER,voucher.getCodeVoucher());
//        cv.put(COLUMN_DISCOUNT,voucher.getDiscount());
//        cv.put(COLUMN_MIN_BILL,voucher.getMinBill());
//        cv.put(COLUMN_START_TIME_VOUCHER,voucher.getStartTimeVoucher());
//        cv.put(COLUMN_END_TIME_VOUCHER,voucher.getEndTimeVoucher());
//        long insert = db.insert(VOUCHER_TABLE,null,cv);
//
//        return true;
//    }

//    public boolean addUser(Users users)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_USERNAME,users.getUsername());
//        cv.put(COLUMN_PASSWORD,users.getPassword());
//        cv.put(COLUMN_POSITION,users.getPosition());
//        cv.put(COLUMN_GMAIL,users.getGmail());
//        cv.put(COLUMN_FULLNAME,users.getFullname());
//
//        long insert = db.insert(USER_TABLE,null,cv);
//
//        return true;
//    }

//    public boolean addNotify(Notify1 notify)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_TITLE,notify.getTitle());
//        cv.put(COLUMN_DETAIL,notify.getDetail());
//        cv.put(COLUMN_TIMENOTI,notify.getTimeNoti());
//        db.insert(NOTI_TABLE,null,cv);
//
//        return true;
//    }

    public boolean addBill(Bill bill)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CODE,bill.getCode());
        cv.put(USER_ID,bill.getUsers().getId());
        cv.put(VOUCHER_ID,bill.getVoucher().getId());
        cv.put(QUANTITY,bill.getQuantity());
        cv.put(TOTAL,bill.getTotal());
        cv.put(START_AT,bill.getStart_at());
        cv.put(END_AT,bill.getEnd_at());
        cv.put(STATUS,bill.isStatus());
        db.insert(BILL,null,cv);
        return true;
    }

    public List<Notify> getAllNotifyThanh() ///sua lai trong code
    {
        List<Notify> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + NOTIFY;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do {
                int idNoti = cursor.getInt(0);
                String title = cursor.getString(1);
                String detail = cursor.getString(2);
                String timeNoti = cursor.getString(3);

                Notify notify1 = new Notify(idNoti,title,detail,timeNoti);
                returnList.add(notify1);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public Bicycle getBicycle(String codeBicycle)
    {

        String queryString = " SELECT * FROM " + BICYCLE + " WHERE code = '" + codeBicycle  + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int idBicycle1 = cursor.getInt(0);
        String codeBicycle1 = cursor.getString(1);
        String condition = cursor.getString(2);
        String noteBicycle = cursor.getString(3);
        Boolean status = getBillInprocess(idBicycle1);
        Bicycle bicycle = new Bicycle(idBicycle1,codeBicycle1,condition,noteBicycle,status);//1 la dang cho thue
        cursor.close();
        db.close();
        return bicycle;
    }

    public int getIdBill(String codeBill)
    {
        String queryString = " SELECT ID FROM " + BILL + " WHERE code = " + codeBill + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int idBill = cursor.getInt(0);
        cursor.close();
        db.close();

        return idBill;

    }

    public int getIdBicycle(String codeBicycle)
    {
        String queryString = " SELECT ID FROM " + BICYCLE + " WHERE code =  " + codeBicycle + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int idBicycle = cursor.getInt(0);
        cursor.close();
        db.close();

        return  idBicycle;

    }

    public int getQuantityFromBill(String codeBill)
    {
        String queryString = " SELECT quantity FROM " + BILL + " WHERE code = '" + codeBill + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int quantity = cursor.getInt(0);
        cursor.close();
        db.close();
        return quantity;

    }



    public String getCodeBicycle(int idBicycle)
    {
        String queryString = " SELECT code FROM " + BICYCLE + " WHERE id = " + idBicycle ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        String codeBicycle = cursor.getString(0); // ??
        cursor.close();
        db.close();
        return codeBicycle;
    }

    public List<String> getAllCode()
    {
        List<String> codeList = new ArrayList<>();
        String queryString = " SELECT code FROM " + BICYCLE  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                String codeBicycle = cursor.getString(0);
                codeList.add(codeBicycle);
            }while (cursor.moveToNext());
        }
        return codeList;
    }

    public List<Integer> getListIdBicycleFromBillDetail(int idBill)
    {
        List<Integer> listIdBicycle = new ArrayList<>();
        String queryString = " SELECT bicycleid FROM " + BILL_DETAIL + " WHERE billid = " + idBill + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do {
                int idBicycle = cursor.getInt(0);
                listIdBicycle.add(idBicycle);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listIdBicycle;

    }

    public Bicycle getBicycleById(int idBicycle)
    {
        String queryString = " SELECT * FROM " + BICYCLE + " WHERE ID = " + idBicycle + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int idBicycle1 = cursor.getInt(0);
        String codeBicycle = cursor.getString(1);
        String condition = cursor.getString(2);
        String noteBicycle = cursor.getString(3);
        Boolean status =  getBillInprocess(idBicycle1);
        Bicycle bicycle = new Bicycle(idBicycle1,codeBicycle,condition,noteBicycle,status);
        cursor.close();
        db.close();

        return bicycle;

    }

    public Voucher getVoucherByIdThanh(int idVoucher) // sua lai ten ham theo code
    {
        String queryString =  " SELECT * FROM " + VOUCHER + " WHERE ID = " + idVoucher + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int idVoucher1 = cursor.getInt(0);
        String codeVoucher = cursor.getString(1);
        int discount = cursor.getInt(2);
        int minBll = cursor.getInt(3);
        String startTimeVoucher = cursor.getString(4);
        String endTimeVoucher = cursor.getString(5);

        Voucher  voucher = new Voucher(idVoucher,codeVoucher,discount,minBll,startTimeVoucher,endTimeVoucher);
        cursor.close();
        db.close();
        return voucher;
    }

    public List<Bill> getAllBill(int idUser)
    {
        List<Bill> returnList = new ArrayList<>();
        String queryString = " SELECT * FROM " + BILL + " WHERE usersid = " + idUser + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do {
                int idBill = cursor.getInt(0);
                String codeBill = cursor.getString(1);
                int idUser1 = cursor.getInt(2);
                Users user = getUser(idUser1);
                List<Bicycle> bicycleList;
                bicycleList = new ArrayList<>();
                List<Integer> listIdBicycle = new ArrayList<>();
                listIdBicycle = getListIdBicycleFromBillDetail(idBill);
                for (int i=0;i<listIdBicycle.size();i++)
                {
                    bicycleList.add(getBicycleById(listIdBicycle.get(i)));
                }
                int idVoucher = cursor.getInt(3);
                Voucher voucher = getVoucherByIdThanh(idVoucher);
                int quantity = cursor.getInt(4);
                int total = cursor.getInt(5);
                String startTimeBill = cursor.getString(6);
                String endTimeBill = cursor.getString(7);
                boolean status = cursor.getInt(8) == 1;

                Bill bill = new Bill(idBill,codeBill,user,bicycleList,voucher,quantity,total,startTimeBill,endTimeBill,status);
                returnList.add(bill);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return returnList;
    }

    public Users getUser(int idUser)
    {
        String queryString =  " SELECT * FROM " + USERS + " WHERE ID = " + idUser + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int idUser1 = cursor.getInt(0);
        String username = cursor.getString(1);
        String password = cursor.getString(2);
        String position = cursor.getString(3);
        String fullname = cursor.getString(4);
        String gmail = cursor.getString(5);

        Users users = new Users(idUser1,username,password,position,fullname,gmail);
        cursor.close();
        db.close();

        return users;


    }


    public String getDiscount(String codeVoucher)
    {
        String queryString = " SELECT discount FROM " + VOUCHER + " WHERE code = '" + codeVoucher + "'"
                + " AND start_at <= datetime('now','localtime') AND datetime('now','localtime') <= end_at ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        String discount = cursor.getString(0); // ??
        cursor.close();
        db.close();
        return discount;
    }

    public String getMinBill(String codeVoucher){
        String queryString = " SELECT min_bill FROM " + VOUCHER + " WHERE code = '" + codeVoucher + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        String minBill = cursor.getString(0);
        cursor.close();
        db.close();
        return minBill;
    }

    public Users getUser(String username)
    {
        String queryString = " SELECT * FROM " + USERS + " WHERE username = '" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int idUser = cursor.getInt(0);
        String username1 = cursor.getString(1);
        String password = cursor.getString(2);
        String position = cursor.getString(3);
        String fullname = cursor.getString(4);
        String gmail =  cursor.getString(5);
        Users user = new Users(idUser,username1,password,position,fullname,gmail);
        cursor.close();
        db.close();
        return user;
    }

    public Voucher getVoucher(String codeVoucher)
    {
        String queryString = " SELECT * FROM " + VOUCHER + " WHERE code = '" + codeVoucher + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int idVoucher = cursor.getInt(0);
        String codeVoucher1 = cursor.getString(1);
        int discount = cursor.getInt(2);
        int minbill = cursor.getInt(3);
        String startTimeVoucher = cursor.getString(4);
        String endTimeVoucher =  cursor.getString(5);
        //Users user = new Users(idUser,username1,password,position,fullname,gmail);
        Voucher voucher = new Voucher(idVoucher,codeVoucher1,discount,minbill,startTimeVoucher,endTimeVoucher);
        cursor.close();
        db.close();
        return voucher;
    }

    public Integer getIdUser(String username)
    {
        String queryString = " SELECT ID FROM " + USERS + " WHERE username = '" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int idUser = cursor.getInt(0);
        cursor.close();
        db.close();

        return idUser;
    }

    public String getCodeBill(int idUser)
    {
        String queryString = " SELECT code FROM " + BILL + " WHERE usersid = '" + idUser + "'"
                + " AND status = 1 ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        String codeBill = cursor.getString(0);
        cursor.close();
        db.close();
        return codeBill;
    }

    public String getCodeBicycleByIdBicycle(int idBicycle)
    {
        String queryString = " SELECT code FROM " + BICYCLE + " WHERE id = " + idBicycle + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        String codeBicycle = cursor.getString(0);
        cursor.close();
        db.close();
        return  codeBicycle;

    }


    public List<String> getAllCodeVoucher()
    {
        List<String> codeList = new ArrayList<>();
        String queryString = " SELECT code FROM " + VOUCHER  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                String codeVoucher = cursor.getString(0);
                codeList.add(codeVoucher);
            }while (cursor.moveToNext());
        }
        return codeList;
    }

    public int isHaveBill(int idUser)
    {
        String queryString =  " SELECT count(*) FROM " + BILL + " WHERE usersid = " + idUser + ""
                + " AND status = 1 ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;

    }

    public int isHaveVoucher(String codeVoucher)
    {
        String queryString = " SELECT count(*) FROM " + VOUCHER + " WHERE code = '" + codeVoucher + "'"
                + " AND start_at <= datetime('now','localtime') AND datetime('now','localtime') <= end_at ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;


    }

    public int isHaveCodeBicycle(String codeBicycle)
    {
        String queryString = " SELECT count(*) FROM " + BICYCLE + " WHERE code = " + codeBicycle + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();

        return count;
    }

    public void updateStatusBill()
    {
        String queryString = " UPDATE " + BILL + " SET status = 0 WHERE end_at <= datetime('now','localtime') ";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);
        db.close();
    }

    public void updateStatusBill2(String codeBill)
    {
        String queryString = " UPDATE " + BILL + " SET status = 0 WHERE code =  '" + codeBill + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);
        db.close();
    }



}
