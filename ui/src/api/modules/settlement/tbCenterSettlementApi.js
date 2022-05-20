import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/settlement/tbCenterSettlement/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (settlementDay, merchantGroupId, merchantId, siteId) => {
  return request({
    url: `api/v1/settlement/tbCenterSettlement/${settlementDay}/${merchantGroupId}/${merchantId}/${siteId}`,
    method: 'get'
  })
}


export const deleteByIds = (tbCenterSettlementList) => {
  return request({
    url: 'api/v1/settlement/tbCenterSettlement/deleteByIds',
    method: 'delete',
    data: tbCenterSettlementList || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/settlement/tbCenterSettlement',
    method: 'post',
    data: data
  })
}

export const update = (settlementDay, merchantGroupId, merchantId, siteId, data) => {
  return request({
    url: `api/v1/settlement/tbCenterSettlement/${settlementDay}/${merchantGroupId}/${merchantId}/${siteId}`,
    method: 'put',
    data: data
  })
}

export const myConfirm = (data) => {
  return request({
    url: 'api/v1/settlement/tbCenterSettlement/confirm',
    method: 'post',
    data: data
  })
}

export const batchConfirm = (records) => {
  return request({
    url: 'api/v1/settlement/tbCenterSettlement/batchConfirm',
    method: 'post',
    data: records || []
  })
}
