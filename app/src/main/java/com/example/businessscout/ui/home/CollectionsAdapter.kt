package com.example.businessscout.ui.home

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.RecyclerView
import com.example.businessscout.MainActivity
import com.example.businessscout.R
import com.example.businessscout.data.BussinessCollection
import com.example.businessscout.databinding.CollectionItemBinding
import com.example.businessscout.databinding.DialogAddCollectionBinding




class CollectionsAdapter(private val viewModel: CollectionsViewModel,    private val listener: OnCollectionClickListener

) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

    private var collections = listOf<BussinessCollection>()

    interface OnCollectionClickListener {
        fun onCollectionClick(collectionId: Int)
    }


    fun setCollections(newCollections: List<BussinessCollection>) {
        collections = newCollections
        notifyDataSetChanged() // Notify the adapter about data changes
    }



    inner class ViewHolder(private val binding: CollectionItemBinding) : RecyclerView.ViewHolder(binding.root) {


        private fun showOptionsMenu(collection: BussinessCollection, onDelete: () -> Unit) {
            val options = arrayOf("Edit", "Delete")
            AlertDialog.Builder(itemView.context)
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> showEditDialog(collection) // Edit option
                        1 -> viewModel.deleteCollection(collection.id) // Delete option
                    }
                }.show()
        }

        private fun showEditDialog(collection: BussinessCollection) {
            val binding = DialogAddCollectionBinding.inflate(LayoutInflater.from(itemView.context))
            binding.collectionName.setText(collection.collectionName)
            // check if color is valid

            binding.collectionColor.setText(collection.colorHex)

            AlertDialog.Builder(itemView.context)
                .setView(binding.root)
                .setPositiveButton("Save") { _, _ ->
                    val newName = binding.collectionName.text.toString()
                    val newColor = binding.collectionColor.text.toString()
                    val updatedCollection =
                        collection.copy(collectionName = newName, colorHex = newColor)
                    viewModel.updateCollection(updatedCollection)

                    val intent = Intent(itemView.context, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    val pendingIntent: PendingIntent =
                        PendingIntent.getActivity(itemView.context, 0, intent, 0)

                    val notificationBuilder =
                        NotificationCompat.Builder(itemView.context, "collection_updates")
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle("Collection Updated")
                            .setContentText("Your collection has been successfully updated.")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)

                    with(NotificationManagerCompat.from(itemView.context)) {
                        if (ActivityCompat.checkSelfPermission(
                                itemView.context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@with
                        }
                        notify(1, notificationBuilder.build())
                    }


                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        fun bind(collection: BussinessCollection, onEdit: () -> Unit, onDelete: () -> Unit) {
            binding.collectionName.text = collection.collectionName
            binding.optionsButton.setOnClickListener {
                // Show options menu or dialog
                showOptionsMenu(collection, onDelete)
            }

            val color = try {
                if (collection.colorHex.isNotEmpty()) Color.parseColor("#" + collection.colorHex) else Color.parseColor(
                    "#FF0000"
                )
            } catch (e: IllegalArgumentException) {
                // log to logcat
                Log.e("CollectionsAdapter", "Invalid color: ${collection.colorHex}")


                Color.parseColor("#FFFFFF") // Default color in case of an invalid hex string
            }
            binding.root.setBackgroundColor(color)

            binding.root.setBackgroundColor(
                color
            )
            binding.root.setOnClickListener {
                val collectionId = collections[adapterPosition].id
                listener.onCollectionClick(collectionId)

            }
        }
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collections[position],
            onEdit = { /* TODO */ },
            onDelete = { /* TODO */ })
    }

    override fun getItemCount() = collections.size
}



