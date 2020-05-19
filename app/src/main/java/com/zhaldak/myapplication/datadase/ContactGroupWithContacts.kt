package com.zhaldak.myapplication.datadase

import androidx.room.Embedded
import androidx.room.Relation

class ContactGroupWithContacts {
    @Embedded
    var group: ContactGroup? = null
    @Relation(parentColumn = "id", entityColumn = "group_id", entity = Contact::class)
    var contacts: List<Contact> = listOf()
}