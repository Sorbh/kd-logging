package in.unicodelabs.kdlogging.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

import in.unicodelabs.kdlogging.Utils.KdDebugUtils;

public class KdLog {
    static KdLog instance;
    Context context;

    public static void initialize(Context context) {
        KdLoggingProvider.initialize(context);
        if (instance == null) {
            instance = new KdLog(context);
        } else {
            throw new IllegalStateException("KdLog Already initialize");
        }
    }

    public KdLog(Context context) {
        this.context = context;
    }

    public static KdLog getInstance() {
        if (instance != null)
            return instance;
        else
            throw new IllegalStateException("Kdlog not initialize");
    }

    public static void d(@NonNull String tag, @Nullable String message) {
        d(tag, message, null);
    }

    public static void d(@Nullable String message) {
        d(KdDebugUtils.getCallerCallerClassName(), message, null);
    }

    public static void d(@NonNull String tag, @Nullable Throwable t) {
        d(tag, "", null);
    }

    public static void d(@Nullable Throwable t) {
        d(KdDebugUtils.getCallerCallerClassName(), "", null);
    }

    public static void d(@NonNull String tag, @Nullable String message, @Nullable Throwable t) {
        KdLogWrapper kdLog = create(Log.DEBUG, tag, message, t);
        KdLoggingProvider.insertKdLogging(KdLog.getInstance().context, kdLog);
    }

    public static void e(@NonNull String tag, @Nullable String message) {
        e(tag, message, null);
    }

    public static void e(@Nullable String message) {
        e(KdDebugUtils.getCallerCallerClassName(), message, null);
    }

    public static void e(@NonNull String tag, @Nullable Throwable t) {
        e(tag, "", null);
    }

    public static void e(@Nullable Throwable t) {
        e(KdDebugUtils.getCallerCallerClassName(), "", null);
    }

    public static void e(@NonNull String tag, @Nullable String message, @Nullable Throwable t) {
        KdLogWrapper kdLog = create(Log.ERROR, tag, message, t);
        KdLoggingProvider.insertKdLogging(KdLog.getInstance().context, kdLog);
    }

    public static void v(@NonNull String tag, @Nullable String message) {
        v(tag, message, null);
    }

    public static void v(@Nullable String message) {
        v(KdDebugUtils.getCallerCallerClassName(), message, null);
    }

    public static void v(@NonNull String tag, @Nullable Throwable t) {
        v(tag, "", null);
    }

    public static void v(@Nullable Throwable t) {
        v(KdDebugUtils.getCallerCallerClassName(), "", null);
    }

    public static void v(@NonNull String tag, @Nullable String message, @Nullable Throwable t) {
        KdLogWrapper kdLog = create(Log.VERBOSE, tag, message, t);
        KdLoggingProvider.insertKdLogging(KdLog.getInstance().context, kdLog);
    }

    public static void i(@NonNull String tag, @Nullable String message) {
        i(tag, message, null);
    }

    public static void i(@Nullable String message) {
        i(KdDebugUtils.getCallerCallerClassName(), message, null);
    }

    public static void i(@NonNull String tag, @Nullable Throwable t) {
        i(tag, "", null);
    }

    public static void i(@Nullable Throwable t) {
        i(KdDebugUtils.getCallerCallerClassName(), "", null);
    }

    public static void i(@NonNull String tag, @Nullable String message, @Nullable Throwable t) {
        KdLogWrapper kdLog = create(Log.INFO, tag, message, t);
        KdLoggingProvider.insertKdLogging(KdLog.getInstance().context, kdLog);
    }

    public static void w(@NonNull String tag, @Nullable String message) {
        w(tag, message, null);
    }

    public static void w(@Nullable String message) {
        w(KdDebugUtils.getCallerCallerClassName(), message, null);
    }

    public static void w(@NonNull String tag, @Nullable Throwable t) {
        w(tag, "", null);
    }

    public static void w(@Nullable Throwable t) {
        w(KdDebugUtils.getCallerCallerClassName(), "", null);
    }

    public static void w(@NonNull String tag, @Nullable String message, @Nullable Throwable t) {
        KdLogWrapper kdLog = create(Log.WARN, tag, message, t);
        KdLoggingProvider.insertKdLogging(KdLog.getInstance().context, kdLog);
    }

    public static void a(@NonNull String tag, @Nullable String message) {
        a(tag, message, null);
    }

    public static void a(@Nullable String message) {
        a(KdDebugUtils.getCallerCallerClassName(), message, null);
    }

    public static void a(@NonNull String tag, @Nullable Throwable t) {
        a(tag, "", null);
    }

    public static void a(@Nullable Throwable t) {
        a(KdDebugUtils.getCallerCallerClassName(), "", null);
    }

    public static void a(@NonNull String tag, @Nullable String message, @Nullable Throwable t) {
        KdLogWrapper kdLog = create(Log.ASSERT, tag, message, t);
        KdLoggingProvider.insertKdLogging(KdLog.getInstance().context, kdLog);
    }

    public static KdLogWrapper create(int priority, @NonNull String tag, @Nullable String message) {
        return create(priority, tag, message, null);
    }

    public static KdLogWrapper create(int priority, @NonNull String tag, @Nullable String message, @Nullable Throwable t) {
        return new KdLogWrapper(
                System.currentTimeMillis(),
                priority,
                tag,
                buildMessage(message, t)
        );
    }

    @NonNull
    private static String buildMessage(@Nullable String message, @Nullable Throwable t) {
        if (t != null) {
            StringWriter sw = new StringWriter(256);
            if (!TextUtils.isEmpty(message)) {
                sw.append(message);
                sw.append('\n');
            }
            PrintWriter pw = new PrintWriter(sw, false);
            t.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        }
        return TextUtils.isEmpty(message) ? "" : message;
    }


}
