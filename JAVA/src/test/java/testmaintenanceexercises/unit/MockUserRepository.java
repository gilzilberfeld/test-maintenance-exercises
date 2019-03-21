package testmaintenanceexercises.unit;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import testmaintenanceexercises.User;
import testmaintenanceexercises.UserRepository;

public class MockUserRepository implements UserRepository {

	public User mockUser;

	@Override
	public <S extends User> Optional<S> findOne(Example<S> example) {
		return (Optional<S>) Optional.of(mockUser);
	}

	/* Not used by tests*/ 
	@Override public List<User> findAll() { return null; }
	@Override public List<User> findAll(Sort sort) { return null; }
	@Override public List<User> findAllById(Iterable<Long> ids) {	return null;	}
	@Override public <S extends User> List<S> saveAll(Iterable<S> entities) { return null; }
	@Override public void flush() {	}
	@Override public <S extends User> S saveAndFlush(S entity) { return null;	}
	@Override public void deleteInBatch(Iterable<User> entities) {	}
	@Override public void deleteAllInBatch() {	}
	@Override public User getOne(Long id) { return null;}
	@Override public <S extends User> List<S> findAll(Example<S> example) {	return null;}
	@Override public <S extends User> List<S> findAll(Example<S> example, Sort sort) { return null;	}
	@Override public Page<User> findAll(Pageable pageable) { return null; }
	@Override public <S extends User> S save(S entity) { return null;	}
	@Override public Optional<User> findById(Long id) { return null;	}
	@Override public boolean existsById(Long id) { return false; 	}
	@Override public long count() {	return 0;	}
	@Override public void deleteById(Long id) {	}
	@Override public void delete(User entity) {	}
	@Override public void deleteAll(Iterable<? extends User> entities) {}
	@Override public void deleteAll() {	}
	@Override public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {return null;	}
	@Override public <S extends User> long count(Example<S> example) { return 0;}
	@Override public <S extends User> boolean exists(Example<S> example) { return false; }

}
