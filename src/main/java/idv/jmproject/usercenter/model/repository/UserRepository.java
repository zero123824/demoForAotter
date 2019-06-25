package idv.jmproject.usercenter.model.repository;

import idv.jmproject.usercenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserName(String name);

}
