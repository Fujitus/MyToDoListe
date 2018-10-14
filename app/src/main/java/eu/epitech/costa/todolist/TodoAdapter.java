package eu.epitech.costa.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fujitus on 29/01/2018.
 */

public class TodoAdapter extends ArrayAdapter<Todo> {
    public TodoAdapter(Context context, ArrayList<Todo> todoArrayAdapter) {
        super(context, 0, todoArrayAdapter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Todo todo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.elem, parent, false);
        }

        TextView tvTaskTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvContent = convertView.findViewById(R.id.tvContent);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvTag  = convertView.findViewById(R.id.tvTag);

        tvTaskTitle.setText(todo.getTitle());
        tvContent.setText(todo.getContent());
        tvTag.setText(todo.getTag());
        if (todo.getDate().split("#").length > 1)
            tvDate.setText(todo.getDate().split("#")[0] + "  " + todo.getDate().split("#")[1]);
        CheckBox checkBox = convertView.findViewById(R.id.cbDone);
        if (todo.getDone()) {
            checkBox.setChecked(true);
        }
        else {
            checkBox.setChecked(false);
        }
        return convertView;
    }
}
