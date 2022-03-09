package com.hohimlee.mpa.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hohimlee.mpa.R;

import java.util.ArrayList;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.RunningHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<RecyclerView_data_handler> list;

    public RecyclerView_Adapter(Context context, ArrayList<RecyclerView_data_handler> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RunningHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.running_card, parent, false);
        return new RunningHolder(v, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull RunningHolder holder, int position) {
        RecyclerView_data_handler handler = list.get(position);
        String event = handler.getEvent();

        if (event.equals("Running")) {
            holder.img.setImageResource(R.drawable.running_icon);
        }
        if (event.equals("Cycling")) {
            holder.img.setImageResource(R.drawable.cycling_icon);
        }
        holder.event.setText(event);
        holder.date.setText(handler.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RunningHolder extends RecyclerView.ViewHolder {

        TextView event,date;
        ImageView img;

        public RunningHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            event = itemView.findViewById(R.id.workout_event);
            date = itemView.findViewById(R.id.workout_date);
            img = itemView.findViewById(R.id.workout_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

}
