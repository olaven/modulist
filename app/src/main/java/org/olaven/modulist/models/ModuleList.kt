package org.olaven.modulist.models

import android.graphics.Color
import kotlin.random.Random

class ModuleList (val name: String, val items: List<Item> = emptyList(), val superLists: List<ModuleList> = emptyList(), val color: Int = pickRandomColor())


