package edu.uiuc.cs427app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom Adaptor class to get the delete and detail buttons displaying alongside the city.
 */
public class CityCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private static InterfaceRemoveCity interfaceRemove;

    /**
     * Public constructor for CityCustomAdapter
     * @param list
     * @param context
     */
    public CityCustomAdapter(ArrayList<String> list, Context context, InterfaceRemoveCity interfaceR) {
        this.list = list;
        this.context = context;

        interfaceRemove = interfaceR;
    }

    /**
     * Returns size of the city list
     * @return int
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Returns the city string stored at the given position
     * @param pos
     * @return city String
     */
    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    /**
     * Does nothing - needed for interface override
     * @param pos
     * @return
     */
    @Override
    public long getItemId(int pos) {
        return 0;
        //return list.get(pos).getId();
    }

    /**
     * Returns the view for the given position in the city list.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.city_child, null);
        }

        //Handle TextView and displays the city string from the city list
        TextView cityName= (TextView)view.findViewById(R.id.cityName);
        cityName.setText(list.get(position));

        //Handle buttons and adds onClickListeners for each button
        Button deleteButton= (Button)view.findViewById(R.id.button_delete);
        Button detailsButton= (Button)view.findViewById(R.id.button_details);

        detailsButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Launches the details activity when the details button is clicked on this particular city
             */
            @Override
            public void onClick(View v) {
                //TODO: Launch the details activity where the weather and map data can be displayed

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Deletes this city when the delete button is clicked on this particular city
             * @param v
             */
            @Override
            public void onClick(View v) {
                //cityList.removeCity(cityName.getText());
                String city = cityName.getText().toString();
                interfaceRemove.removeCity(city);
                //notifyDataSetChanged();

            }
        });

        return view;
    }
}

