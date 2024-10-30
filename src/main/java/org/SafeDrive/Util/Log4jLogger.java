package org.SafeDrive.Util;

import org.SafeDrive.Modelo.EntidadeBase;

public class Log4jLogger<T extends EntidadeBase> extends EntidadeBase implements Loggable<T>{

    private final org.apache.logging.log4j.Logger logger;

    public Log4jLogger(Class<T> clazz) {
        this.logger = org.apache.logging.log4j.LogManager.getLogger(clazz);
    }

    @Override
    public void logCreate(T entidade) {
        logger.info("creat: "+entidade.toString());
    }

    @Override
    public void logRead(T entidade) {
        logger.info("read: "+ entidade.toString());
    }

    @Override
    public void logReadAll(T entidade) {
        logger.info("Read All ");
    }

}
