package xyz.deftu.filemanager.api

import java.io.File
import java.io.FileNotFoundException
import kotlin.jvm.Throws

object FileManagerAPI {
    private val deftuDir: File
        get() = File(System.getProperty("user.home"), ".deftu")

    @JvmStatic
    @Throws(FileNotFoundException::class)
    fun downloadFileManager(
        baseUrl: String = "https://example.com/"
    ) {
        val metadataUrl = "$baseUrl/metadata.properties"

    }

    private data class Metadata(
        val version: String,
        val downloadUrl: String
    )
}
