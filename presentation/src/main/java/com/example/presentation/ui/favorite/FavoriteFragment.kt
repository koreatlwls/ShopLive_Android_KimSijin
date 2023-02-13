package com.example.presentation.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.presentation.R
import com.example.presentation.databinding.FragmentFavoriteBinding
import com.example.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    private val viewModel: FavoriteViewModel by viewModels()

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    override fun initBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

    private fun initAdapter() {
        favoriteAdapter = FavoriteAdapter {

        }

        binding.favoriteRecyclerView.apply {
            adapter = favoriteAdapter
            layoutManager = GridLayoutManager(requireContext(), ROW_CNT)
        }
    }

    companion object {
        private const val ROW_CNT = 2
    }

}