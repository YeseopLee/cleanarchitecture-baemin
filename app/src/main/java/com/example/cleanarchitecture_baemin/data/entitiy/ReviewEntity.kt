package com.example.cleanarchitecture_baemin.data.entitiy

import android.net.Uri

class ReviewEntity(
    override val id: Long,
    val title: String,
    val description: String,
    val grade: Int,
    val images: List<Uri>? = null

) : Entity