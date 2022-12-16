package ru.netology.graphics.image;

public class ImplTextColorSchema implements TextColorSchema{
    private final char [] symbols = {'#', '$', '@', '%', '*', '+', '-', '\''};
    @Override
    public char convert(int color) {

        int i = color / 32;
        char symbol = symbols[i];
        return symbol;
    }

    public char[] getSymbols() {
        return symbols;
    }
}
