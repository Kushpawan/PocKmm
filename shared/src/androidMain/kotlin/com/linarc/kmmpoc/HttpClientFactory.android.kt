package com.linarc.kmmpoc

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun defaultEngine(): HttpClientEngine = OkHttp.create()