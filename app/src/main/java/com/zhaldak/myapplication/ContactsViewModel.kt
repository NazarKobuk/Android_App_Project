package com.zhaldak.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zhaldak.myapplication.datadase.AppDatabase
import com.zhaldak.myapplication.datadase.Contact
import com.zhaldak.myapplication.datadase.ContactGroupWithContacts
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class ContactsViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {
    var contactGroup: ContactGroupWithContacts? = null


    private var database: AppDatabase = AppDatabase.getDatabase(app)
    private var contactsDao = database.getContactsDao()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job: Job = Job()

    suspend fun requestContacts(): LiveData<List<Contact>> {
        return withContext(Dispatchers.Default) {
            contactsDao.getContactsOfGroup(contactGroup?.group?.id ?: 0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
