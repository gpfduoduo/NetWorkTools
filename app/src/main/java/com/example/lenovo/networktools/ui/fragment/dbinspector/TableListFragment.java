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
import com.example.lenovo.networktools.utils.FileUtils;
import com.example.lenovo.networktools.utils.Logger;
import com.example.lenovo.networktools.utils.command.GeneralCommand;
import com.example.lenovo.networktools.utils.dbinspector.DataBaseHelper;

import java.io.File;
import java.util.List;

/**
 * Created by 10129302 on 15-2-15.
 * 显示所有的数据库表
 */
public class TableListFragment extends ListFragment {

    private static final String KEY_DATABASE = "database_name";
    /**
     * 其他app的数据库文件
     */
    private File database;
    /**
     * 将其他app数据库copy到自己的data/data/package name/databases/下的文件
     */
    private File selfDatabase;

    public static TableListFragment newInstance(File database) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATABASE, database);
        TableListFragment tlf = new TableListFragment();
        tlf.setArguments(args);

        return tlf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e(getClass().getSimpleName() + " onCreate function");

        if(getArguments() != null) {
            database = (File) getArguments().getSerializable(KEY_DATABASE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.e(getClass().getSimpleName() + " onActivityCreated function");

        if (database.getAbsolutePath().startsWith("/data")) {
            GeneralCommand.chmod(database.getAbsolutePath());
        }

        /**
         * 将数据文件copy到自己的data/data/package name/databases/下
         * 原因：有些app程序退出后，他的数据库是不关闭的，这是我们openOrCreateDatabase()会出错
         */
        FileUtils.mkDir(getActivity().getFilesDir().getParentFile().getAbsolutePath() + File.separator + "databases");
        selfDatabase = FileUtils.copy(database,
                getActivity().getFilesDir().getParentFile().getAbsolutePath() + File.separator + "databases" + File.separator + database.getName());

        List<String> tableList = DataBaseHelper.getAllTables(selfDatabase);
        if(tableList != null) {
            ListAdapter adapter = new ArrayAdapter<>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    tableList);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(tableClickListener);
        }
    }

    private AdapterView.OnItemClickListener tableClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.dbinspector_container, TableFragment.newInstance(selfDatabase, (String) getListAdapter().getItem(position)));
            ft.addToBackStack(null).commit();
            fm.executePendingTransactions();
        }
    };
}
