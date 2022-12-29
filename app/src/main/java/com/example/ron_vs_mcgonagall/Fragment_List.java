package com.example.ron_vs_mcgonagall;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Fragment_List extends Fragment implements CallBack_OnItemClickListener {

    private Adapter_TopTen adapter_topTen;
    private ImageView highscore_title;
    private RecyclerView list_topten;
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String SP_KEY_TOPTEN = "SP_KEY_PLAYLIST";

    public static final String TEXT = "text";
    private int index;
    CallBack_OnItemClickListener listener;

    GameManager gameManager;

    private CallBack_TopTenProtocol callBack_topTenProtocol;

    public void setCallBack_topTenProtocol(CallBack_TopTenProtocol callBack_topTenProtocol) {
        this.callBack_topTenProtocol = callBack_topTenProtocol;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        gameManager = GameManager.set();

        findViews(view);
        transferDetails();

        initViews();

        return view;
    }

    private void initViews() {

        Adapter_TopTen adapter_topTen = new Adapter_TopTen(this.getContext(), gameManager.getTopTen().getTopTen(), this::onItemClick);
        list_topten.setLayoutManager(new LinearLayoutManager(this.getContext()));
        list_topten.setAdapter(adapter_topTen);
        list_topten.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (callBack_topTenProtocol != null) {

                    callBack_topTenProtocol.topTenDetails(gameManager.getTopTen().getTopTen().get(index));
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (callBack_topTenProtocol != null) {

                    callBack_topTenProtocol.topTenDetails(gameManager.getTopTen().getTopTen().get(rv.getChildAdapterPosition(getView())));
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    public void transferDetails() {

        MySharedPreferences.init(getContext());

        setListFromJson();

    }

    private void findViews(View view) {

        list_topten = view.findViewById(R.id.list_topten);
        highscore_title = view.findViewById(R.id.title_LBL_highscores);

    }

    @Override
    public void onItemClick(int position) {
        index = position;
    }

    public void setListFromJson() {
        ArrayList<TopTenDetails> topten;
        String serializedObject = MySharedPreferences.getInstance().getString(SP_KEY_TOPTEN, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<TopTenDetails>>() {
            }.getType();
            topten = gson.fromJson(serializedObject, type);
            TopTen_Arr.setTopTens(topten);
        }
    }
}
