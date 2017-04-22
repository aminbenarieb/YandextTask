package com.aminbenarieb.yandextask.Services.Repository;


import com.aminbenarieb.yandextask.Entity.Word.Word;

public interface Repository {

    interface RepositoryCompletionHandler {
        void handle(RepositoryResponse response);
    }

    void addWord(final RepositoryRequest request, final RepositoryCompletionHandler completion);

    void removeWord(final RepositoryRequest request, final RepositoryCompletionHandler completion);

    void getHistoryWords(final RepositoryCompletionHandler handler);

    void getFavoriteHistoryWords(final RepositoryCompletionHandler handler);

    void cleanHistory(final RepositoryCompletionHandler handler);

}
