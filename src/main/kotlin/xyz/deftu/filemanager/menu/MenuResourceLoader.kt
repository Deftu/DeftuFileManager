package xyz.deftu.filemanager.menu

import java.awt.Font
import java.awt.GraphicsEnvironment
import java.awt.font.TextAttribute

class MenuResourceLoader {
    lateinit var outfitFont: Font
        private set
    lateinit var manropeFont: Font
        private set

    lateinit var headerFont: Font
        private set
    lateinit var header2Font: Font
        private set
    lateinit var header3Font: Font
        private set
    lateinit var bodyFont: Font
        private set
    lateinit var buttonFont: Font
        private set

    fun initialize() {
        try {
            outfitFont = Font.createFont(Font.TRUETYPE_FONT, javaClass.getResourceAsStream("/fonts/Outfit.ttf"))
            manropeFont = Font.createFont(Font.TRUETYPE_FONT, javaClass.getResourceAsStream("/fonts/Manrope.ttf"))

            headerFont = manropeFont.deriveFont(50f).deriveFont(mapOf(TextAttribute.WEIGHT to TextAttribute.WEIGHT_SEMIBOLD))
            header2Font = manropeFont.deriveFont(40f).deriveFont(mapOf(TextAttribute.WEIGHT to TextAttribute.WEIGHT_SEMIBOLD))
            header3Font = manropeFont.deriveFont(30f).deriveFont(mapOf(TextAttribute.WEIGHT to TextAttribute.WEIGHT_SEMIBOLD))
            bodyFont = manropeFont.deriveFont(20f).deriveFont(mapOf(TextAttribute.WEIGHT to TextAttribute.WEIGHT_REGULAR))
            buttonFont = manropeFont.deriveFont(25f).deriveFont(mapOf(TextAttribute.WEIGHT to TextAttribute.WEIGHT_SEMIBOLD))

            val environment = GraphicsEnvironment.getLocalGraphicsEnvironment()
            environment.registerFont(outfitFont)
            environment.registerFont(manropeFont)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
