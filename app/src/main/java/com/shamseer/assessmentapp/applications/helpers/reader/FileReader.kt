package com.shamseer.assessmentapp.applications.helpers.reader

import android.app.Activity
import android.os.AsyncTask
import com.orhanobut.logger.Logger
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference

/**
 * Created by Shamseer on 5/29/20.
 */

/** class to read the response from response body, used here to read the response from json file */
/** using AsyncTask to manage reading heavy json files
  * and doing operations in background to show the result without affecting the main thread */
class FileReader
internal constructor(private val readHelper: ReadHelper, private val activity: Activity, private val responseBody: ResponseBody) : AsyncTask<Void, Void, ByteArrayOutputStream>() {

    private val activityReference: WeakReference<Activity> = WeakReference(activity)

    /** doing operations in background to show the result without affecting the main thread */
    override fun doInBackground(vararg params: Void): ByteArrayOutputStream? {
        val buffer: ByteArrayOutputStream?
        return try {
            var inputStream: InputStream? = null
            try {
                inputStream = responseBody.byteStream()
                buffer = ByteArrayOutputStream()
                var nRead: Int = -1
                val fileReader = ByteArray(4096)
                while ({ nRead = inputStream.read(fileReader, 0, fileReader.size); nRead }() != -1) {
                    buffer.write(fileReader, 0, nRead)
                }
                buffer.flush()
                buffer
            } catch (e: IOException) {
                Logger.d(e)
                null
            } finally {
                inputStream?.close()
            }
        } catch (e: IOException) {
            Logger.d(e)
            null
        }
    }

    /** call function to show the gallery after the background computation finishes */
    override fun onPostExecute(buffer: ByteArrayOutputStream?) {
        val context = activityReference.get()
        if (context == null || activity.isFinishing) return

        if (buffer != null) {
            readHelper.showItemInfo(buffer)
        }
    }

    /** helper to interact with activity functions */
    interface ReadHelper {
        fun showItemInfo(buffer: ByteArrayOutputStream)
    }
}