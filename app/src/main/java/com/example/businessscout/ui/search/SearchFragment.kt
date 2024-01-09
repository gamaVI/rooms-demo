package com.example.businessscout.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.businessscout.databinding.FragmentSearchBinding
import androidx.appcompat.app.AppCompatActivity
import com.example.businessscout.MyApplication
import com.example.businessscout.R
import com.example.businessscout.data.Bussiness
import com.example.businessscout.data.BussinessCollectionCrossRef
import com.example.businessscout.databinding.DialogBusinessDetailsBinding

import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker


class SearchFragment : Fragment(){

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Configuration.getInstance().load(
            requireContext(),
            requireActivity().getSharedPreferences(getString(R.string.app_name), AppCompatActivity.MODE_PRIVATE)
        )
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mapView = binding.osmmap
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        val mapController = mapView.controller
        mapController.setZoom(20.0)
        val startPoint = GeoPoint(46.056946, 15.505751) // Default start point

        mapController.setCenter(startPoint)

        // Observe business data and add markers
        searchViewModel.businesses.observe(viewLifecycleOwner) { businesses ->
            businesses.forEach { business ->
                val geoPoint = GeoPoint(business.lat, business.lng)
                // set some content to the marker
                val marker = Marker(mapView).apply {
                    position = GeoPoint(business.lat, business.lng)
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = business.naziv // For example
                    setOnMarkerClickListener { marker, mapView ->
                        // Open dialog or navigate to a detailed fragment
                        showBusinessDetailsDialog(business)
                        true
                    }
                }
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                mapView.overlays.add(marker)
            }
            mapView.invalidate()
        }
        return root
    }

    private fun showBusinessDetailsDialog(business: Bussiness) {
        val binding = DialogBusinessDetailsBinding.inflate(LayoutInflater.from(context))
        binding.businessName.text = business.naziv
        binding.businessAddress.text = business.address
        binding.gsm.text = business.gsm
        binding.email.text = business.email
        binding.website.text = business.website
        binding.tax.text = business.tax
        binding.datumVpisa.text = business.datumVpisa


        // Setup other views in the dialog

        AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton("Add to Collection") { dialog, id ->

                addToCollection(business)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAddToCollectionDialog(business: Bussiness) {
        // Logic to show a dialog or a new screen for adding business to a collection
    }

    private fun addToCollection(business: Bussiness, colletionId: Int = 0) {
        val crossRef = BussinessCollectionCrossRef(
            businessId = business.id,
            collectionId = colletionId
        )
        val thread = Thread {
            MyApplication.database.bussinessCollectionCrossRefDao().insertCrossRef(
                crossRef
            )
        }
        thread.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}