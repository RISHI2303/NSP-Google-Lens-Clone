package com.example.shoppingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productList = rv_product_list.apply {
            layoutManager = LinearLayoutManager(activity)

            adapter = ProductAdapter {
                /*
                 1. Bundle is a container which takes key-value pair.
                 2. it uses map internally
                 3. it is used to send data from one fragment to another fragment or from one activity to another activity
                 4. we should try to minimize the data sent to another activity or fragment b/c it has some limitations
                    that's why we have sent only the id of the product which works as a distinguishing feature of each product.
                 5. There are 3 things in navigation -
                    (i) Navigation Graph - where we have to set all the fragments and their actions
                    (ii) NavHost Fragment - which hosts all the fragments
                    (iii) NavController - which handles the replacing and movement of the fragment
                 6. findNavController() is used to get the controller.
                 */

                // Now, since we are using the safeargs library for type safety, we do not need to
                // create a bundle. This work can be done using the <class name>Directions.action<any name of the action as defined in navigation.xml>(it.id)
                // here, "it" is of Integer type as we mentioned the "argument type" in the "navigation.xml" file.

                /*
                val bundle = Bundle()
                bundle.putInt("ID", it.id)
                */

//                findNavController().navigate(R.id.details, bundle) // one way
//                findNavController().navigate(R.id.action_listFragment_to_detailsFragment, bundle) //second and preferred way

                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToDetailsFragment(it.id)
                )
            }
            setHasFixedSize(true)
        }

        (productList.adapter as ProductAdapter).submitList(products)
    }

}