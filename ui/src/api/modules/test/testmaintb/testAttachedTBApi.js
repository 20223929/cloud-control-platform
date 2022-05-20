import request from 'ecip-web/utils/request'

export const listByMaster = (data) => {
  return request({
    url: `api/v1/test/testmaintb/testAttachedTB/listByMaster`,
    method: 'post',
    data: data
  })
}