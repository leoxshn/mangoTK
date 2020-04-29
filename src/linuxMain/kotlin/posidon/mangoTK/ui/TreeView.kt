package posidon.mangoTK.ui

import gtk3.*
import kotlinx.cinterop.*

actual class TreeView actual constructor(init: TreeView.() -> Unit): View, NodeI() {

    override val gtkWidget = gtk_tree_view_new()!!
    override val treeView: TreeView = this
    override val parentIter: CPointer<GtkTreeIter>? = null

    val model: CPointer<GtkTreeStore>
    val cellRenderer = gtk_cell_renderer_text_new()

    init {
        val column = gtk_tree_view_column_new()
        gtk_tree_view_append_column(gtkWidget.reinterpret(), column)
        gtk_tree_view_column_set_title(column, "blablabla")
        gtk_tree_view_column_pack_start(column, cellRenderer, 1)
        gtk_tree_view_column_add_attribute(column, cellRenderer, "text", 0)
        println("aaaaaaaaaaaaaaaaaaa")
        println("dfgdfgsfdg")
        model = gtk_tree_store_new(1, G_TYPE_STRING)!!
        println("adgdfhdghdfgfh")
        gtk_tree_view_set_model(gtkWidget.reinterpret(), model.reinterpret())
        init()
    }
}

actual open class Node(
    override val treeView: TreeView,
    override val parentIter: CPointer<GtkTreeIter>?
) : NodeI() {

    actual var text: String
        get() = TODO("Not yet implemented")
        set(value) {

        }
}

actual abstract class NodeI {
    abstract val treeView: TreeView
    abstract val parentIter: CPointer<GtkTreeIter>?

    actual fun node(init: Node.() -> Unit) {
        node("", init)
    }

    actual fun node(text: String, init: Node.() -> Unit) {
        val iter: CPointer<GtkTreeIter> = nativeHeap.alloc<GtkTreeIter>().ptr
        gtk_tree_store_append(treeView.model, iter, parentIter)
        gtk_tree_store_set(treeView.model, iter, 0, text, -1)
        Node(treeView, iter).apply(init)
        nativeHeap.free(iter)
    }
}