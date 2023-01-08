package workers

import Application
import Assignment
import views.SimilarityView
import javax.swing.JFrame
import javax.swing.SwingWorker

class LoadCodeCompare(private val application: Application, private val pair: Pair<Assignment, Assignment>) :
    SwingWorker<Boolean, Boolean>() {
    override fun doInBackground(): Boolean {
        val similarity = application.dataManager.similarityResults[pair] ?: return false
        if (similarity.codeB.isEmpty() && similarity.codeA.isEmpty()) {
            application.dataManager.parseSimilarityMatch(pair)
        }
        return true
    }

    override fun done() {
        val similarity = application.dataManager.similarityResults[pair] ?: return
        JFrame("Comparison between ${similarity.similarityA} and ${similarity.similarityB}").also {
            it.add(SimilarityView(application, pair))
            it.setSize(1280, 720)
            it.setLocation(application.x + 100, application.y + 100)
            it.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
            it.isVisible = true
        }
    }
}