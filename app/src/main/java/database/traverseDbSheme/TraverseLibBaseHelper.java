package database.traverseDbSheme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TraverseLibBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="TraverBase.db";
    public TraverseLibBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TraverseLibDbSheme.TraverseLibTable.NAME
                + "(" + "_id integer primary key autoincrement, " +
                TraverseLibDbSheme.TraverseLibTable.Cols.UUID + ", " +
                TraverseLibDbSheme.TraverseLibTable.Cols.NAME + ", " +
                TraverseLibDbSheme.TraverseLibTable.Cols.ANGLE + ", " +
                TraverseLibDbSheme.TraverseLibTable.Cols.AZIMUTH + ", " +
                TraverseLibDbSheme.TraverseLibTable.Cols.X + ", " +
                TraverseLibDbSheme.TraverseLibTable.Cols.Y +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
