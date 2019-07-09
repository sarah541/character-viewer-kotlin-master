package com.sample.simpsonviewer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseModel {
    @SerializedName("Heading")
    @Expose
    var heading: String? = null
    @SerializedName("Redirect")
    @Expose
    var redirect: String? = null
    @SerializedName("Infobox")
    @Expose
    var infobox: String? = null
    @SerializedName("DefinitionSource")
    @Expose
    var definitionSource: String? = null
    @SerializedName("Entity")
    @Expose
    var entity: String? = null
    @SerializedName("Type")
    @Expose
    var type: String? = null
    @SerializedName("DefinitionURL")
    @Expose
    var definitionURL: String? = null
    @SerializedName("Definition")
    @Expose
    var definition: String? = null
    @SerializedName("Image")
    @Expose
    var image: String? = null
    @SerializedName("ImageWidth")
    @Expose
    var imageWidth: String? = null
    @SerializedName("Answer")
    @Expose
    var answer: String? = null
    @SerializedName("AbstractURL")
    @Expose
    var abstractURL: String? = null
    @SerializedName("ImageHeight")
    @Expose
    var imageHeight: String? = null
    @SerializedName("AbstractText")
    @Expose
    var abstractText: String? = null
    @SerializedName("ImageIsLogo")
    @Expose
    var imageIsLogo: String? = null
    @SerializedName("AbstractSource")
    @Expose
    var abstractSource: String? = null
    @SerializedName("AnswerType")
    @Expose
    var answerType: String? = null
    @SerializedName("Abstract")
    @Expose
    var abstract: String? = null
    @SerializedName("RelatedTopics")
    @Expose
    var relatedTopics: List<RelatedTopic>? = null
}

