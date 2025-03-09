package vn.iostar.tuan6;

import vn.iostar.tuan6.databinding.ItemListUserBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.MyViewHolder> {
    private List<User> userList;
    private OnItemClickListener onItemClickListener; // Định nghĩa interface bên ngoài

    public ListUserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListUserBinding itemListUserBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_list_user, parent, false);
        return new MyViewHolder(itemListUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setBinding(userList.get(position), position);
        holder.setOnItemClickListener(onItemClickListener); // Truyền sự kiện click
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setOnItemClickListener(HomeActivity homeActivity) {
        this.onItemClickListener = homeActivity;
    }


    // Interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void itemClick(User user);
    }



    // ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ObservableField<String> stt = new ObservableField<>();
        public ObservableField<String> firstName = new ObservableField<>();
        public ObservableField<String> lastName = new ObservableField<>();
        private ItemListUserBinding itemListUserBinding;
        private OnItemClickListener onItemClickListener; // Interface

        private User user;

        public MyViewHolder(ItemListUserBinding itemView) {
            super(itemView.getRoot());
            this.itemListUserBinding = itemView;
            itemView.getRoot().setOnClickListener(this); // Bắt sự kiện click
        }

        public void setBinding(User user, int position) {
            if (itemListUserBinding.getViewHolder() == null) {
                itemListUserBinding.setViewHolder(this);
            }
            this.user = user;
            stt.set(String.valueOf(position));
            firstName.set(user.getFirstName());
            lastName.set(user.getLastName());
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.itemClick(user); // Gọi sự kiện click
            }
        }


        public void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }
    }

}
