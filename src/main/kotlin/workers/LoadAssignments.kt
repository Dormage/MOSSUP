package workers

import Application
import AssignmentsTable
import models.SimilarityTable
import views.SimilarityOptions
import java.awt.BorderLayout
import java.io.File
import java.nio.ByteOrder
import javax.swing.BoxLayout
import javax.swing.JScrollPane
import javax.swing.SwingWorker


class LoadAssignments(
    private val application: Application,
    private val file: File,
) :
    SwingWorker<Boolean, Boolean>() {
    override fun doInBackground(): Boolean {
        application.dataManager.loadAssignments(file)
        //val url = application.dataManager.submitAssignments()
        //application.dataManager.similarityResults = application.dataManager.parseMossResult(url)
        return true
    }

    override fun done() {
        application.mainView.removeAll()
        application.mainView.layout = BoxLayout(application.mainView, BoxLayout.PAGE_AXIS)
        application.mainView.add(JScrollPane(AssignmentsTable(application.dataManager.assignments)))
        application.mainView.revalidate()
        application.add(SimilarityOptions(application), BorderLayout.SOUTH)
    }
}