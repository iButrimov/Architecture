package com.example.architecturebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturebase.adapter.MainAdapter
import com.example.architecturebase.databinding.FragmentViewBinding

class ViewFragment : Fragment(), MvpContract.IView {

    private var _binding: FragmentViewBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val presenter: MvpContract.IPresenter = MvpPresenter(this)

    val mainAdapter = MainAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }
        binding.listSRL.isRefreshing = true
        presenter.getPost()

        binding.listSRL.setOnRefreshListener {
            mainAdapter.items = emptyList()
            presenter.getPost()
        }
    }
}