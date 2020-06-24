
cart-objects 


Postgres sink
-----

Postgres
create table if not exists pulsar_postgres_jdbc_sink
(
id serial PRIMARY KEY,
name VARCHAR(255) NOT NULL    
);

vi connectors/pulsar-postgres-jdbc-sink.yaml 

configs:
  userName: "testuser"
  password: "password"
  jdbcUrl: "jdbc:postgresql://localhost:5432/testdb"
  tableName: "pulsar_postgres_jdbc_sink"

vi connectors/avro-schema

{
  "type": "AVRO",
  "schema": "{\"type\":\"record\",\"name\":\"Test\",\"fields\":[{\"name\":\"id\",\"type\":[\"null\",\"int\"]},{\"name\":\"name\",\"type\":[\"null\",\"string\"]}]}",
  "properties": {}
}

 schema to a topic
bin/pulsar-admin schemas upload pulsar-postgres-jdbc-sink-topic -f ./connectors/avro-schema

bin/pulsar-admin schemas get pulsar-postgres-jdbc-sink-topic

Create JDBC
—
bin/pulsar-admin sinks create \
--archive ./connectors/pulsar-io-jdbc-postgres-2.6.0.nar \
--inputs pulsar-postgres-jdbc-sink-topic \
--name pulsar-postgres-jdbc-sink \
--sink-config-file ./connectors/pulsar-postgres-jdbc-sink.yaml \
--parallelism 1

List

bin/pulsar-admin sinks list \
--tenant public \
--namespace default

get/status/stop/restart/delete

bin/pulsar-admin sinks delete \
--tenant public \
--namespace default \
--name pulsar-postgres-jdbc-sink


Update 
bin/pulsar-admin sinks update \
--name pulsar-postgres-jdbc-sink \
--parallelism 2


Elastic search:

vi file_elastic_kibana_latest.yml 

version: '3.1'
services:
  kibana:
     image: docker.elastic.co/kibana/kibana:7.3.0
     ports:
         - 5601:5601

  elasticsearch:
     environment:
            - "discovery.type=single-node"
            - "MAX_CLAUSE_COUNT=4096"
            - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
     image: docker.elastic.co/elasticsearch/elasticsearch:7.3.0
     ports:
         - 9200:9200


docker-compose -f file_elastic_kibana_latest.yml up


vi connectors/elasticsearch-sink.yml

configs:
    elasticSearchUrl: "http://localhost:9200"
    indexName: “test

bin/pulsar-admin sinks create \
    --archive ./connectors/pulsar-io-elastic-search-2.6.0.nar \
    --tenant public \
    --namespace default \
    --name elasticsearch-test-sink \
    --sink-config-file ./connectors/elasticsearch-sink.yml \
    --inputs elasticsearch_test

bin/pulsar-client produce elasticsearch_test --messages "{\"a\":1}"

curl -s http://localhost:9200/test/_search