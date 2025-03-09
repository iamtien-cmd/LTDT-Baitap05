package vn.iostar.tuan6.VD1DangNhap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.iostar.tuan6.R;

public class DangNhapActivity extends AppCompatActivity {
    // Khai báo biến toàn cục
    private Button buttonTxt;
    private EditText usernameTxt, passwordTxt;
    private CheckBox cbRememberMe;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap); // Sửa lại layout chính xác

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        // Ánh xạ các thành phần giao diện
        AnhXa();

        // Lấy giá trị SharedPreferences để hiển thị lên giao diện
        usernameTxt.setText(sharedPreferences.getString("taikhoan", ""));
        passwordTxt.setText(sharedPreferences.getString("matkhau", ""));
        cbRememberMe.setChecked(sharedPreferences.getBoolean("trangthai", false));

        // Đặt sự kiện click cho button đăng nhập
        buttonTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();

                if (username.equals("admin") && password.equals("admin")) {
                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (cbRememberMe.isChecked()) {
                        // Lưu thông tin đăng nhập
                        editor.putString("taikhoan", username);
                        editor.putString("matkhau", password);
                        editor.putBoolean("trangthai", true);
                    } else {
                        // Xóa thông tin đã lưu
                        editor.remove("taikhoan");
                        editor.remove("matkhau");
                        editor.remove("trangthai");
                    }
                    editor.apply(); // Lưu dữ liệu hiệu quả hơn

                } else {
                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        buttonTxt = findViewById(R.id.buttonTxt);
        usernameTxt = findViewById(R.id.usernameTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        cbRememberMe = findViewById(R.id.cbrememberme); // Đảm bảo đúng ID trong XML
    }
}
