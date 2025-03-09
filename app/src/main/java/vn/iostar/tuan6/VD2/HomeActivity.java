package vn.iostar.tuan6.VD2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.iostar.tuan6.R;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kiểm tra trạng thái đăng nhập
        PrefManager prefManager = new PrefManager(this);
        if (!prefManager.isUserLoggedIn()) {  // Nếu chưa đăng nhập
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng HomeActivity để tránh quay lại bằng nút Back
            return;
        }

        setContentView(R.layout.activity_vd2_home);

        welcomeText = findViewById(R.id.welcome_text);
        logoutButton = findViewById(R.id.logout_button);

        // Lấy email từ PrefManager để hiển thị
        String email = prefManager.getEmail();
        welcomeText.setText(email != null ? "Chào mừng, " + email + "!" : "Chào mừng!");

        // Xử lý sự kiện đăng xuất
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.isUserLoggedOut(); // Đánh dấu là đã đăng xuất
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
