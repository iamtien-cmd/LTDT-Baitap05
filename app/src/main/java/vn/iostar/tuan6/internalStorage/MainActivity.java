package vn.iostar.tuan6.internalStorage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import vn.iostar.tuan6.R;

public class MainActivity extends AppCompatActivity { // Kế thừa AppCompatActivity
    EditText editname, editpass, getname, getpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internalstorage);

        editname = findViewById(R.id.editname);
        editpass = findViewById(R.id.editpass);
        getname = findViewById(R.id.getname); // Thêm EditText hiển thị dữ liệu đọc từ file
        getpass = findViewById(R.id.getpass);
    }

    public void saveMe(View view) { // Hàm xử lý lưu file
        File file;
        String name = editname.getText().toString();
        String password = editpass.getText().toString();
        FileOutputStream fileOutputStream = null;
        try {
            name = name + " ";
            file = getFilesDir(); // Lấy thư mục lưu file nội bộ
            fileOutputStream = openFileOutput("Code.txt", MODE_PRIVATE);
            fileOutputStream.write(name.getBytes());
            fileOutputStream.write(password.getBytes());

            Toast.makeText(this, "Saved at: " + file + "/Code.txt", Toast.LENGTH_LONG).show();
            editname.setText("");
            editpass.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadMe(View view) { // Hàm đọc file
        try {
            FileInputStream fileInputStream = openFileInput("Code.txt");
            int read;
            StringBuilder buffer = new StringBuilder();
            while ((read = fileInputStream.read()) != -1) {
                buffer.append((char) read);
            }
            fileInputStream.close();

            Log.d("Code", buffer.toString()); // Log dữ liệu đọc được
            String data = buffer.toString();

            if (data.contains(" ")) {
                String name = data.substring(0, data.indexOf(" "));
                String pass = data.substring(data.indexOf(" ") + 1);

                getname.setText(name);
                getpass.setText(pass);
            } else {
                Toast.makeText(this, "File không có dữ liệu hợp lệ!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Không thể đọc file!", Toast.LENGTH_SHORT).show();
        }
    }
}
