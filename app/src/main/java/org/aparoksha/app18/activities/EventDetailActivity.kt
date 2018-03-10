package org.aparoksha.app18.activities

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.aparoksha.app18.R
import android.graphics.BitmapFactory
import org.aparoksha.app18.adapters.EventDetailViewPagerAdapter
import org.aparoksha.app18.models.Event
//import org.aparoksha.app18.ui.GlideApp
import org.aparoksha.app18.utils.AppDB
import org.aparoksha.app18.viewModels.AppViewModel
import java.text.SimpleDateFormat
import java.util.*

class EventDetailActivity : AppCompatActivity() {

    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val image = BitmapFactory.decodeResource(resources, R.drawable.hint)
        Blurry.with(this@EventDetailActivity)
                .radius(40)
                .sampling(2)
                .color(Color.argb(120,0,0,0))
                .async()
                .animate(500)
                .from(image)
                .into(eventFirstIV)

        val eventID = intent.getLongExtra("id",0)
        if (eventID != 0L) setEventDetails(eventID)
        setViewPager(eventID)

        val eventViewModel = AppViewModel.create(application)
        val appDB = AppDB.getInstance(this)
        eventViewModel.getEventById(eventID,appDB)

        eventViewModel.event.observe(this,android.arch.lifecycle.Observer {
            it?.let{
                event = it
            }
        })
    }

    private fun setViewPager(eventID: Long) {
        navigationStrip.setTitles("Description", "Organizers", "Updates")
        viewPager.adapter = EventDetailViewPagerAdapter(supportFragmentManager,eventID)
        navigationStrip.setViewPager(viewPager)
    }

    private fun setEventDetails(eventID: Long) {
        val event = Event()

        eventTitleTV.text = event.name
        //eventDescriptionTV.text = event.description
        //eventFbLinkTV.text = event.facebookEventLink

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
        if (event.timestamp < 100L) {
            eventTimeTV.text = "Online Event"
            //reminderTV.text = "Online Event"
            //reminderRL.isClickable = false
            eventLocationTV.text = "Online"
        } else {
            calendar.timeInMillis = event.timestamp.times(1000L)

            val sdf = SimpleDateFormat("MMMM d, hh:mm a")
            sdf.timeZone = TimeZone.getTimeZone("Asia/India")

            eventTimeTV.text = sdf.format(calendar.time)
            eventLocationTV.text = event.location
        }

        if (event.imageUrl != "") {
           // GlideApp.with(this).load(event.imageUrl).into(eventSecondIV)

            Blurry.with(this@EventDetailActivity)
                    .color(Color.argb(120, 0, 0, 0))
                    //.onto(eventFirstIV)
        }

        /*event.organizers.map {
            val view = View.inflate(this, R.layout.organizer_layout, null)
            view.organizerNameTV.text = it.name
            callNumber = it.phoneNumber.toString()
            view.positionTextView.text = callNumber

            view.button2.setOnClickListener({
                if (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            requestCode)
                }
                else
                    startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + callNumber)))
            })

            organizerLinearLayout.addView(view)
        }
*/
        /*if (event.facebookEventLink.isBlank()) {
            facebookLinkLayout.visibility = View.GONE
        }

        if (event.organizers.isEmpty()) {
            organizersLayout.visibility = View.GONE
        }

        if (event.additionalInfo.isEmpty()) {
            additionalInfoLayout.visibility = View.GONE
        } else {
            additionalInfoTextView.text = event.additionalInfo.joinToString { "\n" }
        }

        */

        /*reminderRL.setOnClickListener({
            if ((event.timestamp - 330 * 60) > System.currentTimeMillis() / 1000L) {
                toast("Reminder Added Successfully!!")
                if (event.location.isEmpty())
                    remindForEvent(event.timestamp, "Reminder!!",
                            event.name + " is about to start!")
                else
                    remindForEvent(event.timestamp, "Reminder!!",
                            event.name + " is about to start. Reach " + event.location + "!")
            }
        })

        facebookLinkLayout.setOnClickListener({
            val url = event.facebookEventLink
            try {
                baseContext.packageManager.getPackageInfo("com.facebook.katana", 0)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url))
                baseContext.startActivity(intent)
            } catch (e: Exception) {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(this, Uri.parse(url))
            }
        })*/
    }

}
