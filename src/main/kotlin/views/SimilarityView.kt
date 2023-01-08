package views

import Application
import Assignment
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import org.fife.ui.rsyntaxtextarea.SyntaxConstants
import org.fife.ui.rsyntaxtextarea.Theme
import org.fife.ui.rtextarea.RTextScrollPane
import java.awt.GridLayout
import javax.swing.JPanel

class SimilarityView(application: Application, pair: Pair<Assignment, Assignment>) : JPanel(GridLayout(1, 2)) {
    private val textAreaA = RSyntaxTextArea(20, 60)
    private val textAreaB = RSyntaxTextArea(20, 60)

    //
    init {
        val theme = Theme.load(SimilarityView::class.java.getResourceAsStream("/org/fife/ui/rsyntaxtextarea/themes/dark.xml"))
        textAreaA.syntaxEditingStyle = SyntaxConstants.SYNTAX_STYLE_JAVA
        textAreaA.isCodeFoldingEnabled = true
        textAreaA.text = application.dataManager.similarityResults[pair]?.codeA ?: "Missing code!"
        textAreaA.isEditable = false;
        val spA = RTextScrollPane(textAreaA)
        textAreaB.syntaxEditingStyle = SyntaxConstants.SYNTAX_STYLE_JAVA
        textAreaB.isCodeFoldingEnabled = true
        textAreaB.text = application.dataManager.similarityResults[pair]?.codeB ?: "Missing code!"
        val spB = RTextScrollPane(textAreaB)
        theme.apply(textAreaA)
        theme.apply(textAreaB)
        add(spA)
        add(spB)
        var colorIndex: Int = 0
        application.dataManager.similarityResults[pair]?.matched?.chunked(2)?.forEach {
            for (i: Int in it[0]) {
                textAreaA.addLineHighlight(i, Application.colors[colorIndex])
            }
            for (i: Int in it[1]) {
                textAreaB.addLineHighlight(i, Application.colors[colorIndex])
            }
            colorIndex++
            colorIndex %= Application.colors.size
        }
    }
}