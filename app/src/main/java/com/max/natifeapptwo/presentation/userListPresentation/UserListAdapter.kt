package com.max.natifeapptwo.presentation.userListPresentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.max.natifeapptwo.data.retrofit.responseModels.User
import com.max.natifeapptwo.data.room.entities.UserEntity
import com.max.natifeapptwo.databinding.ListUserItemBinding

class UserListAdapter(
    private val onItemClickListener: (UserEntity) -> Unit
) : ListAdapter<UserEntity, UserListAdapter.UserViewHolder>(UserComparator()) {

    inner class UserViewHolder(
        private val binding: ListUserItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(userItem: UserEntity) = with(binding) {
            tvName.text = "${userItem.first} ${userItem.last}"
            root.setOnClickListener {
                onItemClickListener(userItem)
            }
        }
    }

    class UserComparator : DiffUtil.ItemCallback<UserEntity>() {

        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(ListUserItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }


}