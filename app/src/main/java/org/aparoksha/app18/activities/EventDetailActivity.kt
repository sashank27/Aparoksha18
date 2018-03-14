package org.aparoksha.app18.activities

import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.aparoksha.app18.R
import android.graphics.BitmapFactory
import android.util.Log
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import org.aparoksha.app18.GlideApp
import org.aparoksha.app18.adapters.EventDetailViewPagerAdapter
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.viewModels.AppViewModel
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class EventDetailActivity : AppCompatActivity() {

    private var event: Event = Event()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val eventID = intent.getLongExtra("id",0)

        val eventViewModel = AppViewModel.create(application)
        eventViewModel.getEvents(this,false)

        setViewPager(eventID)

        eventViewModel.events.observe(this, Observer {
            it?.let {
                val temp = it.find { it.id == eventID }
                if (it.find { temp?.id== eventID }!= null)
                    event = temp!!
                if (eventID != 0L)
                    setEventDetails()
                setViewPager(eventID)
            }
        })
    }

    private fun setViewPager(eventID: Long) {
        navigationStrip.setTitles("Description", "Organizers", "Updates")
        viewPager.adapter = EventDetailViewPagerAdapter(supportFragmentManager,eventID)
        navigationStrip.setViewPager(viewPager)
    }

    private fun setEventDetails() {

        eventTitleTV.text = event.name

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
        if (event.timestamp < 20000L) {
            eventTimeTV.text = "Online Event"
            eventLocationTV.text = "Online"
        } else {
            calendar.timeInMillis = event.timestamp.times(1000L)

            val sdf = SimpleDateFormat("MMMM d, hh:mm a")
            sdf.timeZone = TimeZone.getTimeZone("Asia/India")

            eventTimeTV.text = sdf.format(calendar.time)
            eventLocationTV.text = event.location
        }

            GlideApp.with(this)
                    .load(event.imageUrl)
                    .placeholder(R.drawable.logo)
                    .into(eventSecondIV)

            GlideApp.with(this@EventDetailActivity)
                    .asBitmap()
                    .load(event.imageUrl)
                    .placeholder(R.drawable.background)
                    .into(object : SimpleTarget<Bitmap>(500,500) {
                        override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                            Blurry.with(this@EventDetailActivity)
                                    .radius(30)
                                    .sampling(2)
                                    .color(Color.argb(120,0,0,0))
                                    .async()
                                    .animate(500)
                                    .from(resource)
                                    .into(eventFirstIV)

                        }
                    })

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
