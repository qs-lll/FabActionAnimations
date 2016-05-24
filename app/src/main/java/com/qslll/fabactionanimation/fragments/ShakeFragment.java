package com.qslll.fabactionanimation.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslll.fabactionanimation.R;
import com.qslll.library.fabs.QsShakeFab;

/**
 */
public class ShakeFragment extends Fragment {


    private QsShakeFab qsShakeFab;

    public ShakeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_shake, container, false);

        qsShakeFab = (QsShakeFab)inflate.findViewById(R.id.shake);
        qsShakeFab.setBackgroundTintColor(Color.parseColor("#039BE5"));
        qsShakeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qsShakeFab.start(-1);
            }
        });
        qsShakeFab.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                qsShakeFab.start(-1);
            }
        });


        inflate.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qsShakeFab.stop();
            }
        });
        return inflate;
    }

}
