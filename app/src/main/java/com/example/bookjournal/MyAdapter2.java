package com.example.bookjournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookjournal.Models.MyBooks;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {
    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;
    private final List<MyBooks> items;
    private final DatabaseHelper dbHelper;
    private final Integer loggedUserId;

    public MyAdapter2(Context context, List<MyBooks> items, Integer loggedUserId, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.items = items;
        this.loggedUserId = loggedUserId;
        this.recyclerViewInterface = recyclerViewInterface;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view2, parent, false);
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        MyBooks book = items.get(position);

        holder.titleAuthor.setText(book.getTitle() + " - " + book.getAuthor());
        holder.publisher.setText(book.getPublisher());
        holder.itemView.setTag(book);

        boolean isBookAlreadyFave = dbHelper.isBookAlreadyFave(book.getId(), loggedUserId);
        if (isBookAlreadyFave) {
            holder.addToFaveButton.setVisibility(View.GONE);
        } else {
            holder.addToFaveButton.setVisibility(View.VISIBLE);
            holder.addToFaveButton.setOnClickListener(v -> {
                dbHelper.setMyBookFave(loggedUserId, book.getId(), true);
                holder.addToFaveButton.setVisibility(View.GONE);
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Nested ViewHolder class
    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView titleAuthor, publisher;
        public Button addToFaveButton;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            titleAuthor = itemView.findViewById(R.id.titleAuthor2);
            publisher = itemView.findViewById(R.id.publisher2);
            addToFaveButton = itemView.findViewById(R.id.btnAddToFave);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}
