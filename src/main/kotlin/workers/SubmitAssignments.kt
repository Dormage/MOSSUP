package workers

import Application
import models.SimilarityTable
import javax.swing.JScrollPane
import javax.swing.SwingWorker

class SubmitAssignments(private val application: Application) : SwingWorker<Boolean, Boolean>() {
    override fun doInBackground(): Boolean {
        val url = application.dataManager.submitAssignments()
        application.dataManager.similarityResults = application.dataManager.parseMossResult(url)
        return true
    }

    override fun done() {
        application.mainView.add(JScrollPane(SimilarityTable(application)))
        application.mainView.revalidate()
    }
}