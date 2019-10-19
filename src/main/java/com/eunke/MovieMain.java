package com.eunke;

import com.eunke.client.MovieDownloadParse;
import com.eunke.client.MovieParse;
import com.eunke.client.URLHandle;
import com.eunke.model.Movie;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class MovieMain {

    public static void main(String[] args) {

        HttpClient client = null;
        for (int pagesize = 1; pagesize <= 1000; pagesize++) {
            String url = "http://www.dygang.com/ys/index.htm";
            if (pagesize > 1) {
                // 电影网第一页的链接有点不同
                url = MessageFormat.format("http://www.dygang.com/ys/index_{0}.htm", pagesize);
            }
            System.err.println("正在爬取当前第" + pagesize + "页数据...");
            System.out.println(url);
            List movieList = null;
            client = HttpClientBuilder.create().build();

            //开始解析
            try {
                try {
                    Thread.sleep(200);
                    // 获取电影基本信息
                    movieList = URLHandle.urlParser(client, url, new MovieParse());
                    // 获取电影下载链接
                    for (Object object : movieList) {
                        URLHandle.urlParser(client, ((Movie) object).getTargetUrl(), new MovieDownloadParse(((Movie) object).getId()));
                    }

                    // 判断是否已爬取完毕
                    if (movieList == null || movieList.size() == 0) {
                        pagesize = 1;
                        break;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("开始输出结果...");

            for (Object object : movieList) {
                Movie movie = (Movie) object;
                System.out.println(movie.getTitle());

            }
            pagesize += 1;
        }
    }

}
