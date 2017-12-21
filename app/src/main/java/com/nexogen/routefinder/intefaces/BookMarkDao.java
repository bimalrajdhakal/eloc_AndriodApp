package com.nexogen.routefinder.intefaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nexogen.routefinder.databases.BookMarkTable;

import java.util.List;

/**
 * Created by nexogen on 19/12/17.
 */

@Dao
public interface BookMarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void addUser(BookMarkTable user);

    @Query("select * from BookMarkTable")
    List<BookMarkTable> getAllUser();


    @Query("select * from BookMarkTable where id = :userId")
    List<BookMarkTable> getUserById(long userId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(BookMarkTable user);

    @Query("delete from BookMarkTable")
    void removeAllUsers();
}
