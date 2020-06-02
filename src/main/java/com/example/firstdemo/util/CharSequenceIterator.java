package com.example.firstdemo.util;

import java.lang.reflect.Array;
import java.util.Iterator;

public final class CharSequenceIterator implements Iterator<Character> {
    private final CharSequence charSequence;
    private final int startIndex;
    private final int endIndex;
    private int index;

    public CharSequenceIterator(CharSequence charSequence) {
        this(charSequence, 0);
    }

    public CharSequenceIterator(CharSequence charSequence, int startIndex) {
        this(charSequence, startIndex, Array.getLength(charSequence));
    }

    public CharSequenceIterator(CharSequence charSequence, int startIndex, int endIndex) {
        this.index = 0;
        this.charSequence = charSequence;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.index = startIndex;
        int len = charSequence.length();
        this.checkBound(startIndex, len, "start");
        this.checkBound(endIndex, len, "end");
        if (endIndex < startIndex) {
            throw new IllegalArgumentException("End index must not be less than start index. ");
        }
    }

    public CharSequence getCharSequence() {
        return this.charSequence;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    protected void checkBound(int bound, int len, String type) {
        if (bound > len) {
            throw new ArrayIndexOutOfBoundsException("Attempt to make an ArrayIterator that "
                    + type + "s beyond the end of the charSequence. ");
        } else if (bound < 0) {
            throw new ArrayIndexOutOfBoundsException("Attempt to make an ArrayIterator that " + type + "s before the start of the charSequence. ");
        }
    }

    public boolean hasNext() {
        return this.index < this.endIndex;
    }

    public Character

    next() {
        return this.charSequence.charAt(this.index++);
    }

    public void remove() {
        throw new UnsupportedOperationException(" remove() method is not supported");
    }
}

