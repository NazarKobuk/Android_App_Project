package com.zhaldak.myapplication

import android.app.Activity.RESULT_OK
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.zhaldak.myapplication.datadase.Contact
import kotlinx.android.synthetic.main.add_contact_fragment.*
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 
 * The class is responsible for implementing 
 * the change of the old or adding a new contact to the application.
 *
 */
 
class AddOrEditContactFragment : Fragment() {
	
    companion object {
        fun newInstance() = AddOrEditContactFragment()
        const val PICK_IMAGE = 1
    }

    private lateinit var viewModel: AddOrEditContactViewModel

/**
 *
 * override "onCreateView" - methode for binding the xml-view model to the fragment class
 
 * @param inflater object to create a View instance with xml file
 * @param container ViewGroup object to store View items 
 * @param savedInstanceState is a standard Bundle object to store the state after closing the program
 *
 */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_contact_fragment, container, false)
    }

/**
 *
 *override "onActivityCreated" - Activity handling method Records contact information in name fields, etc.
 *
 *@param savedInstanceState is the standard Bundle object for state storage
 */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity = activity as MainActivity
        viewModel = ViewModelProviders.of(mainActivity).get(AddOrEditContactViewModel::class.java)
        if(viewModel.existingContact != null) {
            val contact = viewModel.existingContact!!

            editName.setText(contact.name)
            editPhone.setText(contact.phone)
            editComments.setText(contact.comment)

            editEmail.setText(contact.email)
            editFacebook.setText(contact.facebookId)
            contact.photoUrl?.apply { imageWasSelected(Uri.parse(contact.photoUrl)) }


        }

        activity?.fab?.apply {
            visibility = View.GONE
        }

        imagePhoto.setOnClickListener {
            startActivityToPickPhoto()
        }

        buttonSubmit.setOnClickListener {
            val name = editName.text.toString()
            val phone = editPhone.text.toString()
            val email = editEmail.text.toString()

            val comment = editComments.text.toString()
            val facebookId = editFacebook.text.toString()
            val existingContact = viewModel.existingContact

            val contact: Contact = if (existingContact != null) {

                existingContact.name = name
                existingContact.phone = phone
                existingContact.email = email
                existingContact.comment = comment
                existingContact.facebookId = facebookId
                existingContact

            } else {
                Contact(
                    name = name, phone = phone, email = email,
                    comment = comment, groupId = viewModel.groupId,
                    facebookId = facebookId
                )
            }
            viewModel.saveContact(contact)
            it.findNavController().popBackStack()
        }
    }

/**
 *
 * private "startActivityToPickPhoto" - managing method that allows us to set a picture for your contacts
 *
 */
    private fun startActivityToPickPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        } else {
            intent.action = Intent.ACTION_GET_CONTENT
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }
/**
 *
 * override "onActivityResult" - checking image selection
 *
 */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data
            imageWasSelected(selectedImageUri)
        }
    }

/**
 *
 * private "imageWasSelected" - uploads a pic
 *
 */
    private fun imageWasSelected(selectedImageUri: Uri?) {
        Picasso.get()
            .load(selectedImageUri)
            .resize(imagePhoto.maxWidth, imagePhoto.maxHeight)
            .centerCrop()
            .into(imagePhoto)
        viewModel.selectedImageUri = selectedImageUri
    }
}
