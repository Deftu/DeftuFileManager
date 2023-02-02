package xyz.deftu.filemanager

import org.slf4j.LoggerFactory
import xyz.deftu.filemanager.menu.Menu
import java.io.File

val logger = LoggerFactory.getLogger("FileManager")
const val ACCENT = 0xC91212

fun main(args: Array<String>) {
    val argMap = ArgumentMap.parse(args)

    val guiOnly = argMap.has("onlygui") && argMap.getSingular("onlygui") == "true"
    val silent = argMap.has("silent") && argMap.getSingular("silent") == "true"
    val move = compileMove(argMap.getAll("move"))
    val delete = if (argMap.has("delete")) argMap.getAll("delete")!! else emptyList()

    val accent = argMap.getSingular("accent")?.toIntOrNull() ?: ACCENT
    val support = argMap.getSingular("support")

    val menu = Menu()
    if (guiOnly || !silent) {
        Thread({
            menu.display(accent, support)
        }, "Menu Thread").start()
        menu.handleMoved("hi")
    } else menu.shouldExit = true
    if (guiOnly) return

    // First, move files
    for (pair in move) {
        val toMove = File(pair.first)
        if (!validateFile(toMove)) {
            logger.warn("File ${toMove.absolutePath} is not valid, skipping")
            continue
        }

        val destination = File(pair.second)
        if (!validateFile(destination)) {
            logger.warn("File ${destination.absolutePath} is not valid, skipping")
            continue
        }

        // Ensure that the destination is a directory, and exists
        if (!destination.exists()) {
            destination.mkdirs()
        } else if (!destination.isDirectory) {
            logger.warn("File ${destination.absolutePath} is not a directory, skipping")
            continue
        }

        // Move the file
        if (!toMove.renameTo(File(destination, toMove.name))) {
            logger.warn("Failed to move file ${toMove.absolutePath} to ${destination.absolutePath}, skipping")
        } else menu.handleMoved(toMove.name)
    }

    // Then, delete files
    for (file in delete) {
        val toDelete = File(file)
        if (!validateFile(toDelete)) {
            logger.warn("File ${toDelete.absolutePath} is not valid, skipping")
            continue
        }

        if (!toDelete.deleteAccordingly()) {
            logger.warn("Failed to delete file ${toDelete.absolutePath}, skipping")
        } else menu.handleDeleted(toDelete.name)
    }

    while (!menu.shouldExit) {
        Thread.sleep(1)
    }
}

private fun compileMove(input: List<String>?): List<Pair<String, String>> {
    if (input == null) return emptyList()

    val output = mutableListOf<Pair<String, String>>()
    for (pair in input) {
        val split = pair.split(":")
        if (split.size != 2) {
            // TODO: Warn
            continue
        }

        output.add(Pair(split[0], split[1]))
    }

    return output
}

private fun validateFile(file: File): Boolean {
    return file.exists() && file.canRead() && file.canWrite()
}

private fun File.deleteAccordingly(): Boolean {
    return if (isDirectory) deleteRecursively() else delete()
}
