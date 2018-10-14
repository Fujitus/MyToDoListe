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

public class addElem extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private dbManager   db;
    private TextView    txtDate;
    private TextView    txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_elem);
        db = new dbManager(this);
        txtDate = findViewById(R.id.tvDate);
        txtTime = findViewById(R.id.tvTime);
    }

    public void creatElem(View v) {
        String task = ((EditText)findViewById(R.id.etTitle)).getText().toString();
        String content = ((EditText)findViewById(R.id.etContent)).getText().toString();
        String tag = ((TextView)findViewById(R.id.tvTag)).getText().toString();
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();
        if (task.equals("") || content.equals("") || date.equals("Set Date") || time.equals("Set Time") || tag.equals("Set Tag"))
        {
            new AlertDialog.Builder(addElem.this)
                    .setTitle("Invalid input")
                    .setMessage("Please specify all fields")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        else if (task.indexOf(";") == 0 || content.indexOf(";") == 0) {
            new AlertDialog.Builder(addElem.this)
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
            db.insertNewTask(task, content, timestamp, false, tag);
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
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
                            txtTime.setText(hourOfDay + ":" + minute);
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
