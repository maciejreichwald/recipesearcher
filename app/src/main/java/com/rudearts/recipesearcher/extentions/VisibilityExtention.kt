package com.rudearts.recipesearcher.extentions

import android.view.View

var View.visible : Boolean
    set(value) = when(value) {
        true -> visibility = View.VISIBLE
        false -> visibility = View.GONE
    }
    get() = when(visibility) {
        View.VISIBLE -> true
        View.GONE -> false
        else -> false
    }

var View.visibleOrNot : Boolean
    set(value) = when(value) {
        true -> visibility = View.VISIBLE
        false -> visibility = View.INVISIBLE
    }
    get() = when(visibility) {
        View.VISIBLE -> true
        View.INVISIBLE -> false
        else -> false
    }
