package com.nexogen.routefinder;

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

import com.nexogen.routefinder.MultipleSelection.Model;
import com.nexogen.routefinder.MultipleSelection.MyAdapter;
import com.nexogen.routefinder.databases.AppDatabase;
import com.nexogen.routefinder.databases.BookMarkTable;
import com.nexogen.routefinder.model.BookMarkModel;
import com.nexogen.routefinder.utils.UtilClass;

import java.util.ArrayList;
import java.util.List;

public class BookMarkActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    public List<BookMarkModel> bookMarkModels;
    EditText edt_from;
    List<Model> list, duplicateList;
    double[] data = {0, 0};
    private ImageButton btnSave, btnCancel, btnClear, btnDelete;
    private String addLocation;
    private ListView listView;
    private BookMarkAdapter bookMarkAdapter;
    private AppDatabase appDatabase;
    private List<BookMarkTable> bookMarkModel;
    private UtilClass utilClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        bookMarkModels = new ArrayList<>();


        appDatabase = AppDatabase.getDatabase(this);

        bookMarkModel = appDatabase.bookMarkDao().getAllUser();


        utilClass = new UtilClass();

        list = new ArrayList<Model>();

        duplicateList = new ArrayList<Model>();
        init();
        listView.setOnItemClickListener(BookMarkActivity.this);

        for (int count = 0; count < 10; count++) {
            list.add(new Model("dinesh kumar--" + count));
        }


        listView.setAdapter(new MyAdapter(this, list));
    }


    private void init() {

        listView = (ListView) findViewById(R.id.list_view);

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


        data = utilClass.getAddressName(from, BookMarkActivity.this);

        utilClass.openGoogleMap(BookMarkActivity.this, MainActivity.latLongModel.get(0).latitude, MainActivity.latLongModel.get(0).longitude
                , data[0], data[1]);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_cancel:

                finish();

                break;
            case R.id.save_btn:

                ////////////////// add loction from editText ///////////////////////////////////////
                addLocation = edt_from.getText().toString().trim();
                if (addLocation.isEmpty() || addLocation.equals("")) {
                    Toast.makeText(this, "please enter city/place", Toast.LENGTH_SHORT).show();
                } else {
                    list.add(new Model(addLocation));
//                    bookMarkModels.add(new BookMarkModel(addLocation));

                    listView.setAdapter(new MyAdapter(BookMarkActivity.this, list));
                    edt_from.setText("");


                }
//                bookMarkAdapter();

                break;
            case R.id.clear_btn:
                list.clear();

                for (int count = 0; count < 10; count++) {
                    list.add(new Model("dinesh kumar--" + count));
                }


                listView.setAdapter(new MyAdapter(this, list));

//                for(int count = 0; count< MyAdapter.list.size(); count++){
//                    if(MyAdapter.list.get(count).isSelected()==true)
//                        duplicateList.add(MyAdapter.list.get(count));
//                }
//                Log.d("check",duplicateList.size()+"");

                break;
            case R.id.delete_btn:
                break;

        }
    }


}
