package com.verinite.cla.config;

import java.util.List;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Value("${server.servlet.context-path}")
	private String contextPath;
	@Value("${spring.application.name:}")
	private String serviceName;

	@Bean
	public OpenAPI springShopOpenAPI() {
		OpenAPI swaggerOpenAPI = new OpenAPI()
				.info(new Info().title(serviceName.toUpperCase() + " Management APIs")
						.description("This is an API that covers the MVP functionality").version("v0.0.1"))
				.servers(List.of(new Server().url(contextPath)));
//		swaggerOpenAPI.components(new Components().addParameters("tenant-id",
//				new HeaderParameter().name("tenant-id").required(true).schema(new StringSchema())));
		
//		swaggerOpenAPI.path("/auth/**", new PathItem()
//                .get(new io.swagger.v3.oas.models.Operation()
//                        .tags(List.of("authentication-controller"))
//                        .operationId("getAuth")
//                        .parameters(List.of(
//                                new io.swagger.v3.oas.models.parameters.Parameter().in("header").name("tenant-id").$ref("#/components/parameters/tenant-id")
//                        ))
//                        .responses(new ApiResponses().addApiResponse("200", new ApiResponse()
//                                .description("OK")
//                                .content(new Content().addMediaType("*/*",
//                                        new MediaType().schema(new Schema<String>().type("string"))))
//                        ))));
		
		return swaggerOpenAPI;
	}

//	@Bean
//	public OpenApiCustomizer openApiCustomizer() {
//		return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> 
//			pathItem.readOperations().stream())
//				.forEach(operation -> {
//					System.out.print(operation);
////					operation.
//					operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/tenant-id"));
//					System.out.print(operation);
//				});
//	}
}