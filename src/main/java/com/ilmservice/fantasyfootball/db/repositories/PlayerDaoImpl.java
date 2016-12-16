package com.ilmservice.fantasyfootball.db.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PlayerDaoImpl implements PlayerDao {

  private static final Logger logger = LoggerFactory.getLogger(PlayerDaoImpl.class);

  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  @Modifying
  public void restartNflRanking(Integer nextGeneratedValue) {
    logger.debug("in restartNflRanking(): nextGeneratedValue={}", nextGeneratedValue);

    // The JPA 2.1 spec says: "Input parameters can only be used in the WHERE
    // clause or HAVING clause of a query or as the new value for an update item
    // in the SET clause of an update statement."
    // So in order to avoid the exception mentioned below, needed to not use a
    // JPQL (positional nor named) input parameter (nor a SpEL parameter), but
    // rather simply concatenate the 'nextGeneratedValue' variable into the
    // string - that variable never originates from user input, so no need to
    // worry about a SQL injection.
    //  * java.sql.SQLSyntaxErrorException: Syntax error: Encountered "?" at line 1, column 58.
    em.createNativeQuery("ALTER TABLE players ALTER COLUMN nflRanking RESTART WITH " + nextGeneratedValue)
      .executeUpdate();
  }
}
