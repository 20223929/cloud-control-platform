import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/agent/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findDataById = (id) => {
  return request({
    url: `api/v1/agent/getAgentByPrimaryKey/${id}`,
    method: 'get'
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/agent/save',
    method: 'post',
    data: data || {}
  })
}
