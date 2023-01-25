package views

import Application
import java.awt.Dialog
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JDialog
import javax.swing.JLabel


class AboutView(application: Application) : JDialog(application, "About MOSSUP", Dialog.ModalityType.DOCUMENT_MODAL) {
    init {
        setSize(400, 200)
        setLocation(application.width / 2 - width / 2, application.height / 2 - height / 2)

        val myPicture = ImageIO.read(File("logo_up_famnit.png"))
        val picLabel = JLabel(ImageIcon(myPicture))
        picLabel.icon = ImageIcon(
            ImageIcon("logo_up_famnit.png").image.getScaledInstance(
                100,
                50,
                Image.SCALE_DEFAULT
            )
        );
        picLabel.setSize(100, 100)
        contentPane.add(picLabel)
        isVisible = true
    }
}