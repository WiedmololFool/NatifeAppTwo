package com.max.natifeapptwo.presentation.userDetailsPresentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.max.natifeapptwo.Constants
import com.max.natifeapptwo.R
import com.max.natifeapptwo.app.appComponent
import com.max.natifeapptwo.data.room.entities.UserEntity
import com.max.natifeapptwo.databinding.FragmentUserDetailsBinding
import javax.inject.Inject

class UserDetailsFragment : Fragment() {

    private var binding: FragmentUserDetailsBinding? = null
    private lateinit var viewModel: UserDetailsViewModel

    @Inject
    lateinit var viewModelFactory: UserDetailsViewModelFactory.Factory

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        viewModel = viewModelFactory
            .create(arguments?.getString(Constants.UUID_KEY) ?: Constants.UUID_DEFAULT_VALUE)
            .create(UserDetailsViewModel::class.java)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner) { user ->
            showUser(user)
        }
        viewModel.getUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showUser(userEntity: UserEntity) {
        binding?.apply {
            with(userEntity) {
                tvName.text = resources.getString(
                    R.string.tv_name_text, title, first, last
                )
                tvUserName.text = username
                tvLocation.text = resources.getString(
                    R.string.tv_location_text, city, country, postcode
                )
                tvAge.text = age.toString()
                tvGender.text = gender
                tvNationality.text = nat
                tvEmail.text = email
                tvDate.text = date.toFormattedDate()
                tvPassword.text = password
                tvAddress.text = resources.getString(
                    R.string.tv_address_text, state, streetName, streetNumber
                )
                Glide.with(this@UserDetailsFragment)
                    .load(picture)
                    .fitCenter()
                    .placeholder(R.drawable.ic_account_circle)
                    .into(img)
            }
        }
    }

    private fun String.toFormattedDate(): String = this.substringBefore("T")

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