package com.gfk.business.system.data.controller;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.gfk.business.data.service.BusinessDataService;
import com.gfk.business.data.service.DataFilterService;
import com.gfk.business.system.data.listener.*;
import com.gfk.business.system.data.model.*;
import com.gfk.business.system.data.service.DataImportService;
import com.gfk.business.system.project.model.Project;
import com.gfk.business.system.project.service.ProjectService;
import com.gfk.common.cache.CacheRepository;
import com.gfk.common.constant.BaseConstants;
import com.gfk.common.enums.ErrorEnum;
import com.gfk.common.enums.ImportEnum;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.model.Result;
import com.gfk.common.util.IdGenerateHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 数据导入Controller
 *
 * @author wzl
 * @version 1.0 2023/3/11
 */
@Api(tags = "2 - 数据导入")
@RestController
@RequestMapping("/sys/data")
@RequiredArgsConstructor
@Slf4j
public class DataImportController {

    private final ProjectService projectService;

    private final DataImportService dataImportService;

    private final BusinessDataService businessDataService;

    private final DataFilterService dataFilterService;

    private final CacheRepository cacheRepository;

    @ApiIgnore
    @ApiOperation("SalesDate数据excel导入")
    @PostMapping(value = "/sales_date/import")
    public Result<String> salesDateImport(@RequestPart("file") @RequestParam(value = "file") @ApiParam(value = "模板文件", required = true) MultipartFile file,
                                          @RequestParam("projectId") @ApiParam(value = "项目ID", required = true) Long projectId,
                                          @RequestParam("adminKey") @ApiParam(value = "管理员秘钥", required = true) String adminKey) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        String taskId = IdGenerateHelper.nextId();
        log.info("导入SalesDate数据开始，taskId：{}", taskId);
        Project project = projectService.findById(projectId);
        if (null == project) {
            throw new BusinessException("project Id 错误");
        }
        //先清空导入表数据
        dataImportService.truncateSalesDateImport();
        try {
            try (ExcelReader excelReader = EasyExcel.read(
                    file.getInputStream(),
                    SalesDateImport.class,
                    new SalesDateImportListener(project.getId(), taskId, dataImportService)).build()) {
                ReadSheet sheet = EasyExcel.readSheet("Sales Date").build();
                excelReader.read(sheet);
            }
            log.info("数据导入完成，开始转换主表数据");
            int i = businessDataService.transformSalesDate(taskId);
            log.info("数据导入成功，共导入{}条数据", i);
        } catch (Exception e) {
            log.error("导入失败", e);
            return Result.failed(ErrorEnum.IMPORT_FAIL);
        }
        return Result.success("导入成功");
    }

    @ApiIgnore
    @ApiOperation("Media数据excel导入")
    @PostMapping(value = "/media/import")
    public Result<String> mediaImport(@RequestPart("file") @RequestParam("file") MultipartFile file,
                                      @RequestParam("projectId") Long projectId) {
        String taskId = IdGenerateHelper.nextId();
        log.info("导入Media数据开始，taskId：{}", taskId);
        Project project = projectService.findById(projectId);
        if (null == project) {
            throw new BusinessException("project Id 错误");
        }
        //先清空导入表数据
        dataImportService.truncateMediaImport();
        try {
            try (ExcelReader excelReader = EasyExcel.read(
                    file.getInputStream(),
                    MediaImport.class,
                    new MediaImportListener(project.getId(), taskId, dataImportService)).build()) {
                ReadSheet sheet = EasyExcel.readSheet("media数据").build();
                excelReader.read(sheet);
            }
            log.info("数据导入完成，开始转换主表数据");
            int i = businessDataService.transformMedia(taskId);
            log.info("数据导入成功，共导入{}条数据", i);
        } catch (Exception e) {
            log.error("导入失败", e);
            return Result.failed(ErrorEnum.IMPORT_FAIL);
        }
        return Result.success("导入成功");
    }

    @ApiIgnore
    @ApiOperation("Media Channel数据excel导入")
    @PostMapping(value = "/media_channel/import")
    public Result<String> mediaChannelImport(@RequestPart("file") @RequestParam("file") MultipartFile file,
                                             @RequestParam("projectId") Long projectId) {
        String taskId = IdGenerateHelper.nextId();
        log.info("导入Media数据开始，taskId：{}", taskId);
        Project project = projectService.findById(projectId);
        if (null == project) {
            throw new BusinessException("project Id 错误");
        }
        //先清空导入表数据
        dataImportService.truncateMediaChannelImport();
        try {
            try (ExcelReader excelReader = EasyExcel.read(
                    file.getInputStream(),
                    MediaChannelImport.class,
                    new MediaChannelImportListener(project.getId(), taskId, dataImportService)).build()) {
                ReadSheet sheet = EasyExcel.readSheet("media_channel").build();
                excelReader.read(sheet);
            }
            log.info("数据导入完成，开始转换主表数据");
            int i = businessDataService.transformMediaChannel(taskId);
            log.info("数据导入成功，共导入{}条数据", i);
            try {
                dataFilterService.cacheClear(null);
            } catch (Exception e) {
                log.error("缓存清除失败", e);
            }
        } catch (Exception e) {
            log.error("导入失败", e);
            return Result.failed(ErrorEnum.IMPORT_FAIL);
        }
        return Result.success("导入成功");
    }

    @ApiIgnore
    @ApiOperation("HL数据excel导入")
    @PostMapping(value = "/hl/import")
    public Result<String> hlImport(@RequestPart("file") @RequestParam("file") MultipartFile file,
                                   @RequestParam("projectId") Long projectId) {
        String taskId = IdGenerateHelper.nextId();
        log.info("导入HL数据开始，taskId：{}", taskId);
        Project project = projectService.findById(projectId);
        if (null == project) {
            throw new BusinessException("project Id 错误");
        }
        //先清空导入表数据
        dataImportService.truncateHlImport();
        try {
            try (ExcelReader excelReader = EasyExcel.read(
                    file.getInputStream(),
                    HlImport.class,
                    new HlImportListener(project.getId(), taskId, dataImportService)).build()) {
                ReadSheet sheet = EasyExcel.readSheet("HL").build();
                excelReader.read(sheet);
            }
            log.info("数据导入完成，开始转换主表数据");
            businessDataService.transformHlAndGroupRelation(taskId);
            log.info("数据导入成功");
        } catch (Exception e) {
            log.error("导入失败", e);
            return Result.failed(ErrorEnum.IMPORT_FAIL);
        }
        return Result.success("导入成功");
    }

    @ApiIgnore
    @ApiOperation("curve数据excel导入")
    @PostMapping(value = "/curve/import")
    public Result<String> curveImport(@RequestPart("file") @RequestParam("file") MultipartFile file,
                                      @RequestParam("projectId") Long projectId) {
        String taskId = IdGenerateHelper.nextId();
        log.info("导入curve数据开始，taskId：{}", taskId);
        Project project = projectService.findById(projectId);
        if (null == project) {
            throw new BusinessException("project Id 错误");
        }
        dataImportService.truncateCurveImportAll();
        try {
            log.info("导入Curve common数据");
            try (ExcelReader excelReader = EasyExcel.read(
                    file.getInputStream(),
                    CurveImport.class,
                    new CurveImportListener(project.getId(), taskId, dataImportService)).build()) {
                ReadSheet sheet = EasyExcel.readSheet("curve").build();
                excelReader.read(sheet);
            }
            log.info("导入Curve Important数据");
            try (ExcelReader excelReader = EasyExcel.read(
                    file.getInputStream(),
                    CurveImportantImport.class,
                    new CurveImportantImportListener(project.getId(), taskId, dataImportService)).build()) {
                ReadSheet sheet = EasyExcel.readSheet("curve important points").build();
                excelReader.read(sheet);
            }
            log.info("数据导入完成，开始转换主表数据");
            int i = businessDataService.transformCurve(taskId);
            log.info("数据导入成功，共导入{}条数据", i);
            try {
                dataFilterService.cacheClear(null);
            } catch (Exception e) {
                log.error("缓存清除失败", e);
            }
        } catch (Exception e) {
            log.error("导入失败", e);
            return Result.failed(ErrorEnum.IMPORT_FAIL);
        }
        return Result.success("导入成功");
    }

    @ApiOperationSupport(order = 1)
    @ApiOperation("导入新方法")
    @PostMapping(value = "/abstract/import")
    public Result<String> abstractImport(@RequestPart("file") @RequestParam("file") @ApiParam(value = "模板文件", required = true) MultipartFile file,
                                         @RequestParam("projectId") @ApiParam(value = "项目ID", required = true) Long projectId,
                                         @RequestParam("adminKey") @ApiParam(value = "管理员秘钥", required = true) String adminKey) {
        try (FastByteArrayOutputStream os = IoUtil.read(file.getInputStream())) {
            log.info("导入数据");
            for (ImportEnum value : ImportEnum.values()) {
                log.info("当前导入图表：" + value.displayName);
                try (ExcelReader excelReader = EasyExcel.read(
                        IoUtil.toStream(os.toByteArray()),
                        new ImportListener(value, projectId)).build()) {
                    ReadSheet sheet = EasyExcel.readSheet(value.sheetName).build();
                    excelReader.read(sheet);
                }
                log.info(value.displayName + "导入完成");
            }
        } catch (Exception e) {
            log.error("导入失败", e);
            return Result.failed(ErrorEnum.IMPORT_FAIL);
        }
        return Result.success("导入成功");
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "单sheet导入（开发用）")
    @PostMapping(value = "/import/single")
    public Result<String> importSingle(@RequestPart("file") @RequestParam("file") @ApiParam(value = "模板文件", required = true) MultipartFile file,
                                       @RequestParam("projectId") @ApiParam(value = "项目ID", required = true) Long projectId,
                                       @RequestParam("tableNo") @ApiParam(value = "数据sheetID", required = true) Integer tableNo,
                                       @RequestParam("adminKey") @ApiParam(value = "管理员秘钥", required = true) String adminKey) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        try (FastByteArrayOutputStream os = IoUtil.read(file.getInputStream())) {
            log.info("导入数据");
            ImportEnum value = ImportEnum.getByTableNo(tableNo);
            if (null == value) {
                throw new BusinessException("Table No 错误");
            }
            log.info("当前导入图表：" + value.displayName);
            try (ExcelReader excelReader = EasyExcel.read(
                    IoUtil.toStream(os.toByteArray()),
                    new ImportListener(value, projectId)).build()) {
                ReadSheet sheet = EasyExcel.readSheet(value.sheetName).build();
                excelReader.read(sheet);
            }
            log.info(value.displayName + "导入完成");
        } catch (Exception e) {
            log.error("导入失败", e);
            return Result.failed(ErrorEnum.IMPORT_FAIL);
        }
        return Result.success("导入成功");
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation("清空缓存（开发用）")
    @GetMapping(value = "/cache")
    public Result<String> removeCache(@RequestParam("key") String key) {
        cacheRepository.delete(key);
        return Result.success("删除结束");
    }
}
