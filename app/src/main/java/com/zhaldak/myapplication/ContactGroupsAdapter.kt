package com.zhaldak.myapplication

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zhaldak.myapplication.datadase.ContactGroupWithContacts
import kotlinx.android.synthetic.main.contact_group_card.view.*

class ContactGroupsAdapter(private val items: List<ContactGroupWithContacts>,
                           val context: Context,
                           private val contactsViewModel: ContactsViewModel) :
    RecyclerView.Adapter<GroupsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        return GroupsViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.contact_group_card, parent, false))
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        val groupWithContacts = items[position]
        val group = groupWithContacts.group!!

        holder.titleTxt?.text = group.name
        holder.descriptionTxt?.text = group.description
        val hexColor = "#${java.lang.Integer.toHexString(group.color)}"
        holder.card.setCardBackgroundColor(Color.parseColor(hexColor))

        holder.countTxt.text = "${groupWithContacts.contacts.size}"

        holder.rootLayout.setOnClickListener {
            contactsViewModel.contactGroup = groupWithContacts
            val navController = it.findNavController()
            navController.navigate(R.id.contactsFragment)
        }

    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int = items.size

}

class GroupsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val titleTxt = view.titleTxt
    val descriptionTxt = view.descriptionTxt
    val card = view.card
    val countTxt = view.countTxt
    val rootLayout = view.rootLayout
}