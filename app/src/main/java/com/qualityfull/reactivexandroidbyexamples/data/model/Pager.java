package com.qualityfull.reactivexandroidbyexamples.data.model;

import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Result;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class Pager {

    private int page;
    private String query;
    private List<Result> mCharacterList = null;
    public static final String LIMIT = "10";

    @Inject
    public Pager() {
        this.mCharacterList = new ArrayList<>();
        this.page = 0;
    }

    public Pager(String query) {
        this();
        this.query = query;
    }

    public List<Result> getItemList() {
        return mCharacterList;
    }

    public String getOffset() {
        return Integer.valueOf(page + this.mCharacterList.size()).toString();
    }

    public String getQuery() {
        return query;
    }

    public void updateItemList(List<Result> characterList) {
        if (this.mCharacterList != null && !characterList.isEmpty()) {
            this.mCharacterList.addAll(characterList);
        } else {
            this.mCharacterList = characterList;
        }
        this.page++;
    }
}
