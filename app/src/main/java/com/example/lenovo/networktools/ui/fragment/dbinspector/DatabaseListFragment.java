package com.example.lenovo.networktools.ui.fragment.dbinspector;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


import com.example.lenovo.networktools.R;
import com.example.lenovo.networktools.utils.dbinspector.DataBaseHelper;

import java.io.File;

/**
 * Created by 10129302 on 15-2-15.
 * 显示所有的数据库文件
 */
public class DatabaseListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    /**
     * 数据库所在的路径， 必须是db文件的上一层
     */
    private String dbDir = null;

    private static final String KEY_FILE = "database_file";

    public static DatabaseListFragment newInstance(File databaseFile) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_FILE, databaseFile);
        DatabaseListFragment dlf = new DatabaseListFragment();
        dlf.setArguments(args);

        return dlf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            File databaseFile = (File) getArguments().getSerializable(KEY_FILE);
            dbDir = databaseFile.getParentFile().getAbsolutePath();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListAdapter adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                DataBaseHelper.getDatabaseList(dbDir));
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.dbinspector_container, TableListFragment.newInstance((File) getListAdapter().getItem(position)));
        ft.addToBackStack(null).commit();
        fm.executePendingTransactions();
    }
}
