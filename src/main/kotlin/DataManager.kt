import it.zielke.moji.SocketClient
import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.tinyzip.TinyZip
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class DataManager (private val database: Database){
    var assignments = mutableListOf<Assignment>()
    var similarityResults = mutableMapOf<Pair<Assignment, Assignment>, Similarity>()

    fun loadAssignments(file: File) {
        val tempDirectory: File = Files.createTempDirectory("MOSS_").toFile()
        val submissionDirs: List<File> = file.listFiles().filter { it.isDirectory }

        submissionDirs.forEachIndexed { index, assignmentFolder ->
            println("Processing student : ${assignmentFolder.absolutePath}")
            val assignmentTmpFolder = File(tempDirectory, assignmentFolder.name.replace("\\s".toRegex(), ""))
            println(assignmentTmpFolder)
            val compressed: List<File> = assignmentFolder.listFiles().filter { it.toString().endsWith(".zip") }
            val sourceFiles: List<File> = assignmentFolder.listFiles().filter {
                it.toString().endsWith(Application.settings.language.lowercase(Locale.getDefault()))
            }
            val assignment = Assignment(index, assignmentTmpFolder.name, assignmentTmpFolder)
            //TODO: Support other compression standards and uncompressed submissions
            compressed.forEach { file ->
                try {
                    TinyZip.unzip(file.path, assignmentTmpFolder.path)
                } catch (e: Exception) {
                    assignment.error = "Error uncompressing assignment"
                }
            }
            sourceFiles.forEach { file -> file.copyTo(Paths.get(assignmentTmpFolder.toPath().toString(), file.name).toFile()) }
            if (compressed.isEmpty() && sourceFiles.isEmpty()) {
                assignment.error = "No source files found"
            }
            assignments.add(assignment)
        }
    }

    fun submitAssignments(): URL {
        val socketClient = SocketClient()
        socketClient.userID = Application.settings.ApiKey
        socketClient.language = Application.settings.language.lowercase(Locale.getDefault())
        socketClient.run()
        assignments.filter { it.enabled && it.error.isEmpty() }.forEach { assignment ->
            val files = FileUtils.listFiles(assignment.folderRoot, arrayOf(socketClient.language), true)
            println("Socket status ${socketClient.socket.isConnected}  Stage: ${socketClient.currentStage}")
            files.forEach { it ->
                val newData = it.readText().replace("[^\\x00-\\x7F]+".toRegex(), "")
                it.writeText(newData)
                socketClient.uploadFile(it)
                println("Uploading ${it.absolutePath}")
            }
        }
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (socketClient.currentStage == it.zielke.moji.Stage.AWAITING_END) {
                    cancel()
                }
            }
        }, 0, 100)
        socketClient.sendQuery();
        val results = socketClient.resultURL
        println(results)
        return results
    }

    fun parseMossResult(resultUrl: URL): MutableMap<Pair<Assignment, Assignment>, Similarity> {
        val document: Document = Jsoup.connect(resultUrl.toString()).get()
        val rows = document.select("tr")
        val results = mutableMapOf<Pair<Assignment, Assignment>, Similarity>()
        rows.drop(1).forEach { it ->
            val first = it.select("td")[0]
            val second = it.select("td")[1]
            val submissionA = first.select("A").html();
            val linkA: String = first.select("A").attr("href")
            val similarityA: Int = submissionA.substring(submissionA.indexOf("(") + 1).replace("%)", "").toInt()
            val submissionB: String = second.select("A").html()
            val linkB: String = second.select("A").attr("href")
            val similarityB: Int = submissionB.substring(submissionB.indexOf("(") + 1).replace("%)", "").toInt()
            val linesMatched: Int = it.select("td")[2].html().toInt()
            val assignmentA = assignments.find { student -> submissionA.split(" ")[0].contains(student.folderRoot.toString()) } ?: return@forEach
            val assignmentB = assignments.find { student -> submissionB.split(" ")[0].contains(student.folderRoot.toString()) } ?: return@forEach
            results[Pair(assignmentA, assignmentB)] = Similarity(similarityA, similarityB, linesMatched, linkA)
        }
        println(results.size)
        return results
    }

    public fun parseSimilarityMatch(pair: Pair<Assignment, Assignment>) {
        var similarity = similarityResults[pair] ?: return
        val main: Document = Jsoup.connect(similarity.link).get()
        val top = main.select("frame[name=top]").first()?.absUrl("src")
        val A = main.select("frame[name=0]").first()?.absUrl("src")
        val B = main.select("frame[name=1]").first()?.absUrl("src")
        similarity.codeA = Jsoup.connect(A).get().text()
        similarity.codeB = Jsoup.connect(B).get().text()
        val document: Document = Jsoup.connect(top).get()
        document.select("tr > td > a").filter { it.text().isNotEmpty() }.forEach {
            val lines = it.text().split("-")
            similarity.matched.add(IntRange(lines[0].toInt(), lines[1].toInt()))
        }
        similarityResults[pair] = similarity
    }

    data class Similarity(
        val similarityA: Int,
        val similarityB: Int,
        val linesMatched: Int,
        val link: String,
        var codeA: String = "",
        var codeB: String = "",
        var matched: MutableList<IntRange> = mutableListOf()
    )
}