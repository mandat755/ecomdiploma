package com.example.ecomdiploma.presentation.fragments.mainfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ecomdiploma.R
import com.example.ecomdiploma.databinding.FragmentMainBinding
import com.example.ecomdiploma.presentation.fragments.shopfrag.Product
import com.example.ecomdiploma.presentation.viewmodel.AuthorizationViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val authorizationViewModel: AuthorizationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setOnClickListenerForCard()

        binding.shopButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_shopFragment)
        }

        binding.shopButton2.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_shopFragment)
        }

        return binding.root
    }

    private fun setOnClickListenerForCard() {
        val cardClickListener = View.OnClickListener { view ->
            val product: Product = when (view.id) {
                R.id.card11 -> {
                    Product(
                        name = "Cotton Socks",
                        price = "$5.00",
                        images = listOf(R.drawable.socks1, R.drawable.socks2, R.drawable.socks3, R.drawable.socks4),
                        description = "Cotton Socks offer soft, breathable comfort with a clean, everyday design. Made from smooth, durable fabric, they provide a snug fit perfect for all-day wear, layering, or effortless minimal style."
                    )
                }
                R.id.card12 -> {
                    Product(
                        name = "Crimson Grip Boots",
                        price = "$80.00",
                        images = listOf(R.drawable.gripboots1,R.drawable.gripboots2, R.drawable.gripboots3, R.drawable.gripboots4),
                        description = "Sturdy boots with a crimson grip for outdoor activities."
                    )
                }
                R.id.card13 -> {
                    Product(
                        name = "Midnight Urban Pack",
                        price = "$22.00",
                        images = listOf(R.drawable.pack1, R.drawable.pack2, R.drawable.pack3, R.drawable.pack4),
                        description = "A stylish urban backpack perfect for all occasions."
                    )
                }
                R.id.card14 -> {
                    Product(
                        name = "Soft Knit Beanie",
                        price = "$30.00",
                        images = listOf(R.drawable.shap1, R.drawable.shap2, R.drawable.shap3, R.drawable.shap4),
                        description = "A soft, comfortable knit beanie to keep you warm."
                    )
                }
                R.id.card411 -> {
                    Product(
                        name = "Bold Chrono",
                        price = "$92.00",
                        images = listOf(R.drawable.boldcrono1, R.drawable.boldcrono2, R.drawable.boldcrono3, R.drawable.boldcrono4),
                        description = "Its oversized case, textured strap, and precision chronograph details create a statement accessory built for confidence and character. A perfect choice for everyday wear, dynamic looks, or anyone who values bold style with a technical edge."
                    )
                }
                R.id.card412 -> {
                    Product(
                        name = "Minimal Croco",
                        price = "$85.00",
                        images = listOf(R.drawable.minimalcroco1, R.drawable.minimalcroco2, R.drawable.minimalcroco3, R.drawable.minimalcroco4),
                        description = "Featuring a clean dial, slim markers, and a refined leather or mesh strap, it delivers a timeless look with contemporary sophistication. Perfect for daily wear, formal outfits, or anyone who appreciates a sleek, minimalist timepiece with character."
                    )
                }
                R.id.card413 -> {
                    Product(
                        name = "Mesh Chrono",
                        price = "$133.00",
                        images = listOf(R.drawable.meshcrono1, R.drawable.meshcrono2, R.drawable.meshcrono3, R.drawable.meshcrono4),
                        description = "Its clean dial, sharp detailing, and woven stainless-steel strap create a timeless look that elevates any outfit. Perfect for everyday wear, business style, or anyone who values elegance with a technical edge."
                    )
                }
                R.id.card414 -> {
                    Product(
                        name = "AeroSport Racer",
                        price = "$112.00",
                        images = listOf(R.drawable.aerosport1, R.drawable.aerosport2, R.drawable.aerosport3, R.drawable.aerosport4),
                        description = "Built for those who lead with confidence, this watch features a robust case, sharp indicators, and three performance-driven sub-dials. Available in striking color variations, it delivers both personality and reliability. A perfect choice for anyone who wants a dynamic, modern timepiece that stands out."
                    )
                }
                R.id.card5411 -> {
                    Product(
                        name = "Urban High",
                        price = "$65.00",
                        images = listOf(R.drawable.urbanhigh1, R.drawable.urbanhigh2, R.drawable.urbanhigh3, R.drawable.urbanhigh4),
                        description = "Featuring a sleek layered design, cushioned ankle support, and durable outsoles, they deliver the perfect balance of functionality and attitude. Ideal for casual outfits, active days, or anyone who wants a bold and versatile sneaker choice."
                    )
                }
                R.id.card5412 -> {
                    Product(
                        name = "Sand Trek",
                        price = "$42.00",
                        images = listOf(R.drawable.sandtrek1, R.drawable.sandtrek2, R.drawable.sandtrek3, R.drawable.sandtrek4),
                        description = "Featuring premium suede, detailed moc-toe stitching, and a lightweight sole, they deliver a perfect balance of style and practicality. Ideal for casual outfits, smart-casual looks, or anyone who appreciates understated elegance."
                    )
                }
                R.id.card5413 -> {
                    Product(
                        name = "Coffee Lace",
                        price = "$81.00",
                        images = listOf(R.drawable.coffelace1, R.drawable.coffelace2, R.drawable.coffelace3, R.drawable.coffelace4),
                        description = "Crafted from soft suede with clean stitching and a lightweight sole, they deliver a refined yet relaxed look perfect for everyday wear. An ideal choice for anyone who values versatile style and subtle elegance."
                    )
                }
                R.id.card5414 -> {
                    Product(
                        name = "Mocha Edge",
                        price = "$102.00",
                        images = listOf(R.drawable.mochaedge1, R.drawable.mochaedge2, R.drawable.mochaedge3, R.drawable.mochaedge4),
                        description = "Crafted with a smooth matte finish, matching laces, and classic metal eyelets, they offer a perfect balance of style and everyday comfort. Ideal for casual outfits, urban looks, or anyone who wants footwear that stands out with confidence."
                    )
                }
                else -> {
                    return@OnClickListener
                }
            }
            navigateToProductDetailFragment(product)
        }

        binding.card11.setOnClickListener(cardClickListener)
        binding.card12.setOnClickListener(cardClickListener)
        binding.card13.setOnClickListener(cardClickListener)
        binding.card14.setOnClickListener(cardClickListener)

        binding.card411.setOnClickListener(cardClickListener)
        binding.card412.setOnClickListener(cardClickListener)
        binding.card413.setOnClickListener(cardClickListener)
        binding.card414.setOnClickListener(cardClickListener)

        binding.card5411.setOnClickListener(cardClickListener)
        binding.card5412.setOnClickListener(cardClickListener)
        binding.card5413.setOnClickListener(cardClickListener)
        binding.card5414.setOnClickListener(cardClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToProductDetailFragment(product: Product) {
        val bundle = Bundle()
        bundle.putSerializable("product", product)
        findNavController().navigate(R.id.action_nav_home_to_productDetailFragment, bundle)
    }
}
