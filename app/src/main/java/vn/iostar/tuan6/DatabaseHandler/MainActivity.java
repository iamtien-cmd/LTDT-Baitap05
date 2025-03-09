package vn.iostar.tuan6.DatabaseHandler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import vn.iostar.tuan6.R;

public class MainActivity extends AppCompatActivity {
    // Khai báo biến toàn cục
    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NoteAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        // Khởi tạo database
        InitDatabaseSQLite();
        createDatabaseSQLite();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Ánh xạ ListView
        listView = findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        adapter = new NoteAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);

        // Load dữ liệu từ database
        databaseSQLite();

    }

    private void InitDatabaseSQLite() {
        // Khởi tạo database
        databaseHandler = new DatabaseHandler(this);

        // Tạo bảng Notes nếu chưa có
        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes TEXT)");
    }

    private void createDatabaseSQLite() {
        // Thêm dữ liệu vào bảng
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 1')");
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 2')");
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        if (cursor.getCount() == 0) {
            Log.e("Database", "Không có dữ liệu trong bảng Notes!");
        } else {
            while (cursor.moveToNext()) {
                Log.d("Database", "ID: " + cursor.getInt(0) + ", Name: " + cursor.getString(1));
            }
        }
        cursor.close();
    }

    private void databaseSQLite() {
        // Xóa dữ liệu cũ trước khi load mới
        arrayList.clear();

        // Lấy dữ liệu từ database
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            arrayList.add(new NotesModel(id, name));
        }
        cursor.close();

        // Cập nhật ListView
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddNotes) {
            Log.d("DEBUG", "Menu Add Notes được chọn");
            DialogThem();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_notes);

        // Ánh xạ các thành phần trong Dialog
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonAdd = dialog.findViewById(R.id.buttonThem);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        // Xử lý sự kiện nút Thêm
        buttonAdd.setOnClickListener(v -> {
            String name = editText.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
            } else {
                databaseHandler.QueryData("INSERT INTO Notes VALUES(null, '" + name + "')");
                Toast.makeText(MainActivity.this, "Đã thêm Notes", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                databaseSQLite();
            }
        });
        // Xử lý sự kiện nút Hủy
        buttonHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void DialogCapNhatNotes(String name, int id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_notes);

        // Ánh xạ các thành phần trong Dialog
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonEdit = dialog.findViewById(R.id.buttonEdit);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        editText.setText(name);

        // Xử lý sự kiện cập nhật
        buttonEdit.setOnClickListener(v -> {
            String newName = editText.getText().toString().trim();
            if (!newName.isEmpty()) {
                databaseHandler.QueryData("UPDATE Notes SET NameNotes = '" + newName + "' WHERE Id = " + id);
                Toast.makeText(MainActivity.this, "Đã cập nhật Notes thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                databaseSQLite();
            } else {
                Toast.makeText(MainActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện hủy
        buttonHuy.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    public void DialogDelete(String name, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa Notes \"" + name + "\" này không?");

        builder.setPositiveButton("Có", (dialog, which) -> {
            databaseHandler.QueryData("DELETE FROM Notes WHERE Id = " + id);
            Toast.makeText(MainActivity.this, "Đã xóa Notes " + name + " thành công", Toast.LENGTH_SHORT).show();
            databaseSQLite();
        });

        builder.setNegativeButton("Không", null);
        builder.show();
    }
}
