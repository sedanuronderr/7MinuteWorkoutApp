package com.seda.a7minuteworkoutapp.model

data class ExerciseModel( private var id: Int,
                          private var name: String,
                          private var image: Int,
                          private var isCompleted: Boolean,
                          private var isSelected: Boolean) {
}