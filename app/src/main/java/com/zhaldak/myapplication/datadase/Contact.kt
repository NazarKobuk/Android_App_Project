package com.zhaldak.myapplication.datadase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "contacts"
)

/**
 * Contact instance that stores data about
 * Group, name, mail, phone
 * Comments, photos, Facebook page of a single copy
 */
 
class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "group_id")
    var groupId: Int,
    var name: String,
    var email: String? = null,
    var phone: String? = null,
    var comment: String? = null,
    var photoUrl: String? = null,
    var facebookId: String? = null
)