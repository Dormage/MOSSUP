package views

import Application
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import workers.SubmitAssignments
import java.awt.Color
import java.awt.Dimension
import java.io.FileOutputStream
import javax.swing.*


class SimilarityOptions(val application: Application) : JPanel() {
    init {
        this.layout = BoxLayout(this, BoxLayout.LINE_AXIS)

        border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        val submit = JButton("Submit").also {
            it.background = Color(13, 93, 33)
        }
        val export = JButton("Export").also {
            it.background = Color(1, 70, 112)
        }
        val loadingView = LoadingView(application, "small")
        loadingView.isVisible = false
        add(loadingView)
        add(Box.createHorizontalGlue())
        add(export)
        add(Box.createRigidArea(Dimension(10, 10)))
        add(submit)
        export.addActionListener {
            val fileChooser = JFileChooser()
            fileChooser.dialogTitle = "Specify a file to save"
            val userSelection = fileChooser.showSaveDialog(application)
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                var fileName = fileChooser.selectedFile
                val workbook = XSSFWorkbook()
                val spreadsheet = workbook.createSheet(" MOSSUP Results ")
                var rowIndex: Int = 0;
                val header: Row = spreadsheet.createRow(rowIndex++)
                header.createCell(0).also { it.setCellValue("StudentA") }
                header.createCell(1).also { it.setCellValue("SimilarityA") }
                header.createCell(2).also { it.setCellValue("StudentB") }
                header.createCell(3).also { it.setCellValue("SimilarityA") }
                header.createCell(4).also { it.setCellValue("LinesMatched") }
                header.createCell(5).also { it.setCellValue("link") }
                application.dataManager.similarityResults.forEach { (pair, similarity) ->
                    val row: Row = spreadsheet.createRow(rowIndex++)
                    row.createCell(0).also { it.setCellValue(pair.first.studentId) }
                    row.createCell(1).also { it.setCellValue("${similarity.similarityA}") }
                    row.createCell(2).also { it.setCellValue(pair.second.studentId) }
                    row.createCell(3).also { it.setCellValue("${similarity.similarityB}") }
                    row.createCell(4).also { it.setCellValue("${similarity.linesMatched}") }
                    row.createCell(5).also { it.setCellValue(similarity.link) }
                }
                val fos = FileOutputStream(fileName)
                workbook.write(fos)
                fos.close()
            }
        }

        submit.addActionListener {
            SubmitAssignments(application, loadingView).execute()
        }
    }
}