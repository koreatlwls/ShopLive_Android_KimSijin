package com.example.presentation.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.presentation.R
import com.example.presentation.databinding.FragmentSearchBinding
import com.example.presentation.ui.adapter.MarvelCharacterAdapter
import com.example.presentation.ui.base.BaseFragment
import com.example.presentation.ui.bindingadapter.textChangesToFlow
import com.example.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var marvelCharacterAdapter: MarvelCharacterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        listenSearchEditText()
    }

    override fun initBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

    private fun listenSearchEditText() {
        repeatOnStarted {
            val editTextFlow = binding.searchTextInputEdittext.textChangesToFlow()

            editTextFlow
                .debounce(300)
                .onEach {
                    viewModel.setSearchQuery(it.toString())
                }
                .launchIn(this)
        }
    }

    private fun initAdapter() {
        marvelCharacterAdapter = MarvelCharacterAdapter {
            Log.e("ABC", it.toString())
        }

        binding.searchRecyclerView.apply {
            adapter = marvelCharacterAdapter
            layoutManager = GridLayoutManager(requireContext(), ROW_CNT)
        }
    }

    companion object {
        const val ROW_CNT = 2
    }

}