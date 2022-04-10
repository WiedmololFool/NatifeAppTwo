package com.max.natifeapptwo.presentation.userListPresentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.max.natifeapptwo.R
import androidx.fragment.app.viewModels
import com.max.natifeapptwo.databinding.FragmentUserListBinding
import com.max.natifeapptwo.presentation.userDetailsPresentation.UserDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private var binding: FragmentUserListBinding? = null
    private val viewModel: UserListViewModel by viewModels()

    private val adapter by lazy {
        UserListAdapter(onItemClickListener = { user ->
            val userDetailsFragment = UserDetailsFragment.newInstance(user.uuid)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, userDetailsFragment)
                .addToBackStack(null)
                .commit()
        },  onPageEndReached = {
            viewModel.fetchUserList()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUserListBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            rcView.adapter = adapter
            rcView.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        fun newInstance(): UserListFragment {
            return UserListFragment()
        }
    }
}