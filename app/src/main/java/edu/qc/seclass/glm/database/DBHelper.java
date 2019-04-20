package edu.qc.seclass.glm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "GML";
    private static final int DB_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ItemTable.SQL_DELETE);
        onCreate(db);
    }


    public void insertType(SQLiteDatabase db)
    {
        ContentValues value = new ContentValues();

        for(String i : itemTypes)
        {
            value.put("type",i);
            db.insert("TYPE",null,value);
        }
    }
    public void insertItem(SQLiteDatabase db)
    {
        ContentValues value = new ContentValues();
        int id = -1;
        for(int i=0; i<itemTypes.length;i++)
        {
            for(String j:items[i])
            {
                id = getTypeId(itemTypes[i],db);
                value.put("itemName",j);
                value.put("TypeID",id);
                db.insert("ITEM",null,value);
            }
        }
    }
    public int getTypeId(String t, SQLiteDatabase db)
    {
        int id = -1;
        String sql = "SELECT * FROM ITEM_TYPE WHERE type = '" + t + "';";
        Cursor cursor = db.rawQuery(sql,null );
        if(cursor.moveToFirst())
            id = cursor.getInt(0);
        cursor.close();
        return id;
    }
    public int getTypeId(String t)
    {
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM ITEM_TYPE WHERE type = '" + t + "';";
        Cursor cursor = db.rawQuery(sql,null );
        if(cursor.moveToFirst())
            id = cursor.getInt(0);
        cursor.close();
        return id;
    }

    public int getItemId(String name)
    {
        int id = -1;
        String sql = "SELECT * FROM ITEM_LIST WHERE itemName = '" + name + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst())
            id = cursor.getInt(0);
        cursor.close();
        return id;
    }
    public int getItemId(String name, SQLiteDatabase db)
    {
        int id = -1;
        String sql = "SELECT * FROM ITEM_LIST WHERE itemName = '" + name + "';";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst())
            id = cursor.getInt(0);
        cursor.close();
        return id;
    }

    String getItemName(int itemId, SQLiteDatabase db)
    {
        String name = "";
        String sql = "SELECT * FROM ITEM WHERE ItemID = " + itemId;
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst())
            name = cursor.getString(1);
        cursor.close();
        return name;
    }

    String getItemType(int itemID, SQLiteDatabase db){
        String type = "";
        String sql = "SELECT TYPE.*, ITEM.* FROM TYPE, ITEM WHERE ITEM.ItemID = " + itemID + " AND TYPE.TypeID = " + "ITEM.TypeID";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
            type = cursor.getString(1);
        cursor.close();
        return type;
    }
    String getItemType(int itemID){
        String type = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT TYPE.*, ITEM.* FROM TYPE, ITEM WHERE ITEM.ItemID = " + itemID + " AND TYPE.TypeID = " + "ITEM.TypeID";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
            type = cursor.getString(1);
        cursor.close();
        return type;
    }

    ArrayList<String> getItemsByType(String type){
        ArrayList<String> itemList = new ArrayList<>();
        String item;
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "SELECT * FROM ITEM WHERE typeID = " + getTypeId(type, db);
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do{
                item = cursor.getString(1);
                itemList.add(item);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }


    ArrayList<String> getItemList(){
        ArrayList<String> items = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM ITEM";
        Cursor cursor = db.rawQuery(sql,null);
        String item = "";
        if(cursor.moveToFirst())
        {
            do{
                item = cursor.getInt(0)+", "+ cursor.getString(1)+","+cursor.getInt(2)+","+cursor.getInt(3);
                items.add(item);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return items;
    }
    ArrayList<String> getTypeList(){
        ArrayList<String> types = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM TYPE";
        Cursor cursor = db.rawQuery(sql,null);
        String type = "";
        if(cursor.moveToFirst())
        {
            do{
                type = cursor.getInt(0)+", "+ cursor.getString(1);
                types.add(type);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return types;
    }

}
