import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/complaintrefund/tbComplaintRefund/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const saveDraft = (data) => {
  return request({
    url: 'api/v1/complaintrefund/tbComplaintRefund/saveDraft',
    method: 'post',
    data: data || {}
  })
}

export const editDraft = (data) => {
  return request({
    url: 'api/v1/complaintrefund/tbComplaintRefund/editDraft',
    method: 'put',
    data: data || {}
  })
}

export const deleteDraft = (sysId) => {
  return request({
    url: `api/v1/complaintrefund/tbComplaintRefund/deleteDraft/${sysId}`,
    method: 'put'
  })
}

export const searchRefundResult = (sysId) => {
  return request({
    url: `api/v1/complaintrefund/tbComplaintRefund/searchRefundResult/${sysId}`,
    method: 'put'
  })
}

export const saveData = (data) => {
  return request({
    url: 'api/v1/complaintrefund/tbComplaintRefund/save',
    method: 'post',
    data: data || {}
  })
}

export const getTransactions = (params, data) => {
  return request({
    url: 'api/v1/complaintrefund/tbComplaintRefund/getTransactions',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const getRefundNo = () => {
  return request({
    url: 'api/v1/complaintrefund/tbComplaintRefund/getRefundNo',
    method: 'get'
  })
}

export const getDetail = (sysId, method) => {
  return request({
    url: `api/v1/complaintrefund/tbComplaintRefund/detail/${sysId}/${method}`,
    method: 'get'
  })
}

export const handle = (data) => {
  return request({
    url: `api/v1/complaintrefund/tbComplaintRefund/handle`,
    method: 'post',
    data: data || {}
  })
}
