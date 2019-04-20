package edu.qc.seclass.glm.database;

public class TypeTable {
    public static final String TABLE_TYPE = "Items";
    public static final String COLUMN_TYPE_ID = "TypeId";
    public static final String COLUMN_NAME = "TypeName";

    public static final String SQL_CREATE =
            "CREATE TABLE "+TABLE_TYPE+" ( "
                    +COLUMN_TYPE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +COLUMN_NAME +" VARCHAR(100) NOT NULL)";


    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_TYPE;

}
