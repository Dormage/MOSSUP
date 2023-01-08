package views

import Application
import workers.SubmitAssignments
import java.awt.Color
import java.awt.Dimension
import javax.swing.*
import kotlin.math.exp

class SimilarityOptions (val application: Application) :JPanel(){
    init {
        this.layout= BoxLayout(this, BoxLayout.LINE_AXIS)
        add(Box.createHorizontalGlue())
        border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        val submit = JButton("Submit").also {
            it.background= Color(13,93,33)
        }
        this.add(submit)
        val export = JButton("Export").also {
            it.background= Color(1,70,112)
        }
        add(Box.createRigidArea(Dimension(10, 10)))
        add(export)

        export.addActionListener {
            println("EXPORT")
        }

        submit.addActionListener {
            SubmitAssignments(application).execute()
        }
    }
}