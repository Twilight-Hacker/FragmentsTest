package task.example.galadar.fragmentTest;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        setContentView(R.layout.activity_details);

        String breed = getIntent().getStringExtra("index");

        Bundle bundle = new Bundle();
        bundle.putString("breed", breed);
        DetailsFragment details = new DetailsFragment();
        details.setArguments(getIntent().getExtras());

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.DetailsFragmentID, details);
        ft.commit();

    }
}