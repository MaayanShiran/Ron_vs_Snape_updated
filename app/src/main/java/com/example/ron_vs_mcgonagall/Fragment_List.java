package com.example.ron_vs_mcgonagall;

import android.content.Context;
import android.content.SharedPreferences;
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

public class Fragment_List extends Fragment implements CallBack_OnItemClickListener {

    private Adapter_TopTen adapter_topTen;
    private ImageView highscore_title;
    private RecyclerView list_topten;
    public static final String SHARED_PREFS = "sharedPrefs";
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

        Adapter_TopTen adapter_topTen = new Adapter_TopTen(this.getContext(), gameManager.getTopTen(), this::onItemClick);
        list_topten.setLayoutManager(new LinearLayoutManager(this.getContext()));
        list_topten.setAdapter(adapter_topTen);
        list_topten.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (callBack_topTenProtocol != null) {

                    callBack_topTenProtocol.topTenDetails(gameManager.getTopTen().get(index));
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (callBack_topTenProtocol != null) {

                    callBack_topTenProtocol.topTenDetails(gameManager.getTopTen().get(rv.getChildAdapterPosition(getView())));
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    public void transferDetails() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

       int numberItems = sharedPreferences.getInt("numberItems", gameManager.getCurrentTopTenNum());

        for (int i = 0; i < 10; i++) {
            gameManager.getTopTen().get(i).setSerialNoImg(i + 1);
            gameManager.getTopTen().get(i).setName(sharedPreferences.getString("name" + i, gameManager.getTopTen().get(i).getName()));
            gameManager.getTopTen().get(i).setScore(sharedPreferences.getInt("score" + i, gameManager.getTopTen().get(i).getScore()));
            gameManager.getTopTen().get(i).setLocation(Double.parseDouble(sharedPreferences.getString("lat" + i, gameManager.getTopTen().get(i).getLatitude())), Double.parseDouble(sharedPreferences.getString("lon" + i, gameManager.getTopTen().get(i).getLongitude())));

        }

    }

    private void findViews(View view) {

        list_topten = view.findViewById(R.id.list_topten);
        highscore_title = view.findViewById(R.id.title_LBL_highscores);

    }

    @Override
    public void onItemClick(int position) {
        index = position;
    }
}
