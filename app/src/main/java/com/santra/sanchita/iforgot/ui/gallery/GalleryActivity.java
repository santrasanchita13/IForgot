package com.santra.sanchita.iforgot.ui.gallery;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.BaseActivity;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sanchita on 22/3/18.
 */

public class GalleryActivity extends BaseActivity implements GalleryMvpView {

    @Inject
    GalleryMvpPresenter<GalleryMvpView> presenter;

    GalleryAdapter galleryAdapter;

    @BindView(R.id.coordinator_layout_gallery)
    ViewGroup container;

    @BindView(R.id.galleryRecyclerView)
    RecyclerView galleryRecyclerView;

    @BindView(R.id.tabFoundText)
    TextView tabFoundText;

    @BindView(R.id.tabSafeText)
    TextView tabSafeText;

    @BindView(R.id.searchEditText)
    EditText searchEditText;

    @BindView(R.id.setDurationButton)
    Button setDurationButton;

    PopupMenu popupMenu;

    List<GalleryItem> galleryItemList;

    String search = "";

    boolean safeTabSelected = true;

    Date startFilter = null;
    Date endFilter = null;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, GalleryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        if(savedInstanceState != null) {
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        hideActionBar();

        setUp();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();

        super.onDestroy();
    }

    @Override
    protected void setUp() {

        if(galleryItemList == null) {
            galleryItemList = new ArrayList<>();
        }

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null) {
                    search = s.toString();

                    if(safeTabSelected) {
                        presenter.getAllDates();
                    }
                    else {
                        presenter.getAllFoundItems();
                    }
                }
            }
        });

        popupMenu = new PopupMenu(GalleryActivity.this, setDurationButton);
        popupMenu.getMenu().add(101, 101, 101, "Today");
        popupMenu.getMenu().add(102, 102, 102, "This week");
        popupMenu.getMenu().add(103, 103, 103, "This month");
        popupMenu.getMenu().add(104, 104, 104, "This year");
        popupMenu.getMenu().add(105, 105, 105, "Set range");
        popupMenu.getMenu().add(106, 106, 106, "Clear filter");
        popupMenu.setOnMenuItemClickListener(item -> {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date today = (new Date(System.currentTimeMillis()));
            String date =  dateFormat.format(today);
            Calendar calendar = Calendar.getInstance();

            try {
                today = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

                switch (item.getItemId()) {
                    case 101:
                        startFilter = today;
                        endFilter = today;
                        break;
                    case 102:
                        endFilter = today;
                        calendar.setTime(today);
                        calendar.add(Calendar.WEEK_OF_YEAR, 1);
                        startFilter = calendar.getTime();
                        break;
                    case 103:
                        endFilter = today;
                        calendar.setTime(today);
                        calendar.add(Calendar.MONTH, 1);
                        startFilter = calendar.getTime();
                        break;
                    case 104:
                        endFilter = today;
                        calendar.setTime(today);
                        calendar.add(Calendar.YEAR, 1);
                        startFilter = calendar.getTime();
                        break;
                    case 105:
                        selectDateRange();
                        break;
                    case 106:
                        startFilter = null;
                        endFilter = null;
                        break;
                    default:
                        startFilter = null;
                        endFilter = null;
                        break;
                }

            if(safeTabSelected) {
                presenter.getAllDates();
            }
            else {
                presenter.getAllFoundItems();
            }

            return true;
        });

        presenter.getAllDates();
    }

    @Override
    public void allDates(List<String> dates) {
        if(dates != null && dates.size() > 0) {

            galleryItemList.clear();

            for (String date : dates) {
                if (date != null) {

                    if(startFilter != null && endFilter != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                        try {
                            Date thisDate = sdf.parse(date);

                            if(thisDate.equals(startFilter) || thisDate.equals(endFilter) || (thisDate.after(startFilter) && thisDate.before(endFilter))) {
                                if (search != null && !search.isEmpty()) {
                                    presenter.getImagesByDateAndSearch(date, search);
                                } else {
                                    presenter.getImagesByDate(date);
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        if (search != null && !search.isEmpty()) {
                            presenter.getImagesByDateAndSearch(date, search);
                        } else {
                            presenter.getImagesByDate(date);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void allFound(List<String> dates) {
        if(dates != null && dates.size() > 0) {

            galleryItemList.clear();

            for (String date : dates) {
                if (date != null) {
                    if(startFilter != null && endFilter != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                        try {
                            Date thisDate = sdf.parse(date);

                            if(thisDate.equals(startFilter) || thisDate.equals(endFilter) || (thisDate.after(startFilter) && thisDate.before(endFilter))) {
                                if (search != null && !search.isEmpty()) {
                                    presenter.getFoundImagesByDateAndSearch(date, search);
                                } else {
                                    presenter.getFoundImagesByDate(date);
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        if (search != null && !search.isEmpty()) {
                            presenter.getFoundImagesByDateAndSearch(date, search);
                        } else {
                            presenter.getFoundImagesByDate(date);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void imagesByDate(String date, List<SafeItem> safeItems) {
        if(safeItems != null && safeItems.size() > 0) {
            galleryItemList.add(0, new GalleryItem(date, safeItems));
        }

        if(galleryAdapter == null) {
            galleryAdapter = new GalleryAdapter(GalleryActivity.this, galleryItemList, container);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(GalleryActivity.this);
            galleryRecyclerView.setLayoutManager(mLayoutManager);
            galleryRecyclerView.setAdapter(galleryAdapter);
            galleryAdapter.notifyDataSetChanged();
        }
        else {
            galleryAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.tabSafeText)
    void tabSafeClick() {

        safeTabSelected = true;

        tabSafeText.setBackground(ContextCompat.getDrawable(GalleryActivity.this, R.drawable.round_rectangle_left_pressed));
        tabFoundText.setBackground(ContextCompat.getDrawable(GalleryActivity.this, R.drawable.round_rectangle_right));

        presenter.getAllDates();
    }

    @OnClick(R.id.tabFoundText)
    void tabFoundClick() {

        safeTabSelected = false;

        tabSafeText.setBackground(ContextCompat.getDrawable(GalleryActivity.this, R.drawable.round_rectangle_left));
        tabFoundText.setBackground(ContextCompat.getDrawable(GalleryActivity.this, R.drawable.round_rectangle_right_pressed));

        presenter.getAllFoundItems();
    }

    @OnClick(R.id.setDurationButton)
    void onFilterClick(View view) {
        popupMenu.show();
    }

    @Override
    public void selectDateRange() {
        DatePickerFragment date = new DatePickerFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));

        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack((view, year, month, dayOfMonth) -> {
            String monthString, dayString;

            if(month < 10) {
                monthString = "0" + (month + 1);
            }
            else {
                monthString = (month + 1) + "";
            }

            if(dayOfMonth < 10) {
                dayString = "0" + dayOfMonth;
            }
            else {
                dayString = "" + dayOfMonth;
            }

            String date1 = year + "-" + monthString + "-" + dayString;

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                startFilter = dateFormat.parse(date1);

                DatePickerFragment endDate = new DatePickerFragment();

                Bundle argsEnd = new Bundle();
                argsEnd.putInt("year", year);
                argsEnd.putInt("month", month);
                argsEnd.putInt("day", dayOfMonth);
                argsEnd.putString("toDate", year + "-" + monthString + "-" + dayString);

                endDate.setArguments(argsEnd);

                endDate.setCallBack((view1, year1, month1, dayOfMonth1) -> {
                    String monthString1, dayString1;

                    if(month1 < 10) {
                        monthString1 = "0" + (month1 + 1);
                    }
                    else {
                        monthString1 = (month1 + 1) + "";
                    }

                    if(dayOfMonth1 < 10) {
                        dayString1 = "0" + dayOfMonth1;
                    }
                    else {
                        dayString1 = "" + dayOfMonth1;
                    }

                    String date11 = year1 + "-" + monthString1 + "-" + dayString1;

                    try {
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        endFilter = dateFormat1.parse(date11);

                        if(safeTabSelected) {
                            presenter.getAllDates();
                        }
                        else {
                            presenter.getAllFoundItems();
                        }

                    } catch(ParseException e) {
                        e.printStackTrace();
                    }
                });

                endDate.show(getFragmentManager(), "Filter to?");

            } catch(ParseException e) {
                e.printStackTrace();
            }
        });
        date.show(getFragmentManager(), "Filter from?");
    }
}
