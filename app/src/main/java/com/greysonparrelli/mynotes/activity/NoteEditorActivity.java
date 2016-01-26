package com.greysonparrelli.mynotes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.greysonparrelli.mynotes.R;
import com.greysonparrelli.mynotes.fragment.NoteEditorFragment;

public class NoteEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        // Add out note editor fragment to the activity
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, NoteEditorFragment.create())
                    .commit();
        }
    }
}
