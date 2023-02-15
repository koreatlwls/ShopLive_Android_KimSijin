package com.example.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.presentation.databinding.FragmentSearchBinding
import com.example.presentation.model.UiState
import com.example.presentation.model.ViewObject
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
        listenRecyclerViewScroll()
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
                    viewModel.getData(it.toString())
                }
                .launchIn(this)
        }
    }

    private fun initAdapter() {
        marvelCharacterAdapter = MarvelCharacterAdapter { commonItem ->
            when (commonItem.viewObject) {
                is ViewObject.SuccessViewObject -> viewModel.insertFavorite(commonItem.viewObject.marvelCharacter)
                is ViewObject.ErrorViewObject -> viewModel.getMoreData(marvelCharacterAdapter.itemCount - 1)
                else -> Unit
            }
        }

        val gridLayoutManager = GridLayoutManager(requireContext(), MAX_ROW_CNT)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (marvelCharacterAdapter.currentList[position].viewType == UiState.Success) {
                    MIN_ROW_CNT
                } else {
                    MAX_ROW_CNT
                }
            }
        }

        binding.searchRecyclerView.apply {
            adapter = marvelCharacterAdapter
            layoutManager = gridLayoutManager
        }
    }

    private fun listenRecyclerViewScroll() {
        binding.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!binding.searchRecyclerView.canScrollVertically(IS_DIRECTION_BOTTOM)) {
                    viewModel.getMoreData(marvelCharacterAdapter.itemCount)
                }
            }
        })
    }

    companion object {
        private const val MAX_ROW_CNT = 2
        private const val MIN_ROW_CNT = 1
        private const val IS_DIRECTION_BOTTOM = 1
    }

}