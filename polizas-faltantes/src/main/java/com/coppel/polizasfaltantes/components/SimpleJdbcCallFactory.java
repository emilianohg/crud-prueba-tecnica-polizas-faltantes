package com.coppel.polizasfaltantes.components;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

@Component
public class SimpleJdbcCallFactory {
   public SimpleJdbcCall create(JdbcTemplate template) {
       return new SimpleJdbcCall(template);
   }
}
