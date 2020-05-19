package com.zhaldak.myapplication

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.zhaldak.myapplication.datadase.AppDatabase
import com.zhaldak.myapplication.datadase.Contact
import com.zhaldak.myapplication.datadase.insertOrUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 *
 *The "AddOrEditContactViewModel" - class implements View-Model editing of contacts
 *
 **/

class AddOrEditContactViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {
    var selectedImageUri: Uri? = null
    private var database: AppDatabase = AppDatabase.getDatabase(app)
    var groupId: Int = 0
    var existingContact: Contact? = null
        set(value) {
            field = value
            value?.apply { groupId = value.groupId }
        }


    private var contactsDao = database.getContactsDao()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job: Job = Job()

/**
 *
 * The method stores the contact in the database
 *
 * @param contact object that stores information about contact
 *
 */
 
    fun saveContact(contact: Contact) {
        launch(Dispatchers.IO) {
            if (selectedImageUri != null) {
                contact.photoUrl = selectedImageUri.toString()
            }
            contactsDao.insertOrUpdate(contact)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

/**
 *
 * The method removes contact with the database
 *
 * @param item Contact object, stores information about contact
 */
    fun removeContact(item: Contact) {
        launch(Dispatchers.IO) {
            contactsDao.delete(item)
        }
    }
}
