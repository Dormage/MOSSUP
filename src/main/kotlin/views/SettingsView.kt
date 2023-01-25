package views

import Application
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*


class SettingsView(private val application: Application) : JDialog(application, "MOSS Options", true) {
    private val languages = listOf(
        "C",
        "C++",
        "Java",
        "C#",
        "Python",
        "VisualBasic",
        "Javascript",
        "FORTRAN",
        "ML",
        "Haskell",
        "Lisp",
        "Scheme",
        "Pascal",
        "Modula2",
        "Ada",
        "Perl",
        "TCL",
        "Matlab",
        "VHDL",
        "Verilog",
        "Spice",
        "MIPSassembly",
        "a8086assembly",
        "a8086assembly",
        "HCL2"
    )
    private val languageSelection = JComboBox(languages.toTypedArray())
    private val apiKeyInput = JTextField(Application.settings.ApiKey)
    private val confirmButton = JButton("Confirm").also {

        it.background= Color(121,53,145) }


    init {
        layout = GridBagLayout()
        val constraints = GridBagConstraints()
        languageSelection.selectedItem = Application.settings.language
        confirmButton.addActionListener {
            Application.settings.language = languageSelection.selectedItem as String
            Application.settings.ApiKey = apiKeyInput.text
            this.dispose()
        }
        constraints.insets = Insets(5,5,5,5)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 0
        constraints.gridy = 0
        add(JLabel("Language: "), constraints)
        constraints.gridx = 1
        add(languageSelection, constraints)
        constraints.gridx = 0
        constraints.gridy = 1
        add(JLabel("API Key: "), constraints)
        constraints.gridx = 1
        constraints.gridy = 1
        add(apiKeyInput, constraints)
        constraints.gridx = 0
        constraints.gridy = 2
        constraints.gridwidth = 2
        add(confirmButton, constraints)

        setSize(300, 300)
        setLocation(application.width / 2 - width / 2, application.height / 2 - height / 2)
        isVisible = true
    }


}