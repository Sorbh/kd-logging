package in.unicodelabs.kdlogging.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.Date;

public class KdLoggingCursor {
    public static long getId(@NonNull Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndex(KdLoggingContract.Columns._ID));
    }

    public static long getTimestamp(@NonNull Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndex(KdLoggingContract.Columns.TIMESTAMP));
    }

    public static Date getDate(@NonNull Cursor cursor) {
        return new Date(getTimestamp(cursor));
    }

    public static int getPriority(@NonNull Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex(KdLoggingContract.Columns.PRIORITY));
    }

    @NonNull
    public static String getTag(@NonNull Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(KdLoggingContract.Columns.TAG));
    }

    @NonNull
    public static String getMessage(@NonNull Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(KdLoggingContract.Columns.MESSAGE));
    }

    @NonNull
    public static KdLogWrapper getKdLogging(@NonNull Cursor cursor) {
        return new KdLogWrapper(
                KdLoggingCursor.getTimestamp(cursor),
                KdLoggingCursor.getPriority(cursor),
                KdLoggingCursor.getTag(cursor),
                KdLoggingCursor.getMessage(cursor)
        );
    }
}