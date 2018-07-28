package in.unicodelabs.kdlogging.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class KdLogWrapper implements Parcelable {
    public final long timestamp;
    public final int priority;
    public final String tag;
    public final String message;

    public KdLogWrapper(long timestamp, int priority, @NonNull String tag, @NonNull String message) {
        this.timestamp = timestamp;
        this.priority = priority;
        this.tag = tag;
        this.message = message;
    }

    protected KdLogWrapper(@NonNull Parcel in) {
        this.timestamp = in.readLong();
        this.priority = in.readInt();
        this.tag = in.readString();
        this.message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(timestamp);
        dest.writeInt(priority);
        dest.writeString(tag);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "KdLog:" + tag + "/" + message;
    }

    public static final Creator<KdLogWrapper> CREATOR = new Creator<KdLogWrapper>() {
        @Override
        public KdLogWrapper createFromParcel(Parcel in) {
            return new KdLogWrapper(in);
        }

        @Override
        public KdLogWrapper[] newArray(int size) {
            return new KdLogWrapper[size];
        }
    };
}
