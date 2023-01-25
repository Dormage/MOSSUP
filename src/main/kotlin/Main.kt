import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.util.SystemInfo
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.UIManager


fun main(args: Array<String>) {
    FlatLightLaf.setup();
    UIManager.setLookAndFeel(FlatDarkLaf())
    if( SystemInfo.isMacOS ) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "My Application");
        System.setProperty("apple.awt.application.appearance", "system");
    }

    if( SystemInfo.isLinux ) {
        // enable custom window decorations
        JFrame.setDefaultLookAndFeelDecorated( true );
        JDialog.setDefaultLookAndFeelDecorated( true );
    }
    val dataManager = DataManager()
    val application = Application(dataManager)
}