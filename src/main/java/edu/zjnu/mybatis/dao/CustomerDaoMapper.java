package edu.zjnu.mybatis.dao;

import edu.zjnu.mybatis.model.Customer;

import java.util.List;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2015-12-03 14:45:51
 * Description: 客户查询类，一对多查询
 */

public interface CustomerDaoMapper {
    List<Customer> getCustomerList();
    Customer getCustomerByName(String name);
}
