import javax.swing.JTable
import javax.swing.table.AbstractTableModel
import javax.swing.table.TableColumnModel


class AssignmentsTable(private val assignment: List<Assignment>) : JTable() {
    init {
        val model = AssignmentTableModel(assignment)
        setModel(model)
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        val colModel: TableColumnModel = getColumnModel()
        colModel.getColumn(0).minWidth = 15
        colModel.getColumn(0).maxWidth = 30
        colModel.getColumn(2).preferredWidth = 40
    }
}

class AssignmentTableModel(private val assignment: List<Assignment>) : AbstractTableModel() {
    override fun getRowCount(): Int {
        return assignment.size
    }

    override fun getColumnCount(): Int {
        return Assignment::class.java.declaredFields.size
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val assignment = assignment[rowIndex]
        return when (columnIndex) {
            0 -> assignment.id
            1 -> assignment.studentId
            2 -> assignment.folderRoot.toString()
            3 -> assignment.error
            4 -> assignment.enabled
            else -> {
                "??"
            }
        }
    }

    override fun getColumnClass(columnIndex: Int): Class<*>? {
        var type: Class<*> = String::class.java
        when (columnIndex) {
            1, 2, 3 -> return String::class.java
            0 -> return Int::class.java
            4 -> return Boolean::class.java
        }
        return type
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean {
        return columnIndex == 4
    }

    override fun getColumnName(column: Int): String? {
        return when (column) {
            0 -> "Id"
            1 -> "Student Id"
            2 -> "Folder Root"
            3 -> "Error"
            4 -> "Enabled"
            else -> {
                "??"
            }
        }
    }
}