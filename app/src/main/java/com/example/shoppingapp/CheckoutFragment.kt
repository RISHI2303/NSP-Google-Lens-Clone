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
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlin.properties.Delegates

const val COUPON: String = "RISHI23"

class CheckoutFragment : Fragment() {

    private lateinit var viewModel: CheckoutViewModel
    private var price by Delegates.notNull<Float>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // this line is getting the data that is passed from previous frag to this frag
        val id = CheckoutFragmentArgs.fromBundle(requireArguments()).id

        // creating an instance of CheckoutViewModelFactory
        val viewModelFactory = CheckoutViewModelFactory(id, 1)

         /*
         generally, android system creates an instance of view model and we only use that instance
         that instance can be called using viewModelProviders
         */

         /*
         We do not need to pass viewModelFactory as argument if the viewModel class does not not have any constructor
         In this case, we have a constructor in CheckoutViewModel so we have to pass viewModelFactory
         as an argument
         */

         /*
         Basically, viewModelFactory is based on the factory pattern.
         As we know that android system creates an instance of view model, factory is passed to
         android system so that it can create new instances using that factory.
         If no constructor is created inside view model, then android creates an instance.
         But if constructor is passed, it needs to know what values are to be passed to it when
         instance is created.
         */
//        viewModel = ViewModelProviders.of(this, viewModelFactory)
//            .get(CheckoutViewModel::class.java)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CheckoutViewModel::class.java)

        // remember to pass "viewLifecycleOwner" instead of "this" b/c of onDestroyView called
        // when going to back stack.
        viewModel.product.observe(viewLifecycleOwner, Observer {
            setData(it)
            price = it.price
        })

        viewModel.qty.observe(viewLifecycleOwner, Observer {
            txt_qty.text = "Quantity: $it"
        })

        btn_add_qty.setOnClickListener {
            viewModel.addQty()
            txt_qty.text = "Quantity: ${viewModel.qty.value}"
            txt_order_total.text = "Order Total: Rs ${price * viewModel.qty.value!!}"
        }

        btn_reduce_qty.setOnClickListener {
            viewModel.reduceQty()
            txt_qty.text = "Quantity: ${viewModel.qty.value}"
            txt_order_total.text = "Order Total: Rs ${price * viewModel.qty.value!!}"
        }

        btn_discount.setOnClickListener {
            et_enter_coupon.isVisible = true
            btn_apply_code.isVisible = true
        }

        btn_apply_code.setOnClickListener {
            if(et_enter_coupon.text.toString() == COUPON) {
                var finalPrice = (price * viewModel.qty.value!!)
                finalPrice -= (finalPrice/2) // 50% discount after applying coupon code
                txt_order_total.text = "Order Total: Rs $finalPrice"
                Toast.makeText(context,
                    "Coupon applied successfully",
                    Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(context,
                    "Invalid coupon code",
                    Toast.LENGTH_LONG).show()
            }

            et_enter_coupon.isVisible = false
            btn_apply_code.isVisible = false
        }

        viewModel.trimmedDesc.observe(viewLifecycleOwner, Observer {
            txt_short_description.text = it
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setData(product: Product?) {
        product?.run {
            txt_checkout_product_name.text = name
            txt_price.text = "Price: Rs $price"
            txt_qty.text = "Quantity: ${viewModel.qty.value}"
            txt_order_total.text = "Order Total: Rs $price"
            img_product_checkout.setImageResource(imageId)

            btn_checkout.setOnClickListener {
                findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToThanksFragment(this.id))
            }
        }
    }
}