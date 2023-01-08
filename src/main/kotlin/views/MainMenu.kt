package views

import Application
import workers.LoadAssignments
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import javax.swing.*


class MainMenu(val application: Application) : JMenuBar() {
    private val mainBar = JMenu("File")
    private val preferences = JMenu("Preferences")
    private val help = JMenu("Help")
    private val menuNew = JMenuItem("New")

    init {
        mainBar.add(JMenuItem(Open(application)))
        mainBar.add(JMenuItem(MossOptionsExistingAction(application)))
        mainBar.add(menuNew)
        preferences.add(JMenuItem(MossOptionsAction(application)))
        preferences.add(JMenuItem(OptionsAction()))
        help.add(AboutAction(application))
        add(mainBar)
        add(preferences)
        add(help)
    }

    class MossOptionsAction(val application: Application) : AbstractAction("Moss Options") {
        override fun actionPerformed(e: ActionEvent) {
            SettingsView(application)
        }
    }

    class MossOptionsExistingAction(private val application: Application) : AbstractAction("Open Existing") {
        override fun actionPerformed(e: ActionEvent) {
            val link = JOptionPane.showInputDialog("Link to previous MOSS results:")
            if (link != null && link.isNotEmpty()) {
                SwingUtilities.invokeLater {
                    application.mainView.layout = GridBagLayout()
                    application.mainView.add(LoadingView(application))
                    application.mainView.revalidate()
                }
               // LoadAssignments(application, File(""), false, URL(link)).execute()
            }
        }
    }

    class OptionsAction : AbstractAction("Options") {
        override fun actionPerformed(e: ActionEvent) {
            println("Options")
        }
    }

    class AboutAction(private val application: Application) : AbstractAction("About") {
        override fun actionPerformed(e: ActionEvent) {
            AboutView(application)
        }
    }

    class Open(private val application: Application) : AbstractAction("Open") {
        override fun actionPerformed(e: ActionEvent) {
            val fc = JFileChooser().also {
                it.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            }
            fc.showOpenDialog(application)
            val file = fc.selectedFile
            SwingUtilities.invokeLater {
                application.mainView.layout = GridBagLayout()
                application.mainView.add(LoadingView(application))
                application.mainView.revalidate()
            }
            LoadAssignments(application, file).execute()
        }
    }
}