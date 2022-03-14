package test.db;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(
		keyspace = "ks_persons", 
		name = "persons",
		readConsistency = "QUORUM",
		writeConsistency = "QUORUM",
		caseSensitiveKeyspace = false,
		caseSensitiveTable = false
)
public class DbPerson {

    @PartitionKey
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "name")
    private String name;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DbPerson [id=" + id + ", name=" + name + "]";
	}
}
