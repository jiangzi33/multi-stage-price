package com.example.multi_stage_price.intergration.vo;

import com.example.multi_stage_price.controller.vo.BaseVO;

public class SingleSystemConfigVO {
    private BaseVO baseVO;
    private SystemConfigVO systemConfigVO;

    public BaseVO getBaseVO() {
        return baseVO;
    }

    public void setBaseVO(BaseVO baseVO) {
        this.baseVO = baseVO;
    }

    public SystemConfigVO getSystemConfigVO() {
        return systemConfigVO;
    }

    public void setSystemConfigVO(SystemConfigVO systemConfigVO) {
        this.systemConfigVO = systemConfigVO;
    }
}
