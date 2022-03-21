package test.init;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;

public class CassandraInit {

	private static Cluster CLUSTER;
	
	private static Session SESSION;

	public static Session init() {
		initCluster();
		
		initExampleDataIfNotExists();
		
		return SESSION;
	}
	
	private static void initCluster() {
		CLUSTER = Cluster.builder()
							.addContactPoint("localhost")
							.withPort(9042)
							.withCredentials("cassandra", "cassandra")
							.withSocketOptions(
										new SocketOptions()
											.setConnectTimeoutMillis(300000) //for debugging
											.setReadTimeoutMillis(300000) //for debugging
							)
							.build();
		
		SESSION = CLUSTER.connect();
	}
	
	private static void initExampleDataIfNotExists() {
		try {
			SESSION.execute("CREATE KEYSPACE ks_persons WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': 1 };");
			
			SESSION.execute("USE ks_persons;");
			
			SESSION.execute("CREATE TABLE persons (id UUID, name TEXT, PRIMARY KEY (id))");
			
			SESSION.execute("INSERT INTO persons (id, name) values (b09d6d64-dc0b-4d19-80fb-3083b24be2d1, 'Person Name 1');");
			SESSION.execute("INSERT INTO persons (id, name) values (f005b2ca-501a-4940-a1c0-11bca61acc9f, 'Person Name 2');");
			SESSION.execute("INSERT INTO persons (id, name) values (f7d13672-160c-4edf-bfd5-96adc972f668, 'Person Name 3');");
			SESSION.execute("INSERT INTO persons (id, name) values (0c655a73-788c-4242-a164-e2f34395cc2c, 'Person Name 4');");
			SESSION.execute("INSERT INTO persons (id, name) values (a89054c9-5d18-40a1-9549-d33777ee4c07, 'Person Name 5');");
		} catch (Exception ex) {
			//Ignore if already exists
			System.out.println("Error in initializing data. Ignore this if already initialized. msg=" + ex.getMessage());
		}
	}
	
	public static void close() {
		if (CLUSTER == null) {
			return;
		}
		
		System.out.println("Closing cluster...");
		CLUSTER.close();
	}
}
