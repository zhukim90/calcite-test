package com.terry.netease.calcite.test;

import java.util.Map;

import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemorySchemaFactory implements SchemaFactory{
	
	private static final Logger logger = LoggerFactory.getLogger(MemorySchemaFactory.class);

	public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
		logger.info("param1 : " + operand.get("param1"));
		logger.info("param2 : " + operand.get("param2"));
		logger.info("Get database " + name);
		return new MemorySchema(name);
	}
}
