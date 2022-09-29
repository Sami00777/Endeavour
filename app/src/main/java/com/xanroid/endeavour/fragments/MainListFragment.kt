package com.xanroid.endeavour.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.xanroid.endeavour.LoadingDialog
import com.xanroid.endeavour.R
import com.xanroid.endeavour.adaptor.MainListAdaptor
import com.xanroid.endeavour.databinding.FragmentMainListBinding
import com.xanroid.endeavour.viewmodel.MainListViewModel
import com.xanroid.endeavour.viewmodel.SavedViewModel

class MainListFragment : Fragment() {

    private var _binding: FragmentMainListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainListBinding.inflate(inflater, container, false)
        val view = binding.root

        val adaptor = MainListAdaptor()
        binding.recView.layoutManager = LinearLayoutManager(requireContext())
        binding.recView.adapter = adaptor
        val loadingDialog = LoadingDialog(requireActivity())


        val model: SavedViewModel by activityViewModels()
        val mainListViewModel: MainListViewModel by activityViewModels()

        mainListViewModel.products.observe(requireActivity()) {
            adaptor.updateList(it)
        }
        mainListViewModel.isLoading.observe(requireActivity()) {
            if (it) {
                loadingDialog.startLoading()
            } else {
                loadingDialog.stopLoading()
            }
        }

        adaptor.onFavClick = {
            model.addProduct(it)
            Snackbar.make(view, "${it.name} Added", Snackbar.LENGTH_SHORT)
                .setAction("Ok"){}
                .setActionTextColor(ContextCompat.getColor(view.context, R.color.my_light_blue))
                .show()
        }

        adaptor.onItemClick = { item, v ->
            val action = MainListFragmentDirections.actionMainListFragmentToDetailsFragment(item.id)
            Navigation.findNavController(v).navigate(action)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}