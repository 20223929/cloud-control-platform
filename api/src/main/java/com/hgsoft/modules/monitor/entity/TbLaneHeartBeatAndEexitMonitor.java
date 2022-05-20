package com.hgsoft.modules.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.modules.utils.ServiceTypeMapToNameUtil;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

import static com.hgsoft.modules.utils.FeeUtil.movePointToLeft2;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="前端软件实时统计", description="前端软件实时统计")
@TableName(value = "tb_lane_heart_beat_and_eexit_monitor", resultMap = "BaseResultMap")
public class TbLaneHeartBeatAndEexitMonitor {
    private String merchantGroupId;//拓展方id
    private String merchantId;//运营方id
    private Integer serviceType;

    @Excel(name = "拓展方", width = 25)
    private String merchantGroupIdStr;

    @Excel(name = "运营方", width = 25)
    private String merchantIdStr;

    @Excel(name = "服务类型", width = 25)
    private String serviceTypeStr;

    @Excel(name = "接入场景数量", width = 25)
    private Integer allSiteCount;

    @Excel(name = "接入前端软件数量", width = 25)
    private Integer allSoftCount;

    @Excel(name = "停用数量", width = 25)
    private Integer disableSoftCount;

    @Excel(name = "启用数量", width = 25)
    private Integer enableSoftCount;

    @Excel(name = "心跳正常", width = 25)
    private Integer heartBeatNormal;

    @Excel(name = "心跳丢失超12小时", width = 25)
    private Integer heartBeatLoseOver12h;

    @Excel(name = "心跳丢失超24小时", width = 25)
    private Integer heartBeatLoseOver24h;

    @Excel(name = "心跳丢失超7天", width = 25)
    private Integer heartBeatLoseOver7d;

    @Excel(name = "未上传省中心流水数", width = 25)
    private Long unUploadEexitCount;

    private Long unUploadEexitAmount;

    @Excel(name = "未上传省中心流水金额", width = 25)
    private String unUploadEexitAmountStr;

    @Excel(name = "已上传省中心流水数", width = 25)
    private Long uploadEexitCount;

    private Long uploadEexitAmount;

    @Excel(name = "已上传省中心流水金额", width = 25)
    private String uploadEexitAmountStr;

    @Excel(name = "省中心接收流水数", width = 25)
    private Long provinceRecvEexitCount;

    private Long provinceRecvEexitAmount;

    @Excel(name = "省中心接收流水金额(元)", width = 25)
    private String provinceRecvEexitAmountStr;

    @Excel(name = "本系统与省中心接收流水数量顺差", width = 25)
    private Long diffCount;//发送数量-省中心数量

    /**
     * 让自己变成能展示的状态
     */
    public void triggerShowState(Map<String, String> merchantInfo){
        this.setMerchantGroupIdStr(merchantInfo.get(this.getMerchantGroupId()));
        this.setMerchantIdStr(merchantInfo.get(this.getMerchantId()));
        this.setServiceTypeStr(ServiceTypeMapToNameUtil.getServiceTypeName(this.getServiceType()));
        this.setUnUploadEexitAmountStr(movePointToLeft2(this.getUnUploadEexitAmount()));
        this.setUploadEexitAmountStr(movePointToLeft2(this.getUploadEexitAmount()));
        this.setProvinceRecvEexitAmountStr(movePointToLeft2(this.getProvinceRecvEexitAmount()));
        this.setDiffCount(getUploadEexitCount()-getProvinceRecvEexitCount());
    }
}
