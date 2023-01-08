package views

import Application
import javax.swing.ImageIcon
import javax.swing.JLabel

class LoadingView(application: Application) : JLabel() {
    init {
        icon = ImageIcon("loading_purple.gif")
        setSize(128, 128)
        isVisible=true
    }
}