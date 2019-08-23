package com.example.practicetwo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.BundleCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.main.MainContract;
import java.util.List;

public class CustomRecyclerView extends RecyclerView.Adapter<CustomRecyclerView.ViewHolder> {
    private List<Task> taskList;
    private Context context;
    MainContract.Presenter presenter;

    public CustomRecyclerView(Context context, MainContract.Presenter presenter, List<Task> taskList){
        this.presenter = presenter;
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public CustomRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomRecyclerView.ViewHolder holder, final int position) {
        Task task = taskList.get(position);
        holder.taskTitleTV.setText(task.getTitle());
        holder.taskDescriptionTV.setText(task.getDescription());
        holder.menuImitationHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder);
            }
        });
    }

    private void showPopupMenu(CustomRecyclerView.ViewHolder holder) {
        PopupMenu popupMenu = new PopupMenu(context, holder.menuImitationHamburger);
        popupMenu.inflate(R.menu.menu_recycler_view_item);

        Task task = taskList.get(holder.getAdapterPosition());
        popupMenu.getMenu().findItem(R.id.favouriteItem)
                .setTitle(task.isFavourite() ? R.string.ordinaryPopupText : R.string.favouritePopupText);

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.editItem:
                    presenter.showEditActivity(task);
                    break;
                case R.id.deleteItem:
                    presenter.deleteTask(task);
                    break;
                case R.id.favouriteItem:
                    presenter.changeFavourite(task);
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView taskTitleTV;
        public TextView taskDescriptionTV;
        public TextView menuImitationHamburger;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitleTV = itemView.findViewById(R.id.taskTitleTV);
            taskDescriptionTV = itemView.findViewById(R.id.taskDescriptionTV);
            menuImitationHamburger = itemView.findViewById(R.id.menuImitationHamburger);
        }
    }
}
