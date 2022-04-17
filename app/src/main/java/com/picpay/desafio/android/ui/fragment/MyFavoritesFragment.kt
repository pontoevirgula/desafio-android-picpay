package com.picpay.desafio.android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.FragmentMyFavoritesBinding
import com.picpay.desafio.android.ui.activity.ContactActivity
import com.picpay.desafio.android.ui.adapter.ContactAdapter
import com.picpay.desafio.android.ui.viewmodel.ContactViewModel

class MyFavoritesFragment : Fragment() {

    private var _binding: FragmentMyFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactViewModel
    lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ContactActivity).viewModel
        viewModel.fetchAllContacts()

        setupRecyclerView()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val contact = contactAdapter.differ.currentList[position]
                viewModel.deleteContact(contact)
                Snackbar.make(view, "${contact.username} ${getString(R.string.snackbar_favorites_remove)}", Snackbar.LENGTH_LONG).apply {
                    setAction(getString(R.string.undo)) {
                        viewModel.saveContact(contact)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavoriteContact)
        }

        viewModel.getFavoriteContact()
            .observe(viewLifecycleOwner) { contacts ->
                contactAdapter.differ.submitList(contacts)
            }
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter()
        binding.rvFavoriteContact.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}