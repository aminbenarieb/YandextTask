package com.aminbenarieb.yandextask.Screens.History;


import com.aminbenarieb.yandextask.Entity.Word.WordInfo;

public interface HistoryAdapterDelegate {
    void didTapOnWord(WordInfo word);
    void didToggleWordFavorite(WordInfo word);
    void didDeleteWord(WordInfo word);
}
