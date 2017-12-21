//package com.nexogen.routefinder.MultipleSelection;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.CheckBox;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.nexogen.routefinder.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity implements OnItemClickListener {
//
//    ListView listView;
//    ArrayAdapter<Model> adapter;
//    List<Model> list, duplicateList;
//    ImageButton cancel;
//    private Model model;
//
//    public void onCreate(Bundle icicle) {
//        super.onCreate(icicle);
//        setContentView(R.layout.main);
//
//        list  = new ArrayList<Model>();
//
//        duplicateList=new ArrayList<Model>();
//
//        //TODO 19-Dec-017
////        cancel = (ImageButton) findViewById(R.id.cancel);
//        listView = (ListView) findViewById(R.id.my_list);
//        adapter = new MyAdapter(this, getModel());
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                getModel().clear();
////
////                adapter = new MyAdapter(MainActivity.this, getModel());
////                listView.setAdapter(adapter);
////                Toast.makeText(MainActivity.this, "jhh", Toast.LENGTH_SHORT).show();
//
//                for(int count = 0; count< MyAdapter.list.size(); count++){
//                    if(MyAdapter.list.get(count).isSelected()==true)
//                    duplicateList.add(MyAdapter.list.get(count));
//                }
//                Log.d("check",duplicateList.size()+"");
//
//
//            }
//        });
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
//        TextView label = (TextView) v.getTag(R.id.label);
//        CheckBox checkbox = (CheckBox) v.getTag(R.id.check);
////		Toast.makeText(v.getContext(), label.getText().toString(),isCheckedOrNot(checkbox), Toast.LENGTH_LONG).show();
//
//        isCheckedOrNot(checkbox, position);
//
//    }
//
//
//    private void isCheckedOrNot(CheckBox checkbox, int position) {
//        if (checkbox.isChecked()) {
//
//        } else {
//        }
//    }
////	private String isCheckedOrNot(CheckBox checkbox) {
////		if(checkbox.isChecked())
////			return "is checked";
////		else
////			return "is not checked";
////	}
//
//    public List<Model> getModel() {
//        list.add(new Model("Linux"));
//        list.add(new Model("Windows7"));
//        list.add(new Model("Suse"));
//        list.add(new Model("Eclipse"));
//        list.add(new Model("Ubuntu"));
//        list.add(new Model("Solaris"));
//        list.add(new Model("Android"));
//        list.add(new Model("iPhone"));
//        list.add(new Model("Java"));
//        list.add(new Model(".Net"));
//        list.add(new Model("PHP"));
//        list.add(new Model("Linux"));
//        list.add(new Model("Windows7"));
//        list.add(new Model("Suse"));
//        list.add(new Model("Eclipse"));
//        list.add(new Model("Ubuntu"));
//        list.add(new Model("Solaris"));
//        list.add(new Model("Android"));
//        list.add(new Model("iPhone"));
//        list.add(new Model("Java"));
//        list.add(new Model(".Net"));
//        list.add(new Model("PHP"));
//        return list;
//    }
//}