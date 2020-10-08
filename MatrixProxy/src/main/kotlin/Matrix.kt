import java.util.function.BinaryOperator

interface Matrix {
    fun getRowCount(): Int
    fun getColCount(): Int
    operator fun get(x: Int, y: Int): Int
    operator fun plus(a: Matrix): Matrix
    operator fun minus(a: Matrix): Matrix
}

// TODO операции с отдельными строками (в разном порядке сложение и выделение строк)
// TODO сеттеры

class MatrixPlusProxy(
    private val a: Matrix,
    private val b: Matrix,
    private val operation: BinaryOperator<Int>
): Matrix {
    init {
        if (a.getColCount() != b.getColCount() || a.getRowCount() != b.getRowCount())
            throw IllegalArgumentException("Cannot add matrix with different dimensions.")
    }

    override fun getRowCount(): Int {
        return a.getRowCount()
    }

    override fun getColCount(): Int {
        return a.getColCount()
    }

    override fun get(x: Int, y: Int): Int {
        return operation.apply(a[x, y], b[x, y])
    }

    override fun plus(a: Matrix): Matrix {
        return MatrixPlusProxy(this, a){x, y -> x + y}
    }

    override fun minus(a: Matrix): Matrix {
        return MatrixPlusProxy(this, a){x, y -> x - y}
    }
}

// Этот класс нужен, чтобы можно было пользоваться плюсом и минусом!
// Сделать абстракции
class MatrixProxy(private val a: Matrix): Matrix {
    override fun getRowCount(): Int {
        return a.getRowCount()
    }

    override fun getColCount(): Int {
        return a.getRowCount()
    }

    override fun get(x: Int, y: Int): Int {
        return a[x, y]
    }

    override fun plus(a: Matrix): Matrix {
        return MatrixPlusProxy(this, a){x, y -> x + y}
    }

    override fun minus(a: Matrix): Matrix {
        return MatrixPlusProxy(this, a){x, y -> x - y}
    }
}