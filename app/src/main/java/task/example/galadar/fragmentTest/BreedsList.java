package task.example.galadar.fragmentTest;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BreedsList extends ListFragment {

    final static ArrayList<String> Dogs = MainActivity.getDogs();

    BreedLink link;

    public interface BreedLink{
        void Chosen(String breed);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Dogs);

        setListAdapter(myAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();

        try {
            link = (BreedLink) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement Chosen(String breed");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        link.Chosen(Dogs.get(position));
    }
}

