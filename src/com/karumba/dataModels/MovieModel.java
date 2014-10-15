package com.karumba.dataModels;

import android.graphics.Bitmap;

public class MovieModel {
	String title, imbID, type, year, poster,rated,released,director,writer,genre,plot,language;
	long id;

	
    
	public MovieModel (String title,String imbID,String type, String year, String poster,long id,String rated,String released,String director, String writer,String genre,String plot, String language){
		this.id = id;
		this.title = title;
		this.imbID = imbID;
		this.type = type;
		this.year = year;
		this.poster = poster;
		this.rated = rated;
		this.released = released;
		this.director = director;
		this.writer = writer;
		this.genre = genre;
		this.plot = plot;
		this.language = language;
		
		
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getYea(){
		return this.year;
	}
	
	public String getImbID(){
		return this.imbID;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getPoster(){
		return this.poster;
	}
	
	public String getRated(){
		return this.rated;
	}
	
	public String getReleased(){
		return this.released;
	}
	
	public String getDirector(){
		return this.director;
	}
	
	public String getWriter(){
		return this.writer;
	}
	
	public String getGenre(){
		return this.genre;
	}
	
	public String getPlot(){
		return this.plot;
	}
	
	public String getLanguage(){
		return this.language;
	}
	public long getMovieId(){
		return this.id;
	}
}

