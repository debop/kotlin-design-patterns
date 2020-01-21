package io.kommons.designpatterns.cqrs

import io.kommons.designpatterns.cqrs.commands.CommandService
import io.kommons.designpatterns.cqrs.domain.repository.AuthorRepository
import io.kommons.designpatterns.cqrs.queries.QueryService
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = [AuthorRepository::class])
@ComponentScan(basePackageClasses = [QueryService::class, CommandService::class])
class CqrsJpaConfiguration {

    @Primary
    @Bean(destroyMethod = "shutdown")
    fun dataSource() =
        EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build()
}