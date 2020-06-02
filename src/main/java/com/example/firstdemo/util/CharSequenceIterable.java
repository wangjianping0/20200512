package com.example.firstdemo.util;

import java.util.Iterator;

public final class CharSequenceIterable implements Iterable<Character> {
    private final CharSequence charSequence;

    private final int startIndex;
    private final int endIndex;
    public CharSequenceIterable(CharSequence charSequence) {
        this(charSequence,0);
    }
    public CharSequenceIterable(CharSequence charSequence, int startIndex) {
        this( charSequence, startIndex, charSequence. length());
    }
    public CharSequenceIterable(CharSequence charSequence, int startIndex, int endIndex) {
        this . charSequence = charSequence;
        this. startIndex = startIndex;
        this . endIndex = endIndex;
    }
    public Iterator<Character> iterator() {
        return new CharSequenceIterator(this.charSequence, this.startIndex, this.endIndex);

    }
}