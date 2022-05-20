package com.hgsoft.modules.merchant.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.api.client.service.OpenUserApiService;
import com.hgsoft.ecip.api.vo.PageVo;
import com.hgsoft.ecip.api.vo.SysrUserVo;
import com.hgsoft.ecip.framework.common.annotation.AopLog;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.ecip.framework.common.springboot.constant.EcipConfig;
import com.hgsoft.ecip.framework.exception.BusinessException;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.util.BeanUtil;
import com.hgsoft.ecip.framework.util.ValidateUtil;
import com.hgsoft.modules.merchant.entity.SysUserMerchant;
import com.hgsoft.modules.merchant.service.SysUserMerchantService;
import com.hgsoft.modules.merchant.vo.EtcUserVo;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.merchantcommon.MerchantTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户商户关联表Controller
 * @author 吴锡霖
 * @version 2021-04-19 09:13:54
 */

@Slf4j
@Api(tags = "用户商户关联表")
@RestController
@Profile("!alone")
@RequestMapping("api/v1/merchant/sysUserMerchant")
public class SysUserMerchantController {

    @Autowired
    private SysUserMerchantService sysUserMerchantService;
    @Autowired
    private OpenUserApiService openUserApiService;

    @Autowired
    private EcipConfig ecipConfig;
    @Autowired
    private MerchantManageService merchantManageService;

    @ApiOperation("分页查询用户列表")
    @PostMapping({"/userPage"})
    public ResultBean userPage(@RequestBody SysrUserVo sysrUser, PageVo<SysrUserVo> page, String queryDeptId, Integer queryAppId) {
        if (!this.ecipConfig.getSuperAdmin()) {
            sysrUser.setAppId(this.ecipConfig.getAppId());
            sysrUser.setTenantId(ShiroSecurityUtil.tenantId());
        }

        PageVo<SysrUserVo> userPage = this.openUserApiService.findUserPage(sysrUser, page, queryDeptId,
                queryAppId, ShiroSecurityUtil.userId());
        List<EtcUserVo> userVoList = new LinkedList<>();

        PageVo<EtcUserVo> etcUserVoPage = new PageVo<>();
        BeanUtil.copyBean(userPage, etcUserVoPage);
        userPage.getRecords().forEach(user -> {
            String userId = user.getId();
            List<MerchantTree> merchantByUserId = sysUserMerchantService.findMerchantByUserId(userId);
            EtcUserVo etcUserVo = BeanUtil.copyBean(user, new EtcUserVo());
            if (CollUtil.isNotEmpty(merchantByUserId)) {
                etcUserVo.setNodeLevel(merchantByUserId.get(0).getNodeLevel());
                etcUserVo.setMerchantIds(merchantByUserId.stream().map(MerchantTree::getId).collect(Collectors.toList()));
            }
            userVoList.add(etcUserVo);
        });
        etcUserVoPage.setRecords(userVoList);
        return ResultHandler.ok(etcUserVoPage);
    }

    @PostMapping("/userMerchant")
    @ApiOperation(value = "获取用户关联的商户", notes = "获取用户关联的商户")
    public ResultBean<String> userMerchant(String userId){
        SysUserMerchant sysUserMerchant = sysUserMerchantService.userMerchant(userId);
        String merchantId = "";
        if (sysUserMerchant != null) {
            merchantId = sysUserMerchant.getMerchantId();
        }
        return ResultHandler.ok(merchantId);
    }

    @PostMapping("/data")
    @ApiOperation(value = "分页查询用户商户关联表", notes = "分页查询用户商户关联表")
    public ResultBean<IPage<SysUserMerchant>> findPage(Page<SysUserMerchant> page, @RequestBody SysUserMerchant sysUserMerchant){
        return ResultHandler.ok(sysUserMerchantService.sysUserMerchantPage(page, sysUserMerchant));
    }

    /**
     * 查询list 集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询用户商户关联表集合", notes = "查询用户商户关联表集合")
    public ResultBean<List<SysUserMerchant>> list(@RequestBody SysUserMerchant sysUserMerchant) {
        return ResultHandler.ok(sysUserMerchantService.findList(sysUserMerchant));
    }

    /**
      * 根据主键查询数据
      */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据主键查询用户商户关联表数据", notes = "根据主键查询用户商户关联表数据")
    public ResultBean<SysUserMerchant> findByPrimaryKey(@PathVariable String id){
        return ResultHandler.ok(sysUserMerchantService.getByPrimaryKey(id));
    }

    @PostMapping("/findDataByIds")
    @ApiOperation(value = "根据ID查询用户商户关联表数据", notes = "根据ID查询用户商户关联表数据")
    public ResultBean<Collection<SysUserMerchant>> findDataByIds(@RequestBody List<String> ids){
        if (ids == null || ids.isEmpty()) {
            return ResultHandler.ok(new ArrayList<>());
        }
        return ResultHandler.ok(sysUserMerchantService.listByIds(ids));
    }

