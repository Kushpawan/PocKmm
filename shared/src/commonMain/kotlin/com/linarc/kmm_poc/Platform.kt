package com.linarc.kmm_poc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform