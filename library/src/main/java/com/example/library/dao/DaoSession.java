package com.example.library.dao;

import com.example.library.bean.LoginData;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig loginDataDaoConfig;

    private final LoginDataDao loginDataDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        loginDataDaoConfig = daoConfigMap.get(LoginDataDao.class).clone();
        loginDataDaoConfig.initIdentityScope(type);

        loginDataDao = new LoginDataDao(loginDataDaoConfig, this);

        registerDao(LoginData.class, loginDataDao);
    }
    
    public void clear() {
        loginDataDaoConfig.clearIdentityScope();
    }

    public LoginDataDao getLoginDataDao() {
        return loginDataDao;
    }

}
