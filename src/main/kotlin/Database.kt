import java.sql.Connection
import java.sql.DriverManager

class Database() {

    private val connection: Connection = DriverManager.getConnection("jdbc:sqlite:MOSSDb")

    init {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS MOSS(id integer PRIMARY KEY, url TEXT NOT NULL, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)")
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS ASSIGNMENT (id integer PRIMARY KEY, mid integer, name TEXT NOT NULL, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)")
    }
}