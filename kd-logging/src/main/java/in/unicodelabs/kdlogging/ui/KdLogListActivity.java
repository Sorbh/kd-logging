package in.unicodelabs.kdlogging.ui;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import in.unicodelabs.kdlogging.R;
import in.unicodelabs.kdlogging.data.KdLoggingContract;
import in.unicodelabs.kdlogging.data.KdLoggingProvider;
import in.unicodelabs.kdlogging.ui.adapter.KdLogListAdapter;
import in.unicodelabs.kdlogging.ui.dialog.PreferenceSelectionDialog;

public class KdLogListActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        KdLogListAdapter.Callbacks {

    private static final int LOADER_LOGS = 0;
    private static final String SORT_ORDER = "sortOrder";
    private static final String MIN_PRIORITY = "minPriority";

    private static final String DEFAULT_SORT = KdLoggingContract.Sort.DEFAULT;
    private static final int DEFAULT_MIN_PRIORITY = Log.VERBOSE;

    private KdLogListAdapter mLogEntryAdapter;

    private String mSortOrder;
    private int mMinPriority;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreInstanceState(savedInstanceState);

        setTitle("Kd Log");

        RecyclerView recycler = new RecyclerView(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);
        setContentView(recycler);

        mLogEntryAdapter = new KdLogListAdapter();
        recycler.setAdapter(mLogEntryAdapter);
        mLogEntryAdapter.setCallbacks(this);
//        mLogEntryAdapter.attachSwipeToRecyclerView(recycler);

        getSupportLoaderManager().initLoader(LOADER_LOGS, null, this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SORT_ORDER, mSortOrder);
        outState.putInt(MIN_PRIORITY, mMinPriority);
    }

    private void restoreInstanceState(@Nullable Bundle args) {
        if (args == null) {
            mSortOrder = DEFAULT_SORT;
            mMinPriority = DEFAULT_MIN_PRIORITY;
        } else {
            String sort = args.getString(SORT_ORDER);
            // api 12 default values for args not supported
            if (sort == null) {
                sort = DEFAULT_SORT;
            }
            int minPriority = args.getInt(MIN_PRIORITY);
            // api 12 default values for args not supported
            if (minPriority == 0) {
                minPriority = DEFAULT_MIN_PRIORITY;
            }
            mSortOrder = sort;
            mMinPriority = minPriority;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_LOGS: {
                return new CursorLoader(this,
                        KdLoggingContract.CONTENT_URI,
                        KdLoggingContract.Projection.ALL,
                        KdLoggingContract.Columns.PRIORITY + " >= ?",
                        new String[]{String.valueOf(mMinPriority)},
                        mSortOrder
                );
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_LOGS:
                mLogEntryAdapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_LOGS:
                mLogEntryAdapter.swapCursor(null);
                break;
        }
    }

    @Override
    public void onLogEntryClick(int position) {
    }

    @Override
    public void onLogEntryDelete(int position) {
        long id = mLogEntryAdapter.getItemId(position);
        if (KdLoggingProvider.deleteKdLogging(this, id)) {
            mLogEntryAdapter.notifyItemRemoved(position);
            reloadEntries();
        }
    }

    private void reloadEntries() {
        getSupportLoaderManager().restartLoader(LOADER_LOGS, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kd_log_list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int action = item.getItemId();
        if (action == R.id.action_filter) {
            showFilterOptions();
            return true;
        } else if (action == R.id.action_sort) {
            showSortOptions();
            return true;
        } else if (action == R.id.action_clear) {
            deleteAllEntries();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortOptions() {
        PreferenceSelectionDialog.show(this, R.string.select_sort, R.array.sort_names, new PreferenceSelectionDialog.OnSelection() {
            @Override
            public void onSelection(int index) {
                switch (index) {
                    case 0:
                        mSortOrder = KdLoggingContract.Sort.DATE;
                        break;
                    case 1:
                        mSortOrder = KdLoggingContract.Sort.PRIORITY;
                        break;
                    default:
                        return;
                }
                reloadEntries();
            }
        });
    }

    private void showFilterOptions() {
        PreferenceSelectionDialog.show(this, R.string.select_priority, R.array.priority_names, new PreferenceSelectionDialog.OnSelection() {
            @Override
            public void onSelection(int index) {
                switch (index) {
                    case 0:
                        mMinPriority = Log.VERBOSE;
                        break;
                    case 1:
                        mMinPriority = Log.DEBUG;
                        break;
                    case 2:
                        mMinPriority = Log.INFO;
                        break;
                    case 3:
                        mMinPriority = Log.WARN;
                        break;
                    case 4:
                        mMinPriority = Log.ERROR;
                        break;
                    case 5:
                        mMinPriority = Log.ASSERT;
                        break;
                    default:
                        return;
                }
                reloadEntries();
            }
        });
    }

    private void deleteAllEntries() {
        if (0 < KdLoggingProvider.deleteAllLogEntries(this)) {
            reloadEntries();
        }
    }
}