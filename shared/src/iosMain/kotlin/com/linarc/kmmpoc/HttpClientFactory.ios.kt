package com.linarc.kmmpoc

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun defaultEngine(): HttpClientEngine = Darwin.create()