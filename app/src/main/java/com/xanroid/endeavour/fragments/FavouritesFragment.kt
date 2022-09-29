package com.xanroid.endeavour.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.xanroid.endeavour.R
import com.xanroid.endeavour.adaptor.SavedListAdaptor
import com.xanroid.endeavour.databinding.FragmentFavouritesBinding
import com.xanroid.endeavour.viewmodel.SavedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val view = binding.root

        val adaptor = SavedListAdaptor()
        binding.recView.layoutManager = LinearLayoutManager(requireContext())
        binding.recView.adapter = adaptor

        val model: SavedViewModel by activityViewModels()

        lifecycleScope.launch {
            model.products.collectLatest {
                adaptor.updateList(it)
                if (it.isEmpty()){
                    binding.textNoItem.visibility = View.VISIBLE
                } else {
                    binding.textNoItem.visibility = View.GONE
                }
            }
        }

        adaptor.onFavClick = { item ->
            val v = View.inflate(requireContext(), R.layout.dialog_remove, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(v)
            val dialog = builder.create()
            v.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
                dialog.dismiss()
            }
            v.findViewById<Button>(R.id.buttonYes).setOnClickListener {
                model.deleteProduct(item)
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        adaptor.onItemClick = { item, v ->
            val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(item.id)
            Navigation.findNavController(v).navigate(action)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}