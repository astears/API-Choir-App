package org.androidtown.choir;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by astears on 11/27/17.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private static final String TAG = CalendarAdapter.class.getSimpleName();
    private int mNumberItems;
    private ArrayList<Date> mDates;
    List<Uniform> universityList = new ArrayList<>();

    final private CalendarAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }



    /**
     * Constructor for SongAdapter that accepts a number of items to display and the specification
     * for the ListItemClickListener.
     *
     * @param numberOfItems Number of items to display in list
     */
    public CalendarAdapter(int numberOfItems, ArrayList<Date> dates, CalendarAdapter.ListItemClickListener listener) {
        mDates = dates;
        mOnClickListener = listener;
        mNumberItems = numberOfItems;

    }
    /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    @Override
    public CalendarAdapter.CalendarViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.calendar_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        CalendarAdapter.CalendarViewHolder viewHolder = new CalendarAdapter.CalendarViewHolder(view);

        return viewHolder;
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CalendarAdapter.CalendarViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(mDates.get(position), position);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {

        return mNumberItems;
    }

    /**
     * Cache of the children views for a list item.
     */
    class CalendarViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listDayView;
        TextView listDayOfWeekView;
        TextView listMenUniform;
        TextView listMen;
        TextView listWomenUniform;
        TextView listWomen;


        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         * @param itemView The View that you inflated in
         *                 {@link CalendarAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public CalendarViewHolder(View itemView) {
            super(itemView);

            listDayView = (TextView) itemView.findViewById(R.id.tv_item_day);
            listDayOfWeekView = (TextView) itemView.findViewById(R.id.tv_item_day_of_week);
            listMenUniform = (TextView) itemView.findViewById(R.id.tv_item_men_uniform);
            listMen = (TextView) itemView.findViewById(R.id.tv_item_men);
            listWomenUniform = (TextView) itemView.findViewById(R.id.tv_item_women_uniform);
            listWomen = (TextView) itemView.findViewById(R.id.tv_item_women);
            itemView.setOnClickListener(this);
        }

        /**
         * A method we wrote for convenience. This method will take an integer as input and
         * use that integer to display the appropriate text within a list item.
         * @param d Position of the item in the list
         */
        void bind(Date d, final int position) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("d");
            String day = dateFormat.format(d).toString();
            dateFormat = new SimpleDateFormat("E");
            String weekDay = dateFormat.format(d).toString();
            dateFormat = new SimpleDateFormat("M");
            String month = dateFormat.format(d).toString();

            listDayView.setText(month + "/" + day);
            listDayOfWeekView.setText(weekDay);
            listMen.setText("Men's Uniform");
            //listMenUniform.setText("All Black");
            listWomen.setText("Women's Uniform");
            //listWomenUniform.setText("All Black");

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Uniforms");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    int i = 0;
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        Uniform university = postSnapshot.getValue(Uniform.class);
                        universityList.add(university);

                            if (i == position) {
                                listMenUniform.setText(university.getMensUniform());
                                listWomenUniform.setText(university.getWomensUniform());
                                Log.i("ds", String.valueOf(i) + " " + String.valueOf(position));
                            } else {

                            }
                            i++;
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: ");
                }
            });

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

}
