package gini.ohadsa.moneyconverter.models.coin


import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import gini.ohadsa.moneyconverter.R

class AdapterCoins(
    private var coins: List<Coin> = mutableListOf(),
    private var refAmount: Double,
    var coinClickedListener: CoinClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val typesOfView: Pair<Int, Int> = 0 to 1

    fun interface CoinClickListener {
        fun coinClicked(coin: Coin, coinImage: CardView, coinImage1: ImageView)
    }

    fun getItem(position: Int): Coin {
        return coins[position]
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = when (viewType) {
            typesOfView.first -> LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.coin_card, viewGroup, false)
            typesOfView.second -> LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.coin_card_low, viewGroup, false)
            else -> LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.coin_card, viewGroup, false)
        }
        return CoinViewHolderLower(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val coinViewHolder = holder as CoinViewHolderLower
        val coin = getItem(position)
        coinViewHolder.coinName.text = coin.id

        var coinVal = "%.4f".format(coin.moneyValue)

        coinViewHolder.coinValue.text = "1€ = $coinVal"
        coinViewHolder.coinValueRef.text = "1this = ${coin.convert(refAmount)}₪"
        Picasso
            .get()
            .load(Uri.parse("${coin.imagePath}"))
            .into(coinViewHolder.coinImage)

    }

    override fun getItemViewType(position: Int): Int {
        return if (coins[position].higher) 0
        else 1
    }


    inner class CoinViewHolderLower(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var coinImage: ImageView
        var coinName: TextView
        var coinValue: TextView
        var coinValueRef: TextView

        init {
            coinImage = itemView.findViewById(R.id.coin_image)
            coinName = itemView.findViewById(R.id.coin_name)
            coinValue = itemView.findViewById(R.id.coin_value)
            coinValueRef = itemView.findViewById(R.id.coin_value_ref)

            itemView.setOnClickListener {
                coinClickedListener.coinClicked(getItem(adapterPosition) , itemView.findViewById(R.id.card_12) , coinImage )

                if (getItem(adapterPosition).faceUp) {
                    getItem(adapterPosition).faceUp = false
                    coinImage.visibility = View.VISIBLE
                    coinValue.visibility = View.GONE
                    coinValueRef.visibility = View.GONE
                } else {
                    getItem(adapterPosition).faceUp = true
                    coinImage.visibility = View.GONE
                    coinValue.visibility = View.VISIBLE
                    coinValueRef.visibility = View.VISIBLE
                }
            }
        }


    }

}
