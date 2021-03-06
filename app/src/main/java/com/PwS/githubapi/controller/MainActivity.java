package com.PwS.githubapi.controller;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.PwS.githubapi.ItemAdapter;
import com.PwS.githubapi.R;
import com.PwS.githubapi.api.Client;
import com.PwS.githubapi.api.Service;
import com.PwS.githubapi.model.Item;
import com.PwS.githubapi.model.Users;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PwS
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    TextView Disconnected;
    private RelativeLayout emptyView;
    private TextView errorTitle, errorMessage;
    private ProgressBar progressBar;




    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        swipeContainer = findViewById(R.id.swipeContainer);

        //ConfigureRefreshing
        swipeContainer.setOnRefreshListener(() -> {
            loadJson();
            Toast.makeText(MainActivity.this, "Github User Refreshed", Toast.LENGTH_SHORT).show();
        });

    }

    //First Method while Trying connect api
    private void initViews() {
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Github User");
        pd.setCancelable(false);
        pd.show();
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJson();
    }


    private void loadJson() {
        Disconnected = findViewById(R.id.disconnected);
        progressBar = findViewById(R.id.progressBar);
        //Try Hit Service
        try {
            new Client();
            Service apiService = Client.build().create(Service.class);
            Call<List<Item>> call = apiService.getItems();

            call.enqueue(new Callback<List<Item>>() {
                @Override
                public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                    try {
                        List<Item> items = response.body();
                        recyclerView.setAdapter(new ItemAdapter(items, getApplicationContext()));
                        recyclerView.smoothScrollToPosition(0);
                        swipeContainer.setRefreshing(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        Disconnected.setVisibility(View.INVISIBLE);
                        pd.hide();
                    } catch (Exception e) {
                        Log.d("Failed Load Data", Objects.requireNonNull(e.getMessage()));
                        Toast.makeText(MainActivity.this, "Failed Load Data ", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(MainActivity.this, "Error Fetching Data", Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    pd.hide();
                }
            });

        } catch (Exception e) {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void searchJson(final String keyword,int perPage) {
        Disconnected = findViewById(R.id.disconnected);
        progressBar = findViewById(R.id.progressBar);
        //Try Hit Service
        try {
            new Client();
            Service apiService = Client.build().create(Service.class);
            Call<Users> call = apiService.getSearchUser(keyword,perPage);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                    try {
                        List<Item> users = response.body().getItems();
                        int totalCount = response.body().getTotalCount();
                        if (!response.isSuccessful() || response.body().getItems() == null || totalCount == 0) {
                            userListFailure("No Result for '" + keyword + "'", "Try Searching for Other Users");
                        } else {
                            recyclerView.setAdapter(new ItemAdapter(users, getApplicationContext()));
                            recyclerView.smoothScrollToPosition(0);
                            swipeContainer.setRefreshing(false);
                            Disconnected.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            pd.hide();
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Failed While Searching , Please Close Application " +
                                        "and Try Again. "
                                , Toast.LENGTH_LONG).show();
                        Log.d("Error Response Search", Objects.requireNonNull(e.getMessage()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Error Find " + keyword, Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    pd.hide();
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                }
            });

        } catch (Exception e) {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.searchIcon);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchIcon).getActionView();
        searchView.setQueryHint("Search Github Users");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                /*if (users != null) {
                    users.clear();
                }
                errorView(View.VISIBLE, "Octocat", "Search Github Users");
                return false;*/
                loadJson();
                return true;
            }
        });

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int perPage=100;
                searchJson(query,perPage);
                Toast.makeText(MainActivity.this, "Display Top 100 Data"
                        , Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }


   /* private void observeEndlessScrolling() {
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Utilities.isNetworkConnected(MainActivity.this)) {
                    viewModel.fetchMoreImages(binding.svImageKey.getQuery().toString(), page);
                } else {
                    showToast(MainActivity.this, getString(R.string.internet_error));
                }
            }
        };
        binding.rvImage.addOnScrollListener(scrollListener);
    }*/


    public void userListFailure(@NonNull String errorMessage, @NonNull String keyword) {
        errorView(View.VISIBLE, errorMessage, keyword);
    }

    private void errorView(int visibility, String title, String message) {
        emptyView = findViewById(R.id.empty_view);
        emptyView.setVisibility(visibility);
        errorTitle = findViewById(R.id.errorTitle);
        errorTitle.setText(title);
        errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setText(message);
    }

    //Back
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Quit Application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", (arg0, arg1) -> finish())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }


}
