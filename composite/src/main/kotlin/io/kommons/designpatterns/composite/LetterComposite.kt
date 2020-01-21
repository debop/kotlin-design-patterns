package io.kommons.designpatterns.composite

/**
 * LetterComposite
 */
abstract class LetterComposite {

    private val children = arrayListOf<LetterComposite>()

    fun add(letter: LetterComposite) {
        children.add(letter)
    }

    val count: Int get() = children.size

    protected open fun onBeforePrint() {}
    protected open fun onAfterPrint() {}

    fun print() {
        onBeforePrint()

        children.forEach { it.print() }

        onAfterPrint()
    }
}

class Letter(private val c: Char): LetterComposite() {

    override fun onBeforePrint() {
        print(c)
    }
}

class Word(letters: Iterable<Letter>): LetterComposite() {

    init {
        letters.forEach { add(it) }
    }

    override fun onBeforePrint() {
        print(" ")
    }
}

class Sentence(words: Iterable<Word>): LetterComposite() {
    init {
        words.forEach { add(it) }
    }

    override fun onAfterPrint() {
        print(".")
    }
}