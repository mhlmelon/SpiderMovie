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
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * 电影网电影详情页爬取内容清洗类
 */
public class MovieDownloadParse extends AbstractParse{
    private Integer id;
    public MovieDownloadParse (Integer id) {
        this.id = id;
    }
    @Override
    public Integer getId() {
        return this.id;
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
         Elements elements = doc.select("a");


         StringBuffer downloadUrl = new StringBuffer();
         boolean ciliFound = false;
        boolean dianlvFound = false;
         boolean found = false;
        for (Element element : elements) {
            // 获取详情链接
            for (Attribute attribute : element.attributes()) {
                if (!ciliFound && attribute.getKey().equals("href") && (attribute.getValue().startsWith("magnet:"))) {
                    downloadUrl.append("磁力: " + attribute.getValue() + "\r\n");
                    ciliFound = true;
                }
                if (!dianlvFound && attribute.getKey().equals("href") && (attribute.getValue().startsWith("magnet:") || attribute.getValue().startsWith("ed2k:"))) {
                    downloadUrl.append("电驴: " + attribute.getValue() + "\r\n");
                    dianlvFound = true;
                }
                if (ciliFound && dianlvFound) {
                    break;
                }
            }
        }
        if (!StringUtils.isEmptyOrWhitespaceOnly(downloadUrl.toString())) {
            // 更新下载链接
            Movie movie = new Movie();
            movie.setId(this.getId());
            movie.setDownloadUrl(downloadUrl.toString());
            movieMapper.updateDownloadUrlById(movie);
            sqlSession.commit();
        }



        return data;
    }
}
