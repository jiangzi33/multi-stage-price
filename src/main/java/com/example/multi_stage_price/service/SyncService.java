package com.example.multi_stage_price.service;

import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;

public interface SyncService {
    void sync(PlayRecordCmd cmd);
}
