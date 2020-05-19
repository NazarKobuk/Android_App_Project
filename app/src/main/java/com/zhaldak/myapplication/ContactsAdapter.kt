package com.zhaldak.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zhaldak.myapplication.datadase.Contact
import kotlinx.android.synthetic.main.contact_card.view.*
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.startActivity

/**
 *
 * Class-Adapter for displaying contact data in the list
 *
 */

class ContactsAdapter(
    private val items: List<Contact>,
    val context: Context,
    private val addOrEditContactViewModel: AddOrEditContactViewModel
) :
    RecyclerView.Adapter<ContactViewHolder>() {

    var FACEBOOK_URL = "https://www.facebook.com/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.contact_card, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = items[position]

        holder.nameText.text = item.name
        holder.emailText.text = item.email
        holder.phoneText.text = item.phone
        holder.commentText.text = item.comment

        if (item.photoUrl == null) {
            Picasso.get()
                .load(R.mipmap.ic_launcher)
                .centerCrop()
                .resize(holder.avatarImage.maxWidth, holder.avatarImage.maxHeight)
                .into(holder.avatarImage)
        } else {
            val uri = Uri.parse(item.photoUrl)
            Picasso.get()
                .load(uri)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .resize(holder.avatarImage.maxWidth, holder.avatarImage.maxHeight)
                .into(holder.avatarImage)
        }


        holder.menuIcon.setOnClickListener { v ->

            showPopupMenu(v, item)

        }

    }

/**
 * Displays the menu of an individual contact.
 * Allows you to go to the FaceBook page.
 * Delete and edit contact.
 * Call the recorded number
 */

    private fun showPopupMenu(v: View, item: Contact) {
        val popupMenu = PopupMenu(context, v)
        popupMenu.inflate(R.menu.contact_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    addOrEditContactViewModel.existingContact = item
                    val navController = v.findNavController()
                    navController.navigate(R.id.addContactFragment)
                    true
                }
                R.id.action_delete -> {
                    addOrEditContactViewModel.removeContact(item)
                    true
                }
                R.id.action_call -> {
                    val uri = "tel:${item.phone}"
                    val intent = Intent(Intent.ACTION_DIAL)

                    intent.data = Uri.parse(uri)
                    context.startActivity(intent)
                    true
                }
                R.id.action_facebook -> {
                    val facebookIntent = Intent(Intent.ACTION_VIEW)
                    val facebookUrl = getFacebookPageURL(context, item.facebookId ?: "")

                    facebookIntent.data = Uri.parse(facebookUrl)
                    context.startActivity(facebookIntent)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

/**
 * Method to get a link for a FaceBook page
 * @param context Context APK application object
 * @param facebookId Id facebook page.
 */
    fun getFacebookPageURL(context: Context, facebookId: String): String {
        val packageManager = context.packageManager
        try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            return if (versionCode >= 3002850) { //newer versions of fb app
                "fb://facewebmodal/f?href=$FACEBOOK_URL$facebookId"
            } else { //older versions of fb app
                "fb://page/$facebookId"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            return FACEBOOK_URL //normal web url
        }

    }

}

/**
 * Displays contact information
 */

class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nameText = view.nameTxt
    val phoneText = view.phoneTxt
    val avatarImage = view.imageView
    val menuIcon = view.imageView4
    val emailText = view.emailText
    val commentText = view.commentTxt
}