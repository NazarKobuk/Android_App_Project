package com.zhaldak.myapplication

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhaldak.myapplication.datadase.ContactGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contact_groups_fragment.*
import androidx.appcompat.app.AlertDialog
import com.zhaldak.myapplication.datadase.ContactGroupWithContacts
import kotlinx.android.synthetic.main.add_group_dialog.view.*


/**
 *
 *
 *
 */
class ContactGroupsFragment : Fragment() {

    companion object {
        fun newInstance() = ContactGroupsFragment()
    }

    private lateinit var viewModel: ContactGroupsViewModel

/**
 * override method for associating an xml-view model with
 * fragment class
 * @param inflater object to create a View instance from an xml file
 * @param container ViewGroup object for View view elements
 * @param savedInstanceState is the standard Bundle object for state storage after closing the program
 */
 
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.contact_groups_fragment, container, false)
    }

/**
 * override Activity handling method
 * Records contact information in name fields, etc.
 * @param savedInstanceState is a standard Bundle object
 * to save the state
 */

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContactGroupsViewModel::class.java)
        viewModel.requestGroups()

        recycler.layoutManager = LinearLayoutManager(context)
        viewModel.groups.observe(this, Observer<List<ContactGroupWithContacts>> {
            val mainActivity = activity as MainActivity
            val contactsViewModel = ViewModelProviders.of(mainActivity).get(ContactsViewModel::class.java)
            recycler.adapter = ContactGroupsAdapter(it, mainActivity, contactsViewModel)
        })

        activity?.fab?.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                createAndShowGroupDialog()
            }
        }



    }

/**
 *
 * The method reproduces a dialog object to create a group
 * contacts record group name, description, and background color for
 * display the group in the list
 *
 */
 
    private fun createAndShowGroupDialog() {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(R.string.add_contact_group)


        val dialogView = layoutInflater
            .inflate(R.layout.add_group_dialog, null)

        builder.setView(dialogView)
        builder.setPositiveButton(R.string.ok) { dialogInterface, _ ->
            val color = dialogView.colorPickerView.selectedColor
            val title = dialogView.editTitle.text.toString()
            val description = dialogView.editDescription.text.toString()

            val group = ContactGroup(name = title, description = description, color = color)
            viewModel.saveGroup(group)
            dialogInterface.dismiss()


        }
        builder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

}
