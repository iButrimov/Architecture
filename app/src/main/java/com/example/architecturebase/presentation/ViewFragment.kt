package com.example.architecturebase.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturebase.presentation.adapter.MainAdapter
import com.example.architecturebase.databinding.FragmentViewBinding
import com.example.architecturebase.domain.entities.Post
import java.lang.Exception

class ViewFragment : Fragment(), MvpContract.IView {

    private var _binding: FragmentViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val presenter: MvpContract.IPresenter = MvpPresenter(this)
    private val mainAdapter = MainAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }
        presenter.getPost()

        binding.listSRL.setOnRefreshListener {
            mainAdapter.items = emptyList()
            presenter.getPost()
        }
    }

    override fun showPosts(posts: List<Post>) {
        binding.listSRL.isRefreshing = false
        mainAdapter.items = posts
    }

    override fun showErrors(e: Exception) {
        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
    }
}