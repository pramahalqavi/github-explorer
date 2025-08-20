package com.example.explorer.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explorer.utils.setLightStatusBar
import com.example.explorer.view.adapter.RepositoryAdapterDelegate
import com.example.explorer.view.adapter.UserDetailAdapterDelegate
import com.example.explorer.view.viewmodel.UserDetailViewModel
import com.example.explorer.view.viewmodel.UserDetailViewModelImpl
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.ActivityUserDetailBinding
import com.github.pramahalqavi.adapterdelegate.adapter.DelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : ComponentActivity() {

    companion object {
        const val EXTRA_USERNAME = "USERNAME"
    }

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel: UserDetailViewModel by viewModels<UserDetailViewModelImpl>()

    private val delegateAdapter = DelegateAdapter(
        delegates = listOf(
            UserDetailAdapterDelegate(),
            RepositoryAdapterDelegate()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initObserver()
        intent.extras?.getString(EXTRA_USERNAME)?.let {
            viewModel.fetchUserDetails(it)
        } ?: run {
            showUsernameError()
        }
    }

    private fun initObserver() {
        viewModel.combinedDetailsLiveData.observe(this) { details ->
            binding.rvContent.visibility = View.VISIBLE
            delegateAdapter.submitList(details)
        }
        viewModel.isLoadingLiveData.observe(this) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                binding.progressCircular.visibility = View.GONE
            }
        }
        viewModel.errorLiveData.observe(this) { error ->
            with(binding) {
                error?.let {
                    tvStatus.visibility = View.VISIBLE
                    btnRetry.visibility = View.VISIBLE
                    tvStatus.text = getString(R.string.error_message)
                    btnRetry.setOnClickListener {
                        viewModel.fetchUserDetails(viewModel.getUsername())
                    }
                } ?: run {
                    tvStatus.visibility = View.GONE
                    btnRetry.visibility = View.GONE
                }
            }
        }
    }

    private fun showUsernameError() {
        with(binding) {
            tvStatus.visibility = View.VISIBLE
            btnRetry.visibility = View.VISIBLE
            tvStatus.text = getString(R.string.error_message)
            btnRetry.text = getString(R.string.back)
            btnRetry.setOnClickListener {
                finish()
            }
        }
    }

    private fun initView() {
        window.setLightStatusBar(isLight = true)
        with(binding) {
            ivBack.setOnClickListener { finish() }
            rvContent.adapter = delegateAdapter
            rvContent.layoutManager = LinearLayoutManager(this@UserDetailActivity)
            rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    val visibleThreshold = 5
                    if (totalItemCount > 1 && lastVisibleItem >= totalItemCount - visibleThreshold) {
                        viewModel.fetchMoreRepos()
                    }
                }
            })
        }
    }

    private fun showLoading() {
        with(binding) {
            progressCircular.visibility = View.VISIBLE
            rvContent.visibility = View.GONE
            tvStatus.visibility = View.GONE
            btnRetry.visibility = View.GONE
        }
    }
}