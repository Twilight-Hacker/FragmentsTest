package task.example.galadar.fragmentTest;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Loader extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loader);

        new HttpAsyncTask().execute("http://127.0.0.1");
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.test);
            int size = is.available();
            byte[] buffer = new byte[size];
            int a = is.read(buffer);
            json = new String(buffer, "UTF-8");
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String GET(String url){
        InputStream is = null;
        String result = "";
        int len;


        if(isConnected()){

            try {
                URL httpurl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) httpurl.openConnection();
                conn.setReadTimeout(15000); // milliseconds
                conn.setConnectTimeout(25000); // milliseconds
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();

                if(response!=200){ //If not successful, load test data
                    result += loadJSONFromAsset();
                } else {
                    len = conn.getContentLength();
                    is = conn.getInputStream();

                    // Convert the InputStream into a string
                    result += reader(is, len);
                }
            } catch (Exception e){  //Catch any kind of error and use test data instead
                result += loadJSONFromAsset();
                e.printStackTrace();
            } finally { //Whatever the result, close the input stream
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else { //if not connected return test data
            result += loadJSONFromAsset();
        }

        return result;  //return data
    }

    public String reader(InputStream stream, int len) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8"); //Assuming utf-8 character encoding
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<Dog>> {
        @Override
        protected ArrayList<Dog> doInBackground(String... urls) {
            String data = GET(urls[0]);

            if(data.isEmpty()) cancel(true);
            return parseJSON( data );
        }

        @Override
        protected void onCancelled() {
            /*
            If read fails completely, the app should exit
             */
            Loader.this.finish();
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(ArrayList<Dog> list) { //Launches Main Activity
            super.onPostExecute(list);

            Intent intent = new Intent(Loader.this, MainActivity.class);
            intent.putParcelableArrayListExtra("Dogs", list);
            startActivity(intent);
        }
    }


    private ArrayList<Dog> parseJSON(String JsonString){

        ArrayList<Dog> doglist = new ArrayList<>();

        try {
            JSONObject data = new JSONObject(JsonString);
            JSONArray foods = data.getJSONArray("food");
            HashMap foodlist = getFoodList(foods);
            String food;
            JSONArray dogsdata = data.getJSONArray("dogs");

            for (int i = 0; i < dogsdata.length(); i++) {
                Dog dog = new Dog();
                JSONObject oneDog= dogsdata.getJSONObject(i);

                dog.setImageLink(oneDog.getJSONObject("image").getString("xxhdpi"));
                dog.setColor(oneDog.getString("colour"));
                dog.setBreed(oneDog.getString("breed"));
                dog.setLength(oneDog.getInt("length"));
                dog.setSize(oneDog.getString("size"));
                dog.setHeight(oneDog.getInt("height"));
                food = oneDog.getString("prefered-food");
                dog.setFavFood(food, foodlist.get(food).toString());

                doglist.add(dog);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return doglist;
    }

    private String getServedString(String string, JSONArray foods) {
        String result = "";

        for (int i = 0; i <foods.length(); i++) {
            try {
                JSONObject meal = foods.getJSONObject(i);
                if(meal.getString("name").equalsIgnoreCase(string)){
                    result += meal.getString("package");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private HashMap<String, String> getFoodList(JSONArray foods){
        HashMap<String, String> list = new HashMap();

        for (int i = 0; i < foods.length(); i++) {
            JSONObject food = null;
            try {
                food = foods.getJSONObject(i);
                list.put(food.getString("name"), food.getString("package"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return list;
    }


}
