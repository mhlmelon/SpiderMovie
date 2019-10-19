package com.eunke.client;

import com.eunke.mapper.MovieMapper;
import com.eunke.model.Movie;
import com.mysql.cj.util.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * 电影网电影列表页爬取内容清洗类
 */
public class MovieParse extends AbstractParse{

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public List<Movie> getData(String entity){
        /**
         * 读取mybatis配置文件
         */
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 得到连接对象注册sqlsession
         */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        MovieMapper movieMapper = sqlSession.getMapper(MovieMapper.class);

         List<Movie> data = new ArrayList<>();
         Document doc = Jsoup.parse(entity);
         Elements elements = doc.select("table.border1");
         Elements name =  elements.select("tr").select("td").select("a");

        Movie [] movies = new Movie[elements.size()];
        for (int i =0; i < movies.length; i++) {
            movies[i] = new Movie();
        }
        int i = 0;
        for (Element element : name) {
            // 获取详情链接
            for (Attribute attribute : element.attributes()) {
                if (attribute.getKey().equals("href")) {
                    movies[i].setTargetUrl(attribute.getValue());
                }
            }
            for (Node node : element.childNodes()) {

                for (Attribute attribute : node.attributes()) {
                    // 获取标题
                    if (attribute.getKey().equals("alt")) {
                        movies[i].setTitle(attribute.getValue());
                    }
                    // 获取图片链接
                    if (attribute.getKey().equals("src")) {
                        movies[i].setPicUrl(attribute.getValue());
                    }
                }
            }
            // 获取简介
            String introduction = element.parentNode().parentNode().parentNode().parentNode().parentNode().parentNode().parentNode().childNode(2).childNode(1).childNodes().toString();
            movies[i].setIntroduction(introduction);
            i+=1;
        }

        for (int k =0; k < movies.length; k++) {
             if (StringUtils.isEmptyOrWhitespaceOnly(movies[k].getTitle())) {
                 continue;
             }
            movieMapper.insert(movies[k]);
            sqlSession.commit();
            data.add(movies[k]);
        }

        return data;
    }
}
