package com.sq.sell.logtest;

import com.sq.sell.SellApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class LogTest extends SellApplicationTests {
    /**
     * 复杂操作
     */
//    private final Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void test()
    {
        String name = "slj";
        String password = "ffx10101fsn";
        log.debug("debug...");
        log.info("info....");
        log.info("name: {}, password: {}",name,password);
        log.error("error..");
    }
}
