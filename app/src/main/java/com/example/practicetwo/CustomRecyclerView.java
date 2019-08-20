package com.example.practicetwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.practicetwo.entity.Task;
import java.util.List;

public class CustomRecyclerView extends RecyclerView.Adapter<CustomRecyclerView.ViewHolder> {
    private List<Task> taskList;
    private Context context;

    public CustomRecyclerView(Context context, List<Task> taskList){
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
    public void onBindViewHolder(@NonNull final CustomRecyclerView.ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskTitleTV.setText(task.getTitle());
        holder.taskDescriptionTV.setText(task.getDescription());
        holder.menuImitationHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "onBindViewHolder", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopupMenu(CustomRecyclerView.ViewHolder holder) {
        PopupMenu popupMenu = new PopupMenu(context, holder.menuImitationHamburger);
        popupMenu.inflate(R.menu.recycler_view_item_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.editItem:
                        break;
                    case R.id.deleteItem:
                        break;
                    case R.id.favouriteItem:
                        break;
                }
                return false;
            }
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
            menuImitationHamburger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "menuImitationHamburger", Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "itemView onClick", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
