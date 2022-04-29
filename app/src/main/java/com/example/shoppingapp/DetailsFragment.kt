package com.example.shoppingapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.list_item.*

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // creating a reference of Product nullable and setting it to null
//        var product: Product? = null

        // getting the id of the from the bundle using the argument?.getInt
        // it is nullable b/c the id can be null
        // arguments contain the data from any previous frag or activity
//        val id = arguments?.getInt("ID")

        // find() iterates through whole Products list and will compare all the id's
        // if the id's matches, it will assign that element to the product
//        id?.let {
//            product = products.find {
//                it.id == id
//            }
//        }

//        arguments?.let {
//            val args = DetailsFragmentArgs.fromBundle(it)
//            product = products.find { args.id == it.id }
//        }

        val id = DetailsFragmentArgs.fromBundle(requireArguments()).id

        val viewModelFactory = DetailsViewModelFactory(id, "NA")

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)

        viewModel.product.observe(viewLifecycleOwner, Observer {
            setData(it)
            product = it // to update the changes in rating property, we need product reference
        })

        viewModel.rating.observe(viewLifecycleOwner, Observer {
            if(!viewModel.rating.value.equals("NA")) {
                txt_rating.text = "Rating: $it"
            }
        })

        btn_save.setOnClickListener {
            val userRating: Int = et_add_rating.text.toString().toInt()
            if (userRating in 1..5) {
                product.rating = userRating.toString()
                viewModel.rating.value = userRating.toString()
                Toast.makeText(context,
                    "Rating for ${product.name} is set to $userRating\n\nThanks for Rating",
                    Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(context, "Please enter rating between 1-5", Toast.LENGTH_SHORT).show()
            }

            et_add_rating.isVisible = false
            btn_save.isVisible = false
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setData(product: Product) {
        product.run {
            txt_name.text = name
            txt_price.text = "Price: Rs $price"
            txt_description.text = shortDescription
            txt_full_description.text = longDescription
            txt_rating.text = "Rating: $rating"
            img_product_image.setImageResource(imageId)

            btn_buy_product.setOnClickListener {
//                    val bundle = Bundle()
//                    bundle.putInt("ID", this.id)
//                    findNavController().navigate(R.id.action_detailsFragment_to_checkoutFragment, bundle)

                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToCheckoutFragment(this.id))
            }

            btn_add_rating.setOnClickListener {
                et_add_rating.isVisible = true
                btn_save.isVisible = true
            }
        }
    }
}