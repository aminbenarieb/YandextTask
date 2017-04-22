package com.aminbenarieb.yandextask.Services.Repository;

import com.aminbenarieb.yandextask.Entity.Word.ABWord;
import com.aminbenarieb.yandextask.Entity.Word.Word;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ABRepository implements Repository {

    private Realm realm ;

    ABRepository() {
        realm = Realm.getDefaultInstance();
    }

    public void addWord(final RepositoryRequest request,
                        final RepositoryCompletionHandler completion) {
        final Word word = ((ABRepositoryRequest)request).word;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                realm.copyToRealmOrUpdate((ABWord)word);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                completion.handle( new ABRepositoryResponse(null, Boolean.TRUE, null) );
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                completion.handle( new ABRepositoryResponse(null, Boolean.FALSE, error) );
            }
        });
    }

    public void removeWord(final  RepositoryRequest request,
                           final RepositoryCompletionHandler completion) {
        final Word word = ((ABRepositoryRequest)request).word;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                ((ABWord)word).deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                completion.handle( new ABRepositoryResponse(null, Boolean.TRUE, null) );
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                completion.handle( new ABRepositoryResponse(null, Boolean.FALSE, error) );
            }
        });
    }

    public void getHistoryWords(final RepositoryCompletionHandler completion) {
        ArrayList<Word> wordList = new ArrayList<Word>(realm.where(ABWord.class).findAll());
        completion.handle( new ABRepositoryResponse(wordList, Boolean.TRUE, null) );
    }

    public void getFavoriteHistoryWords(final RepositoryCompletionHandler handler) {
        ArrayList<Word> wordList = new ArrayList<Word>(realm
                .where(ABWord.class)
                .equalTo("isFavorite", true)
                .findAll());
        handler.handle( new ABRepositoryResponse(wordList, Boolean.TRUE, null) );
    }

    public void cleanHistory(final RepositoryCompletionHandler completion) {
        final RealmResults<ABWord> wordResults = realm.where(ABWord.class).findAll();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                wordResults.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                completion.handle(new ABRepositoryResponse(null, Boolean.TRUE, null) );
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                completion.handle(new ABRepositoryResponse(null, Boolean.FALSE, error) );
            }
        });
    }


}
