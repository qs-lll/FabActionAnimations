package com.qslll.fabactionanimation.fragments;


import android.animation.AnimatorSet;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslll.fabactionanimation.R;
import com.qslll.library.fabs.simple.QsBarController;
import com.qslll.library.fabs.simple.QsFabFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleBarFragment extends Fragment {



    public SimpleBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_simple_bar, container, false);
        FloatingActionButton fab = (FloatingActionButton) inflate.findViewById(R.id.fab);
        final QsFabFactory qsFabFactory = QsFabFactory.loadControl(fab, new QsBarController(), Color.WHITE);
        qsFabFactory.start(-1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qsFabFactory.start(-1);
            }
        });
        inflate.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qsFabFactory.stop();
            }
        });
        return inflate;
    }

}
