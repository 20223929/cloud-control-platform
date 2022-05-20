import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/settlement/tbBankSettlementDetail/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (transactionId) => {
  return request({
    url: `api/v1/settlement/tbBankSettlementDetail/${transactionId}`,
    method: 'get'
  })
}


export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/settlement/tbBankSettlementDetail/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/settlement/tbBankSettlementDetail',
    method: 'post',
    data: data
  })
}

export const update = (transactionId, data) => {
  return request({
    url: `api/v1/settlement/tbBankSettlementDetail/${transactionId}`,
    method: 'put',
    data: data
  })
}
