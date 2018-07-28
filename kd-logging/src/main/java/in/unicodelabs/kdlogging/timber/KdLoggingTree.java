package in.unicodelabs.kdlogging.timber;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import in.unicodelabs.kdlogging.data.KdLog;
import in.unicodelabs.kdlogging.data.KdLogWrapper;
import in.unicodelabs.kdlogging.data.KdLoggingProvider;
import timber.log.Timber;

public class KdLoggingTree extends Timber.Tree {

    @NonNull
    private final Context context;

    @NonNull
    private final String mDefaultTag;

    public KdLoggingTree(@NonNull Context appContext, @NonNull String defaultTag) {
        context = appContext;
        mDefaultTag = defaultTag;
    }

    @Override
    protected void log(int priority, @Nullable String tag, @NonNull String message, @Nullable Throwable t) {
        if (TextUtils.isEmpty(tag)) tag = mDefaultTag;
        KdLogWrapper kdLogWrapper = KdLog.create(priority, tag, message, t);
        KdLoggingProvider.insertKdLogging(context, kdLogWrapper);
    }

}