    @PostMapping("/findDataByPropValues")
    @ApiOperation(value = "根据propField, values查询数据", notes = "根据propField, values查询数据")
    public ResultBean<List<SysUserMerchant>> findDataByPropValues(String propField, @RequestBody List<String> propValues){
        if (propValues == null || propValues.isEmpty() || StringUtils.isBlank(propField)) {
            return ResultHandler.ok(new ArrayList<>());
        }
        return ResultHandler.ok(sysUserMerchantService.list(new QueryWrapper<SysUserMerchant>().in(propField, propValues)));
    }

    /**
     * 保存
     */
    @PostMapping()
    @ApiOperation(value = "保存用户商户关联表", notes = "保存用户商户关联表")
//    @RequiresPermissions(value = {"sysUserMerchant:add"})
    @AopLog(module = "sys_user_merchant", optType = "add", description = "新增用户商户关联表")
    public ResultBean<Void> saveSysUserMerchant(@RequestBody EtcUserVo etcUserVo, Model model){
        List<SysUserMerchant> list = etcUserVo.getMerchantIds().stream().map(vo->{
            SysUserMerchant sysUserMerchant = new SysUserMerchant();
            sysUserMerchant.setUserId(etcUserVo.getId());
            sysUserMerchant.setMerchantId(vo);
            return sysUserMerchant;
        }).collect(Collectors.toList());
        sysUserMerchantService.saveSysUserMerchant(list,etcUserVo.getNodeLevel());
        return ResultHandler.ok();
    }

    /**
     * 根据id更新数据
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "更新用户商户关联表数据", notes = "更新用户商户关联表数据")
    @RequiresPermissions(value = {"sysUserMerchant:edit"})
    @AopLog(module = "sys_user_merchant", optType = "edit", description = "更新用户商户关联表")
    public ResultBean<Void> updateSysUserMerchant(@PathVariable String id, @RequestBody SysUserMerchant sysUserMerchant, Model model){
        sysUserMerchant.setId(id);

        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = ValidateUtil.beanValidator(sysUserMerchant);
        if (StringUtils.isNotBlank(errMsg)){
            return ResultHandler.error(errMsg);
        }

        sysUserMerchantService.update(sysUserMerchant, new LambdaQueryWrapper<SysUserMerchant>()
            .eq(SysUserMerchant::getId, id));
        model.addAttribute("id", id);

        return ResultHandler.ok();
    }


    /**
      * 批量删除(逻辑删除)
      */
    @PutMapping("/removeByIds")
    @ApiOperation(value = "移除用户商户关联表数据", notes = "移除用户商户关联表数据")
    @RequiresPermissions(value = {"sysUserMerchant:remove"})
    @AopLog(module = "sys_user_merchant", optType = "remove", recordId = "ids", description = "移除用户商户关联表")
    public ResultBean<Void> removeByIds(@RequestBody List<String> ids, Model model){
        sysUserMerchantService.removeByPrimaryKey(ids);
        model.addAttribute("ids", StringUtils.wrap(StringUtils.join(ids, ","), ","));
        return ResultHandler.ok();
    }
    /**
      * 批量删除(物理删除)
      */
    @DeleteMapping("/deleteByIds")
    @ApiOperation(value = "移除用户商户关联表数据", notes = "移除用户商户关联表数据")
    @RequiresPermissions(value = {"sysUserMerchant:delete"})
    @AopLog(module = "sys_user_merchant", optType = "delete", recordId = "ids", description = "删除用户商户关联表")
    public ResultBean<Void> deleteByIds(@RequestBody List<String> ids, Model model) {
        sysUserMerchantService.deleteByPrimaryKey(ids);
        model.addAttribute("ids", StringUtils.wrap(StringUtils.join(ids, ","), ","));
        return ResultHandler.ok();
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载用户商户关联表模板", notes = "下载用户商户关联表模板")
    @RequiresPermissions(value = {"sysUserMerchant:download"})
    public ModelAndView exportSysUserMerchantFileTemplate(HttpServletRequest request) {
        return sysUserMerchantService.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出用户商户关联表数据", notes = "导出用户商户关联表数据")
    @RequiresPermissions(value = {"sysUserMerchant:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody SysUserMerchant sysUserMerchant) {
        return sysUserMerchantService.exportData(request, sysUserMerchant);
    }

    /**
     * @param file
     * @return
     * @description 导入数据皆是新建，有id则判断是否冲突，冲突则报错
     */
    @PostMapping("/importExcel")
    @ApiOperation(value = "导入用户商户关联表数据", notes = "导入用户商户关联表数据")
    @RequiresPermissions(value = {"sysUserMerchant:import"})
    @AopLog(module = "sys_user_merchant", optType = "import", recordId = "ids", description = "导入用户商户关联表")
    public ResultBean importExcel(@RequestParam("file") MultipartFile file, @RequestParam("isNewPk") boolean isNewPk,
                                  String strategy, Model model) throws Exception {
        try {
            List<String> ids = sysUserMerchantService.importExcel(file, isNewPk, strategy);
            model.addAttribute("ids", StringUtils.wrap(StringUtils.join(ids, ","), ','));
        } catch (BusinessException e) {
            log.error("导入数据出错", e);
            throw new BusinessException("导入数据出错");
        }
        return ResultHandler.ok();
    }

}
