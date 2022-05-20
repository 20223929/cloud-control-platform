import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/manage/paySign/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const update = (sysId, data) => {
  return request({
    url: `api/v1/manage/paySign/editStatus/${sysId}`,
    method: 'put',
    data: data || {}
  })
}
