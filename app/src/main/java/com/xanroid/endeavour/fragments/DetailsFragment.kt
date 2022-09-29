package com.xanroid.endeavour.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.xanroid.endeavour.R
import com.xanroid.endeavour.databinding.FragmentDetailsBinding
import com.xanroid.endeavour.main_list_data.ProductModel
import com.xanroid.endeavour.viewmodel.MainListViewModel
import com.xanroid.endeavour.viewmodel.SavedViewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        val itemId = arguments?.getInt("id")
        val mainListViewModel: MainListViewModel by activityViewModels()
        val model: SavedViewModel by activityViewModels()



        binding.myToolbar.setNavigationOnClickListener {
            it.findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToMainListFragment())
        }
        binding.rating.isEnabled = false

        var item = ProductModel(0,"","",0.0,0.0)

        if (itemId != 0){
            mainListViewModel.products.observe(requireActivity()) {
                for (i in it) {
                    if (i.id == itemId) {
                        item = ProductModel(i.id, i.name, i.image, i.price, i.rating)
                        break
                    }
                }
                binding.textName.text = item.name
                binding.textPrice.text = "$"+String.format("%.2f", item.price)
                Picasso.get().load(item.image).error(R.drawable.image_error).into(binding.imageView)
                binding.rating.rating = item.rating.toFloat()
            }
        } else {
            binding.imageView.setImageResource(R.drawable.image_error)
            binding.textName.text = "Invalid Item"
            binding.textPrice.text = "000"
            binding.btnFav.visibility = GONE
            binding.btnAddToCart.visibility = GONE
        }

        binding.btnFav.setOnClickListener {
            model.addProduct(item)
            Snackbar.make(view, "${item.name} Added", Snackbar.LENGTH_SHORT)
                .setAction("Ok"){}
                .setActionTextColor(ContextCompat.getColor(view.context, R.color.my_light_blue))
                .show()
        }


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}