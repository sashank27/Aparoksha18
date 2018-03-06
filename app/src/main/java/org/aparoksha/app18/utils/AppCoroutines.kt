package org.aparoksha.app18.utils

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import org.aparoksha.app18.models.Event
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Created by akshat on 6/3/18.
 */

class WrappedContinuation<T>(val c: Continuation<T>) : Continuation<T> {
    var isResolved = false
    override val context: CoroutineContext
        get() = c.context

    override fun resume(value: T) {
        if (!isResolved) {
            isResolved = true
            c.resume(value)
        }
    }

    override fun resumeWithException(exception: Throwable) {
        if (!isResolved) {
            isResolved = true
            c.resumeWithException(exception)
        }
    }

}

inline suspend fun <T> suspendCoroutineWrapped(crossinline block: (WrappedContinuation<T>) -> Unit): T =
        suspendCoroutine { c ->
            val wd = WrappedContinuation(c)
            block(wd)
        }

suspend fun readEventsAsync(ref: String): List<Event> =
        suspendCoroutineWrapped { d ->
            val client = OkHttpClient()
            try {
                val request = Request.Builder()
                        .url(ref)
                        .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val data = Moshi.Builder()
                            .build()
                            .adapter<Array<Event>>(Array<Event>::class.java)
                            .fromJson(response.body()?.string()).toList()

                    d.resume(data)
                } else d.resumeWithException(Exception("Unsuccessful response"))
            } catch (e: Exception) {
                d.resumeWithException(e)
            }
        }


suspend fun <T> readFromDatabaseAsync(dbref: DatabaseReference, dataType: Class<T>): T = suspendCoroutineWrapped { d ->
    dbref.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onCancelled(e: DatabaseError?) {
            d.resumeWithException(e?.toException() ?: Exception("cancelled"))
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            try {
                val data: T? = snapshot.getValue(dataType)
                if (data != null) {
                    d.resume(data)
                } else {
                    val errMessage =
                            if (snapshot.value == null)
                                "data missing"
                            else
                                "invalid read data format"
                    d.resumeWithException(Exception(errMessage))
                }
            } catch (e: Exception) {
                d.resumeWithException(e)
            }
        }
    })
}
