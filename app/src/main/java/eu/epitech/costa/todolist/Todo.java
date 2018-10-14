package eu.epitech.costa.todolist;

import android.util.Log;


/**
 * Created by fujitus on 29/01/2018.
 */

public class Todo {
    private String        title;
    private String        content;
    private String        date;
    private boolean       done;
    private String        tag;

    public Todo(String title, String content, String date, String done, String tag) {
        this.title = title;
        this.content = content;
        this.date = date;

        if (done.equals("0"))
            this.done = false;
        else {
            this.done = true;
        }
        this.tag = tag;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getDate() {
        return this.date;
    }

    public boolean getDone() {
        return this.done;
    }

    public String getTag() {
        return this.tag;
    }
}
