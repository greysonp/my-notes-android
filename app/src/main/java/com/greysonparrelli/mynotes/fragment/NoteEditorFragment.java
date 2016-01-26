package com.greysonparrelli.mynotes.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greysonparrelli.mynotes.R;

public class NoteEditorFragment extends Fragment {

    public static  NoteEditorFragment create() {
        return new NoteEditorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_editor, container, false);
    }
}
