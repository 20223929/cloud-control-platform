import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/report/etcPayReport/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}
