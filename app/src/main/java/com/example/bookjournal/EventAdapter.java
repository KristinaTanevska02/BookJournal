package com.example.bookjournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookjournal.Models.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Event> eventList;
    private DatabaseHelper dbHelper;



    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.place.setText("Место: "+event.getPlace());
        holder.date.setText("Датум: "+event.getDateTime().toString());
        holder.startDestination.setText("Дестинација: "+event.getLatStart()+" "+event.getLonStart());
        holder.buttonJoin.setVisibility(View.VISIBLE);

        boolean isEventAdded = dbHelper.isUserAlreadyParticipant(event.getId(), 2);
        if (isEventAdded) {
            holder.buttonJoin.setVisibility(View.GONE);
            holder.buttonJoined.setVisibility(View.VISIBLE);
        } else {
            holder.buttonJoin.setVisibility(View.VISIBLE);
            holder.buttonJoin.setOnClickListener(v -> {
                dbHelper.markEvent(2, event.getId());
                holder.buttonJoin.setVisibility(View.GONE);
                holder.buttonJoined.setVisibility(View.VISIBLE);
            });
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView place, date, startDestination;
        Button buttonJoin, buttonJoined;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            place = itemView.findViewById(R.id.place);
            date = itemView.findViewById(R.id.date);
            startDestination = itemView.findViewById(R.id.startDestination);
            buttonJoin = itemView.findViewById(R.id.buttonJoin);
            buttonJoined = itemView.findViewById(R.id.buttonJoined);
        }
    }
}
