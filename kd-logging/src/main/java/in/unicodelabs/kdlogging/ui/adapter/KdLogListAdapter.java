package in.unicodelabs.kdlogging.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertController;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.unicodelabs.kdlogging.R;
import in.unicodelabs.kdlogging.data.KdLoggingCursor;
import in.unicodelabs.kdlogging.ui.customView.KdLogTextView;

public class KdLogListAdapter extends RecyclerView.Adapter<KdLogListAdapter.LogViewHolder> {


    public interface Callbacks {
        void onLogEntryClick(int position);

        void onLogEntryDelete(int position);
    }

    private Cursor mCursor = null;

    @Nullable
    private Callbacks mCallbacks = null;

    public KdLogListAdapter() {
    }

    public void setCallbacks(@Nullable Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    private void postOnClick(int position) {
        if (mCallbacks != null) {
            mCallbacks.onLogEntryClick(position);
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder) {
        if (mCallbacks != null) {
            int position = viewHolder.getAdapterPosition();
            mCallbacks.onLogEntryDelete(position);
        }
    }

    @Override
    public KdLogListAdapter.LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_log_entry, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KdLogListAdapter.LogViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Invalid cursor position");
        }
        holder.setPriority(KdLoggingCursor.getPriority(mCursor));
        holder.setDate(KdLoggingCursor.getDate(mCursor));
        holder.setTag(KdLoggingCursor.getTag(mCursor));
        holder.setMessage(KdLoggingCursor.getMessage(mCursor));
    }

    @Override
    public long getItemId(int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Invalid cursor position");
        }

        return KdLoggingCursor.getId(mCursor);
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public void swapCursor(@Nullable Cursor cursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = cursor;

        notifyDataSetChanged();
    }

    class LogViewHolder implements View.OnClickListener {

        @NonNull
        final DateFormat timeFormat;

        @NonNull
        final KdLogTextView priority;

        @NonNull
        final KdLogTextView date;

        @NonNull
        final KdLogTextView tag;

        @NonNull
        final KdLogTextView message;

        public LogViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            priority = (KdLogTextView) itemView.findViewById(R.id.text_priority);
            date = (KdLogTextView) itemView.findViewById(R.id.text_date);
            tag = (KdLogTextView) itemView.findViewById(R.id.text_tag);
            message = (KdLogTextView) itemView.findViewById(R.id.text_message);
            timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.getDefault());
        }

        @Override
        public void onClick(View view) {
//            postOnClick(getAdapterPosition());
        }

        public void setPriority(int priority) {
            this.priority.setPriorityColor(priority);
            this.priority.setPriorityText(priority);
            this.date.setPriorityColor(priority);
            this.tag.setPriorityColor(priority);
            this.message.setPriorityColor(priority);
        }

        public void setTag(@NonNull String tag) {
            this.tag.setText(tag);
        }

        public void setDate(@NonNull Date date) {
            this.date.setText(timeFormat.format(date));
        }

        public void setMessage(@NonNull String message) {
            this.message.setText(message);
        }

    }

}
