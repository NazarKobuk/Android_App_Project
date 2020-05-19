package com.zhaldak.myapplication


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zhaldak.myapplication.datadase.AppDatabase
import com.zhaldak.myapplication.datadase.ContactGroup
import com.zhaldak.myapplication.datadase.ContactGroupWithContacts
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * 
 * The class is responsible viewing 
 * editing groups of users and interacting with them
 *
 */
 
class ContactGroupsViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {
    var groups: MutableLiveData<List<ContactGroupWithContacts>> = MutableLiveData()
    private var database: AppDatabase = AppDatabase.getDatabase(app)

    private var groupsDao = database.getGroupsDao()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private  val job: Job = Job()

	
    fun requestGroups() {
        launch(Dispatchers.Main) {
            groups.value = withContext(Dispatchers.Default) {
                groupsDao.getAll()
            }
        }
    }

/**
 *
 * Storing the group in the database
 *
 * @param group Contact group object
 *
 */

    fun saveGroup(group: ContactGroup) {
        launch(Dispatchers.Default) {
            groupsDao.insert(group)
        }
        requestGroups()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
