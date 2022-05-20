import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/settlement/tbBankSettlement/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const getDetail = (params, data) => {
  return request({
    url: 'api/v1/settlement/tbBankSettlement/detail',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (bankTransDate, merchantId) => {
  return request({
    url: `api/v1/settlement/tbBankSettlement/${bankTransDate}/${merchantId}`,
    method: 'get'
  })
}


export const deleteByIds = (tbBankSettlementList) => {
  return request({
    url: 'api/v1/settlement/tbBankSettlement/deleteByIds',
    method: 'delete',
    data: tbBankSettlementList || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/settlement/tbBankSettlement',
    method: 'post',
    data: data
  })
}

export const update = (bankTransDate, merchantId, data) => {
  return request({
    url: `api/v1/settlement/tbBankSettlement/${bankTransDate}/${merchantId}`,
    method: 'put',
    data: data
  })
}
