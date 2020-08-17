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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "org.cwgy.stock.core.dao", sqlSessionFactoryRef = "firstSessionFactory")
public class ConfigureDataSourceFirst {

	private static final String MAPPER_LOCATION = "classpath:mapper/generatorMapper/**Mapper.xml";

	@Bean(name = "firstDataSource")
	@ConfigurationProperties(prefix = "first.datasource")
	public DataSource firstDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "firstTransactionManager")
	public DataSourceTransactionManager firstTransactionManager(@Qualifier("firstDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	// First Datasource JdbcTemplate
	@Bean(name = "firstJdbcTemplate")
	public JdbcTemplate firstTemplate(@Qualifier("firstDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	// First Datasource JdbcTemplate
	@Bean(name = "firstNameJdbcTemplate")
	public NamedParameterJdbcTemplate firstNameTemplate(@Qualifier("firstDataSource") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * 自定义SqlSessionFactory后，MyBatis就不自动添加SqlSessionFactory了，所以必须有
	 *
	 * @return .
	 * @throws Exception .
	 */
	@Bean(name = "firstSessionFactory")
	public SqlSessionFactory firstSqlSessionFactory(@Qualifier("firstDataSource") DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(ConfigureDataSourceFirst.MAPPER_LOCATION));

		return sessionFactoryBean.getObject();
	}
}
