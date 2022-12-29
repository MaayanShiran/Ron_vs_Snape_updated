package com.example.ron_vs_mcgonagall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter_TopTen extends RecyclerView.Adapter<Adapter_TopTen.TopTenViewHolder> {

    private Context context;
    private AdapterView.OnItemClickListener listener;
    private ArrayList<TopTenDetails> topTen_list;
    private View view;
    static TopTenViewHolder myTopTenViewHolder;
    public static int clickedPosition = -1;
    private CallBack_OnItemClickListener onItemClickListener;

    public Adapter_TopTen(Context context, ArrayList<TopTenDetails> topTen_list, CallBack_OnItemClickListener listener) {
        this.context = context;
        this.topTen_list = topTen_list;
        onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return topTen_list == null ? 0 : topTen_list.size();
    }

    @Override
    public TopTenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycle_view, parent, false);

        myTopTenViewHolder = new TopTenViewHolder(view, onItemClickListener);

        return myTopTenViewHolder;
    }

    @Override
    public void onBindViewHolder(final TopTenViewHolder holder, @SuppressLint("RecyclerView") int position) {

        TopTenDetails topTenDetails = topTen_list.get(position);

        holder.score.setText("" + topTenDetails.getScore());
        holder.name.setText(topTenDetails.getName());
        holder.serialNo_img.setText("" + topTenDetails.getImage());

    }

    class TopTenViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        private TextView serialNo_img;
        private TextView name;
        private TextView score;
        public int clickedPosition = -1;
        CallBack_OnItemClickListener onItemClickListener;


        public TopTenViewHolder(View itemView, CallBack_OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnTouchListener(this);
            serialNo_img = itemView.findViewById(R.id.topTen_IMG_number);
            name = itemView.findViewById(R.id.topTen_IMG_name);
            score = itemView.findViewById(R.id.topTen_IMG_score);
            this.onItemClickListener = onItemClickListener;

        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            clickedPosition = getAdapterPosition();
            onItemClickListener.onItemClick(getAdapterPosition());
            return false;
        }

    }

    public interface CallBack_OnItemClickListener {
        void onItemClick(int position);
    }
}

