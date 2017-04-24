package com.aminbenarieb.yandextask.Services.Repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.aminbenarieb.yandextask.Entity.Word.ABWord;
import com.aminbenarieb.yandextask.Entity.Word.WordInfo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ABRepository implements Repository  {

    private Realm realm;

    public ABRepository(Context context) {
        Realm.init(context);
    }

    public void addWord(final @NonNull RepositoryRequest request,
                        final @NonNull RepositoryCompletionHandler completion) {
        final WordInfo wordInfo = ((ABRepositoryRequest)request).word;

        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                ABWord word = bgRealm.where(ABWord.class).equalTo("source", wordInfo.getSource()).findFirst();
                if (word == null) {
                    word = new ABWord(
                            getNextKey(bgRealm),
                            wordInfo.getSource(),
                            wordInfo.getResult());
                }
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
        final WordInfo wordInfo = ((ABRepositoryRequest)request).word;

        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                ABWord word = bgRealm.where(ABWord.class).equalTo("source", wordInfo.getSource()).findFirst();
                word.setFavorite( !word.getFavorite() );
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
        final WordInfo wordInfo = ((ABRepositoryRequest)request).word;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                ABWord word = bgRealm.where(ABWord.class).equalTo("id", wordInfo.getId()).findFirst();
                word.deleteFromRealm();
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
        ArrayList<ABWord> wordList = new ArrayList<>(realm.where(ABWord.class)
                .findAllSorted("dateCreated", Sort.DESCENDING));


        ArrayList<WordInfo> wordInfoArrayList = new ArrayList<>();
        for (ABWord realmWord : wordList) {
            wordInfoArrayList.add( new WordInfo(
                    realmWord.getId(),
                    realmWord.getSource(),
                    realmWord.getResult(),
                    realmWord.getDateCreated(),
                    realmWord.getFavorite()
                    )
            );
        }

        completion.handle( new ABRepositoryResponse(wordInfoArrayList, true, null) );
    }

    public void getFavoriteHistoryWords(final @NonNull RepositoryCompletionHandler completion) {
        realm = Realm.getDefaultInstance();
        ArrayList<ABWord> wordList = new ArrayList<>(realm
                .where(ABWord.class)
                .equalTo("isFavorite", true)
                .findAllSorted("dateCreated", Sort.DESCENDING));

        ArrayList<WordInfo> wordInfoArrayList = new ArrayList<>();
        for (ABWord realmWord : wordList) {
            wordInfoArrayList.add( new WordInfo(
                            realmWord.getId(),
                            realmWord.getSource(),
                            realmWord.getResult(),
                            realmWord.getDateCreated(),
                            realmWord.getFavorite()
                    )
            );
        }

        completion.handle( new ABRepositoryResponse(wordInfoArrayList, true, null) );
    }

    public void cleanHistory(final @NonNull RepositoryCompletionHandler completion) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                final RealmResults<ABWord> wordResults = bgRealm.where(ABWord.class).findAll();
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

    private int getNextKey(Realm realm)
    {
        Number numberId = realm.where(ABWord.class).max("id");
        if (numberId == null) {
            return 0;
        }

        return numberId.intValue() + 1;
    }

}
