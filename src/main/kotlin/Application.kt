import views.MainMenu
import views.MainView
import java.awt.Color
import javax.swing.JFrame


class Application(val dataManager: DataManager) : JFrame("MossUP") {
    private val mainMenu = MainMenu(this)
    var mainView = MainView(this)

    init {
        setSize(1280, 720)
        defaultCloseOperation = 3
        jMenuBar = mainMenu
        add(mainView)
        menuBar
        isVisible = true
    }

    companion object {
        val colors = arrayOf(
            Color(159, 107, 182),
            Color(40, 76, 110),
            Color(128, 34, 128),
            Color(23, 101, 54),
            Color(169, 65, 81),
            Color(150, 150, 69),
            Color(42, 134, 24),
            Color(96, 96, 206),
            Color(24, 54, 108),
        )
        var settings = Settings()
    }

    data class Settings(
        var language: String = "Java",
        var ApiKey: String = "632113431"
    )
}