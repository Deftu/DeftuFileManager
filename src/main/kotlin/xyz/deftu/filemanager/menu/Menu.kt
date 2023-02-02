
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.geom.RoundRectangle2D
import javax.swing.*

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

    //the main 3 rows
    private val titleRow = JPanel()
    private val outputRow = JPanel()
    private val actionRow = JPanel()

    //the menu title
    private val titleLabel = JLabel("File Manager")
    //the output area scrollbar, text area, and the list with the items
    private val outputArea = JScrollPane()
    private val outputText = JTextPane()
    private val outputList = mutableListOf<String>()
    //buttons!
    private val supportButton = JButton("Support")
    private val closeButton = JButton("Close")

    private var support: String? = null

    // ???
    var shouldExit = false

    fun display(
        accent: Int,
        support: String?
    ) {
        val accentColor = Color(accent)
        resourceLoader.initialize()

        this.support = support

        UIManager.put("Button.select", background2)

        //title label, should be fine
        titleLabel.foreground = textColor
        titleLabel.font = resourceLoader.header2Font
        titleLabel.horizontalAlignment = JLabel.CENTER

        outputText.foreground = textColor
        outputText.background = background3
        outputText.isEditable = false
        outputText.contentType = "text/html"
        outputText.font = resourceLoader.bodyFont

        outputArea.background = background3
        outputArea.isOpaque = false
        outputArea.border = BorderFactory.createEmptyBorder()
        outputArea.viewport.isOpaque = false
        outputArea.viewport.add(outputText)
        outputArea.viewportBorder = BorderFactory.createEmptyBorder(0, 15, 0, 15)
        outputArea.verticalScrollBar.autoscrolls = true

        supportButton.isFocusPainted = false
        supportButton.font = resourceLoader.buttonFont
        supportButton.foreground = textColor
        supportButton.background = background3
        supportButton.addActionListener(this)
        //TODO: add this only on hover
        supportButton.border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        )

        //TODO: make this not goofy
        val space = JLabel(" ")
        space.foreground = textColor
        space.font = resourceLoader.bodyFont
        space.horizontalAlignment = JLabel.CENTER

        closeButton.isFocusPainted = false
        closeButton.font = resourceLoader.buttonFont
        closeButton.foreground = textColor
        closeButton.background = background3
        closeButton.addActionListener(this)
        //TODO: add this only on hover
        closeButton.border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        )

        // Setup rows | Works!
        titleRow.background = background1
        titleRow.minimumSize = Dimension(width, 80)
        titleRow.layout = FlowLayout(FlowLayout.CENTER, 0, 0)
        titleRow.add(titleLabel)

        // the scrollbar
        outputArea.minimumSize = Dimension(outputRow.width, outputRow.height)
        outputArea.size = Dimension(outputRow.width, outputRow.height)

        outputRow.background = background1
        outputRow.minimumSize = Dimension(width, 190)
        outputRow.layout = GridLayout(1, 1)
        outputRow.add(outputArea)

        actionRow.background = background1
        actionRow.minimumSize = Dimension(width, 80)
        actionRow.layout = FlowLayout(FlowLayout.CENTER, 10, 0)
        // this make sthe buttons move down
        actionRow.border = BorderFactory.createEmptyBorder(height + 60, 0, 10, 0)
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
        //append to outputText
        outputText.text = outputList.joinToString("<html><ul>${outputList.joinToString("") { "<li>$it</li>" }}</ul><br></html>")
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