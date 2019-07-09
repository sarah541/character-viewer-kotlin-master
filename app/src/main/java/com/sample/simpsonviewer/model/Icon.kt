package com.sample.simpsonviewer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Icon {
    @SerializedName("URL")
    @Expose
    var url: String? = null
    @SerializedName("Width")
    @Expose
    var width: String? = null
    @SerializedName("Height")
    @Expose
    var height: String? = null

}
