package edu.qc.seclass.glm.database;

public class ItemTable {
    public static final String TABLE_ITEMS = "Items";
    public static final String COLUMN_ID = "ItemID";
    public static final String COLUMN_NAME = "ItemName";
    public static final String COLUMN_TYPE_ID = "TypeId";


    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " VARCHAR(100) NOT NULL, " +
                    COLUMN_TYPE_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY ("+ COLUMN_TYPE_ID + ") REFERENCES Item_Type( " + COLUMN_TYPE_ID +" ) ON DELETE CASCADE)";


    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ITEMS;
}
