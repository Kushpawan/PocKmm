package com.linarc.kmmpoc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform