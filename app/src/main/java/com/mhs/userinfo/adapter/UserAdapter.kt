package com.mhs.userinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mhs.userinfo.R
import com.mhs.userinfo.databinding.LayoutUserListItemBinding
import com.mhs.userinfo.response.UserListResponse
import javax.inject.Inject

class UserAdapter @Inject constructor() : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private lateinit var binding: LayoutUserListItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = LayoutUserListItemBinding.inflate(inflater, parent, false)
        return MyViewHolder()
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class MyViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserListResponse.UserListResponseItem) {
            binding.apply {
                imgUser.load(item.image) {
                    crossfade(true)
                    crossfade(500)
                    placeholder(R.drawable.person_icon)
                    error(R.drawable.person_icon)
                }

                if (item.name == "")
                    txtName.text = "- - - - -"
                else
                    txtName.text = "Name: ${item.name}"

                if (item.actor == "")
                    txtActorName.text = "- - - - -"
                else
                    txtActorName.text = "Actor name: ${item.actor}"

                if (item.house == "")
                    txtHouseName.text = "- - - - -"
                else
                    txtHouseName.text = "House: ${item.house}"

                //item click value pass
                root.setOnClickListener{
                    onItemClickListener?.let {
                        it(item)
                    }
                }


            }
        }
    }

    private var onItemClickListener: ((UserListResponse.UserListResponseItem) -> Unit)? = null
    fun setOnClickListener(listener: (UserListResponse.UserListResponseItem) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallBack =
        object : DiffUtil.ItemCallback<UserListResponse.UserListResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserListResponse.UserListResponseItem,
                newItem: UserListResponse.UserListResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UserListResponse.UserListResponseItem,
                newItem: UserListResponse.UserListResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }

    val differ = AsyncListDiffer(this, differCallBack)
}