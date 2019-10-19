package com.eunke.client;

import java.util.List;

/**
 * 爬取数据清洗抽象类
 */
public abstract class AbstractParse {

    public abstract Integer getId();
    public abstract List<?> getData(String entity);
}
