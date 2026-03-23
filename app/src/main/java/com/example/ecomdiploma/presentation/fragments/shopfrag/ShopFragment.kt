package com.example.ecomdiploma.presentation.fragments.shopfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomdiploma.R
import com.example.ecomdiploma.databinding.FragmentShopBinding
import com.example.ecomdiploma.domain.shopfrag.GetAllProductUseCase
import com.example.ecomdiploma.domain.shopfrag.ProductModel
import com.example.ecomdiploma.server.RetrofitClient

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    private val shopViewModel: ShopViewModel by viewModels {
        ShopViewModelFactory(
            GetAllProductUseCase(RetrofitClient.apiService)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        recyclerView = binding.root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val layout = R.layout.item_product

        val adapter = ProductAdapter(emptyList(), { product ->
            onProductClick(product)
        }, layout)
        recyclerView.adapter = adapter

        shopViewModel.getAllProduct()

        shopViewModel.allProductModelList.observe(viewLifecycleOwner) { productList ->
            if (productList.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Empty list", Toast.LENGTH_SHORT).show()
            } else {
                adapter.updateProducts(productList)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onProductClick(product: ProductModel) {
        val bundle = Bundle()
        bundle.putSerializable("product", product)
        findNavController().navigate(R.id.action_nav_shop_to_productDetailFragment, bundle)
    }
}