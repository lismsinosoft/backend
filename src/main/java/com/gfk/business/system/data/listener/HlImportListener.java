package com.gfk.business.system.data.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.gfk.business.system.data.model.HlImport;
import com.gfk.business.system.data.service.DataImportService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
@Slf4j
public class HlImportListener implements ReadListener<HlImport> {

    /**
     * 缓存数据大小
     */
    private static final int CACHE_SIZE = 100;


    /**
     * 导入任务ID
     */
    private final String taskId;

    private final Long projectId;

    private final DataImportService service;

    /**
     * 缓存数据
     */
    private final List<HlImport> cachedList = new ArrayList<>(CACHE_SIZE);


    public HlImportListener(Long projectId, String taskId, DataImportService service) {
        this.projectId = projectId;
        this.taskId = taskId;
        this.service = service;
    }

    @Override
    public void invoke(HlImport data, AnalysisContext context) {
        log.info("解析第" + context.readRowHolder().getRowIndex() + "行数据:{}", data);
        data.setCreateTime(new Date());
        data.setTaskId(taskId);
        data.setProjectId(projectId);
        data.setSeq(context.readRowHolder().getRowIndex());
        cachedList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedList.size() >= CACHE_SIZE) {
            saveData();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成");
    }

    private void saveData() {
        try {
            log.info("{}条数据，开始存储数据库！", cachedList.size());
            int i = service.saveHl(cachedList);
            log.info("存储数据库成功！，共{}条数据", i);
            // 存储完成清理 list
            cachedList.clear();
        } catch (Exception e) {
            log.error("Excel数据中间表落库失败！", e);
            cachedList.clear();
        }
    }
}

