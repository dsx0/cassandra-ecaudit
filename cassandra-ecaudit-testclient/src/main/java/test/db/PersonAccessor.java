package test.db;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface PersonAccessor {

	@Query("SELECT * FROM persons")
	Result<DbPerson> getAll();
	
}
