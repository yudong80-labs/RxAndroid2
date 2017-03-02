package com.yudong80.rxandroid2.chapter02;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yudong80.rxandroid2.R;
import com.yudong80.rxandroid2.common.OkHttpHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

public class OpenWeatherMapFragment extends Fragment {
    private static final String TAG = OpenWeatherMapFragment.class.getSimpleName();

    @BindView(R.id.city) EditText mDan;
    @BindView(R.id.result) EditText mResult;
    private Unbinder unbinder;

    public OpenWeatherMapFragment() {
        // Required empty public constructor
    }

    public static OpenWeatherMapFragment newInstance() {
        OpenWeatherMapFragment fragment = new OpenWeatherMapFragment();
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
        View view = inflater.inflate(R.layout.fragment_open_weather_map, container, false);
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

    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=";
    public static final String API_KEY = "5712cae3137a8f6bcbebe4fb35dfb434";

    @OnClick(R.id.button)
    public void run() {
        mResult.setText("");
        Observable<String> source = Observable.just(URL + API_KEY)
                .map(OkHttpHelper::getWithLog)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        //어떻게 호출을 한번만 하게 할 수 있을까?
        ConnectableObservable<String> connectable = source.publish();
        connectable.map(this::parseTemperature)
                .subscribe(this::appendResult);
        connectable.map(this::parseCityName)
                .subscribe(this::appendResult);
        connectable.map(this::parseCountry)
                .subscribe(this::appendResult);
        connectable.connect();
    }

    private void appendResult(String text) {
        mResult.setText(mResult.getText().toString() + text + "\n");
    }

    private String parseTemperature(String json) {
        return parse(json, "\"temp\":[0-9]*.[0-9]*");
    }

    private String parseCityName(String json) {
        return parse(json, "\"name\":\"[a-zA-Z]*\"");
    }

    private String parseCountry(String json) {
        return parse(json, "\"country\":\"[a-zA-Z]*\"");
    }

    private String parse(String json, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(json);
        if (match.find()) {
            return match.group();
        }
        return "N/A";
    }
}
