package com.sample.simpsonviewer

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.afollestad.materialdialogs.MaterialDialog
import com.sample.simpsonviewer.adapter.CharactersAdapter
import com.sample.simpsonviewer.data.remote.ApiService
import com.sample.simpsonviewer.model.BaseModel
import com.sample.simpsonviewer.model.RelatedTopic
import dagger.android.AndroidInjection
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    private var dialog: MaterialDialog? = null
    private val relatedTopicList = ArrayList<RelatedTopic>()
    private var charactersAdapter: CharactersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val linearLayoutManager = LinearLayoutManager(this)
        charactersAdapter = CharactersAdapter(relatedTopicList, resources.getBoolean(R.bool.isTablet))
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                linearLayoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = charactersAdapter
        getCharacters()
    }

    private fun getCharacters() {
        apiService.getCharacterViewer(getString(R.string.query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { charactersAdapter!!.notifyDataSetChanged() }
                .subscribe(object : SingleObserver<BaseModel> {
                    override fun onSubscribe(d: Disposable) {
                        val builder = MaterialDialog.Builder(this@MainActivity)
                                .title(resources.getString(R.string.loading))
                                .content(resources.getString(R.string.please_wait))
                                .cancelable(false)
                                .progress(true, 0)
                        dialog = builder.build()
                        dialog!!.show()
                    }

                    override fun onSuccess(baseModel: BaseModel) {
                        dialog!!.dismiss()
                        baseModel.relatedTopics?.let {
                            this@MainActivity.relatedTopicList.addAll(it)
                        }
                    }

                    override fun onError(e: Throwable) {
                        dialog!!.dismiss()
                        e.printStackTrace()
                        val builder = MaterialDialog.Builder(this@MainActivity)
                                .title(R.string.something_went_wrong)
                                .positiveText(R.string.retry)
                                .onPositive { _, _ -> getCharacters() }
                                .cancelable(false)
                        dialog = builder.build()
                        dialog!!.show()
                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        search(searchView)
        return true
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                charactersAdapter?.filter?.filter(newText)
                return true
            }
        })
    }
}
