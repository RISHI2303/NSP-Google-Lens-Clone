package com.example.shoppingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_thanks.*

class ThanksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thanks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var product: Product? = null

//        val id = arguments?.getInt("ID")
//        id?.let {
//            product = products.find {
//                it.id == id
//            }
//        }

        arguments?.let {
            val args = ThanksFragmentArgs.fromBundle(it)
            product = products.find { args.id == it.id }
        }

        product?.let {
            with(it) {

                txt_thanks_msg.text = "Thank you for purchasing $name. You will soon get an email confirming the order. Your product will be shipped within 4-5 days."

                btn_continue_shopping.setOnClickListener {
                    findNavController().navigate(R.id.action_thanksFragment_to_listFragment)
                }
            }
        }
    }
}