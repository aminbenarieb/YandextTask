package com.aminbenarieb.yandextask.Screens.History;


import com.aminbenarieb.yandextask.Entity.Word.Word;

public interface HistoryAdapterDelegate {
    void didTapOnWord(Word word);
    void didToggleWordFavorite(Word word);
    void didDeleteWord(Word word);
    void didClearHistory();
}
