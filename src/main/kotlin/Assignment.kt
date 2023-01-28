import java.io.File

class Assignment(
    val id: Int,
    val studentId: String,
    val folderRoot: File,
    var error: String = "",
    var enabled: Boolean = true
) {

}
