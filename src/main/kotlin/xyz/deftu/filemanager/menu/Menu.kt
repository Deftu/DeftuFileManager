package xyz.deftu.filemanager.menu

import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.geom.RoundRectangle2D
import java.net.URI
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.UIManager

class Menu : JFrame(
    "File Manager"
), ActionListener, WindowListener {
    companion object {
        private val background1 = Color(0x28282B)
        private val background2 = Color(0x151316)
        private val background3 = Color(0x1D1D20)
        private val background4 = Color(0x131316)

        private val textColor = Color(0xFDFBF9)
    }

    private val resourceLoader = MenuResourceLoader()

    private val titleRow = JPanel()
    private val outputRow = JPanel()
    private val actionRow = JPanel()

    private val titleLabel = JLabel("File Manager")
    private val outputArea = JScrollPane()
    private val outputText = JLabel()
    private val outputList = mutableListOf<String>()
    private val supportButton = JButton("Support")
    private val closeButton = JButton("Close")

    private var support: String? = null
    var shouldExit = false

    fun display(
        accent: Int,
        support: String?
    ) {
        val accentColor = Color(accent)
        resourceLoader.initialize()

        this.support = support

        UIManager.put("Button.select", background2)

        titleLabel.foreground = textColor
        titleLabel.font = resourceLoader.header2Font
        titleLabel.horizontalAlignment = JLabel.CENTER

        outputText.foreground = textColor
        outputText.font = resourceLoader.bodyFont
        outputText.horizontalAlignment = JLabel.CENTER

        val outputAreaPane = JPanel()

        outputArea.background = background3
        outputArea.isOpaque = false
        outputArea.border = BorderFactory.createEmptyBorder()
        outputArea.viewport.isOpaque = false
        outputArea.viewport.add(outputText)

        supportButton.isFocusPainted = false
        supportButton.font = resourceLoader.buttonFont
        supportButton.foreground = textColor
        supportButton.background = background3
        supportButton.addActionListener(this)
        supportButton.border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        )

        val space = JLabel(" ")
        space.foreground = textColor
        space.font = resourceLoader.bodyFont
        space.horizontalAlignment = JLabel.CENTER

        closeButton.isFocusPainted = false
        closeButton.font = resourceLoader.buttonFont
        closeButton.foreground = textColor
        closeButton.background = background3
        closeButton.addActionListener(this)
        closeButton.border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        )

        // Setup rows
        titleRow.background = background1
        titleRow.minimumSize = Dimension(width, 80)
        titleRow.layout = FlowLayout(FlowLayout.CENTER, 0, 0)
        titleRow.add(titleLabel)

        outputAreaPane.background = background3
        outputAreaPane.minimumSize = Dimension(width - 30, 190)
        outputAreaPane.size = Dimension(width - 30, 190)
        outputAreaPane.layout = FlowLayout(FlowLayout.CENTER, 0, 0)
        outputAreaPane.add(outputArea)

        outputArea.minimumSize = Dimension(outputAreaPane.width, outputAreaPane.height)
        outputArea.size = Dimension(outputAreaPane.width, outputAreaPane.height)

        // q: why is the output area pane only as big as the output area text?
        // a: because the output area text is the only thing in the output area pane
        outputText.minimumSize = Dimension(outputAreaPane.width, outputAreaPane.height)
        outputText.size = Dimension(outputAreaPane.width, outputAreaPane.height)

        outputRow.background = background1
        outputRow.minimumSize = Dimension(width, 190)
        outputRow.add(outputAreaPane)

        actionRow.background = background1
        actionRow.minimumSize = Dimension(width, 80)
        actionRow.layout = FlowLayout(FlowLayout.CENTER, 0, 0)
        if (support != null) {
            actionRow.add(supportButton)
            actionRow.add(space)
        }
        actionRow.add(closeButton)

        // Setup frame
        setSize(500, 350)
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)
        contentPane.background = background1
        isUndecorated = true
        rootPane.border = BorderFactory.createLineBorder(accentColor, 2, true)
        shape = RoundRectangle2D.Double(0.0, 0.0, width.toDouble(), height.toDouble(), 15.0, 15.0)
        setLocationRelativeTo(null)
        addWindowListener(this)

        // Add rows
        add(titleRow)
        add(outputRow)
        add(actionRow)

        isVisible = true
    }

    private fun updateOutputText() {
        outputText.text = "<html><ul>${outputList.joinToString("") { "<li>$it</li>" }}</ul></html>"
    }

    fun handleMoved(name: String) {
        // add a line of orange text (#F17605) to the output area that says
        // "Moved $name"

        outputList.add("<font color=\"#F17605\">Moved $name</font>")
        updateOutputText()
    }

    fun handleDeleted(name: String) {
        // add a line of red text (#DD0426) to the output area that says
        // "Deleted $name"

        outputList.add("<font color=\"#DD0426\">Deleted $name</font>")
        updateOutputText()
    }

    override fun actionPerformed(e: ActionEvent) {
        when (e.source) {
            supportButton -> {
                if (support == null) return
                Desktop.getDesktop().browse(URI.create(support!!))
            }

            closeButton -> {
                dispose()
            }
        }
    }

    override fun windowOpened(e: WindowEvent) {
    }

    override fun windowClosing(e: WindowEvent) {
    }

    override fun windowClosed(e: WindowEvent) {
        shouldExit = true
    }

    override fun windowIconified(e: WindowEvent) {
    }

    override fun windowDeiconified(e: WindowEvent) {
    }

    override fun windowActivated(e: WindowEvent) {
    }

    override fun windowDeactivated(e: WindowEvent) {
    }
}
