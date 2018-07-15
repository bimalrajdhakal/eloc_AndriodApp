package com.nexogen.routefinder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexogen.routefinder.ChekNetwork.CheckNetworkConnection;
import com.nexogen.routefinder.R;
import com.nexogen.routefinder.model.Model;
import com.nexogen.routefinder.adapter.MyAdapter;
import com.nexogen.routefinder.databases.AppDatabase;
import com.nexogen.routefinder.databases.BookMarkTable;
import com.nexogen.routefinder.intefaces.TagName;
import com.nexogen.routefinder.model.BookMarkModel;
import com.nexogen.routefinder.utils.UtilClass;

import java.util.ArrayList;
import java.util.List;

import static com.nexogen.routefinder.intefaces.TagName.addedData;
import static com.nexogen.routefinder.intefaces.TagName.delteSuccsessfully;

public class BookMarkActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    public List<BookMarkModel> bookMarkModels;
    EditText edt_from;
    List<Model> list, deleteList;
    double[] data = {0, 0};
    private ImageButton btnSave, btnCancel, btnClear, btnDelete;
    private String addLocation;
    private ListView listView;
    private AppDatabase appDatabase;
    private List<BookMarkTable> bookMarkModel;
    private UtilClass utilClass;
    private List<BookMarkTable> saveData;
    private List<BookMarkTable> updatedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);


        objectInitialize();

        init();


        for (int count = 0; count < bookMarkModel.size(); count++) {
            list.add(new Model(bookMarkModel.get(count).getAddress(), bookMarkModel.get(count).getId()));
        }
        listView.setAdapter(new MyAdapter(this, list));
    }

    private void objectInitialize() {
        bookMarkModels = new ArrayList<>();
        appDatabase = AppDatabase.getDatabase(this);
        bookMarkModel = appDatabase.bookMarkDao().getAllUser();
        utilClass = new UtilClass();
        list = new ArrayList<Model>();
        deleteList = new ArrayList<Model>();
    }


    private void init() {

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(BookMarkActivity.this);

        edt_from = (EditText) this.findViewById(R.id.edt_froms);

        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.save_btn);
        btnClear = findViewById(R.id.clear_btn);
        btnDelete = findViewById(R.id.delete_btn);

        btnCancel.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
        TextView label = (TextView) v.getTag(R.id.label);
        CheckBox checkbox = (CheckBox) v.getTag(R.id.check);

        String from = label.getText().toString();


        if (CheckNetworkConnection.isConnectionAvailable(BookMarkActivity.this)) {
            data = utilClass.getAddressName(from, BookMarkActivity.this);
            utilClass.openGoogleMap(BookMarkActivity.this, MainActivity.latLongModel.get(0).latitude, MainActivity.latLongModel.get(0).longitude
                    , data[0], data[1]);
        } else {
            Toast.makeText(BookMarkActivity.this, TagName.notConnected, Toast.LENGTH_SHORT).show();

        }


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();

                break;
            case R.id.save_btn:

                saveData();

                break;
            case R.id.clear_btn:
                delteTable(true);

                break;
            case R.id.delete_btn:
                delteTable(false);
                break;

        }
    }

    private void saveData() {
        ////////////////// add loction from editText ///////////////////////////////////////
        addLocation = edt_from.getText().toString().trim();
        if (addLocation.isEmpty() || addLocation.equals("")) {
            Toast.makeText(this, "please enter city/place", Toast.LENGTH_SHORT).show();
        } else {

            list.clear();
            bookMarkModel = appDatabase.bookMarkDao().getAllUser();


            appDatabase.bookMarkDao().addUser(new BookMarkTable(bookMarkModel.size() + 1, addLocation));
            saveData = appDatabase.bookMarkDao().getAllUser();
            for (int count = 0; count < saveData.size(); count++) {
                list.add(new Model(saveData.get(count).getAddress(), saveData.get(count).getId()));
            }

            Toast.makeText(this, addedData, Toast.LENGTH_SHORT).show();

            listView.setAdapter(new MyAdapter(BookMarkActivity.this, list));
            edt_from.setText("");


        }
    }

    private void delteTable(boolean b) {

        deleteList.clear();
        for (int count = 0; count < MyAdapter.list.size(); count++) {
            if (MyAdapter.list.get(count).isSelected() == true) {
                deleteList.add(MyAdapter.list.get(count));
            }
        }


        if (b == false) {
            for (int counts = 0; counts < deleteList.size(); counts++) {
                appDatabase.bookMarkDao().deleteById(deleteList.get(counts).getId());
            }

            Toast.makeText(this, delteSuccsessfully, Toast.LENGTH_SHORT).show();
        }

        updatedData = appDatabase.bookMarkDao().getAllUser();
        list.clear();
        for (int count = 0; count < updatedData.size(); count++) {
            list.add(new Model(updatedData.get(count).getAddress(), updatedData.get(count).getId()));
        }
        MyAdapter myAdapter = new MyAdapter(BookMarkActivity.this, list);
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();


    }


}
