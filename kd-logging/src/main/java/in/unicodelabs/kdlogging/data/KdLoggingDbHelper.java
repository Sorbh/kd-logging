package in.unicodelabs.kdlogging.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KdLoggingDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " +
            KdLoggingContract.TABLE +
            "(" +
            KdLoggingContract.Columns._ID + " INTEGER PRIMARY KEY, " +
            KdLoggingContract.Columns.TIMESTAMP + " INTEGER, " +
            KdLoggingContract.Columns.PRIORITY + " INTEGER, " +
            KdLoggingContract.Columns.TAG + " TEXT NOT NULL, " +
            KdLoggingContract.Columns.MESSAGE + " TEXT NOT NULL " +
            ")";

    public KdLoggingDbHelper(Context context) {
        super(context, KdLoggingContract.TABLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // nuke and rebuild to reload data
        db.execSQL("drop table if exists " + KdLoggingContract.TABLE);
        onCreate(db);
    }

}