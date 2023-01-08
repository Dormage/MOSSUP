import java.io.File

class Assignment(
    val id: Int,
    val studentId: String,
    val folderRoot: File,
    val error: String = "",
    val enabled: Boolean = true
) {

}
