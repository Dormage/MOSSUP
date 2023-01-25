package views

import Application
import javax.swing.ImageIcon
import javax.swing.JLabel

class LoadingView(application: Application, size: String = "large") : JLabel() {
    init {
        icon = if (size == "large") {
            ImageIcon("loading_large.gif")
        } else {
            ImageIcon("loading_small.gif")
        }
        isVisible = true
    }
}