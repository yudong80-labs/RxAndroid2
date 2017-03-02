package com.yudong80.rxandroid2.chapter01;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yudong80.rxandroid2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;

public class GugudanFragment extends Fragment {

    @BindView(R.id.city) EditText mDan;
    @BindView(R.id.result) EditText mResult;
    private Unbinder unbinder;

    public GugudanFragment() {
        // Required empty public constructor
    }

    public static GugudanFragment newInstance() {
        GugudanFragment fragment = new GugudanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gugudan, container, false);
        unbinder = ButterKnife.bind(this, view);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.button)
    public void gugudan() {
        mResult.setText("");
        Observable.just(mDan.getText().toString())
                .map(Integer::parseInt)
                .flatMap(dan -> Observable.range(1,9)
                                          .map(i -> dan + " * " + i + " = " + (dan * i) + "\n"))
                .subscribe(line -> {
                    String before = mResult.getText().toString();
                    mResult.setText(before + line);
                });
    }
}
