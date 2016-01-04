package task.example.galadar.fragmentTest;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    public String getShownIndex() {
        return getArguments().getString("index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) { //In case the device rotates while app running
            return null;
        }

        Configuration configuration = this.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp; //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        int screenHeightDp = configuration.screenHeightDp; //The current height of the available screen space, in dp units, corresponding to screen width resource qualifier.
        
        Bundle bundle = getArguments();
        
        String breed, imagelink, favfood, served, color, size;
        int length, height;

        breed=bundle.getString("breed", "Invalid Selection");
        length=bundle.getInt("length", 0);
        imagelink=bundle.getString("imagelink", "Invalid Selection");
        favfood=bundle.getString("favfood", "Invalid Selection");
        served=bundle.getString("served", "Invalid Selection");
        color=bundle.getString("color", "#000000"); //Black color if error in getting it
        size=bundle.getString("size", "Invalid Selection");
        height=bundle.getInt("height", 0);


        View parent = inflater.inflate(R.layout.fragment_details, container, false);

        TextView breedView = (TextView)parent.findViewById(R.id.breedDt);
        breedView.setText(breed);
        ImageView colour = (ImageView)parent.findViewById(R.id.colourDt);
        try {
            colour.setBackgroundColor(Color.parseColor(color.toUpperCase()));
        } catch (IllegalArgumentException e){
            colour.setBackgroundColor(Color.parseColor("#FFFFFF")); //If the colour string given was wrong, default to White colour
        }
        TextView sizeT = (TextView)parent.findViewById(R.id.sizeDt);
        sizeT.setText(size);
        TextView lengthT = (TextView)parent.findViewById(R.id.lengthDt);
        lengthT.setText(Integer.toString(length));
        TextView heightT = (TextView)parent.findViewById(R.id.heightDt);
        heightT.setText(Integer.toString(height));
        TextView food = (TextView)parent.findViewById(R.id.foodDt);
        food.setText(favfood);
        TextView servedT = (TextView)parent.findViewById(R.id.servedDt);
        servedT.setText(served);

        int id = getResources().getIdentifier("task.example.galadar.fragmentTest:raw/" + imagelink, null, null);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidthDp/2, screenHeightDp/2);
        ImageView iv = (ImageView)parent.findViewById(R.id.imageView);
        try {
            Drawable res = getActivity().getDrawable(id);
            iv.setImageDrawable(res);
        } catch (Exception e){
            iv.setImageDrawable(getActivity().getDrawable(R.drawable.notfound));
        }
        iv.setLayoutParams(layoutParams);

        return parent;
    }

}
