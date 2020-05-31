package database.LevelLibResultDbSheme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.LevelLibDbSheme.LevelLibDbSheme;

public class LibResultBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="levelresultBase.db";
    public LibResultBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + LibResultDbSheme.LibResultTable.NAME
                + "(" + "_id integer primary key autoincrement, " +
                LibResultDbSheme.LibResultTable.Cols.UUID + ", " +
                LibResultDbSheme.LibResultTable.Cols.CEZHAN + ", " +
                LibResultDbSheme.LibResultTable.Cols.JULI + ", " +
                LibResultDbSheme.LibResultTable.Cols.GAOCHA + ", " +
                LibResultDbSheme.LibResultTable.Cols.GAIZHENGSHU + ", " +
                LibResultDbSheme.LibResultTable.Cols.GAIHOUGAOCHA +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
