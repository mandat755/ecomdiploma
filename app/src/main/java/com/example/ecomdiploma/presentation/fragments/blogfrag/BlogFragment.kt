package com.example.ecomdiploma.presentation.fragments.blogfrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomdiploma.R
import com.example.ecomdiploma.databinding.FragmentAboutInfoBinding
import com.example.ecomdiploma.databinding.FragmentBlogBinding

class BlogFragment : Fragment() {

    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!

    private lateinit var blogAdapter: BlogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val blogList = listOf(
            BlogItem(R.drawable.blogone, "Summer freedom styled your way.", "Jan 11, 2025", "4 min read", "Warm days, late sunsets and outfits that move with you. This story is all about easy summer looks that feel as good as they look.\n" +
                    "\n" +
                    "Forget heavy layers and complicated outfits. Summer style is about pieces that you barely feel on the body, but that still look put-together.\n" +
                    "\n" +
                    "Start with a breathable base: lightweight cotton tees, linen shirts and relaxed shorts. Then add one bold element – a bright lip, statement earrings or a patterned scarf.\n" +
                    "\n" +
                    "Don’t be afraid of color. Greens, oranges and soft pastels instantly make any look feel more alive.\n" +
                    "\n" +
                    "Pro tip: build a small summer capsule: 2–3 tops, 2 bottoms and 1 statement dress or jumpsuit. With them, you can create dozens of combinations without any extra thought."),
            BlogItem(R.drawable.blogtwo, "Where calm meets style and inspiration grows.", "01.01.2023", "5 min read", "Some outfits are made for movement, others are made for breathing deeply and feeling present.\n" +
                    "\n" +
                    "Good style doesn’t have to shout. Nude, warm, and earthy shades create a sense of calm even on the craziest day.\n" +
                    "\n" +
                    "Choose clean silhouettes and focus on textures: soft knits, brushed cotton, suede details.\n" +
                    "\n" +
                    "Create a small ritual before leaving home: choose your outfit the evening before, prepare your accessories, put on your favorite track. This calms you down and saves time in the morning.\n" +
                    "\n" +
                    "Style can be your quiet space, even when the day is loud."),
            BlogItem(R.drawable.blogthree, "Leather made for confidence, not just for looks.", "Sep 20, 2025", "3 min read", "Agood leather jacket doesn’t just keep you warm – it changes the way you walk into a room.\n" +
                    "\n" +
                    "Classic black leather will never go out of style, but you're not limited to it. Deep brown, petrol blue or even burgundy work just as well.\n" +
                    "\n" +
                    "The key is the fit. The seams should follow your shoulders, sleeves end right at the wrist and the jacket must close without pulling.\n" +
                    "\n" +
                    "Pair leather with something soft: a white tee, knitwear or even a flowy dress. The contrast of textures makes the image more expensive and thoughtful."),
            BlogItem(R.drawable.blogfour, "Color that speaks louder than words.", "Nov 9, 2025", "4 min read", "There are days when you don’t want to explain yourself – you want your outfit to do it for you.\n" +
                    "\n" +
                    "Bold color is the fastest way to change the mood of a look. One bright piece – a coat, lipstick, or even just a bag – shifts everything.\n" +
                    "\n" +
                    "To keep color from feeling “too much”, pair it with simple shapes and neutral companions: black, white, denim.\n" +
                    "\n" +
                    "If you're afraid to start, add color to your shoes or accessories. It's a small but noticeable step towards a new style."),
        )

        blogAdapter = BlogAdapter(blogList)
        recyclerView.adapter = blogAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}