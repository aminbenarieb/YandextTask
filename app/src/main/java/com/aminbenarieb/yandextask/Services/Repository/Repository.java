package com.aminbenarieb.yandextask.Services.Repository;


import android.support.annotation.NonNull;

public interface Repository {

    interface RepositoryCompletionHandler {
        void handle(RepositoryResponse response);
    }

    void addWord(final @NonNull RepositoryRequest request, final @NonNull RepositoryCompletionHandler completion);

    void removeWord(final @NonNull RepositoryRequest request, final @NonNull RepositoryCompletionHandler completion);

    void getHistoryWords(final @NonNull RepositoryCompletionHandler handler);

    void getFavoriteHistoryWords(final @NonNull RepositoryCompletionHandler handler);

    void cleanHistory(final @NonNull RepositoryCompletionHandler handler);

}
