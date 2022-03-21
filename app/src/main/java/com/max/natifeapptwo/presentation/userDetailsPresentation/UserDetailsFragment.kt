package com.max.natifeapptwo.presentation.userDetailsPresentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.max.natifeapptwo.Constants
import com.max.natifeapptwo.R
import com.max.natifeapptwo.UserApp
import com.max.natifeapptwo.data.repository.UserListRepository
import com.max.natifeapptwo.data.retrofit.RetrofitUserListDataSource
import com.max.natifeapptwo.data.room.DatabaseObject
import com.max.natifeapptwo.data.room.RoomUserListDataSource
import com.max.natifeapptwo.data.room.entities.UserEntity
import com.max.natifeapptwo.databinding.FragmentUserDetailsBinding

class UserDetailsFragment : Fragment() {

    private var binding: FragmentUserDetailsBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(
            viewModelStore,
            UserDetailsViewModelFactory(
                arguments?.getString(Constants.UUID_KEY) ?: "404"
            )
        ).get(UserDetailsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        this.binding = binding
        Log.e(Constants.TAG, arguments?.getString(Constants.UUID_KEY) ?: "404")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner) { result ->
            result.fold(
                { user -> showUser(user) },
                {}
            )

        }
        viewModel.getUser(
            UserListRepository(
                userListLocalDataSource = RoomUserListDataSource(
                    DatabaseObject.apply {
                        init(context = requireActivity().applicationContext)
                    }.database.userListDao()
                ),
                userListRemoteDataSource = RetrofitUserListDataSource(
                    (activity?.application as UserApp).userApi
                )
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showUser(userEntity: UserEntity) {
        binding?.apply {
            tvName.text = "${userEntity.title} ${userEntity.first} ${userEntity.last}"
            tvUserName.text = userEntity.username
            tvLocation.text = "${userEntity.city}, ${userEntity.country}, ${userEntity.postcode}"
            tvAge.text = userEntity.age.toString()
            tvGender.text = userEntity.gender
            tvNationality.text = userEntity.nat
            tvEmail.text = userEntity.email
            tvDate.text = userEntity.date.substringBefore("T")
            tvPassword.text = userEntity.password
            tvAddress.text = "${userEntity.state}, ${userEntity.streetName}" +
                    " ${userEntity.streetNumber}"
            Glide.with(this@UserDetailsFragment)
                .load(userEntity.picture)
                .fitCenter()
                .placeholder(R.drawable.ic_account_circle)
                .into(img)

        }
    }

    companion object {

        fun newInstance(uuid: String): UserDetailsFragment {
            return UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.UUID_KEY, uuid)
                }
            }
        }
    }
}