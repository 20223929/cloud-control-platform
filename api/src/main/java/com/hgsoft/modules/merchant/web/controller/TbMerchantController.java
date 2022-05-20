package com.hgsoft.modules.merchant.web.controller;

import com.hgsoft.ecip.framework.common.annotation.AopLog;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.agent.cache.AgentCache;
import com.hgsoft.modules.merchant.entity.Merchant;
import com.hgsoft.modules.merchant.entity.MerchantGroup;
import com.hgsoft.modules.merchant.entity.Site;
import com.hgsoft.modules.merchant.service.MerchantService;
import com.hgsoft.modules.merchant.service.SysUserMerchantService;
import com.hgsoft.modules.merchantcommon.MerchantTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * TbMerchantController
 *
 * @author 吴锡霖
 * @version 2021-02-27 00:40:22
 */

@Slf4j
@Api(tags = "商户信息")
@RestController("com.hgsoft.modules.merchant.web.controller.TbMerchantController")
@RequestMapping("api/v1/merchant/tbMerchant")
public class TbMerchantController {
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SysUserMerchantService sysUserMerchantService;

    @PostMapping("/data")
    @ApiOperation(value = "商户信息树查询", notes = "商户信息树查询")
    public ResultBean<List<MerchantTree>> findTreeData() {
        return ResultHandler.ok(merchantService.findTreeData());
    }

    @PostMapping("/data/{nodeLevel}")
    @ApiOperation(value = "商户信息树查询(根据节点查询)", notes = "商户信息树查询(根据节点查询)")
    public ResultBean<List<MerchantTree>> findTreeData(@PathVariable Integer nodeLevel) {
        return ResultHandler.ok(merchantService.findMerchantByNodeLevel(nodeLevel));
    }

    /**
     * 根据主键查询一级商户信息数据
     */
    @GetMapping("getMerchantGroup/{id}")
    @ApiOperation(value = "根据主键查询一级商户信息数据", notes = "根据主键查询一级商户信息数据")
    public ResultBean<MerchantGroup> getMerchantGroupById(@PathVariable String id) {
        return ResultHandler.ok(merchantService.getMerchantGroupById(id));
    }

    /**
     * 根据主键查询二级商户信息数据
     */
    @GetMapping("getMerchant/{id}")
    @ApiOperation(value = "根据主键查询二级商户信息数据", notes = "根据主键查询二级商户信息数据")
    public ResultBean<Merchant> getMerchantById(@PathVariable String id) {
        return ResultHandler.ok(merchantService.getMerchantById(id));
    }

    /**
     * 根据主键查询三级商户信息数据
     */
    @GetMapping("getSite/{id}")
    @ApiOperation(value = "根据主键查询三级商户信息数据", notes = "根据主键查询三级商户信息数据")
    public ResultBean<Site> getSiteById(@PathVariable String id) {
        return ResultHandler.ok(merchantService.getSiteById(id));
    }

    /**
     * 获取代理商信息
     *
     * @return
     */
    @GetMapping("getAgentMap")
    @ApiOperation(value = "获取代理商信息", notes = "获取代理商信息")
    public ResultBean<Map<String, String>> getAgentMap() {
        return ResultHandler.ok(AgentCache.getAgentMap());
    }

    /**
     * 保存一级商户信息
     */
    @PostMapping("/saveMerchantGroup")
    @ApiOperation(value = "保存一级商户信息", notes = "保存一级商户信息")
    @RequiresPermissions(value = {"tbMerchant:add", "tbMerchant:edit"}, logical = Logical.AND)
    @AopLog(module = "tb_merchant", optType = "addOrEdit", description = "新增/修改一级商户信息")
    public ResultBean<Void> saveMerchantGroup(@RequestBody MerchantGroup merchantGroup) {
        merchantService.saveMerchantGroup(merchantGroup);
        return ResultHandler.ok();
    }

    /**
     * 保存二级商户信息
     */
    @PostMapping("/saveMerchant")
    @ApiOperation(value = "保存二级商户信息", notes = "保存二级商户信息")
    @RequiresPermissions(value = {"tbMerchant:add", "tbMerchant:edit"}, logical = Logical.AND)
    @AopLog(module = "tb_merchant", optType = "addOrEdit", description = "新增/修改二级商户信息")
    public ResultBean<Void> saveMerchant(@RequestParam(name = "certFile", required = false) MultipartFile certFile, Merchant merchant) {
        merchantService.saveMerchant(certFile, merchant);
        return ResultHandler.ok();
    }

    /**
     * 保存三级商户信息
     */
    @PostMapping("/saveSite")
    @ApiOperation(value = "保存三级商户信息", notes = "保存三级商户信息")
    @RequiresPermissions(value = {"tbMerchant:add", "tbMerchant:edit"}, logical = Logical.AND)
    @AopLog(module = "tb_merchant", optType = "addOrEdit", description = "新增/修改三级商户信息")
    public ResultBean<Void> saveSite(@RequestBody Site site) {
        merchantService.saveSite(site);
        return ResultHandler.ok();
    }

    /**
     * 下载平台证书
     *
     * @param response
     * @return
     */
    @PostMapping("/downloadCert")
    @ApiOperation(value = "下载平台证书", notes = "下载平台证书")
    @RequiresPermissions(value = {"tbMerchant:download"})
    public byte[] downloadCert(HttpServletResponse response) {
        return merchantService.downloadCert(response);
    }
}
