import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class Database() {

    private val connection: Connection = DriverManager.getConnection("jdbc:sqlite:MOSSDb")

    init {
        connection.createStatement().execute(
            "CREATE TABLE IF NOT EXISTS " +
                    "MOSS(id integer PRIMARY KEY, " +
                    "url TEXT NOT NULL, " +
                    "html TEXT," +
                    "itemCount integer, " +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)"
        )
        connection.createStatement()
            .execute("CREATE TABLE IF NOT EXISTS ASSIGNMENTS (id integer PRIMARY KEY, mid integer, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP )")
    }

    fun addMoss(url: String, html: String, itemCount:Int) {
        val statement = connection.prepareStatement("INSERT INTO MOSS(url,html,itemCount) VALUES(?,?,?)")
        statement.setString(1,url)
        statement.setString(2,html)
        statement.setInt(3,itemCount);
        statement.executeUpdate()
    }

    fun listMoss(): MutableList<DataManager.MOSS> {
        val statement = connection.prepareStatement("SELECT * FROM MOSS")
        val results: ResultSet = statement.executeQuery()
        val MOSSList = mutableListOf<DataManager.MOSS>()
        while (results.next()) {
            MOSSList.add(
                DataManager.MOSS(
                    results.getInt("id"),
                    results.getString("url"),
                    results.getString("html"),
                    results.getInt("itemCount"),
                    results.getLong("timestamp")
                )
            )
        }
        return MOSSList
    }

    fun getAssignments()
}