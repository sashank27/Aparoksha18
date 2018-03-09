package org.aparoksha.app18.activities

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.aparoksha.app18.R
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.richpath.RichPath
import com.richpath.RichPathView
import com.richpathanimator.RichPathAnimator


class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val image = BitmapFactory.decodeResource(resources, R.drawable.hint)
        Blurry.with(this@EventDetailActivity)
                .color(Color.argb(120, 0, 0, 0))
                .async()
                .from(image)
                .into(eventFirstIV)
    }
}
