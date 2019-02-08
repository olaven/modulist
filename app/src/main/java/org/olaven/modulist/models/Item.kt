package org.olaven.modulist.models

data class Item(val name: String, var done: Boolean, val attachments: List<Any> = emptyList())