package workers

import Application
import models.SimilarityTable
import views.LoadingView
import javax.swing.JProgressBar
import javax.swing.JScrollPane
import javax.swing.SwingWorker

class SubmitAssignments(private val application: Application, private val loadingView: LoadingView) :
    SwingWorker<Boolean, Boolean>() {
    override fun doInBackground(): Boolean {
        loadingView.isVisible=true
        val url = application.dataManager.submitAssignments()
        application.dataManager.similarityResults = application.dataManager.parseMossResult(url)
        return true
    }

    override fun done() {
        application.mainView.add(JScrollPane(SimilarityTable(application)))
        loadingView.isVisible=false
        application.mainView.revalidate()
    }
}