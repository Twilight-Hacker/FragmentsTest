package task.example.galadar.fragmentTest;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BreedsList.BreedLink{

    public static boolean isDual;
    public static ArrayList<Dog> doglist;
    int screenWidthDp; //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
    int screenHeightDp; //The current height of the available screen space, in dp units, corresponding to screen width resource qualifier.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        doglist = getIntent().getParcelableArrayListExtra("Dogs");

        if(doglist ==null){
            finish();
        }

        Configuration configuration = this.getResources().getConfiguration();
        screenWidthDp = configuration.screenWidthDp;
        screenHeightDp = configuration.screenHeightDp;

        setContentView(R.layout.activity_main);

        FrameLayout DetailsView = (FrameLayout)findViewById(R.id.DetailsFragmentID);
        isDual = DetailsView != null && DetailsView.getVisibility() == View.VISIBLE;

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if(isDual) {
            LinearLayout layout = (LinearLayout)findViewById(R.id.FullLayout);
            layout.setWeightSum(3.0f);
            String breed = doglist.get(0).getBreed();
            Bundle bundle = new Bundle();
            Dog animal = getDogObj(breed);
            bundle.putString("breed", animal.getBreed());
            bundle.putInt("length", animal.getLength());
            bundle.putString("imagelink", animal.getImageLink());
            bundle.putString("favfood", animal.getFavFood());
            bundle.putString("served", animal.getServed());
            bundle.putString("color", animal.getColor());
            bundle.putString("size", animal.getSize());
            bundle.putInt("height", animal.getHeight());
            bundle.putString("breed", breed);
            DetailsFragment details = new DetailsFragment();
            details.setArguments(bundle);
            ft.add(R.id.DetailsFragmentID, details);
            ft.commit();
            ft = getFragmentManager().beginTransaction();
        }

        ft.add(R.id.ListFragmentID, new BreedsList());
        ft.commit();
    }

    public static ArrayList<String> getDogs(){
        ArrayList<String> names = new ArrayList<>();
        for (Dog dog: doglist) {
            names.add(dog.getBreed());
        }
        return names;
    }

    public static Dog getDogObj(String breed){
        for (int i = 0; i < doglist.size(); i++) {
            Dog dog = doglist.get(i);
            if(dog.getBreed().equalsIgnoreCase(breed))return dog;
        }
        return doglist.get(0);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void Chosen(String breed) {
        if(isDual){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            Dog animal = getDogObj(breed);
            bundle.putString("breed", animal.getBreed());
            bundle.putInt("length", animal.getLength());
            bundle.putString("imagelink", animal.getImageLink());
            bundle.putString("favfood", animal.getFavFood());
            bundle.putString("served", animal.getServed());
            bundle.putString("color", animal.getColor());
            bundle.putString("size", animal.getSize());
            bundle.putInt("height", animal.getHeight());
            DetailsFragment details = new DetailsFragment();
            details.setArguments(bundle);
            ft.replace(R.id.DetailsFragmentID, details);
            ft.commit();
        } else {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            Dog animal = getDogObj(breed);
            Bundle bundle = new Bundle();
            bundle.putString("breed", animal.getBreed());
            bundle.putInt("length", animal.getLength());
            bundle.putString("imagelink", animal.getImageLink());
            bundle.putString("favfood", animal.getFavFood());
            bundle.putString("served", animal.getServed());
            bundle.putString("color", animal.getColor());
            bundle.putString("size", animal.getSize());
            bundle.putInt("height", animal.getHeight());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
