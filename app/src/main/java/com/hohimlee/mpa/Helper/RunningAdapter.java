package com.hohimlee.mpa.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.User;
import com.hohimlee.mpa.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RunningAdapter extends RecyclerView.Adapter<RunningAdapter.RunningHolder> {

    Context context;
    ArrayList<RunningDataHandler> list;

    public RunningAdapter(Context context, ArrayList<RunningDataHandler> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RunningHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.running_card, parent, false);
        return new RunningHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RunningHolder holder, int position) {
        RunningDataHandler user = list.get(position);
        String event = user.getEvent();

        holder.event.setText(event);
        holder.miles.setText("Miles: " + user.getMiles());
        holder.route.setText("Route: " + user.getRoute());
        holder.duration.setText("duration: " + user.getDuration());
        holder.date.setText("Date: " + user.getDate());

        if(event.equals("Running")){
            holder.img.setImageResource(R.drawable.running_icon);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RunningHolder extends RecyclerView.ViewHolder {

        TextView miles, duration, route, event, date;
        ImageView img;

        public RunningHolder(@NonNull View itemView) {
            super(itemView);

            miles = itemView.findViewById(R.id.miles);
            duration = itemView.findViewById(R.id.duration);
            route = itemView.findViewById(R.id.route);
            event = itemView.findViewById(R.id.event);
            date = itemView.findViewById(R.id.date);
            img = itemView.findViewById(R.id.card_img);
        }
    }

}
