package com.example.practicetwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.practicetwo.entity.Task;
import java.util.List;

public class CustomRecyclerView extends RecyclerView.Adapter<CustomRecyclerView.ViewHolder> {
    private List<Task> tasksList;
    private final Context context;
    private final TaskContract.TaskPresenter taskPresenter;

    public CustomRecyclerView(Context context, TaskContract.TaskPresenter taskPresenter, List<Task> tasksList){
        this.taskPresenter = taskPresenter;
        this.context = context;
        this.tasksList = tasksList;
    }

    @NonNull
    @Override
    public CustomRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomRecyclerView.ViewHolder holder, final int position) {
        Task task = tasksList.get(position);
        holder.taskTitleTV.setText(task.getTitle());
        holder.taskDescriptionTV.setText(task.getDescription());
        holder.menuImitationHamburger.setOnClickListener(view -> showPopupMenu(holder));
    }

    public void setData(List<Task> tasksList){
        this.tasksList = tasksList;
    }

    private void showPopupMenu(CustomRecyclerView.ViewHolder holder) {
        PopupMenu popupMenu = new PopupMenu(context, holder.menuImitationHamburger);
        popupMenu.inflate(R.menu.menu_recycler_view_item);

        Task task = tasksList.get(holder.getAdapterPosition());
        popupMenu.getMenu().findItem(R.id.favouriteItem)
                .setTitle(task.isFavourite() ? R.string.ordinaryPopupText : R.string.favouritePopupText);

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.editItem:
                    taskPresenter.showEditActivity(task);
                    return true;
                case R.id.deleteItem:
                    taskPresenter.deleteTask(task.getId());
                    return true;
                case R.id.favouriteItem:
                    taskPresenter.changeFavourite(task.getId());
                    return true;
            }
            return false;
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
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
