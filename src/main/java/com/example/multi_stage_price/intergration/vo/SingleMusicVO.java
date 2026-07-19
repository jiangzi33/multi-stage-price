package com.example.multi_stage_price.intergration.vo;

import com.example.multi_stage_price.controller.vo.BaseVO;

public class SingleMusicVO {
    private BaseVO baseVO;
    private MusicVO musicVO;

    public BaseVO getBaseVO() {
        return baseVO;
    }

    public void setBaseVO(BaseVO baseVO) {
        this.baseVO = baseVO;
    }

    public MusicVO getMusicVO() {
        return musicVO;
    }

    public void setMusicVO(MusicVO musicVO) {
        this.musicVO = musicVO;
    }
}