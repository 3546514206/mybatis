package edu.zjnu.mybatis.dao;

import edu.zjnu.mybatis.model.Customer;
import edu.zjnu.mybatis.model.Order;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2015-12-03 15:41:30
 * Description: 主要测试一对多结果集查询
 */
public class CustomerDaoTest {

    private Log log = LogFactory.getLog(this.getClass());

    private SqlSessionFactory sqlSessionFactory;

    private Reader reader;

    private SqlSession session;

    private CustomerDaoMapper customerDao;

    @Before
    public void init() throws Exception {
        reader = Resources.getResourceAsReader("configuration.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        customerDao = session.getMapper(CustomerDaoMapper.class);
    }

    @After
    public void close() throws Exception {
        if (session != null) {
            session.close();
        }
        if (reader != null) {
            reader.close();
        }
    }

    @Test
    public void testGetCustomerList() throws Exception {
        log.info("testGetCustomerList");
        List<Customer> customerList = customerDao.getCustomerList();
        log.info("customerList:" + customerList.size());
        for (Customer customer : customerList) {
            String name = customer.getName();
            log.info("name:" + name);
            List<Order> orders = customer.getOrders();
            log.info("orders:" + orders.size());
            for (Order order : orders) {
                System.out.println("name:" + order.getName() + ",price:" + order.getPrice());
            }
        }
//        session.commit();
    }
}
