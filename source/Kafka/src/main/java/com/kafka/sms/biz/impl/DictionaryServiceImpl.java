/***
 * @pName management
 * @name DictionaryServiceImpl
 * @user DF
 * @date 2018/8/16
 * @desc
 */
package com.kafka.sms.biz.impl;

import com.kafka.sms.biz.IDictionaryService;
import com.kafka.sms.entity.DataDictionary;
import com.kafka.sms.entity.DynamicConfiguration;
import com.kafka.sms.entity.db.Dictionary;
import com.kafka.sms.exception.InfoException;
import com.kafka.sms.exception.MsgException;
import com.kafka.sms.repository.DictionaryMapper;
import com.kafka.sms.utils.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary> implements IDictionaryService {
    private final DictionaryMapper dictionaryMapper;
    @Autowired
    private DynamicConfiguration dynamicConfiguration;

    @Autowired
    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper) {
        this.dictionaryMapper = dictionaryMapper;
    }


    /**
     * 查询数据字典信息列表 2018年10月31日21:46:59
     *
     * @return
     */
    @Override
    public List<Dictionary> getList() {
        return dictionaryMapper.selectAll();
    }
}
