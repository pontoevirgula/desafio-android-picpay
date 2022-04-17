package com.picpay.desafio.android.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.util.Resource
import com.picpay.desafio.android.databinding.FragmentContactBinding
import com.picpay.desafio.android.ui.activity.ContactActivity
import com.picpay.desafio.android.ui.adapter.ContactAdapter
import com.picpay.desafio.android.ui.viewmodel.ContactViewModel


class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactViewModel
    lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ContactActivity).viewModel
        viewModel.fetchAllContacts()
        setupRecyclerView()

        contactAdapter.setOnItemClickListener { contact ->
            viewModel.saveContact(contact)
            Snackbar.make(
                view,
                "${contact.username} ${getString(R.string.snackbar_favorites_add)}",
                Snackbar.LENGTH_SHORT
            ).apply {
                setAction(getString(R.string.undo)) {
                    viewModel.deleteContact(contact)
                }
            }.show()
        }

        viewModel.contactLiveData.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { contactListResponse ->
                        contactAdapter.differ.submitList(contactListResponse)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(
                            activity,
                            "${getString(R.string.error)} ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.pbContact.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.pbContact.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter()
        binding.rvContact.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}