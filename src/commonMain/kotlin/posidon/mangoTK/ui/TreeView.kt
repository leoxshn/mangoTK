package posidon.mangoTK.ui

expect class TreeView(init: TreeView.() -> Unit) : View, NodeI {

}

expect open class Node : NodeI {
    var text: String
}

expect abstract class NodeI {
    fun node(init: Node.() -> Unit)
    fun node(text: String, init: Node.() -> Unit)
}