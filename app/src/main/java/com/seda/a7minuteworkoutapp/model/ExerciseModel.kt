package com.seda.a7minuteworkoutapp.model

data class ExerciseModel(val id: Int,
                         val name: String,
                         val image: Int,
                         var isCompleted: Boolean,
                         var isSelected: Boolean) {
}