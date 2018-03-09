package tw.sysbase.servie.sec;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sysbase.entity.sec.User;
@Service
@Transactional
public class UserSessionService {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 
}
