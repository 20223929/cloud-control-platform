import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/bankbillcheck/bankCheck/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const getExceptionData = (params, data) => {
  return request({
    url: 'api/v1/bankbillcheck/bankCheck/exception',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const getDetailData = (data) => {
  return request({
    url: 'api/v1/bankbillcheck/bankCheck/detail',
    method: 'post',
    data: data || {}
  })
}

export const saveConfirmData = (data) => {
  return request({
    url: 'api/v1/bankbillcheck/bankCheck/confirm/save',
    method: 'post',
    data: data || {}
  })
}
