package com.example.gabistore.model.repository.comment

import com.example.gabistore.model.data.Comment

interface CommentRepository {

    suspend fun getAllComments(productId :String) :List<Comment>
    suspend fun addNewComment(productId: String , text: String , isSuccess:(String) -> Unit )

}