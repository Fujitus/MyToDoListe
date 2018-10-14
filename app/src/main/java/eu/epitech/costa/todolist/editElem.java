package eu.epitech.costa.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class editElem extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private dbManager               db;
    private String                  task;
    private boolean                 done;
    TextView                        taskDate;
    TextView                        taskTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_elem);
        this.db = new dbManager(this);
        this.task = getIntent().getStringExtra("TASK");
        String content = getIntent().getStringExtra("CONTENT");
        String timestamp = getIntent().getStringExtra("DATE");
        String tag = getIntent().getStringExtra("TAG");

        if (getIntent().getStringExtra("DONE").equals("true")) {
            this.done = true;
        }
        else {
            this.done = false;
        }

        EditText taskTitle = findViewById(R.id.etTitle);
        EditText  taskContent = findViewById(R.id.etContent);
        this.taskDate = findViewById(R.id.tvDate);
        this.taskTime = findViewById(R.id.tvTime);
        TextView taskTag = findViewById(R.id.tvTag);

        taskTitle.setText(task, TextView.BufferType.EDITABLE);
        taskContent.setText(content, TextView.BufferType.EDITABLE);
        taskTag.setText(tag, TextView.BufferType.EDITABLE);
        taskDate.setText(timestamp.split("#")[1], TextView.BufferType.EDITABLE);
        taskTime.setText(timestamp.split("#")[0], TextView.BufferType.EDITABLE);
    }

    public void saveElem(View v) {
        String newtask = ((EditText)findViewById(R.id.etTitle)).getText().toString();
        String newContent = ((EditText)findViewById(R.id.etContent)).getText().toString();
        String date = ((TextView)findViewById(R.id.tvDate)).getText().toString();
        String time = ((TextView)findViewById(R.id.tvTime)).getText().toString();
        String tag = ((TextView)findViewById(R.id.tvTag)).getText().toString();
        if (task.indexOf(";") == 0 || newContent.indexOf(";") == 0) {
            new AlertDialog.Builder(editElem.this)
                    .setTitle("Invalid character input")
                    .setMessage("Please re edit your task")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        else {
            String timestamp = time + "#" + date;
            db.editTask(newtask, task, newContent, timestamp, this.done, tag);
            finish();
        }
    }

    public void quit(View v) {
        finish();
    }

    public void addDate(View v) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (view.isShown()){
                            taskDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void addTime(View v) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (view.isShown()) {
                            taskTime.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    public void setTag(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.tags);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TextView tag = findViewById(R.id.tvTag);
        switch (item.getItemId()) {
            case R.id.Urgent:
                tag.setText("Urgent");
                return true;
            case R.id.Epitech:
                tag.setText("Epitech");
                return true;
            case R.id.Stage:
                tag.setText("Stage");
                return true;
            case R.id.Perso:
                tag.setText("Perso");
                return true;
            default:
                return true;
        }
    }
}