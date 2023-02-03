package models

import Application
import Assignment
import DataManager
import workers.LoadCodeCompare
import javax.swing.JButton
import javax.swing.JTable
import javax.swing.table.AbstractTableModel
import javax.swing.table.TableColumnModel

class SimilarityTable(application: Application) : JTable() {
    init {
        val model = SimilarityTableModel(application.dataManager.similarityResults)
        setModel(model)
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        val colModel: TableColumnModel = getColumnModel()
        colModel.getColumn(1).minWidth = 60
        colModel.getColumn(1).maxWidth = 80
        colModel.getColumn(3).minWidth = 60
        colModel.getColumn(3).maxWidth = 80
        colModel.getColumn(4).minWidth = 80
        colModel.getColumn(4).maxWidth = 100
        getSelectionModel().addListSelectionListener() {
            val pair = model.getRowObject(this.selectedRow)
            println("click ${it.hashCode()} " )
            LoadCodeCompare(application,pair).execute()
        }
    }
}

class SimilarityTableModel(private val similarity: Map<Pair<Assignment, Assignment>, DataManager.Similarity>) :
    AbstractTableModel() {
    override fun getRowCount(): Int {
        return similarity.size
    }

    override fun getColumnCount(): Int {
        return 6
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val pair = similarity.keys.toList()[rowIndex]
        val similarity = similarity[pair] ?: return ""
        return when (columnIndex) {
            0 -> pair.first.folderRoot
            1 -> "${similarity.similarityA} %"
            2 -> pair.second.folderRoot
            3 -> "${similarity.similarityB} %"
            4 -> similarity.linesMatched
            5 -> JButton("Compare")
            else -> {
                "??"
            }
        }
    }

    fun getRowObject(rowIndex: Int): Pair<Assignment, Assignment> {
        return similarity.keys.toList()[rowIndex];
    }

    override fun getColumnClass(columnIndex: Int): Class<*>? {
        return getValueAt(0,columnIndex)::class.java
    }

    override fun getColumnName(column: Int): String? {
        return when (column) {
            0 -> "Assignment A"
            1 -> "Percentage A"
            2 -> "Assignment B"
            3 -> "Percentage B"
            4 -> "Lines Matched"
            5 -> "Action"
            else -> {
                "??"
            }
        }
    }
}
