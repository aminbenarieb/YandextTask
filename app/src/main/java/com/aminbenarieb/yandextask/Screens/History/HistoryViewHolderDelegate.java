package com.aminbenarieb.yandextask.Screens.History;


public interface HistoryViewHolderDelegate {
    void didTapOnRow(int row);
    void didTapBookmarkOnRow(int row);
    void didSwipeDeleteOnRow(int row);
}
