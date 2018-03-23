package com.talkweb.xwzcxt.dal;

import java.util.List;

import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import com.talkweb.twdpe.dal.batis.spring.SessionDaoSupport;
import com.talkweb.xwzcxt.pojo.TManageSection;

public class ManSectionDal extends SessionDaoSupport
{

    private static final Logger logger = LoggerFactory
            .getLogger(ManSectionDal.class);

    public List<TManageSection> queryAllSections()
    {
        return (List<TManageSection>) this.getSession().selectList(
                "manSection.queryAllSection");

    }

}