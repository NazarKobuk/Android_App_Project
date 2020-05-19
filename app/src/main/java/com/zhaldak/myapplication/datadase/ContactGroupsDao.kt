package com.zhaldak.myapplication.datadase

import androidx.room.*

@Dao
interface ContactGroupsDao {

   @Query("SELECT * FROM groups")
    fun getAll() : List<ContactGroupWithContacts>

    @Insert
    fun insert(group: ContactGroup)

    @Update
    fun update(group: ContactGroup)

    @Delete
    fun delete(group: ContactGroup)
}