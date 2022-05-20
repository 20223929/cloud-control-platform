import request from 'ecip-web/utils/request'


export const getTreeData = (data, params) => {
  return request({
    url: 'api/v1/test/testtreetable/testLeftTree/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (treeId) => {
  return request({
    url: `api/v1/test/testtreetable/testLeftTree/${treeId}`,
    method: 'get'
  })
}

export const removeByIds = (ids) => {
  return request({
    url: 'api/v1/test/testtreetable/testLeftTree/removeByIds',
    method: 'put',
    data: ids || []
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/test/testtreetable/testLeftTree/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/test/testtreetable/testLeftTree',
    method: 'post',
    data: data
  })
}

export const update = (treeId, data) => {
  return request({
    url: `api/v1/test/testtreetable/testLeftTree/${treeId}`,
    method: 'put',
    data: data
  })
}