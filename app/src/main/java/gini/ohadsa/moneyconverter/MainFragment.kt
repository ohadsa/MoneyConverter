package gini.ohadsa.moneyconverter

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.res.TypedArray
import android.graphics.Color.alpha
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.res.getDrawableOrThrow
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import gini.ohadsa.moneyconverter.databinding.FragmentMainBinding
import gini.ohadsa.moneyconverter.models.coin.AdapterCoins
import gini.ohadsa.moneyconverter.models.coin_list.CoinsListViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private lateinit var coinsLstViewModel: CoinsListViewModel
    private val binding get() = _binding!!
    lateinit var adapter_coins: AdapterCoins
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val coinsLstViewModel =
            ViewModelProvider(this)[CoinsListViewModel::class.java]

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        coinsLstViewModel.coins.observe(viewLifecycleOwner) {
            adapter_coins = AdapterCoins(it, coinsLstViewModel.refAmount) { coin, view,  img->
                animateFunc(view)
            }
            binding.coinsRecycle.layoutManager = GridLayoutManager(requireActivity(), 2)
            binding.coinsRecycle.itemAnimator = DefaultItemAnimator()
            binding.coinsRecycle.setHasFixedSize(true)
            binding.coinsRecycle.adapter = adapter_coins
        }
        coinsLstViewModel.error.observe(viewLifecycleOwner) {
            binding.textviewSecond.text = getString(it)
            //TODO more handle with Errors ///
            Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun animateFunc(card: CardView) {

        var scale = requireActivity().resources.displayMetrics.density
        card.cameraDistance = 8000 * scale
        var animation = AnimatorInflater.loadAnimator(
            requireActivity(),
            R.animator.front_animator
        ) as AnimatorSet
        animation.setTarget(card)
        animation.start()
    }
}