package com.marina_romanovna.apartmentcatalog.presentation.apartment.detail

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
import com.marina_romanovna.apartmentcatalog.databinding.ApartmentDetailFragmentBinding
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import com.marina_romanovna.apartmentcatalog.utils.getNumberFormat
import com.marina_romanovna.apartmentcatalog.utils.observe
import com.marina_romanovna.apartmentcatalog.utils.putIntArguments
import com.marina_romanovna.apartmentcatalog.utils.showSnackbarWithAction
import com.marina_romanovna.apartmentcatalog.utils.states.ApartmentDetailState
import javax.inject.Inject
import kotlin.properties.Delegates

class ApartmentDetailFragment : Fragment(R.layout.apartment_detail_fragment) {

    companion object {

        private const val KEY_ID = "key_id"

        fun newInstance(apartmentId: Int): Fragment {
            return ApartmentDetailFragment().putIntArguments(KEY_ID, apartmentId)
        }
    }

    @Inject
    lateinit var factory: ApartmentDetailViewModel.Factory

    private var apartmentId by Delegates.notNull<Int>()

    private var _binding: ApartmentDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ApartmentDetailViewModel> { factory }

    override fun onAttach(context: Context) {
        ApartmentApplication.dagger.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        _binding = ApartmentDetailFragmentBinding.bind(view)
        apartmentId = requireNotNull(arguments?.getInt(KEY_ID)) { "no arguments received" }
        getApartmentDetail(apartmentId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_apartment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_apartment -> {
                viewModel.onEditClick(apartmentId)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getApartmentDetail(apartmentId: Int) {
        viewModel.getApartmentDetail(apartmentId)
        viewModel.state.observe(lifecycleScope) { apartmentDetailState ->
            renderState(apartmentDetailState)
        }
    }

    private fun renderState(apartmentDetailState: ApartmentDetailState) {
        when (apartmentDetailState) {
            is ApartmentDetailState.ErrorState -> {
                binding.circularLoader.isVisible = false
                showSnackbarWithAction(
                    binding.root,
                    getString(R.string.error),
                    getString(R.string.retry)
                ) {
                    viewModel.getApartmentDetail(apartmentId)
                }
            }
            is ApartmentDetailState.LoadingState -> {
                binding.circularLoader.isVisible = true
            }
            is ApartmentDetailState.SuccessState -> {
                binding.circularLoader.isVisible = false
                fillDetailsFields(apartmentDetailState.data)
            }
        }
    }

    private fun fillDetailsFields(apartmentItem: ApartmentItem) {
        with(binding) {
            tvPrice.text = getNumberFormat(apartmentItem.price)
            tvAddress.text = apartmentItem.address
            tvNumberOfRooms.text = apartmentItem.numberOfRooms.toString()
            tvSquare.text = apartmentItem.square.toString()
            tvFloor.text = apartmentItem.floor.toString()
            tvPricePerSqmeter.text = getNumberFormat(apartmentItem.pricePerSquareMeter.toInt())
        }
    }
}