package com.rudearts.recipesearcher.data.model.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.rudearts.recipesearcher.data.model.greendao.RecipeDb;

import com.rudearts.recipesearcher.data.model.greendao.RecipeDbDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig recipeDbDaoConfig;

    private final RecipeDbDao recipeDbDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        recipeDbDaoConfig = daoConfigMap.get(RecipeDbDao.class).clone();
        recipeDbDaoConfig.initIdentityScope(type);

        recipeDbDao = new RecipeDbDao(recipeDbDaoConfig, this);

        registerDao(RecipeDb.class, recipeDbDao);
    }
    
    public void clear() {
        recipeDbDaoConfig.clearIdentityScope();
    }

    public RecipeDbDao getRecipeDbDao() {
        return recipeDbDao;
    }

}
