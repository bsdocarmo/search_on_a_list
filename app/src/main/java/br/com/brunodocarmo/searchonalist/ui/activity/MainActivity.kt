package br.com.brunodocarmo.searchonalist.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunodocarmo.searchonalist.R
import br.com.brunodocarmo.searchonalist.model.Item
import br.com.brunodocarmo.searchonalist.ui.ItemListAdapter
import br.com.brunodocarmo.searchonalist.ui.MarginItemDecoration
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = rv_item
        recyclerView.adapter =
            ItemListAdapter(notes(), this)

        val layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        val decoration =
            MarginItemDecoration(80)
        recyclerView.addItemDecoration(decoration)

        recyclerView.layoutManager = layoutManager

        sv_item_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (recyclerView.adapter as ItemListAdapter).filter.filter(newText)

                return false
            }

        })
    }

    private fun notes(): List<Item> {
        return listOf(
            Item("Pikachu"),
            Item("Pikchu"),
            Item("Bulbasaur"),
            Item("Charmander"),
            Item("Squirtle"),
            Item("Caterpie"),
            Item("Weedle"),
            Item("Pidgey"),
            Item("Rattata"),
            Item("Spearow"),
            Item("Ekans")
        )
    }
}
