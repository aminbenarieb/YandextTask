package com.aminbenarieb.yandextask.Services.Repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.aminbenarieb.yandextask.Entity.Word.ABWord;
import com.aminbenarieb.yandextask.Entity.Word.Word;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ABRepository implements Repository  {

    private Realm realm;
    private Context context;

    public ABRepository(Context context) {
        this.context = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void addWord(final @NonNull RepositoryRequest request,
                        final @NonNull RepositoryCompletionHandler completion) {
        final Word word = ((ABRepositoryRequest)request).word;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                realm.copyToRealmOrUpdate((ABWord)word);
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
        ArrayList<Word> wordList = new ArrayList<Word>(realm.where(ABWord.class).findAll());
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


}
