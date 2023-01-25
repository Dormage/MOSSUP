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
        colModel.getColumn(4).minWidth = 50
        colModel.getColumn(4).maxWidth = 80
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
    override fun setValueAt(aValue: Any, rowIndex: Int, columnIndex: Int) {
        if (columnIndex == 4) {
            assignment[rowIndex].enabled = aValue as Boolean
        }
    }

    override fun getColumnClass(columnIndex: Int): Class<*>? {
        return getValueAt(0,columnIndex)::class.java
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