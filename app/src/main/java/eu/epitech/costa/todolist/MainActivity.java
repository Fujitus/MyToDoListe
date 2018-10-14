package eu.epitech.costa.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView                list;
    private dbManager               db;
    private ArrayAdapter<Todo>      adapters;
    private String                  orderRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.listView);
        db = new dbManager(this);
        this.orderRule = db.DB_DONE;
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void loadTaskList() {
        ArrayList<Todo> tasks = db.getTaskList(this.orderRule);
        if(adapters == null){
            adapters = new TodoAdapter(this, tasks);
            list.setAdapter(adapters);
        }
        else{
            adapters.clear();
            adapters.addAll(tasks);
            adapters.notifyDataSetChanged();
        }
    }

    public void addNewElem(View v) {
        Intent intent = new Intent(MainActivity.this, addElem.class);
        startActivity(intent);
    }

    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = parent.findViewById(R.id.tvTitle);
        String task = String.valueOf(taskTextView.getText());
        db.deleteTask(task);
        loadTaskList();
    }

    public void editTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = parent.findViewById(R.id.tvTitle);
        TextView contentTextView = parent.findViewById(R.id.tvContent);
        TextView dateTextView = parent.findViewById(R.id.tvDate);
        CheckBox doneCheckBox = parent.findViewById(R.id.cbDone);
        TextView tagTextView= parent.findViewById(R.id.tvTag);
        String task = String.valueOf(taskTextView.getText());
        String content = String.valueOf(contentTextView.getText());
        String date = String.valueOf(dateTextView.getText());
        String done = String.valueOf(doneCheckBox.getText());
        String tag = String.valueOf(tagTextView.getText());
        date = date.split("  ")[0] + "#" + date.split("  ")[1];
        Intent intent = new Intent(MainActivity.this, editElem.class);
        intent.putExtra("TASK", task);
        intent.putExtra("CONTENT", content);
        intent.putExtra("DATE", date);
        intent.putExtra("DONE", done);
        intent.putExtra("TAG", tag);
        startActivity(intent);
    }

    public void doneTask(View view) {
        View parent = (View)view.getParent();
        TextView taskTextView = parent.findViewById(R.id.tvTitle);
        CheckBox checkBox = parent.findViewById(R.id.cbDone);
        TextView contentTextView = parent.findViewById(R.id.tvContent);
        TextView dateTextView = parent.findViewById(R.id.tvDate);
        TextView tagTextView = parent.findViewById(R.id.tvTag);
        String status = String.valueOf(checkBox.isChecked());
        String task = String.valueOf(taskTextView.getText());
        String content = String.valueOf(contentTextView.getText());
        String date = String.valueOf(dateTextView.getText());
        String tag = String.valueOf(tagTextView.getText());
        if (status.equals("true"))
            db.editTask(task, task, content, date, true, tag);
        else
            db.editTask(task, task, content, date, false, tag);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.loadTaskList();
    }

    public void all(View v) {
        this.orderRule = null;
        this.loadTaskList();
    }

    public void orderByNotDone(View v) {
        this.orderRule = db.DB_DONE;
        this.loadTaskList();
    }

    public void orderByTag(View v) {
        this.orderRule = db.DB_TAG;
        this.loadTaskList();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            View view = findViewById(android.R.id.content);
            switch (item.getItemId()) {
                case R.id.allTasks:
                    all(view);
                    return true;
                case R.id.orderByDone:
                    orderByNotDone(view);
                    return true;
                case R.id.tag:
                    orderByTag(view);
                    return true;
                default:
                    return false;
            }
        }
    };

    public void share(View v) {
        View parent = (View)v.getParent();
        TextView taskTextView = parent.findViewById(R.id.tvTitle);
        TextView contentTextView = parent.findViewById(R.id.tvContent);
        String task = String.valueOf(taskTextView.getText());
        String content = String.valueOf(contentTextView.getText());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = task + "\n" + content;
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent, "Sare your task"));
    }

}
