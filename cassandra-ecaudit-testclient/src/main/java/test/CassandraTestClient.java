package test;

import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import test.db.DbPerson;
import test.db.PersonAccessor;
import test.init.CassandraInit;

public class CassandraTestClient {

	private void testCassandra() {
		try {
			Session session = CassandraInit.init();
			
			session.execute("USE ks_persons;");
			
			System.out.println("\n\nTest manual statement");
			this.testManualStatement(session);
			System.out.println("\n\nTest manual PreparedStatement");
			this.testManualPreparedStatement(session);

			
			MappingManager mappingManager = new MappingManager(session);
			System.out.println("\n\nTest MappingManager with Mapper");
			this.testMappingManagerWithMapper(mappingManager);
			System.out.println("\n\nTest MappingManager with Accessor");
			this.testMappingManagerWithAccessor(mappingManager);
			
		} finally {
			CassandraInit.close();
		}
	}

	private void testManualStatement(Session session) {
		try {
			ResultSet resultSet = session.execute("SELECT id, name FROM persons");
			
			for (Row row : resultSet) {
				System.out.println(String.format("ManualStatementTest: Row: id = %s, name = %s", row.getUUID("id"), row.getString("name")));
			}
		} catch (Exception ex) {
			System.err.println("ManualStatementTest: Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void testManualPreparedStatement(Session session) {
		try {
			BoundStatement boundStatement = session.prepare("SELECT id, name FROM persons WHERE id = :id").bind();
			
			boundStatement.setUUID("id", UUID.fromString("f005b2ca-501a-4940-a1c0-11bca61acc9f"));
			
			ResultSet resultSet = session.execute(boundStatement);
	
			for (Row row : resultSet) {
				System.out.println(String.format("ManualPreparedStatementTest: Row: id = %s, name = %s", row.getUUID("id"), row.getString("name")));
			}
		} catch (Exception ex) {
			System.err.println("ManualPreparedStatementTest: Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void testMappingManagerWithMapper(MappingManager mappingManager) {
		try {
			Mapper<DbPerson> personMapper = mappingManager.mapper(DbPerson.class);
			
			DbPerson dbPerson = personMapper.get(UUID.fromString("f005b2ca-501a-4940-a1c0-11bca61acc9f"));
			
			if (dbPerson == null) {
				System.err.println("MapperTest: Got no person from db.");
				return;
			}

			System.out.println("MapperTest: Got person from db: " + dbPerson);
		} catch (Exception ex) {
			System.err.println("MapperTest: Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void testMappingManagerWithAccessor(MappingManager mappingManager) {
		try {
			PersonAccessor personAccessor = mappingManager.createAccessor(PersonAccessor.class);
			
			Result<DbPerson> allPersonsDb = personAccessor.getAll();
			
			if (allPersonsDb == null) {
				System.err.println("AccessorTest: Got no persons from db.");
				return;
			}
			
			List<DbPerson> allPersons = allPersonsDb.all();
			if (allPersons == null || allPersons.isEmpty()) {
				System.err.println("AccessorTest: Got no persons from db after 'all()'.");
				return;
			}
			
			for (DbPerson person : allPersons) {
				System.out.println("AccessorTest: Result-Entry: " + person);
			}
		} catch (Exception ex) {
			System.err.println("AccessorTest: Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CassandraTestClient test = new CassandraTestClient();

		test.testCassandra();
	}
}
