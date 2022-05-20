import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/querymanage/gasStationQuery/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findDataById = (data) => {
  return request({
    url: `api/v1/querymanage/gasStationQuery/detail`,
    method: 'post',
    data: data || {}
  })
}
