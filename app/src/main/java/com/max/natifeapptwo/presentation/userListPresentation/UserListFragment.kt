package com.max.natifeapptwo.presentation.userListPresentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.max.natifeapptwo.UserApp
import com.max.natifeapptwo.data.repository.UserListRepository
import com.max.natifeapptwo.data.retrofit.RetrofitUserListDataSource
import com.max.natifeapptwo.data.room.DatabaseObject
import com.max.natifeapptwo.data.room.RoomUserListDataSource
import com.max.natifeapptwo.data.room.UserListDao
import com.max.natifeapptwo.data.room.entities.UserDatabase
import com.max.natifeapptwo.databinding.FragmentUserListBinding


class UserListFragment : Fragment() {

    private var binding: FragmentUserListBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(UserListViewModel::class.java)
    }
    private val adapter by lazy {
        UserListAdapter { user ->
            Toast.makeText(context, user.toString(), Toast.LENGTH_SHORT).show()
        }
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
        
        val userListRepository = UserListRepository(
            userListLocalDataSource = RoomUserListDataSource(
                DatabaseObject.apply {
                    init(context = requireActivity().applicationContext)
                }.database.userListDao()
            ),
            userListRemoteDataSource = RetrofitUserListDataSource(
                (activity?.application as UserApp).userApi
            )
        )
        viewModel.fetchUserList(userListRepository = userListRepository)
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