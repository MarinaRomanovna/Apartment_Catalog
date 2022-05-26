package com.marina_romanovna.apartmentcatalog.presentation.apartment.addedititem

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
import com.marina_romanovna.apartmentcatalog.databinding.AddEditApartmentItemFragmentBinding
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import com.marina_romanovna.apartmentcatalog.utils.observe
import com.marina_romanovna.apartmentcatalog.utils.putModeAndIdArguments
import com.marina_romanovna.apartmentcatalog.utils.putStringArguments
import com.marina_romanovna.apartmentcatalog.utils.showSnackbar
import com.marina_romanovna.apartmentcatalog.utils.states.AddEditApartmentItemState
import javax.inject.Inject
import kotlin.properties.Delegates

class AddEditApartmentItemFragment : Fragment(R.layout.add_edit_apartment_item_fragment) {

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val APARTMENT_ITEM_ID = "extra_apartment_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): Fragment {
            return AddEditApartmentItemFragment().putStringArguments(SCREEN_MODE, MODE_ADD)
        }

        fun newInstanceEditItem(apartmentId: Int): Fragment {
            return AddEditApartmentItemFragment().putModeAndIdArguments(
                keyMode = SCREEN_MODE,
                modeValue = MODE_EDIT,
                keyId = APARTMENT_ITEM_ID,
                idValue = apartmentId
            )
        }
    }

    @Inject
    lateinit var factory: AddEditApartmentItemViewModel.Factory

    private var screenMode by Delegates.notNull<String>()

    private var apartmentId by Delegates.notNull<Int>()

    private var _binding: AddEditApartmentItemFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AddEditApartmentItemViewModel> { factory }

    override fun onAttach(context: Context) {
        ApartmentApplication.dagger.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = AddEditApartmentItemFragmentBinding.bind(view)
        setHasOptionsMenu(true)
        getFragmentArguments()
        launchRightMode()
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_save_item -> {
                if (screenMode == MODE_ADD) {
                    viewModel.addApartmentItem(
                        inputAddress = binding.etAddress.text?.toString(),
                        inputPrice = binding.etPrice.text?.toString(),
                        inputSquare = binding.etSquare.text?.toString(),
                        inputNumberOfRooms = binding.etRooms.text?.toString(),
                        inputFloor = binding.etFloor.text?.toString()
                    )
                } else if (screenMode == MODE_EDIT) {
                    viewModel.editApartmentItem(
                        inputAddress = binding.etAddress.text?.toString(),
                        inputPrice = binding.etPrice.text?.toString(),
                        inputSquare = binding.etSquare.text?.toString(),
                        inputNumberOfRooms = binding.etRooms.text?.toString(),
                        inputFloor = binding.etFloor.text?.toString()
                    )
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFragmentArguments() {
        screenMode = arguments?.getString(SCREEN_MODE) ?: MODE_UNKNOWN
        apartmentId = arguments?.getInt(APARTMENT_ITEM_ID) ?: ApartmentItem.UNDEFINED_ID
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchAddMode() {
        showSnackbar(binding.root, getString(R.string.msg_fill_all_fields))
    }

    private fun launchEditMode() {
        viewModel.getApartmentItem(apartmentId)
        viewModel.apartmentItem.observe(lifecycleScope) { apartmentItem ->
            if (apartmentItem != null) {
                with(binding) {
                    etAddress.setText(apartmentItem.address)
                    etSquare.setText(apartmentItem.square.toString())
                    etRooms.setText(apartmentItem.numberOfRooms.toString())
                    etFloor.setText(apartmentItem.floor.toString())
                    etPrice.setText(apartmentItem.price.toString())
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputFields.observe(lifecycleScope) { isError ->
            if (isError)
                showSnackbar(binding.root, getString(R.string.msg_error_fields_not_filled))
        }

        viewModel.state.observe(lifecycleScope) { addEditApartmentItemState ->
            renderState(addEditApartmentItemState)
        }
    }

    private fun renderState(addEditApartmentItemState: AddEditApartmentItemState) {
        when (addEditApartmentItemState) {
            is AddEditApartmentItemState.BeginnerState -> {
                binding.circularLoader.isVisible = false
            }
            is AddEditApartmentItemState.LoadingState -> {
                with(binding) {
                    circularLoader.isVisible = true
                }
            }
            is AddEditApartmentItemState.ErrorState -> {
                binding.circularLoader.isVisible = false
                showSnackbar(binding.root, getString(R.string.msg_error_add_edit_state))
            }
            is AddEditApartmentItemState.SuccessState -> {
                with(binding) {
                    circularLoader.isVisible = false
                    if (screenMode == MODE_EDIT) {
                        showSnackbar(this.root, getString(R.string.msg_success_editing))
                    } else if (screenMode == MODE_ADD) {
                        showSnackbar(this.root, getString(R.string.msg_success_adding))
                    }
                }
                viewModel.onClickDone()
            }
        }
    }
}