package com.wsirius.security.domain.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.wsirius.common.ConfigEnum;
import com.wsirius.core.base.BaseService;
import com.wsirius.security.domain.entity.Config;
import com.wsirius.security.domain.service.ConfigService;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.util.Sqls;

/**
 * ConfigServiceImpl
 *
 * @author bojiangzhou 2018/09/17
 */
@Service
public class ConfigServiceImpl extends BaseService<Config> implements ConfigService {

    @Override
    public boolean isEnableCaptcha(Integer passwordErrorTime) {
        Integer times = selectIntegerConfig(ConfigEnum.PASSWORD_ERROR_TIME_TO_ENABLE_CAPTCHA.name());
        return times > 0 && passwordErrorTime >= times;
    }

    private Integer selectIntegerConfig(String code) {
        List<Config> configs = selectByCondition(Condition.builder(Config.class)
                .andWhere(Sqls.custom().andEqualTo(Config.FIELD_CODE, code))
                .build()
        );
        String str = CollectionUtils.isNotEmpty(configs) ? configs.get(0).getValue() : "-1";
        Integer value = null;
        try {
            value = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            value = -1;
        }

        return value;
    }


}
