package io.kommons.designpatterns.composite

/**
 * Messenger
 */
class Messenger {

    fun messageFromOrcs(): LetterComposite {
        val words = arrayListOf<Word>()

        words.add(Word(listOf(Letter('W'), Letter('h'), Letter('e'), Letter('r'), Letter('e'))))
        words.add(Word(listOf(Letter('t'), Letter('h'), Letter('e'), Letter('r'), Letter('e'))))
        words.add(Word(listOf(Letter('i'), Letter('s'))))
        words.add(Word(listOf(Letter('a'))))
        words.add(Word(listOf(Letter('w'), Letter('h'), Letter('i'), Letter('p'))))
        words.add(Word(listOf(Letter('t'), Letter('h'), Letter('e'), Letter('r'), Letter('e'))))
        words.add(Word(listOf(Letter('i'), Letter('s'))))
        words.add(Word(listOf(Letter('a'))))
        words.add(Word(listOf(Letter('w'), Letter('a'), Letter('y'))))

        return Sentence(words)
    }

    fun messageFromElves(): LetterComposite {

        val words = arrayListOf<Word>()

        words.add(Word(listOf(Letter('M'), Letter('u'), Letter('c'), Letter('h'))))
        words.add(Word(listOf(Letter('w'), Letter('i'), Letter('n'), Letter('d'))))
        words.add(Word(listOf(Letter('p'), Letter('o'), Letter('u'), Letter('r'), Letter('s'))))
        words.add(Word(listOf(Letter('f'), Letter('r'), Letter('o'), Letter('m'))))
        words.add(Word(listOf(Letter('y'), Letter('o'), Letter('u'), Letter('r'))))
        words.add(Word(listOf(Letter('m'), Letter('o'), Letter('u'), Letter('t'), Letter('h'))))

        return Sentence(words)
    }
}