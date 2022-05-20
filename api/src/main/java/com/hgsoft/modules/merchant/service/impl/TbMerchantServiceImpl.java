package com.hgsoft.modules.merchant.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hgsoft.modules.merchant.entity.TbMerchant;
import com.hgsoft.modules.merchant.enums.ServiceTypeEnum;
import com.hgsoft.modules.merchant.mapper.TbMerchantMapper;
import com.hgsoft.modules.merchant.service.TbMerchantService;

import com.hgsoft.ecip.framework.core.service.FlatTreeService;

import com.hgsoft.ecip.web.util.SystemUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

import com.hgsoft.ecip.framework.util.TreeBuilder;
import com.hgsoft.ecip.framework.util.SqlUtil;
import com.hgsoft.ecip.framework.util.IdWorkerInit;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.ExcelImportUtil;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.excel.entity.ImportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import org.apache.commons.lang3.StringUtils;

import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;


/**
 * 商户信息ServiceImpl
 * @author 吴锡霖
 * @version 2021-02-27 00:40:22
 */

@Service("com.hgsoft.modules.merchant.service.TbMerchantService")
public class TbMerchantServiceImpl extends FlatTreeService<TbMerchantMapper, TbMerchant> implements TbMerchantService {

    private final String[] tipString = {
        "id(【主键id】): 可以不填写主键id；",
        "name(【名称】): 必填",
        "code(【编号】): 必填",
        "sort(【排序编号】): 非必填",
        "nodeLevel(【节点level】): 非必填",
        "parentIds(【所有父级编号】): 非必填",
        "parentId(【父级编号】): 非必填",
        "idCardType(【证件类型】): 非必填",
        "idCardNum(【证件号码】): 非必填",
        "agentName(【负责人】): 非必填",
        "mobile(【手机号码】): 非必填",
        "contact(【联系人】): 非必填",
        "tel(【联系方式】): 非必填",
        "bank(【开户行银行编号】): 非必填",
        "bankBranch(【开户银行支行】): 非必填",
        "bankAccountName(【开户银行账户名】): 非必填",
        "bankAccount(【开户银行账号】): 非必填",
        "settlePeriod(【结算周期，单位天】): 非必填",
        "clearServiceRate(【清分服务费率】): 非必填",
        "address(【地址】): 非必填",
        "lat(【经度】): 非必填",
        "lng(【纬度】): 非必填",
        "provinceNum(【省份编码】): 非必填",
        "startTime(【起始日期】): 非必填",
        "endTime(【结束日期】): 非必填",
        "operatorId(【所属运营方编号】): 非必填",
        "clearServiceType(【清分服务类型  0按金额，1按条数】): 非必填",
        "unionServiceType(【银联划账手续费类型  0按金额，1按条数】): 非必填",
        "unionServiceRate(【划账手续费率/（划账手续费/条）】): 非必填",
        "bankAdress(【开户行地址】): 非必填",
        "merchantType(【商户类型，1：停车场，2：服务区，3：加油站】): 非必填",
        "accessType(【接入类型，0-联网中心；1-银联】): 非必填",
        "entranceNum(【入口数】): 非必填",
        "exitNum(【出口数】): 非必填",
        "parkingSpaceNum(【车位数】): 非必填",
        "compressorGunNum(【加油枪数量】): 非必填",
        "merGroupNo(【银行服务商编号】): 非必填",
        "merchantNo(【银行商户编号】): 非必填",
        "merPrtclNo(【银行商户协议号】): 非必填",
        "enabled(【银行商户启用或禁用：0-启用；1-禁用；】): 非必填",
        "trxPlace(【银行商户交易场所编号】): 非必填",
        "bankRate(【银行手续费】): 非必填",
        "remarks(【备注信息】): 非必填"
    };

    /**
     * 树查询
     * @param tbMerchant
     * @param excludeId
     * @return
     */
    @Override
    public List<TbMerchant> findTreeData(TbMerchant tbMerchant, String excludeId) {
        if(tbMerchant == null) return new ArrayList<>();
        tbMerchant.setDataScope(SystemUtils.newInstance().findDataScope("tbMerchant:list"));

        List<TbMerchant> list = findTreeList(tbMerchant, excludeId);

        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        if (StringUtils.isBlank(tbMerchant.getName()) && StringUtils.isBlank(tbMerchant.getCode()) && StringUtils.isBlank(tbMerchant.getIdCardNum()) && StringUtils.isBlank(tbMerchant.getMobile()) && StringUtils.isBlank(tbMerchant.getContact()) && StringUtils.isBlank(tbMerchant.getTel()) && StringUtils.isBlank(tbMerchant.getBank()) && StringUtils.isBlank(tbMerchant.getBankBranch()) && StringUtils.isBlank(tbMerchant.getBankAccountName()) && StringUtils.isBlank(tbMerchant.getBankAccount())) {
            return TreeBuilder.buildFlatTree(list);
        }
        Set<String> pidSet = new HashSet<>(TreeBuilder.separateParentIds(list));

        List<TbMerchant> dataList = list(new LambdaQueryWrapper<TbMerchant>().in(TbMerchant::getId, pidSet));
        List<TbMerchant> childList = this.baseMapper.findAllChildNodes(list.stream().map(TbMerchant::getId).collect(Collectors.toList()));

        childList.forEach(item -> {
            if (pidSet.add(item.getId())) {
                dataList.add(item);
            }
        });

        return TreeBuilder.buildFlatTree(dataList);
    }

