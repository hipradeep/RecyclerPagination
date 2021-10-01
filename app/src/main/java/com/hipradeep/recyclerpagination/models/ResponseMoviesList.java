package com.hipradeep.recyclerpagination.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseMoviesList{

	@SerializedName("Response")
	private String response;

	@SerializedName("totalResults")
	private String totalResults;

	@SerializedName("Search")
	private List<MoviesData> search;

	public String getResponse(){
		return response;
	}

	public String getTotalResults(){
		return totalResults;
	}

	public List<MoviesData> getSearch(){
		return search;
	}
}