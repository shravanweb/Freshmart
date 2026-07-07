package main;

import d3e.core.QueryProvider;
import d3e.core.TransactionWrapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.EntityMutator;

@Service
public class SingletonInit {
  @Autowired private QueryProvider queryProvider;
  @Autowired private EntityMutator mutator;
  @Autowired private TransactionWrapper transactional;

  @PostConstruct
  public void initialize() throws jakarta.servlet.ServletException, java.io.IOException {
    QueryProvider.instance = queryProvider;
  }
}
