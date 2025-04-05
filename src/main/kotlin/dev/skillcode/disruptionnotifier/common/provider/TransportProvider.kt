package dev.skillcode.disruptionnotifier.common.provider

import dev.skillcode.disruptionnotifier.common.dataprocessing.DataProcessor

data class TransportProvider(
    val name: String,
    val url: String,
    val dataProcessor: DataProcessor,
)
