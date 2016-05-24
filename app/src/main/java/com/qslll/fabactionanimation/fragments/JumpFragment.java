package com.qslll.fabactionanimation.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslll.fabactionanimation.R;
import com.qslll.library.fabs.QsJumpFab;

/**
 * A simple {@link Fragment} subclass.
 */
public class JumpFragment extends Fragment {


    private QsJumpFab qsJumpFab;

    public JumpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_jump, container, false);
        qsJumpFab = (QsJumpFab) inflate.findViewById(R.id.jump);
        qsJumpFab.setImageResource(R.drawable.linux);
        qsJumpFab.setBackgroundTintColor(Color.parseColor("#FFA726"));
        qsJumpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qsJumpFab.start(-1);
            }
        });
        //需要等到 view加载完成时才能加载启动动画
        qsJumpFab.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                qsJumpFab.start(-1);
            }
        });



        inflate.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qsJumpFab.stop();
            }
        });
        return inflate;
    }

}
