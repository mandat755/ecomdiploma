package com.example.ecomdiploma.presentation.mainactivity

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.widget.NestedScrollView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomdiploma.R
import com.example.ecomdiploma.databinding.ActivityMainBinding
import com.example.ecomdiploma.presentation.fragments.CartAdapter
import com.example.ecomdiploma.presentation.viewmodel.AuthorizationViewModel
import com.example.ecomdiploma.presentation.viewmodel.CartViewModel
import com.example.ecomdiploma.server.KtorServer
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.navigation.NavigationView
import com.google.firebase.functions.FirebaseFunctions
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val authorizationViewModel: AuthorizationViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    private var cartBadge: BadgeDrawable? = null


    private lateinit var cartRecycler: RecyclerView
    private lateinit var tvTotalPrice: TextView
    private lateinit var tvEmptyCart: TextView
    private lateinit var btnPayNow: Button
    private var cartAdapter: CartAdapter? = null


    private lateinit var paymentSheet: PaymentSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        //ініціалізація Stripe PaymentConfiguration з публічним ключем
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51T52AnCthbbMg3LTXJ46I3uCDPEAACjlx2RGnQ0MbMe8o56CpViClnh3jXCripuEZnH9mqbSpgOkKOskX9lFzlmP00qIUjQ3Ss"
        )

        //ініціалізація PaymentSheet
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)


        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END)
        //отримуємо NavHostFragment за допомогою supportFragmentManager
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        val topLevelDestinations = setOf(
            R.id.nav_home,
            R.id.nav_shop,
            R.id.nav_blog,
            R.id.nav_about_info,
            R.id.nav_contact
        )

        appBarConfiguration = AppBarConfiguration(topLevelDestinations, drawerLayout)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navigationView.setupWithNavController(navController)

        authorizationViewModel.user.observe(this, Observer { user ->
            if (user != null) {
                supportActionBar?.show()
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                supportActionBar?.hide()
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        })

        cartViewModel.cartItemCount.observe(this) { count -> updateCartBadge(count) }

        initCartDrawer()

        KtorServer.startServer(context = applicationContext)
    }

    private fun initCartDrawer() {
        val rightDrawer = findViewById<View>(R.id.navigation_view_right)
        cartRecycler = rightDrawer.findViewById(R.id.recycler_cart_items)
        tvTotalPrice = rightDrawer.findViewById(R.id.priceText)
        btnPayNow = rightDrawer.findViewById(R.id.payButton)
        tvEmptyCart = rightDrawer.findViewById(R.id.tv_empty_cart)

        cartRecycler.layoutManager = LinearLayoutManager(this)

        cartAdapter = CartAdapter(emptyList()) { product ->
            cartViewModel.removeItem(product)
            Toast.makeText(this, "Товар видалено", Toast.LENGTH_SHORT).show()
        }
        cartRecycler.adapter = cartAdapter

        cartViewModel.cartItems.observe(this) { items ->
            cartAdapter?.updateItems(items)
            if (items.isEmpty()) {
                cartRecycler.visibility = View.GONE
                tvEmptyCart.visibility = View.VISIBLE
            } else {
                cartRecycler.visibility = View.VISIBLE
                tvEmptyCart.visibility = View.GONE
            }
        }

        cartViewModel.totalPrice.observe(this) { total ->
            tvTotalPrice.text = "$${String.format("%.2f", total)}"
        }

        btnPayNow.setOnClickListener {
            if (cartViewModel.cartItemCount.value ?: 0 > 0) {
                initiatePayment()
            } else {
                Toast.makeText(this, "The cart is empty.", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawer(GravityCompat.END)
        }
    }

    private fun initiatePayment() {
        val functions = FirebaseFunctions.getInstance()

        // Передаємо суму в центах
        val data = hashMapOf("amount" to (cartViewModel.totalPrice.value?.times(100)?.toInt() ?: 0))

        functions
            .getHttpsCallable("createPaymentIntent")
            .call(data)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result?.data as Map<*, *>
                    val clientSecret = result["clientSecret"] as String

                    presentPaymentSheet(clientSecret)
                } else {
                    Toast.makeText(this, "Error retrieving client secret.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun presentPaymentSheet(clientSecret: String) {
        paymentSheet.presentWithPaymentIntent(
            clientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Ecom",
            )
        )
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Completed -> {
                Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show()
                cartViewModel.clearCart()
            }
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(this, "Payment cancelled.", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(this, "Payment error: ${paymentSheetResult.error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateCartBadge(count: Int) {
        cartBadge?.apply {
            number = count
            isVisible = count > 0
        }
        invalidateOptionsMenu()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @OptIn(ExperimentalBadgeUtils::class)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    @OptIn(ExperimentalBadgeUtils::class)
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)

        val cartItem = menu?.findItem(R.id.cart_button) ?: return true

        // Відв’язуємо старий бейдж
        cartBadge?.let { BadgeUtils.detachBadgeDrawable(it, binding.toolbar, cartItem.itemId) }
        cartBadge = null

        val count = cartViewModel.cartItemCount.value ?: 0
        if (count > 0) {
            cartBadge = BadgeDrawable.create(this).apply {
                backgroundColor = getColor(R.color.red)
                badgeTextColor = getColor(android.R.color.white)
                badgeGravity = BadgeDrawable.TOP_END
                maxCharacterCount = 3
                number = count
                isVisible = true
            }
            cartBadge?.let { BadgeUtils.attachBadgeDrawable(it, binding.toolbar, cartItem.itemId) }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cart_button -> {
                drawerLayout.openDrawer(GravityCompat.END)
                true
            }
            R.id.theme_switch -> {
                toggleTheme()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleTheme() {
        val scrollView = findViewById<NestedScrollView>(R.id.nestedScroll)
        val scrollPosition = scrollView?.scrollY ?: 0
        val currentMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        val logoImageView = binding.toolbar.findViewById<ImageView>(R.id.logo_image)
        if (currentMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        scrollView?.post {
            scrollView.scrollTo(0, scrollPosition)
        }
        logoImageView.invalidate()
    }

    override fun onDestroy() {
        KtorServer.stopServer()
        super.onDestroy()
    }
}