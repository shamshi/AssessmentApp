package com.shamseer.assessmentapp.applications.helpers.email

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.orhanobut.logger.Logger
import com.shamseer.assessmentapp.R
import com.shamseer.assessmentapp.applications.helpers.ktx.gone
import com.shamseer.assessmentapp.applications.helpers.ktx.visible
import com.shamseer.assessmentapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.*
import java.io.IOException

/**
 * Created by Shamseer on 5/30/20.
 */

/** open class to send email by downloading and saving image from server url  */
@SuppressLint("Registered")
open class SendEmail: BaseActivity() {

    private val email = ""
    var emailSubject = ""
    var emailMessage = ""
    var emailAttachment: Bitmap? = null
    val completableJob = Job() // cancellable background job to write image on disc
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob) // coroutineScope to to run coroutine

    /** function to download image from the server,
      * using Glide to download image from server url  */
    fun downloadImage(imageUrl: String) {
        Glide.with(this) // Glide to download image from server url
            .asBitmap()
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // successfully downloaded the bitmap image
                    emailAttachment = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // clearing the reference as we can no longer have the bitmap
                    emailAttachment = null
                }
            })
    }

    /** save image - using coroutineScope to to run coroutine,
      * dealing with task cancellations,
      * exception handling and avoiding runtime- and memory-leaks  */
    fun saveImage() {
        coroutineScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            withContext(Dispatchers.Main) {
                emailLoader.visible()
            }

            if(emailAttachment != null) {
                writeImage(this@SendEmail, emailAttachment!!)
            }

            withContext(Dispatchers.Main) {
                emailLoader.gone()
            }
        }
    }

    /** to handle uncaught exceptions while saving image */
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Logger.d(error.printStackTrace())
        showToast(R.string.error_save_image)
    }

    /** write image to disc */
    private fun writeImage(inContext: Context, inImage: Bitmap) {

        val relativeLocation = Environment.DIRECTORY_PICTURES // Set the image save location to "Pictures"
        val contentValues: ContentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis().toString())
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
            }
        }

        // Insert image to the specified location
        val resolver: ContentResolver = inContext.contentResolver
        val uri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        try {
            uri?.let { imageUri ->
                val stream = resolver.openOutputStream(imageUri)

                stream?.let { imageStream ->
                    if (!inImage.compress(Bitmap.CompressFormat.JPEG, 80, imageStream)) {
                        showToast(R.string.error_save_image)
                        Logger.d(R.string.error_save_image)
                    }
                } ?: Logger.d(R.string.error_output_stream)

            } ?: Logger.d(R.string.error_media_store)

        } catch (e: IOException) {
            if (uri != null) {
                resolver.delete(uri, null, null)
            }
            showToast(R.string.error_save_image)
            Logger.d(e)
        } finally {
            if (uri != null) {
                sendEmail(uri)
            }
        }
    }

    /** send email with subject, body and attachment */
    private fun sendEmail(imageUri: Uri?) {
        try {
            val emailIntent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(email))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            if(imageUri != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri) // Attach Image
            }
            emailIntent.putExtra(Intent.EXTRA_TEXT, emailMessage)
            this.startActivity(Intent.createChooser(emailIntent, getString(R.string.text_sending_email)))
        } catch (t: Throwable) {
            showToast(R.string.error_send_email)
        }
    }

}