    @Override
    public TbMerchant getByPrimaryKey(String id) {
        return this.getById(id);
    }

    private void initPath(TbMerchant item) {
        String parentId = item.getParentId();
        if (StringUtils.isBlank(parentId) || "0".equals(parentId)) {
            item.setParentId("0");
            item.setParentIds(",0");
            item.setNodeLevel(0);
            return;
        }
        TbMerchant parent = this.getById(parentId);
        String parentIds = parent.getParentIds();
        Integer nodeLevel = parent.getNodeLevel() + 1;
        item.setParentIds("," + parentId + parentIds);
        item.setNodeLevel(nodeLevel);
    }


    @Override
    @Transactional
    public void saveTbMerchant(TbMerchant tbMerchant) {
        initPath(tbMerchant);
        tbMerchant.preInsert();
        if("2".equals(tbMerchant.getNodeLevel()) && ServiceTypeEnum.PARKING_LOT.getValue().toString().equals(tbMerchant.getMerchantType())){
            tbMerchant.setTerminalNo(tbMerchant.getCode() + "01");
        }
        this.saveEntity(tbMerchant);
    }

    @Override
    @Transactional
    public void updateTbMerchant(TbMerchant tbMerchant) {
        initPath(tbMerchant);
        tbMerchant.preUpdate();
        this.updateEntity(tbMerchant);
    }


    /**
     * 批量删除(物理删除)
     */
    @Transactional
    @Override
    public void deleteByPrimaryKey(List<String> keyList) {
        if (keyList == null || keyList.isEmpty()) {
            return;
        }
        LambdaQueryWrapper<TbMerchant> queryWrapper = new LambdaQueryWrapper<TbMerchant>().in(TbMerchant::getId, keyList);

        keyList.forEach(key -> {
            queryWrapper.or(j -> j.like(TbMerchant::getParentIds, SqlUtil.pathLike(key)));
        });
        this.remove(queryWrapper);
    }

    /**
     * 导入数据
     */
    @Override
    @Transactional
    public List<String> importExcel(MultipartFile file, boolean isNewPk, String strategy) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(2);
        List<TbMerchant> list = ExcelImportUtil.importExcel(file.getInputStream(), TbMerchant.class, params);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        List<TbMerchant> saveList = new ArrayList();
        List<TbMerchant> updateList = new ArrayList();

        if (!saveList.isEmpty()) {
            this.saveBatch(saveList);
        }
        if (!updateList.isEmpty()) {
            this.updateBatchById(updateList);
        }
        return list.stream().map(TbMerchant::getId).collect(Collectors.toList());
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, TbMerchant tbMerchant) {
        List<TbMerchant> list = this.findList(tbMerchant);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "商户信息-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, TbMerchant.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商户信息", "商户信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
      * 导出模板
      */
    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "商户信息模板");
        mv.addObject(NormalExcelConstants.CLASS, TbMerchant.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商户信息", "商户信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    @Override
    public Map<String,String> getAllLevelMerchantInfo(String code){
        if(StrUtil.isBlank(code)) return this.baseMapper.selectList(new LambdaQueryWrapper<>()).stream().collect(Collectors.toMap(key->key.getCode(), value->value.getName()));
        List<TbMerchant> list = this.baseMapper.selectList(new LambdaQueryWrapper<TbMerchant>().like(TbMerchant::getCode,code));
        if (list.isEmpty()) {
            return new HashMap<>();
        }
        List<TbMerchant> result = new ArrayList<>();
        Set<String> pidSet = new HashSet<>(TreeBuilder.separateParentIds(list));
        List<TbMerchant> dataList = this.baseMapper.selectList(new LambdaQueryWrapper<TbMerchant>().in(TbMerchant::getId, pidSet));
        result.addAll(dataList);
        List<TbMerchant> childList = this.baseMapper.findAllChildNodes(list.stream().map(TbMerchant::getId).collect(Collectors.toList()));
        if(CollUtil.isEmpty(childList)) return result.stream().collect(Collectors.toMap(key->key.getCode(), value->value.getName()));
        result.addAll(childList);
        Map<String,String> resultMap = result.stream().collect(Collectors.toMap(key->key.getCode(),value->value.getName()));
        return resultMap;
    }

}
