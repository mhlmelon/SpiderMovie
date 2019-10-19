package com.eunke.mapper;

import com.eunke.model.Movie;

import java.util.List;

public interface MovieMapper {
    Integer insert(Movie movie);
    void updateDownloadUrlById(Movie movie);

}
