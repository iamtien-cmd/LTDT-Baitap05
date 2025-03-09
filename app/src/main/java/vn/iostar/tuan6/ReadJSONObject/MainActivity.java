package vn.iostar.tuan6.ReadJSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import vn.iostar.tuan6.R;

// Định nghĩa ReadJSONObject là một inner class trong MainActivity
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        // Gọi AsyncTask để lấy dữ liệu JSON
        new ReadJSONObject().execute("http://localhost:8090/data3.json");
    }

    private class ReadJSONObject extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object = new JSONObject(s);

                // Đọc JSON bình thường
                String name = object.getString("name");
                String desc = object.getString("desc");
                String pic = object.getString("pic");
                String kq = name + "\n" + desc + "\n" + pic;
                Toast.makeText(MainActivity.this, kq, Toast.LENGTH_SHORT).show();

                // Đọc mảng JSON
                JSONArray array = object.getJSONArray("monhoc");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object1 = array.getJSONObject(i);
                    String nameMonHoc = object1.getString("name");
                    String descMonHoc = object1.getString("desc");
                    String picMonHoc = object1.getString("pic");
                    String kqMonHoc = nameMonHoc + "\n" + descMonHoc + "\n" + picMonHoc;
                    Toast.makeText(MainActivity.this, kqMonHoc, Toast.LENGTH_SHORT).show();
                }

                // Đọc JSON có nested object
                JSONObject objectLang = object.getJSONObject("language");
                JSONObject objectVN = objectLang.getJSONObject("vn");
                String ten = objectVN.getString("ten");
                Toast.makeText(MainActivity.this, ten, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
