package database.QuxianDbSheme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class QuxianLibBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="QuxianBase.db";
    public QuxianLibBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + QuxianLibDbSheme.QuxianLibTable.NAME
                + "(" + "_id integer primary key autoincrement, " +
                QuxianLibDbSheme.QuxianLibTable.Cols.UUID + ", " +
                QuxianLibDbSheme.QuxianLibTable.Cols.X + ", " +
                QuxianLibDbSheme.QuxianLibTable.Cols.Y + ", " +
                QuxianLibDbSheme.QuxianLibTable.Cols.FANGWEIJIAO + ", " +
                QuxianLibDbSheme.QuxianLibTable.Cols.LEFTX + ", " +
                QuxianLibDbSheme.QuxianLibTable.Cols.LEFTY + ", " +
                        QuxianLibDbSheme.QuxianLibTable.Cols.RIGHTX + ", " +
                        QuxianLibDbSheme.QuxianLibTable.Cols.RIGHTY +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
