package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElectionsFragment: Fragment() {

    private lateinit var binding: FragmentElectionBinding

    private val viewModel: ElectionsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_election,
                container,
                false
        )

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.showErrorMessageInt.observe(this, {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        val upcomingAdapter = ElectionListAdapter(
                ElectionListener {
                    viewModel.displayVoterInfo(it)
                }
        )

        binding.upcomingRecyclerView.adapter = upcomingAdapter

        val savedAdapter = ElectionListAdapter(
                ElectionListener {
                    viewModel.displayVoterInfo(it)
                }
        )

        binding.savedRecyclerView.adapter = savedAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getRemoteElections()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.navigateToVoterInfo.observe(viewLifecycleOwner, { election ->
            election?.let {
                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                        it.id,
                        it.division
                ))

                viewModel.displayVoterInfoComplete()
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSavedElections()
    }
}