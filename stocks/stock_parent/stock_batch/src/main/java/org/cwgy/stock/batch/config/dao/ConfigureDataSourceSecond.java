package org.cwgy.stock.batch.config.dao;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "org.cwgy.stock.core.dao1", sqlSessionFactoryRef = "secondSessionFactory")
public class ConfigureDataSourceSecond {

	private static final String MAPPER_LOCATION = "classpath:mapper/generatorMapper/**Mapper.xml";

	@Primary
	@ConfigurationProperties(prefix = "second.datasource")
	@Bean(name = "secondDataSource")
	public DataSource secondDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "secondTransactionManager")
	public DataSourceTransactionManager secondTransactionManager(@Qualifier("secondDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	// First Datasource JdbcTemplate
	@Bean(name = "secondJdbcTemplate")
	public JdbcTemplate secondTemplate(@Qualifier("secondDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "secondNameJdbcTemplate")
	public NamedParameterJdbcTemplate firstNameTemplate(@Qualifier("secondDataSource") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * 自定义SqlSessionFactory后，MyBatis就不自动添加SqlSessionFactory了，所以必须有
	 *
	 * @return .
	 * @throws Exception .
	 */
	@Primary
	@Bean(name = "secondSessionFactory")
	public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(ConfigureDataSourceSecond.MAPPER_LOCATION));

		return sessionFactoryBean.getObject();
	}
}
