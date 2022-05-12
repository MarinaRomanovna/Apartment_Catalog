package com.marina_romanovna.apartmentcatalog.presentation.apartment.list

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.marina_romanovna.apartmentcatalog.ApartmentApplication
import com.marina_romanovna.apartmentcatalog.R
import com.marina_romanovna.apartmentcatalog.databinding.ApartmentListFragmentBinding
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import com.marina_romanovna.apartmentcatalog.presentation.apartment.list.adapter.ApartmentListAdapter
import com.marina_romanovna.apartmentcatalog.utils.observe
import com.marina_romanovna.apartmentcatalog.utils.setupLeftSwipeListener
import com.marina_romanovna.apartmentcatalog.utils.states.ApartmentListState
import javax.inject.Inject

class ApartmentListFragment : Fragment(R.layout.apartment_list_fragment) {

    companion object {
        fun newInstance() = ApartmentListFragment()
    }

    @Inject
    lateinit var factory: ApartmentListViewModel.Factory

    private lateinit var adapter: ApartmentListAdapter

    private var _binding: ApartmentListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ApartmentListViewModel> { factory }

    override fun onAttach(context: Context) {
        ApartmentApplication.dagger.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        _binding = ApartmentListFragmentBinding.bind(view)
        initializeAdapter()
        getApartmentList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_apartment_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_apartment -> {
                viewModel.onAddApartmentClick()
            }
            R.id.sign_out -> {
                viewModel.onExitClick()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeAdapter() {
        adapter = ApartmentListAdapter(object : ApartmentListAdapter.OnItemClickListener {
            override fun onItemClick(item: ApartmentItem) {
                viewModel.onApartmentItemClick(item.id)
            }
        })
        binding.rvApartmentList.adapter = adapter
        setupLeftSwipeListener(
            binding.rvApartmentList,
            adapter
        ) {
            viewModel.deleteApartmentItem(it)
        }
    }

    private fun getApartmentList() {
        viewModel.getApartmentList()
        viewModel.state.observe(lifecycleScope) { apartmentListState ->
            renderState(apartmentListState)
        }
    }

    private fun renderState(apartmentListState: ApartmentListState) {
        when (apartmentListState) {
            is ApartmentListState.EmptyListState -> {
                with(binding) {
                    rvApartmentList.isVisible = false
                    tvEmptyList.isVisible = true
                    circularLoader.isVisible = false
                    btnRetry.isVisible = false
                    tvError.isVisible = false
                }
            }
            is ApartmentListState.ErrorState -> {
                with(binding) {
                    rvApartmentList.isVisible = false
                    tvEmptyList.isVisible = false
                    circularLoader.isVisible = false
                    with(btnRetry) {
                        isVisible = true
                        setOnClickListener {
                            viewModel.getApartmentList()
                        }
                    }
                    tvError.isVisible = true
                }
            }
            is ApartmentListState.LoadingState -> {
                with(binding) {
                    rvApartmentList.isVisible = false
                    tvEmptyList.isVisible = false
                    circularLoader.isVisible = true
                    btnRetry.isVisible = false
                    tvError.isVisible = false
                }
            }
            is ApartmentListState.SuccessState -> {
                with(binding) {
                    adapter.submitList(apartmentListState.data)
                    rvApartmentList.isVisible = true
                    tvEmptyList.isVisible = false
                    circularLoader.isVisible = false
                    btnRetry.isVisible = false
                    tvError.isVisible = false
                }
            }
        }
    }
}