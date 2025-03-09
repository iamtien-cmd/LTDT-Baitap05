package vn.iostar.tuan6.externalStorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import vn.iostar.tuan6.R;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 1;
    private EditText editText;
    private TextView showText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);

        editText = findViewById(R.id.editText);
        showText = findViewById(R.id.showText);
    }

    public void savePublic(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Toast.makeText(this, "Không thể ghi vào bộ nhớ ngoài trên Android 10+", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            return;
        }

        String info = editText.getText().toString();
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File myFile = new File(folder, "myData1.txt");

        writeData(myFile, info);
        editText.setText("");
    }

    public void savePrivate(View view) {
        String info = editText.getText().toString();
        File folder = getExternalFilesDir("Demo");
        File myFile = new File(folder, "myData2.txt");

        writeData(myFile, info);
        editText.setText("");
    }

    private void writeData(File file, String data) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes());
            Toast.makeText(this, "Lưu thành công: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi ghi file", Toast.LENGTH_SHORT).show();
        }
    }

    public void getPublic(View view) {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File myFile = new File(folder, "myData1.txt");

        String text = getData(myFile);
        showText.setText(text != null ? text : "Không có dữ liệu");
    }

    public void getPrivate(View view) {
        File folder = getExternalFilesDir("Demo");
        File myFile = new File(folder, "myData2.txt");

        String text = getData(myFile);
        showText.setText(text != null ? text : "Không có dữ liệu");
    }

    private String getData(File file) {
        if (!file.exists()) return null;

        StringBuilder buffer = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file)) {
            int i;
            while ((i = fis.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
