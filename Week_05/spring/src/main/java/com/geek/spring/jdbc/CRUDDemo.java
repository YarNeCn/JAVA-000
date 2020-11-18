package com.geek.spring.jdbc;


import java.sql.*;
import java.util.Arrays;
import java.util.Random;

/**
 * @program: JAVA-000
 * @description: 原生JDBC CRUD，批处理，以及事务
 * @author: yarne
 * @create: 2020-11-18 14:39
 **/
public class CRUDDemo {
    /**
     * 插入
     *
     * @param status
     */
    public static void insert(String status) {
        PreparedStatement preparedStatement = null;
        Connection con = ConnectUtil.getCon(null);
        try {
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement("insert into sys_user(`username`,`age`,`status`) VALUES (?,?,?)");
            preparedStatement.setString(1, "陶彦男" + new Random().nextInt());
            preparedStatement.setString(2, "18");
            preparedStatement.setString(3, status);
            int i = preparedStatement.executeUpdate();
            System.out.println("插入" + (i == 1 ? "成功" : "失败"));
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据状态修改
     *
     * @param status
     * @throws SQLException
     */
    public static void updateStatus(String status) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection con = ConnectUtil.getCon(null);
        try {
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement("update sys_user set status=? where username=?");
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, "陶彦男" + new Random().nextInt());
            int i = preparedStatement.executeUpdate();
            System.out.println("修改成功" + i + "条记录");
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据状态删除
     *
     * @param status
     * @throws SQLException
     */
    public static void deleteByStatus(String status) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection con = ConnectUtil.getCon(null);
        try {
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement("delete from sys_user where status=?");
            preparedStatement.setString(1, status);
            int i = preparedStatement.executeUpdate();
            System.out.println("删除" + (i == 1 ? "成功" : "失败"));
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据状态查询
     *
     * @param status
     * @throws SQLException
     */
    public static void queryByStatus(String status) throws SQLException {
        Connection con = ConnectUtil.getCon(null);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("select username,age,status from sys_user where status=?");
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            int a = 0;
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int age = resultSet.getInt("age");
                int status1 = resultSet.getInt("status");
                System.out.println("第" + (++a) + "条记录: username:" + username + ",age:" + age + ",status:" + status1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批处理插入
     *
     * @param status
     * @param num
     */
    public static void insertBatch(String status, Integer num) {
        Connection con = ConnectUtil.getCon(null);
        PreparedStatement preparedStatement = null;
        try {
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement("insert into sys_user(`username`,`age`,`status`) VALUES (?,?,?)");
            for (int i = 0; i < num; i++) {
                preparedStatement.setString(1, "陶彦男" + new Random().nextInt());
                preparedStatement.setString(2, "18");
                preparedStatement.setString(3, status);
                preparedStatement.addBatch();
            }
            int[] ints = preparedStatement.executeBatch();
            System.out.println("批量插入"+num+"条记录，结果为："+Arrays.toString(ints));
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                preparedStatement.clearBatch();
                preparedStatement.clearParameters();
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            //根据状态删除
            deleteByStatus("3");
            //插入
            insert("0");
            //根据状态修改
            updateStatus("0");
            //批处理插入
            insertBatch("3", 5);
            //根据状态查询
            queryByStatus("3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
