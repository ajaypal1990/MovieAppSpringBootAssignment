package com.springboot.assingment.MovieApplication.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

	  @Bean
	    public Docket api(){
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("io.codefountain.swagger"))
	                .paths(PathSelectors.any())
	                .build().apiInfo(apiEndPointInfo());
	    }

	    public ApiInfo apiEndPointInfo(){
	        return new ApiInfoBuilder().title("Spring Boot Rest API")
	                .description("Donor Management API")
	                .contact(new Contact("Somnath Musib", "medium.com/codefountain", "codefountain@gmail.com"))
	                .license("Apache 2.0")
	                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
	                .version("0.0.1-SNAPSHOT")
	                .build();
	    }
}
