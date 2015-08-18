package com.example.lenovo.networktools.ui.fragment.dbinspector;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.lenovo.networktools.R;
import com.example.lenovo.networktools.utils.command.GeneralCommand;
import com.example.lenovo.networktools.utils.dbinspector.DataBaseHelper;

import java.io.File;
import java.util.List;

/**
 * Created by 10129302 on 15-2-26.
 * 文件列表(为了寻找数据库，一般app数据库放在自己/data/data/package name/databases下，或者sd card中)，
 * 只显示/data和sd card列表
 */
public class FileFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private ArrayAdapter adapter;
    private List<File> fileList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fileList = DataBaseHelper.getListFile(Environment.getRootDirectory().getParentFile());
        adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                fileList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File file = (File) adapter.getItem(position);
        if (file.isDirectory()) {
            if (file.getAbsolutePath().startsWith("/data"))
                GeneralCommand.chmod(file.getAbsolutePath());
            fileList.clear();
            adapter.notifyDataSetChanged();
            fileList.addAll(DataBaseHelper.getListFile(file));
            adapter.notifyDataSetChanged();
        } else {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.dbinspector_container, DatabaseListFragment.newInstance(file));
            ft.addToBackStack(null).commit();
            fm.executePendingTransactions();
        }
    }

}
