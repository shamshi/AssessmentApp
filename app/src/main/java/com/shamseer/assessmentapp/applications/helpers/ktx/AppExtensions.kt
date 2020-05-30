package com.shamseer.assessmentapp.applications.helpers.ktx

import android.content.Intent
import android.view.View
import com.google.gson.Gson

/**
 * Created by Shamseer on 5/29/20.
 */

/** place to write common functions for the entire app  */

/** function to pass objects between activities */
inline fun <reified T> Intent.getObject(): T? {
    return if (getSerializableExtra(T::class.java.simpleName) != null) {
        getSerializableExtra(T::class.java.simpleName) as T
    } else {
        null
    }
}

/** initialize Gson library to to convert Java Objects into their JSON representation */
var gson = Gson()

/** set visibility of a view to visible */
fun View.visible() {
    this.visibility = View.VISIBLE
}

/** set visibility of a view to gone */
fun View.gone() {
    this.visibility = View.GONE
}

/** set visibility of a view to invisible */
fun View.invisible() {
    this.visibility = View.INVISIBLE
}