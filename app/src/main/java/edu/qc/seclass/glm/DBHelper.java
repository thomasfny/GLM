package edu.qc.seclass.glm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Grocery";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }
    String[] itemTypes = new String[]{"Fruits", "Electronic", "Sport Goods", "Dairy", "Alcohol", "Skin Care", "Vegetable", "Pet Supplies", "shoes", "garden outdoor", "furniture"};
    String[] Fruits = new String[]{"apple", "orange", "banana","pineappple"};
    String[] Electronic = new String[]{"phones", "computers", "charger"};
    String[] SportGoods = new String[]{"basketball", "football", "resistant band","helmet","gloves"};
    String[] Dairy = new String[]{"cheeses", "soymilk", "milk", "butter"};
    String[] Alcohol = new String[]{"wine", "beer", "whisky", "fruit wine"};
    String[] SkinCare = new String[]{"toner", "moisturizer", "mask", "face cleaner"};
    String[] Vegetable = new String[]{"cabbage", "broccoli", "mushroom", "celery"};
    String[] PetSupplies = new String[]{"dog food", "cat food","cage","bowl"};
    String[] Shoes = new String[]{"sneaker", "slipper", "casual shoes", "high heel"};
    String[] GardenOutdoor = new String[]{"patio seat", "outdoor lighting"};
    String[] Furniture = new String[]{"sofa", "desk", "table", "mat"};
    String[][] items = new String[][]{Fruits, Electronic, SportGoods, Dairy, Alcohol, SkinCare, Vegetable, PetSupplies, Shoes, GardenOutdoor, Furniture};

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_type_table = "CREATE TABLE TYPE ("
                + "TypeID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "type VARCHAR(100) NOT NULL)";

        String create_item_table = "CREATE TABLE ITEM ("
                + "ItemID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "itemName VARCHAR(100) NOT NULL, "
                + "qty INTEGER DEFAULT 0,"
                + "isSelected INTEGER DEFAULT 0,"
                + "TypeID INTEGER NOT NULL, "
                + "FOREIGN KEY (TypeID) REFERENCES Item_Type(TypeID) ON DELETE CASCADE)";
        db.execSQL(create_item_table);
        db.execSQL(create_type_table);
        insertType(db);
        insertItem(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

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
    int getSelectedByName(String name)
    {
        int id = getItemId(name);
        int select = 0;
        String sql = "SELECT isSelected FROM ITEM WHERE ItemID= '" + id + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
            select = cursor.getInt(3);
        cursor.close();
        return select;

    }

    void setSeleted(String name)
    {
        int id = getItemId(name);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        if(getSelectedByName(name)==0)
            value.put("isSelected",1);
        else
            value.put("isSelected",0);
        db.update("ITEM",value,"ItemId = " + id+ "AND itemName = "+ name,null);

    }

}
