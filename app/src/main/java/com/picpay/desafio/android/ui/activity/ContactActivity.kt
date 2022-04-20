package com.picpay.desafio.android.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.db.ContactDB
import com.picpay.desafio.android.core.db.ContactRepositoryDBImpl
import com.picpay.desafio.android.core.remote.service.MyService
import com.picpay.desafio.android.databinding.ActivityContactBinding
import com.picpay.desafio.android.core.remote.repository.ContactRepositoryImpl
import com.picpay.desafio.android.ui.viewmodel.ContactViewModel
import com.picpay.desafio.android.ui.viewmodel.ViewModelFactory

class ContactActivity : AppCompatActivity() {

    lateinit var viewModel: ContactViewModel
    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = ContactRepositoryImpl(MyService)
        val repositoryDB = ContactRepositoryDBImpl(ContactDB(this))
        val viewModelProviderFactory = ViewModelFactory(application, repository,repositoryDB)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[ContactViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.contact_nav_host_fragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }



}
