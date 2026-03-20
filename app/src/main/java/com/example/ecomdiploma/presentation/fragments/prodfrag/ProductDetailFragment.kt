package com.example.ecomdiploma.presentation.fragments.prodfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomdiploma.R
import com.example.ecomdiploma.databinding.FragmentProductDetailBinding
import com.example.ecomdiploma.domain.shopfrag.ProductModel
import com.example.ecomdiploma.presentation.fragments.shopfrag.ProductAdapter
import com.example.ecomdiploma.domain.shopfrag.SimpleProductModel
import com.example.ecomdiploma.presentation.viewmodel.CartViewModel

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        recyclerView = binding.root.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val layout = R.layout.item_product_second

        val adapter = ProductAdapter(getProducts(), { product ->
            onProductClick(product)
        }, layout)
        recyclerView.adapter = adapter
        setOnClickListenerForImage()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productModel = arguments?.getSerializable("product") as? ProductModel

        productModel?.let {
            binding.productImage.setImageResource(productModel.images[0])
            binding.productImage.tag = productModel.images[0]

            binding.imchain1.setImageResource(productModel.images[0])
            binding.imchain1.tag = productModel.images[0]

            binding.imchain2.setImageResource(productModel.images[1])
            binding.imchain2.tag = productModel.images[1]

            binding.imchain3.setImageResource(productModel.images[2])
            binding.imchain3.tag = productModel.images[2]

            binding.imchain4.setImageResource(productModel.images[3])
            binding.imchain4.tag = productModel.images[3]

            binding.productName.text = productModel.name
            binding.productPrice.text = productModel.price
            binding.productDescription.text = productModel.description
        }

        binding.addToCartButton.setOnClickListener {
            val name = binding.productName.text.toString()
            val price = binding.productPrice.text.toString()
            val size = binding.sizeSelector.selectedItem.toString()
            val image = binding.productImage.tag as? Int ?: R.drawable.tshirt1


            val simpleProd = SimpleProductModel(name = name, price = price, size = size, imageResId = image)
            cartViewModel.addItemToCart(simpleProd)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun getProducts(): List<ProductModel> {
        return listOf(
            ProductModel(
                name = "Men's Fashion T Shirt",
                price = "$92.00",
                images = listOf(R.drawable.tshirt1, R.drawable.tshirt2, R.drawable.tshirt3,R.drawable.tshirt4),  // Список зображень
                description = "The Gildan Ultra Cotton t-shirt is a heavyweight, classic-fit t-shirt made primarily from 100% U.S. cotton. It is known for its durability, featuring a seamless double-needle collar, taped neck and shoulders, and double-needle sleeves and hem. The shirt is pre-shrunk, ideal for screen printing, and available in a wide variety of solid and heathered colors."
            ),
            ProductModel(
                name ="Grip Boots",
                price = "$80.00",
                images = listOf(R.drawable.gripboots1, R.drawable.gripboots2, R.drawable.gripboots3,R.drawable.gripboots4),  // Список зображень
                description = "Grip Boots are high-quality, durable grip socks designed to provide exceptional stability and comfort during training or everyday use. Made from a soft and breathable cotton blend, they offer a secure, flexible fit that supports natural movement. They feature a reinforced non-slip sole with silicone grip pads that deliver reliable traction on a variety of surfaces, making them ideal for sports, fitness, yoga, or any active lifestyle. The socks include a cushioned heel and toe, an elastic ribbed cuff that stays in place, and a sturdy construction built for long-lasting wear."
            ),
            ProductModel(
                name = "Midnight Urban Pack",
                price = "$75.00",
                images = listOf(R.drawable.pack1, R.drawable.pack2, R.drawable.pack3,R.drawable.pack4),  // Список зображень
                description = "Midnight Urban Pack is a sleek, modern backpack designed for everyday city life. With its clean lines, deep midnight tone, and practical layout, it offers the perfect balance of style and functionality. Spacious compartments, a front quick-access pocket, and comfortable padded straps make it ideal for work, travel, or daily commutes."
            ),
            ProductModel(
                name = "Soft Knit Beanie",
                price = "$25.00",
                images = listOf(R.drawable.shap1, R.drawable.shap2, R.drawable.shap3,R.drawable.shap4),  // Список зображень
                description = "Soft Knit Beanie is a cozy, lightweight hat crafted for everyday comfort. Made from soft, breathable knit fabric, it offers a snug fit that keeps you warm without feeling bulky. Its minimalist design makes it a versatile accessory — perfect for casual wear, outdoor strolls, or cool-weather adventures."
            ),
            ProductModel(
                name = "Hygge Hoodie",
                price = "$110.00",
                images = listOf(R.drawable.hoodie1, R.drawable.hoodie2, R.drawable.hoodie3,R.drawable.hoodie4),  // Список зображень
                description = "Hygge Hoodie brings effortless comfort with a clean, minimalist design. Made from soft, cozy fabric, it offers a relaxed fit perfect for everyday warmth, slow mornings, and laid-back moments. A timeless essential for anyone who values simplicity and comfort."
            ),
            ProductModel(
                name = "Long-Sleeved Shirt",
                price = "$89.00",
                images = listOf(R.drawable.long1, R.drawable.long2, R.drawable.long3,R.drawable.long4),  // Список зображень
                description = "This long-sleeved shirt delivers a clean, refined look with a smooth, lightweight feel. Its fitted silhouette and minimalist design make it a versatile everyday essential — perfect for layering, casual outfits, or effortless elegance."
            ),
            ProductModel(
                name = "Eye Graphic Tee",
                price = "$39.00",
                images = listOf(R.drawable.tee1, R.drawable.tee2, R.drawable.tee3,R.drawable.tee4),  // Список зображень
                description = "Eye Graphic Tee blends bold street style with artistic expression. Crafted from soft, breathable cotton, it features a striking eye motif that adds personality to any outfit. A perfect choice for everyday wear, creative looks, or anyone who loves apparel with character."
            ),
            ProductModel(
                name = "Cotton Socks",
                price = "$5.00",
                images = listOf(R.drawable.socks1, R.drawable.socks2, R.drawable.socks3,R.drawable.socks4),  // Список зображень
                description = "Cotton Socks offer soft, breathable comfort with a clean, everyday design. Made from smooth, durable fabric, they provide a snug fit perfect for all-day wear, layering, or effortless minimal style."
            ),
        )
    }
    private fun onProductClick(productModel: ProductModel) {
        binding.productImage.setImageResource(productModel.images[0])
        binding.productImage.tag = productModel.images[0]

        binding.imchain1.setImageResource(productModel.images[0])
        binding.imchain1.tag = productModel.images[0]

        binding.imchain2.setImageResource(productModel.images[1])
        binding.imchain2.tag = productModel.images[1]

        binding.imchain3.setImageResource(productModel.images[2])
        binding.imchain3.tag = productModel.images[2]

        binding.imchain4.setImageResource(productModel.images[3])
        binding.imchain4.tag = productModel.images[3]

        binding.productName.text = productModel.name
        binding.productPrice.text = productModel.price
        binding.productDescription.text = productModel.description
        binding.prodScroll.scrollTo(0, 0)
    }

    private fun setOnClickListenerForImage() {
        val cardClickListener = View.OnClickListener { view ->
            val imRes = when (view.id) {
                R.id.imchain1 -> binding.imchain1.tag as? Int
                R.id.imchain2 -> binding.imchain2.tag as? Int
                R.id.imchain3 -> binding.imchain3.tag as? Int
                R.id.imchain4 -> binding.imchain4.tag as? Int
                else -> null
            } ?: return@OnClickListener

            val imageDrawable = when (view.id) {
                R.id.imchain1 -> binding.imchain1.drawable
                R.id.imchain2 -> binding.imchain2.drawable
                R.id.imchain3 -> binding.imchain3.drawable
                R.id.imchain4 -> binding.imchain4.drawable
                else -> null
            }

            imageDrawable?.let {
                binding.productImage.tag = imRes
                binding.productImage.setImageDrawable(it)
            }
        }

        binding.imchain1.setOnClickListener(cardClickListener)
        binding.imchain2.setOnClickListener(cardClickListener)
        binding.imchain3.setOnClickListener(cardClickListener)
        binding.imchain4.setOnClickListener(cardClickListener)
    }
}