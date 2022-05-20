import request from 'ecip-web/utils/request'

export const getTreeData = (data) => {
  return request({
    url: 'api/v1/merchant/tbMerchant/data',
    method: 'post'
    // data: data
  })
}

export const getTreeDataByNodeLevel = (nodeLevel) => {
  return request({
    url: `api/v1/merchant/tbMerchant/data/${nodeLevel}`,
    method: 'post'
    // data: data
  })
}

export const findMerchantGroupByPrimaryKey = (id, nodeLevel) => {
  return request({
    url: `api/v1/merchant/tbMerchant/getMerchantGroup/${id}`,
    method: 'get'
  })
}

export const findMerchantByPrimaryKey = (id, nodeLevel) => {
  return request({
    url: `api/v1/merchant/tbMerchant/getMerchant/${id}`,
    method: 'get'
  })
}

export const findSiteByPrimaryKey = (id, nodeLevel) => {
  return request({
    url: `api/v1/merchant/tbMerchant/getSite/${id}`,
    method: 'get'
  })
}

export const getAgentInfo = () => {
  return request({
    url: 'api/v1/merchant/tbMerchant/getAgentMap',
    method: 'get'
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/merchant/tbMerchant/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const saveMerchantGroup = (data) => {
  return request({
    url: 'api/v1/merchant/tbMerchant/saveMerchantGroup',
    method: 'post',
    data: data
  })
}

export const saveSite = (data) => {
  return request({
    url: 'api/v1/merchant/tbMerchant/saveSite',
    method: 'post',
    data: data
  })
}

export const saveMerchant = (data) => {
  return request({
    url: 'api/v1/merchant/tbMerchant/saveMerchant',
    method: 'post',
    data: data
  })
}

export const update = (id, data) => {
  return request({
    url: `api/v1/merchant/tbMerchant/${id}`,
    method: 'put',
    data: data
  })
}
