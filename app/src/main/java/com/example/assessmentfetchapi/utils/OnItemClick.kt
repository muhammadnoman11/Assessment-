package com.example.assessmentfetchapi.utils

import com.example.assessmentfetchapi.db.model.UserEntity


interface OnItemClick {

    fun onItemClickListener(userEntity: UserEntity)
}