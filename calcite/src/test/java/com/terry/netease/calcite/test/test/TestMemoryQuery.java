package com.terry.netease.calcite.test.test;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.terry.netease.calcite.test.function.TimeOperator;

public class TestMemoryQuery {
	private static final Logger logger = LoggerFactory.getLogger(TestMemoryQuery.class);
	
    @SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
    	long start = System.currentTimeMillis();
    	TestMemoryQuery t = new TestMemoryQuery();
    	URL systemResource = t.getClass().getClassLoader().getSystemResource("School.json");
    	String path = systemResource.getPath().toString();
    	try {
			Class.forName("org.apache.calcite.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    	
        Properties info = new Properties();
        try {
            Connection connection = DriverManager.getConnection("jdbc:calcite:model=" + path, info);
            CalciteConnection calciteConn = connection.unwrap(CalciteConnection.class);
            calciteConn.getRootSchema().add("THE_SYEAR", ScalarFunctionImpl.create(TimeOperator.class.getMethod("THE_SYEAR", Date.class)));
            calciteConn.getRootSchema().add("THE_YEAR", ScalarFunctionImpl.create(TimeOperator.class.getMethod("THE_YEAR", Date.class)));
            ResultSet result = connection.getMetaData().getTables(null, null, null, null);
            while(result.next()) {
                logger.info("Catalog : " + result.getString(1) + ",Database : " + result.getString(2) + ",Table : " + result.getString(3));
            }
            result.close();
            result = connection.getMetaData().getColumns(null, null, "Student", null);
            while(result.next()) {
            	logger.info("name : " + result.getString(4) + ", type : " + result.getString(5) + ", typename : " + result.getString(6));
            }
            result.close();
            
            //根据年分类，统计人数
            Statement st = connection.createStatement();
            String sql="select THE_SYEAR(\"birthday\", 'year'), 1 , count(1) from \"Student\" as S INNER JOIN \"Class\" as C on S.\"classId\" = C.\"id\" group by THE_SYEAR(\"birthday\", 'year')";
            result = st.executeQuery(sql);
            logger.info(sql);
            while(result.next()) {
            	logger.info(result.getString(1) + "\t" + result.getString(2) + "\t" + result.getString(3));
            }
            
            sql ="select \"id\", \"name\",\"home\", \"birthday\" from \"Student\"";
            logger.info(sql);
            result = st.executeQuery(sql);
            
            while(result.next()) {
            	logger.info(result.getString(1) + "\t" + result.getString(2) + "\t" + result.getString(3) +"\t"+ result.getString(4));
            }
            
            result.close();
            connection.close();
            long end = System.currentTimeMillis();
            System.out.println((end - start)+"ms");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
