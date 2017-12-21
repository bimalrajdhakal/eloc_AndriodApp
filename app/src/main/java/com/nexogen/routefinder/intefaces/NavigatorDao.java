package com.nexogen.routefinder.intefaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nexogen.routefinder.databases.NavigatorTable;

import java.util.List;

/**
 * Created by nexogen on 19/12/17.
 */
@Dao
public interface NavigatorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void addUser(NavigatorTable user);

    @Query("select * from NavigatorTable")
    List<NavigatorTable> getAllUser();


    @Query("select * from NavigatorTable where id = :userId")
    List<NavigatorTable> getUserById(long userId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(NavigatorTable user);

    @Query("DELETE FROM " + "NavigatorTable" + " WHERE " + "id" + " = :id")
    int deleteById(long id);

    @Query("delete from NavigatorTable")
    void removeAllUsers();

}
