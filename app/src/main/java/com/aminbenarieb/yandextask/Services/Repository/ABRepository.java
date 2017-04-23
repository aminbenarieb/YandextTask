package com.aminbenarieb.yandextask.Services.Repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.aminbenarieb.yandextask.Entity.Word.ABWord;
import com.aminbenarieb.yandextask.Entity.Word.Word;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ABRepository implements Repository  {

    private Realm realm;
    private Context context;

    public ABRepository(Context context) {
        this.context = context;
        Realm.init(context);
    }

    public void addWord(final @NonNull RepositoryRequest request,
                        final @NonNull RepositoryCompletionHandler completion) {
        final ABWord word = (ABWord)((ABRepositoryRequest)request).word;

        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                word.setId(getNextKey());
                bgRealm.copyToRealm(word);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                completion.handle( new ABRepositoryResponse(null, true, null) );
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                completion.handle( new ABRepositoryResponse(null, false, error) );
            }
        });
    }

    @Override
    public void toggleFavoriteWord(final @NonNull RepositoryRequest request,
                                   final @NonNull RepositoryCompletionHandler completion) {
    }

    public void removeWord(final @NonNull RepositoryRequest request,
                           final @NonNull RepositoryCompletionHandler completion) {
        final Word word = ((ABRepositoryRequest)request).word;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                ((ABWord)word).deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                completion.handle( new ABRepositoryResponse(null, true, null) );
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                completion.handle( new ABRepositoryResponse(null, false, error) );
            }
        });
    }

    public void getHistoryWords(final @NonNull RepositoryCompletionHandler completion) {
        realm = Realm.getDefaultInstance();
        ArrayList<Word> wordList = new ArrayList<Word>(realm.where(ABWord.class)
                .findAllSorted("dateCreated", Sort.DESCENDING));
        completion.handle( new ABRepositoryResponse(wordList, true, null) );
    }

    public void getFavoriteHistoryWords(final @NonNull RepositoryCompletionHandler handler) {
        ArrayList<Word> wordList = new ArrayList<Word>(realm
                .where(ABWord.class)
                .equalTo("isFavorite", true)
                .findAll());
        handler.handle( new ABRepositoryResponse(wordList, false, null) );
    }

    public void cleanHistory(final @NonNull RepositoryCompletionHandler completion) {
        final RealmResults<ABWord> wordResults = realm.where(ABWord.class).findAll();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                wordResults.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                completion.handle(new ABRepositoryResponse(null, true, null) );
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                completion.handle(new ABRepositoryResponse(null, false, error) );
            }
        });
    }

    private int getNextKey()
    {
        try {
            return realm.where(ABWord.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e)
        { return 0; }
    }

}
