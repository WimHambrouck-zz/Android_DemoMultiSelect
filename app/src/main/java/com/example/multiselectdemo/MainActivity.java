package com.example.multiselectdemo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Integer> selected_items;
    private int checkedCount;
    private ArrayList<DummyItem> dummyData;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selected_items = new ArrayList<>();

        initDummyData();

        listView = findViewById(R.id.list);
        initAdapter();

        // enable multi-select with contextual action bar (CAB)
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        // set listener to handle multi select (TODO: this should be a seperate file)
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // get amount of selected items
                checkedCount = listView.getCheckedItemCount();

                // set CAB title
                mode.setTitle(checkedCount + " item(s) selected.");

                // if item was checked, add it to the list,
                // if item was unchecked, remove it
                if (checked) {
                    selected_items.add(position);
                } else {
                    selected_items.remove(position);
                }

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // inflate the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.context_menu_delete) {
                    // delete selected id's from list
                    for (int position : selected_items) {
                        dummyData.remove(position);
                    }
                }

                // reset amount of checked items
                checkedCount = 0;
                // empty the selected items array
                selected_items.clear();
                // leave multi-select mode
                mode.finish();

                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // refresh list
                initAdapter();
            }
        });
    }

    private void initAdapter() {
        // fill list with dummy data
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.list_item, R.id.txt_list_item, dummyData));
    }

    private void initDummyData() {
        dummyData = new ArrayList<>();

        for (String cheese : Cheeses.CHEESES) {
            dummyData.add(new DummyItem(cheese));
        }
    }
}
