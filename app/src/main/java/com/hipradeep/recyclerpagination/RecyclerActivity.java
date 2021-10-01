package com.hipradeep.recyclerpagination;

import static com.hipradeep.recyclerpagination.adapters.PaginationScrollListener.PAGE_START;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.hipradeep.recyclerpagination.adapters.MoviesAdapter;
import com.hipradeep.recyclerpagination.adapters.PaginationScrollListener;
import com.hipradeep.recyclerpagination.models.MoviesData;
import com.hipradeep.recyclerpagination.models.ResponseMoviesList;
import com.hipradeep.recyclerpagination.retrofit.ApiServices;
import com.hipradeep.recyclerpagination.retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ApiServices apiServices;
    RecyclerView recyclerView;   SwipeRefreshLayout swipeRefresh;

    private List<MoviesData> list=new ArrayList<>();
    private MoviesAdapter adapter;

    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        apiServices = ServiceGenerator.createService(ApiServices.class);

        swipeRefresh=findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new MoviesAdapter(new ArrayList<>(), RecyclerActivity.this);
        recyclerView.setAdapter(adapter);
        getMoviesList("007",  PAGE_START);



        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                getMoviesList("007",  currentPage);
                Log.e("TAG", "Current Page : "+ currentPage);
            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });



    }

    private void getMoviesList(String searchKey, int page) {
        final List<MoviesData>[] list = new List[]{new ArrayList<>()};
        String mob="123";
        String url="?s="+mob+"&page="+page+"&apikey=5a4eac80";

        Call<ResponseMoviesList> call=apiServices.getMoviesList(url);
        call.enqueue(new Callback<ResponseMoviesList>() {
            @Override
            public void onResponse(Call<ResponseMoviesList> call, Response<ResponseMoviesList> response) {
                try {

                    if (response.body().getResponse().equalsIgnoreCase("True")){


                        if (currentPage != PAGE_START) adapter.removeLoading();

                        adapter.addItems(response.body().getSearch());
                        swipeRefresh.setRefreshing(false);

                        Log.e("TAG", response.body().getSearch().toString());

                        adapter.addLoading();

                        if (currentPage < totalPage) {
                           // adapter.addLoading();
                        } else {
                            isLastPage = true;
                        }
                        isLoading = false;

                    }else {
                        adapter.removeLoading();
                    }

                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseMoviesList> call, Throwable t) {
                Log.e("TAG", t.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        adapter.clear();
        getMoviesList("007",  currentPage);
    }
}