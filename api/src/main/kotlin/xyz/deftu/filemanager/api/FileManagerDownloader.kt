package xyz.deftu.filemanager.api

import java.io.File
import java.net.URL
import java.util.*

internal object FileManagerDownloader {
    fun downloadMetadata(
        url: String,
        directory: File
    ): Metadata {
        // get metadata
        // parse metadata
        // return metadata

        val props = Properties()
        props.load(URL(url).openStream())
        println(props)
        return Metadata(
            props.getProperty("version"),
            props.getProperty("download_url")
        )
    }

    data class Metadata(
        val version: String,
        val downloadUrl: String
    )
}