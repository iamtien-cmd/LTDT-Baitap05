package vn.iostar.tuan6;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import vn.iostar.tuan6.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements ListUserAdapter.OnItemClickListener {
    private ObservableField<String> title = new ObservableField<>();
    private ListUserAdapter listUserAdapter;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.setHome(this);
        title.set("Ví dụ về DataBinding cho RecycleView");
        setData();
        binding.rcView.setLayoutManager(new LinearLayoutManager(this));
        binding.rcView.setAdapter(listUserAdapter);
    }
    public ObservableField<String> getScreenTitle()  {
        return title;
    }


    private void setData() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setFirstName("Huệ " + i);
            user.setLastName("Tiên " + i);
            userList.add(user);
        }
        listUserAdapter = new ListUserAdapter(userList);
        listUserAdapter.setOnItemClickListener(this); // Cần gọi để xử lý click
        binding.rcView.setAdapter(listUserAdapter); // Cần đặt lại Adapter sau khi có dữ liệu

    }

    @Override
    public void itemClick(User user){
        Toast.makeText(this,"bạn vừa click",Toast.LENGTH_SHORT).show();
    }
}
