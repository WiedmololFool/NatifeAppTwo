package com.max.natifeapptwo.presentation.userListPresentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.max.natifeapptwo.R
import com.max.natifeapptwo.data.room.entities.UserEntity
import com.max.natifeapptwo.databinding.ListUserItemBinding

private const val USERS_PREFETCH_COUNT = 5

class UserListAdapter(
    private val onItemClickListener: (UserEntity) -> Unit,
    private val onPageEndReached: () -> Unit
) : ListAdapter<UserEntity, UserListAdapter.UserViewHolder>(UserComparator()) {

    inner class UserViewHolder(
        private val binding: ListUserItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(userItem: UserEntity) = with(binding) {
            tvName.text = itemView.context.resources.getString(
                R.string.tv_short_name,
                userItem.first,
                userItem.last
            )
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
        val currentItem = getItem(position)

        if (currentItem != null){
            holder.bindItem(getItem(position))
        }

        if(itemCount - position == USERS_PREFETCH_COUNT){
            onPageEndReached.invoke()
        }
    }


}