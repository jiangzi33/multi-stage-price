CREATE TABLE play_record (
                             id INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             user_id INT NOT NULL COMMENT '用户ID',
                             sound_id INT NOT NULL COMMENT '音频ID',
                             duration INT NOT NULL DEFAULT 0 COMMENT '播放时长(秒)',
                             sync_time DATETIME COMMENT '同步时间',
                             create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (id),
                             KEY idx_user_id (user_id),
                             KEY idx_sound_id (sound_id),
                             KEY idx_sync_time (sync_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='播放记录表';


CREATE TABLE prize_record (
                              id INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                              user_id INT NOT NULL COMMENT '用户ID',
                              biz_scene VARCHAR(50) NOT NULL COMMENT '业务场景',
                              prize_code VARCHAR(100) NOT NULL COMMENT '奖品编码',
                              prize_date VARCHAR(20) NOT NULL COMMENT '领奖日期',
                              prize_stage INT NOT NULL DEFAULT 0 COMMENT '领奖阶段',
                              prize_amount INT NOT NULL DEFAULT 0 COMMENT '奖励金额',
                              out_biz_no VARCHAR(100) COMMENT '外部业务单号',
                              create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (id),
                              KEY idx_user_id (user_id),
                              KEY idx_prize_code (prize_code),
                              KEY idx_out_biz_no (out_biz_no),
                              KEY idx_biz_scene (biz_scene)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='奖励记录表';

CREATE TABLE total_duration (
                                id INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                user_id INT NOT NULL COMMENT '用户ID',
                                total_duration INT NOT NULL DEFAULT 0 COMMENT '累计时长(秒)',
                                date VARCHAR(20) NOT NULL COMMENT '统计日期',
                                last_sync_time DATETIME COMMENT '最后同步时间',
                                create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (id),
                                UNIQUE KEY uk_user_date (user_id, date),
                                KEY idx_user_id (user_id),
                                KEY idx_date (date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户累计时长统计表';