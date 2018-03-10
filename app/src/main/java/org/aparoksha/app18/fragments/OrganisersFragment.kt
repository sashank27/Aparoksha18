package org.aparoksha.app18.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_organizers.*
import kotlinx.android.synthetic.main.organizer_layout.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.models.Organizer

/**
 * Created by sashank on 10/3/18.
 */

class OrganizersFragment : Fragment() {

    private val requestCode = 123
    private lateinit var callNumber : String

    fun newInstance(eventID: Long): OrganizersFragment {

        val args = Bundle()
        args.putLong("eventId", eventID)
        val fragment = OrganizersFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_organizers, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventId = arguments.getLong("eventId")

        val organizer1 = Organizer("xyzabcde",8877663242)
        val organizer2 = Organizer("xyzabcdf",5677663242)
        val organizer3 = Organizer("xyzabcdty",8877543242)

        val testOrganizers = listOf(organizer1,organizer2,organizer3)

        val event = Event(organizers = testOrganizers)

        event.organizers.map {
            val v = View.inflate(activity, R.layout.organizer_layout, null)
            v.organizerNameTV.text = it.name
            callNumber = it.phoneNumber.toString()
            v.contactTV.text = callNumber

            v.callButton.setOnClickListener({
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CALL_PHONE), requestCode)
                } else
                    startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:$callNumber")))
            })
            organizerLL.addView(v)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            requestCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:$callNumber")))
            } else {
                Log.d("TAG", "Call Permission Not Granted")
            }
        }
    }
}