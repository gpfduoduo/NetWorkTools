package com.example.lenovo.networktools.ui.fragment.dbinspector;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lenovo.networktools.R;
import com.example.lenovo.networktools.utils.dbinspector.TablePageAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by 10129302 on 15-2-15.
 * 显示数据库中的内容
 */
public class TableFragment extends Fragment implements ActionBar.OnNavigationListener {

    private static final String KEY_DATABASE = "database_name";
    private static final String KEY_TABLE = "table_name";

    private static final String KEY_SHOWING_CONTENT = "showing_content";
    private static final String KEY_PAGE = "current_page";

    private File databaseFile;

    private String tableName;

    private boolean showingContent = true;

    /**
     * 当前是第几页内容，默认每页10个内容
     */
    private int currentPage;

    private TableLayout tableLayout;

    private TablePageAdapter adapter;

    /**
     * 下一页内容
     */
    private View nextButton;
    /**
     * 上一页内容
     */
    private View previousButton;

    private TextView currentPageText;

    /**
     * 按钮所在的头View
     */
    private View contentHeader;

    private ScrollView scrollView;

    private HorizontalScrollView horizontalScrollView;


    public static TableFragment newInstance(File databaseFile, String tableName) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATABASE, databaseFile);
        args.putString(KEY_TABLE, tableName);

        TableFragment tf = new TableFragment();
        tf.setArguments(args);

        return tf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            databaseFile = (File) getArguments().getSerializable(KEY_DATABASE);
            tableName = getArguments().getString(KEY_TABLE);
        }

        if(savedInstanceState != null) {
            showingContent = savedInstanceState.getBoolean(KEY_SHOWING_CONTENT, true);
            currentPage = savedInstanceState.getInt(KEY_PAGE, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate) {
        View view = inflater.inflate(R.layout.fragment_dbinspector_table, container, false);
        tableLayout = (TableLayout) view.findViewById(R.id.dbinspector_table_layout);
        previousButton = view.findViewById(R.id.dbinspector_button_previous);
        nextButton = view.findViewById(R.id.dbinspector_button_next);
        currentPageText = (TextView) view.findViewById(R.id.dbinspector_text_current_page);
        contentHeader = view.findViewById(R.id.dbinspector_layout_content_header);
        scrollView = (ScrollView) view.findViewById(R.id.dbinspector_scrollview_table);
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.dbinspector_horizontal_scrollview_table);

        previousButton.setOnClickListener(previousListener);
        nextButton.setOnClickListener(nextListener);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ActionBar actionBar = (getActivity()).getActionBar();
        actionBar.setTitle(tableName);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(
                new ArrayAdapter<>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[]{
                                getString(R.string.dbinspector_content),
                                getString(R.string.dbinspector_structure),
                        }
                ),
                this
        );

        adapter = new TablePageAdapter(getActivity(), databaseFile, tableName, currentPage);

        if (showingContent) {
            showContent();
        } else {
            showStructure();
        }
    }

    private void showContent() {
        showingContent = true;
        tableLayout.removeAllViews();
        List<TableRow> rows = adapter.getContentPage();
        for (TableRow row : rows) {
            tableLayout.addView(row);
        }
        currentPageText.setText(adapter.getCurrentPage() + "/" + adapter.getPageCount());
        contentHeader.setVisibility(View.VISIBLE);
        nextButton.setEnabled(adapter.hasNext());
        previousButton.setEnabled(adapter.hasPrevious());
    }

    private void showStructure() {
        showingContent = false;
        tableLayout.removeAllViews();
        List<TableRow> rows = adapter.getStructure();
        for (TableRow row : rows) {
            tableLayout.addView(row);
        }
        contentHeader.setVisibility(View.GONE);
    }

    private View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentPage++;
            adapter.nextPage();
            showContent();

            scrollView.scrollTo(0, 0);
            horizontalScrollView.scrollTo(0, 0);
        }
    };

    private View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentPage--;
            adapter.previousPage();
            showContent();

            scrollView.scrollTo(0, 0);
            horizontalScrollView.scrollTo(0, 0);
        }
    };

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {
            case 0:
                showContent();
                break;
            case 1:
                showStructure();
                break;
        }
        return true;
    }
}
