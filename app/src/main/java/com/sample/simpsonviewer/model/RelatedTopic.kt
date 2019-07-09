package com.sample.simpsonviewer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RelatedTopic {
    @SerializedName("Text")
    @Expose
    var text: String? = null
    @SerializedName("Result")
    @Expose
    var result: String? = null
    @SerializedName("FirstURL")
    @Expose
    var firstURL: String? = null
    @SerializedName("Icon")
    @Expose
    var icon: Icon? = null

    val characterName: String
        get() = text!!.split(" - ")[0]

    val description: String
        get() = if (text!!.split(" - ").size > 1) text!!.split(" - ")[1] else " "

}
