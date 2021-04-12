package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElectionsFragment: Fragment() {

    private lateinit var binding: FragmentElectionBinding

    //TODO: Declare ViewModel
//    private val viewModel: ElectionsViewModel by viewModels<ElectionsViewModel> {
//        ElectionsViewModelFactory(
//                DefaultElectionRepository(
//                        electionLocalDataSource = ElectionLocalDataSource(ElectionDatabase.getInstance(requireActivity().applicationContext).electionDao),
//                        electionRemoteDataSource = ElectionRemoteDataSource()
//                ),
//                requireActivity().application
//        )
//    }

    private val viewModel: ElectionsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

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

        val upcomingAdapter = ElectionListAdapter(
                ElectionListener {

                }
        )

        binding.upcomingRecyclerView.adapter = upcomingAdapter

        val savedAdapter = ElectionListAdapter(
                ElectionListener {

                }
        )

        binding.savedRecyclerView.adapter = savedAdapter

        //TODO: Populate recycler adapters
        viewModel.getSavedAndRemoteElections()

        return binding.root
    }
}