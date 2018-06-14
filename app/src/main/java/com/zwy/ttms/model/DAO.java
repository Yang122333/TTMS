package com.zwy.ttms.model;

import java.util.List;

/**
 * Created by ${Yang} on 2018/4/26.
 */

public interface DAO<T> {
    int insert(T t);
    void delete(String whereClause, String[] whereArgs);
    void update(String whereClause, String[] whereArgs, T t);
    List<T> queryAll();
    List<T> queryAction(String selection, String[] selectionArgs);

}
