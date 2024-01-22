package com.example.job_search

import com.example.job_search.databinding.FragmentHomeBinding
import com.example.job_search.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun bind() {

    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {
    }
}