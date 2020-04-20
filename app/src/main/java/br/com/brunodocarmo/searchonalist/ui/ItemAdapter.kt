package br.com.brunodocarmo.searchonalist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.com.brunodocarmo.searchonalist.R
import br.com.brunodocarmo.searchonalist.model.Item
import br.com.brunodocarmo.searchonalist.utils.StringValidateUtils
import kotlinx.android.synthetic.main.item.view.*
import java.util.*
import kotlin.collections.ArrayList

class ItemListAdapter(private val itemList: List<Item>,
                      private val context: Context) :
    RecyclerView.Adapter<ItemListAdapter.ViewHolder>(), Filterable {

    private var itemFilterList = itemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)

        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return itemFilterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = itemFilterList[position]
        holder.bindView(note)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Item) {
            val title = itemView.tv_name

            title.text = item.title
        }
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().toLowerCase(Locale.ROOT)

                itemFilterList = if (charSearch.isEmpty()) {
                    itemList
                } else {
                    val resultList = ArrayList<Item>()

                    for (item in itemList) {
                        val itemTitle = item.title.toLowerCase(Locale.ROOT)

                        val isPartialPermutation =
                            StringValidateUtils.isPartialPermutation(charSearch, itemTitle)
                        val isTypo = StringValidateUtils.isTypo(charSearch, itemTitle)

                        if ((isPartialPermutation && !isTypo) || (!isPartialPermutation && isTypo)) {
                            resultList.add(
                                Item(
                                    item.title
                                )
                            )
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemFilterList

                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemFilterList = results?.values as List<Item>

                notifyDataSetChanged()
            }

        }
    }

}