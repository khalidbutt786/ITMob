package com.example.itmob;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Community#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Community extends Fragment {

    Date date ;
    String str_date;
    TextView tvDate ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Community() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Community.
     */
    // TODO: Rename and change types and number of parameters
    public static Community newInstance(String param1, String param2) {
        Community fragment = new Community();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_courses);
        tvDate = view.findViewById(R.id.date_CoursesFrag);

        HomeActivity activity = (HomeActivity) getActivity();
        String email = activity.getUsername();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DBHelper dbHelper = new DBHelper(getContext());


        // getting today's Date

        date = new Date();
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        str_date = inFormat.format(date);

        tvDate.setText("Heute "+str_date);
        int day = date.getDay();    // getting Day

        List<ModelCourses> list = dbHelper.getCoursesByDate(str_date);    // getting courses of particular Date


        recyclerView.setAdapter(new AdapterCourses(list,getContext(), new OnClick() {

            // Interface implemented here to get the click listener on the course


            @Override
            public void onClick(int position, ModelCourses modelCourses) {

                Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext() , ParticipateActivity.class);
                intent.putExtra("modelObject", (Serializable) modelCourses);
                intent.putExtra("day",day);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        }));





        // setting calendar to show previous and next dates i.e showing 3 months here



        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);    // starting date previous month

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);         // ending date of next month



        // Initializing horizontal Calendar
        // Used Library by Mulham-Raee

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {

                Date myDate =  date.getTime();
                SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
                String bc = inFormat.format(myDate);
                int day = myDate.getDay();

                List<ModelCourses> list = dbHelper.getCoursesByDate(bc);             // getting courses by date


                recyclerView.setAdapter(new AdapterCourses(list,getContext(), new OnClick() {
                    @Override
                    public void onClick(int position, ModelCourses modelCourses) {

                        Intent intent = new Intent(getContext() , ParticipateActivity.class);
                        intent.putExtra("modelObject", (Serializable) modelCourses);
                        intent.putExtra("day",day);
                        intent.putExtra("email",email);
                        startActivity(intent);

                    }
                }));





                tvDate.setText("Heute"+bc);

            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {

            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });

    }
}