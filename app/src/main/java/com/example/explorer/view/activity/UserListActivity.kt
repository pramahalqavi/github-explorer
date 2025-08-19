package com.example.explorer.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explorer.utils.Router
import com.example.explorer.view.adapter.UserListAdapterDelegate
import com.example.explorer.view.viewmodel.UserListViewModel
import com.example.explorer.view.viewmodel.UserListViewModelImpl
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.ActivityUserListBinding
import com.github.pramahalqavi.adapterdelegate.adapter.DelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : ComponentActivity() {

    private lateinit var binding: ActivityUserListBinding
    private val viewModel: UserListViewModel by viewModels<UserListViewModelImpl>()

    private val delegateAdapter = DelegateAdapter(
        delegates = listOf(
            UserListAdapterDelegate(::onClickUserItem)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initObserver()
        viewModel.fetchUsers(isInitial = true)
    }

    private fun initView() {
        with(binding) {
            rvUsers.adapter = delegateAdapter
            rvUsers.layoutManager = LinearLayoutManager(this@UserListActivity)
            rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    val visibleThreshold = 5
                    if (!viewModel.isLoadingPagination() && lastVisibleItem >= totalItemCount - visibleThreshold) {
                        viewModel.searchUsersNextPage()
                    }
                }
            })
            etSearchBar.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) = Unit
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val query: String = p0?.toString().orEmpty()
                    viewModel.searchUsers(query)
                }
            })
        }
    }

    private fun showLoading() {
        with(binding) {
            progressCircular.visibility = View.VISIBLE
            rvUsers.visibility = View.GONE
            tvStatus.visibility = View.GONE
            btnRetry.visibility = View.GONE
        }
    }

    private fun initObserver() {
        viewModel.userListLiveData.observe(this) {
            binding.rvUsers.visibility = View.VISIBLE
            delegateAdapter.submitList(it)
        }
        viewModel.errorLiveData.observe(this) { error ->
            with(binding) {
                error?.let {
                    tvStatus.visibility = View.VISIBLE
                    btnRetry.visibility = View.VISIBLE
                    tvStatus.text = getString(R.string.error_message)
                    btnRetry.setOnClickListener {
                        viewModel.retry()
                    }
                } ?: run {
                    tvStatus.visibility = View.GONE
                    btnRetry.visibility = View.GONE
                }
            }
        }
        viewModel.isLoadingLiveData.observe(this) {
            if (it) {
                showLoading()
            } else {
                binding.progressCircular.visibility = View.GONE
            }
        }
    }

    private fun onClickUserItem(username: String) {
        Router.routeToUserDetail(this, username)
    }
}