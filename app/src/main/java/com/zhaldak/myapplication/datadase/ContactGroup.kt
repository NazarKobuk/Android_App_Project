package com.zhaldak.myapplication.datadase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhaldak.myapplication.R

@Entity(tableName = "groups")

/**
 * ContactGroup is an instance that stores data about
 * Id, name, description and colors of the group in the list
 */
 
class ContactGroup(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var description: String? = null,
    var color: Int = R.color.colorPrimary
)