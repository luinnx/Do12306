package com.cheart.do12306.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.cheart.do12306.app.R;
import com.cheart.do12306.app.task.QueryTicketTask;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ResultQueryActivityDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ResultQueryActivityListFragment} and the item details
 * (if present) is a {@link ResultQueryActivityDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ResultQueryActivityListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ResultQueryActivityListActivity extends FragmentActivity
        implements ResultQueryActivityListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    public static final String [] FROM = new String[] {
            QueryTicketTask.STATION_TRAIN_CODE,
            QueryTicketTask.START_TIME,
            QueryTicketTask.ARRIVE_TIME,
            QueryTicketTask.YZ_NUM,
            QueryTicketTask.RZ_NUM,
            QueryTicketTask.YW_NUM,
            QueryTicketTask.RW_NUM,
            QueryTicketTask.ZY_NUM,
            QueryTicketTask.ZE_NUM,
            QueryTicketTask.SWZ_NUM,
    };

    public static int[] TO = new int[] {
            R.id.tv_resultQuery_stationTrainCode,
            R.id.tv_resultQuery_startTime,
            R.id.tv_resultQuery_arriveTime,
            R.id.tv_resultQuery_yzNum,
            R.id.tv_resultQuery_rzNum,
            R.id.tv_resultQuery_ywNum,
            R.id.tv_resultQuery_rwNum,
            R.id.tv_resultQuery_zyNum,
            R.id.tv_resultQuery_zeNum,
            R.id.tv_resultQuery_swzNum,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultqueryactivity_list);

        if (findViewById(R.id.resultqueryactivity_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ResultQueryActivityListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.resultqueryactivity_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ResultQueryActivityListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ResultQueryActivityDetailFragment.ARG_ITEM_ID, id);
            ResultQueryActivityDetailFragment fragment = new ResultQueryActivityDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.resultqueryactivity_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ResultQueryActivityDetailActivity.class);
            detailIntent.putExtra(ResultQueryActivityDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
