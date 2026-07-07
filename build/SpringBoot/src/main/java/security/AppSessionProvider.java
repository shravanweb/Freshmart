package security;

import java.util.Collections;
import models.AnonymousUser;
import models.BaseUser;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repository.jpa.UserRepository;
import store.EntityMutator;

@Component
@Service
public class AppSessionProvider {
  @Autowired private EntityMutator mutator;
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private UserRepository userRepo;

  public void setUser(String type, long id, String session) {
    if (id == 0l) {
      return;
    }
    UserProxy proxy = new UserProxy(type, id, session);
    setUserProxy(proxy);
  }

  public String updateUser() {
    UserProxy user = getCurrentUserProxy();
    if (user.type.equals("User")) {
      User usr = userRepo.getOne(user.userId);
      user.user = usr;
      return usr.getEmail();
    }
    return null;
  }

  public BaseUser getCurrentUser() {
    UserProxy user = getCurrentUserProxy();
    if (user == null) return new AnonymousUser();
    if (user.user != null) return user.user;
    if (user.type.equals("User")) {
      User usr = userRepo.getOne(user.userId);
      return usr;
    }
    return null;
  }

  public void setToken(String token) {
    if (token == null) {
      return;
    }
    UserProxy proxy = jwtTokenUtil.validateToken(token);
    setUserProxy(proxy);
  }

  public void setUserProxy(UserProxy proxy) {
    if (proxy == null) {
      return;
    }
    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(proxy, proxy.sessionId, Collections.emptyList());
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  public UserProxy getCurrentUserProxy() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null
        || auth
            instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
      return null;
    }
    Object principal = auth.getPrincipal();
    return ((UserProxy) principal);
  }

  public void clear() {
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  public AnonymousUser getAnonymousUser() {
    BaseUser user = getCurrentUser();
    if (user instanceof AnonymousUser) {
      return ((AnonymousUser) user);
    }
    return null;
  }

  public User getUser() {
    BaseUser user = getCurrentUser();
    if (user instanceof User) {
      return ((User) user);
    }
    return null;
  }
}
