package database.LevelLibDbSheme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LevelLibBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="levellibBase.db";
    public LevelLibBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + LevelLibDbSheme.LevelLibTable.NAME
        + "(" + "_id integer primary key autoincrement, " +
                        LevelLibDbSheme.LevelLibTable.Cols.UUID + ", " +
                        LevelLibDbSheme.LevelLibTable.Cols.CEZHAN + ", " +
                        LevelLibDbSheme.LevelLibTable.Cols.HHS + ", " +
                        LevelLibDbSheme.LevelLibTable.Cols.HHZ + ", " +
                        LevelLibDbSheme.LevelLibTable.Cols.HHX + ", " +
                        LevelLibDbSheme.LevelLibTable.Cols.QHS + ", " +
                        LevelLibDbSheme.LevelLibTable.Cols.QHZ + ", " +
                        LevelLibDbSheme.LevelLibTable.Cols.QHX + ", " +
                        LevelLibDbSheme.LevelLibTable.Cols.HHONGZ + ", " +
                        LevelLibDbSheme.LevelLibTable.Cols.QHONGZ +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
